package fortec.mscm.base.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.mscm.base.entity.SupplierStatistics;
import fortec.mscm.base.request.SupplierStatisticsQueryRequest;
import fortec.mscm.base.vo.SupplierStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @Author: chen.wu
 * @CreateDate: 2019-11-12 13:35
 * Version:      2.4
 */
@Mapper
public interface SupplierStatisticsMapper extends BaseMapper<SupplierStatistics> {
    IPage<SupplierStatistics> page(IPage page, @Param("request") SupplierStatisticsQueryRequest request);


    /**
     * 根据医院获取供应商
     * @param request
     * @return
     */
    List<SupplierStatistics> pageByNameForHospital(@Param("request") QueryWrapper<SupplierStatistics> request);

    List<SupplierStatisticsVO> exportList(@Param("request") SupplierStatisticsQueryRequest request);

    List<SupplierStatisticsVO> list(@Param("request") QueryWrapper<SupplierStatistics> request);
}
