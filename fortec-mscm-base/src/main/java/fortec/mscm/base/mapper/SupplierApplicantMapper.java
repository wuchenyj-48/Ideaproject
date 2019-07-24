
package fortec.mscm.base.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.SupplierApplicant;
import fortec.mscm.base.request.SupplierApplicantQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* SupplierApplicant mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface SupplierApplicantMapper extends BaseMapper<SupplierApplicant> {
    IPage<SupplierApplicant> page(IPage page, @Param("request") SupplierApplicantQueryRequest request);

    IPage<SupplierApplicant> pageAudit(IPage page, @Param("request") SupplierApplicantQueryRequest request);

}
    