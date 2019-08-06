
package fortec.mscm.cert.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.cert.entity.CertificateHospitalBusiness;
import fortec.mscm.cert.request.CertificateHospitalBusinessQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 院方业务资质定义 mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface CertificateHospitalBusinessMapper extends BaseMapper<CertificateHospitalBusiness> {

    IPage<CertificateHospitalBusiness> page(IPage page, @Param("request") CertificateHospitalBusinessQueryRequest request);

}
    