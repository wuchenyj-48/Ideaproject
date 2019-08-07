package fortec.mscm.feign.configuration;

import com.codingapi.txlcn.tracing.http.FeignTracingTransmitter;
import com.google.common.collect.Lists;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import fortec.common.feign.annotation.EnableDefaultFeignConfig;
import fortec.common.feign.interceptor.TokenInterceptor;
import fortec.mscm.core.consts.ServiceNames;
import fortec.mscm.feign.clients.HospitalClient;
import fortec.mscm.feign.clients.SupplierClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;

/**
 * @Description: <p>手动创建 feign client，官方链接：https://cloud.spring.io/spring-cloud-static/Finchley.RELEASE/single/spring-cloud.html#_creating_feign_clients_manually</p>
 * @Author: yuntao.zhou
 * @CreateDate: 2019/7/24 15:31
 * @Version: 1.0
 */
@EnableDefaultFeignConfig
@Configuration
@Import(FeignClientsConfiguration.class)
public class MscmClientConfiguration {

    private SupplierClient supplierClient;

    private HospitalClient hospitalClient;

    public MscmClientConfiguration(Decoder decoder, Encoder encoder, Client client, Contract contract,
                                   TokenInterceptor tokenInterceptor, @Autowired(required = false) FeignTracingTransmitter feignTracingTransmitter) {

        ArrayList<RequestInterceptor> requestInterceptors = null;
        if(feignTracingTransmitter == null){
            requestInterceptors =  Lists.newArrayList(tokenInterceptor);
        }else{
            requestInterceptors =  Lists.newArrayList(tokenInterceptor, feignTracingTransmitter);
        }

        this.supplierClient = Feign.builder().client(client)
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .requestInterceptors(requestInterceptors)
                .target(SupplierClient.class, "http://" + ServiceNames.BASE);

    }



    @Bean
    public SupplierClient supplierClient() {
        return this.supplierClient;
    }

    @Bean
    public HospitalClient hospitalClient() {
        return this.hospitalClient;
    }


}
