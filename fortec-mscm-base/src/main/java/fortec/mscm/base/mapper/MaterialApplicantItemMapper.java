
package fortec.mscm.base.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fortec.mscm.base.entity.MaterialApplicantItem;
import fortec.mscm.base.request.MaterialApplicantItemQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* MaterialApplicantItem mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface MaterialApplicantItemMapper extends BaseMapper<MaterialApplicantItem> {

    List<MaterialApplicantItem> list(@Param("request") MaterialApplicantItemQueryRequest request);

}
    