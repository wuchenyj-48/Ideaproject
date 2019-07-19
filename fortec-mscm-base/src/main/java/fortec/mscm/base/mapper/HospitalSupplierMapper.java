
package fortec.mscm.base.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.HospitalSupplier;
import fortec.mscm.base.request.HospitalSupplierQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* HospitalSupplier mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface HospitalSupplierMapper extends BaseMapper<HospitalSupplier> {
    IPage<HospitalSupplier> page(IPage page, @Param("request") HospitalSupplierQueryRequest request);

    IPage<HospitalSupplier> pageByKeywords(IPage page, @Param("request")HospitalSupplierQueryRequest request);

}
    