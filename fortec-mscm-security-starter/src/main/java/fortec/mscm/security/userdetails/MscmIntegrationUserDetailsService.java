package fortec.mscm.security.userdetails;

import fortec.common.core.userdetails.OAuthUser;
import fortec.common.security.userdetails.IntegrationUserDetailsService;
import fortec.common.upms.feign.vo.UserInfoVO;
import fortec.mscm.base.feign.vo.HospitalVO;
import fortec.mscm.base.feign.vo.SupplierVO;
import fortec.mscm.feign.clients.HospitalClient;
import fortec.mscm.feign.clients.SupplierClient;

/**
 * @Description:
 * @Author: yuntao.zhou
 * @CreateDate: 2019/8/19 15:10
 * @Version: 1.0
 */
public class MscmIntegrationUserDetailsService extends IntegrationUserDetailsService {

    private SupplierClient supplierClient;

    private HospitalClient hospitalClient;

    public MscmIntegrationUserDetailsService( SupplierClient supplierClient, HospitalClient hospitalClient) {
        this.supplierClient = supplierClient;
        this.hospitalClient = hospitalClient;
    }

    @Override
    protected OAuthUser buildOauthUser(UserInfoVO vo) {
        OAuthUser authUser = super.buildOauthUser(vo);

        SupplierVO supplier = supplierClient.findByOfficeId(authUser.getOfficeId());

        if (supplier != null) {
            return new ExtOAuthUser(authUser, supplier, null);
        }
        HospitalVO hospital = hospitalClient.findByOfficeId(authUser.getOfficeId());
        return new ExtOAuthUser(authUser, null, hospital);

    }
}
