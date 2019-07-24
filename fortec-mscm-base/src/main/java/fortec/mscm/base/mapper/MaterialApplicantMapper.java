
package fortec.mscm.base.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.MaterialApplicant;
import fortec.mscm.base.request.MaterialApplicantQueryRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* MaterialApplicant mapper对象
*
* @author chenchen
* @version 1.0
*/
@Mapper
public interface MaterialApplicantMapper extends BaseMapper<MaterialApplicant> {
    IPage<MaterialApplicant> page(IPage page, @Param("request") MaterialApplicantQueryRequest request);

}
    