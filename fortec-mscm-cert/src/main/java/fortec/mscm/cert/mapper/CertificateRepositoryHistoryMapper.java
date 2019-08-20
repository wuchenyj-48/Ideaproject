
package fortec.mscm.cert.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.cert.entity.CertificateRepositoryHistory;
import fortec.mscm.cert.request.CertificateRepositoryHistoryQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 资质历史版本 mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface CertificateRepositoryHistoryMapper extends BaseMapper<CertificateRepositoryHistory> {

    IPage<CertificateRepositoryHistory> page(IPage page, @Param("request") CertificateRepositoryHistoryQueryRequest request);
}
    