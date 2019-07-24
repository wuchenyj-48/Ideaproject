
package fortec.mscm.base.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.HospitalMaterial;
import fortec.mscm.base.request.HospitalMaterialQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* HospitalMaterial mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface HospitalMaterialMapper extends BaseMapper<HospitalMaterial> {

    IPage<HospitalMaterial> page(IPage page, @Param("request") HospitalMaterialQueryRequest request);

}
    