
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

    /**
     * 根据供应商获取医院
     * @param page
     * @param request
     * @return
     */
    IPage<HospitalSupplier> pageByKeywords(IPage page, @Param("request")HospitalSupplierQueryRequest request);

    /**
     * 根据医院获取供应商
     * @param page
     * @param request
     * @return
     */
    IPage<HospitalSupplier> pageByKeywordsForHospital(IPage page, @Param("request")HospitalSupplierQueryRequest request);
}
    