
package fortec.mscm.cert.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.cert.entity.Certificate;
import fortec.mscm.cert.request.CertificateQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 预定义资质 mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface CertificateMapper extends BaseMapper<Certificate> {
    IPage<Certificate> pageByKeywords(IPage page, @Param("request") CertificateQueryRequest request);
}
    