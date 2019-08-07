
package fortec.mscm.cert.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.cert.entity.CertificateRepository;
import fortec.mscm.cert.request.CertificateRepositoryQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 供应商资质仓库 mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface CertificateRepositoryMapper extends BaseMapper<CertificateRepository> {

    IPage<CertificateRepository> page(IPage page, @Param("request") CertificateRepositoryQueryRequest request);

    List<CertificateRepository> list(@Param("request") CertificateRepositoryQueryRequest request);

    IPage<CertificateRepository> pageForWarning(IPage page, @Param("request") CertificateRepositoryQueryRequest request);

}
    