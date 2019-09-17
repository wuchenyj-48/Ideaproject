package fortec.mscm.order.config;

import com.codingapi.txlcn.common.util.Transactions;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.aspect.interceptor.TxLcnInterceptor;
import com.codingapi.txlcn.tc.aspect.weave.DTXLogicWeaver;
import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import com.google.common.collect.Lists;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Description: 事务管理
 * @Author: yuntao.zhou
 * @CreateDate: 2019/7/19 9:41
 * @Version: 1.0
 */
@Configuration
@EnableTransactionManagement
@EnableDistributedTransaction
public class TransactionConfig {


    /**
     * 本地事务配置
     * @param transactionManager
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public TransactionInterceptor transactionInterceptor(PlatformTransactionManager transactionManager) {
        Properties properties = new Properties();
        properties.setProperty("*", "PROPAGATION_REQUIRED,-Throwable");
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(transactionManager);
        transactionInterceptor.setTransactionAttributes(properties);
        return transactionInterceptor;
    }

    /**
     * 分布式事务配置 设置为LCN模式
     * @param dtxLogicWeaver
     * @return
     */
    @ConditionalOnBean(DTXLogicWeaver.class)
    @Bean
    public TxLcnInterceptor txLcnInterceptor(DTXLogicWeaver dtxLogicWeaver) {
        TxLcnInterceptor txLcnInterceptor = new TxLcnInterceptor(dtxLogicWeaver);
        Properties properties = new Properties();
        properties.setProperty(Transactions.DTX_TYPE,Transactions.LCN);
        properties.setProperty(Transactions.DTX_PROPAGATION, "REQUIRED");
        txLcnInterceptor.setTransactionAttributes(properties);
        return txLcnInterceptor;
    }

    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator(ApplicationContext applicationContext) {

        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Service.class);
        List<String> beanNames = Lists.newArrayList();
        for (String beanName : beansWithAnnotation.keySet()) {
            Object o = beansWithAnnotation.get(beanName);
            Method[] declaredMethods = o.getClass().getDeclaredMethods();

            for (Method declaredMethod : declaredMethods) {
                if(declaredMethod.getDeclaredAnnotation(LcnTransaction.class) != null){
                    beanNames.add(o.getClass().getName());
                }
            }
        }
        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
        //需要调整优先级，分布式事务在前，本地事务在后。
        beanNameAutoProxyCreator.setInterceptorNames("txLcnInterceptor","transactionInterceptor");

        if(beanNames.size() != 0) {
            beanNameAutoProxyCreator.setBeanNames(beanNames.toArray(new String[0]));
        }
        return beanNameAutoProxyCreator;
    }
}
