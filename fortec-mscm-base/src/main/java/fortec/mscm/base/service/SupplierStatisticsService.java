package fortec.mscm.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import fortec.common.core.service.IBaseService;
import fortec.mscm.base.entity.SupplierStatistics;
import fortec.mscm.base.request.SupplierStatisticsQueryRequest;
import fortec.mscm.base.vo.SupplierStatisticsVO;

import java.util.List;

/**
 * @Description:
 * @Author: chen.wu
 * @CreateDate: 2019-11-12 13:41
 * Version:      2.4
 */
public interface SupplierStatisticsService extends IBaseService<SupplierStatistics> {

    IPage<SupplierStatistics> page(SupplierStatisticsQueryRequest request);

    List<SupplierStatistics> pageByNameForHospital(SupplierStatisticsQueryRequest request);
    List<SupplierStatisticsVO> list(SupplierStatisticsQueryRequest request);


    /**
     * 通过医院id获取供应商
     * @param request
     * @return
     */


    List<SupplierStatisticsVO> exportList(SupplierStatisticsQueryRequest request);
}
