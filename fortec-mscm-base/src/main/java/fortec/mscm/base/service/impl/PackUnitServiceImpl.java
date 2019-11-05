
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.model.ImportResult;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.thread.BatchSaveTask;
import fortec.common.core.thread.DefaultSaveHandler;
import fortec.common.core.utils.StringUtils;
import fortec.mscm.base.dto.PackUnitDTO;
import fortec.mscm.base.entity.PackUnit;
import fortec.mscm.base.mapper.PackUnitMapper;
import fortec.mscm.base.request.PackUnitQueryRequest;
import fortec.mscm.base.service.PackUnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * 包装单位 service 实现
 *
 * @author yuntao.zhou
 * @version 1.0
 */
@Slf4j
@Service
public class PackUnitServiceImpl extends BaseServiceImpl<PackUnitMapper, PackUnit> implements PackUnitService {


    @Override
    public IPage<PackUnit> page(PackUnitQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<PackUnit>query().like(StringUtils.isNotBlank(request.getId()), "id", request.getId())
                .like(StringUtils.isNotBlank(request.getName()), "name", request.getName())
                .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public List<PackUnit> list(PackUnitQueryRequest request) {
        List<PackUnit> list = this.list(Wrappers.<PackUnit>query()
                .orderByDesc("gmt_modified")
        );
        return list;
    }


    public ImportResult batchImport(MultipartFile file) {
        ImportResult result = new ImportResult();

        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            final Queue<PackUnit> insertDatas = new ConcurrentLinkedQueue();
            final Queue<PackUnit> updateDatas = new ConcurrentLinkedQueue();
            DefaultSaveHandler<PackUnitDTO> saveHandler = new DefaultSaveHandler<PackUnitDTO>() {
                @Override
                public void doSave(int currentIndex, PackUnitDTO entity) throws BusinessException {
                    PackUnit packUnit = new PackUnit();
                    BeanUtils.copyProperties(entity, packUnit);
                    insertDatas.add(packUnit);
                }
            };
            BatchSaveTask<PackUnitDTO> task = new BatchSaveTask(file, PackUnitDTO.class, saveHandler, 50);

            this.forkJoinPool.invoke(task);
            if (!insertDatas.isEmpty()) {
                super.saveOrUpdateBatch(new ArrayList(insertDatas), 500);
            }
            if (!updateDatas.isEmpty()) {
                this.updateBatchById(new ArrayList(updateDatas), 500);
            }
            stopWatch.stop();
            result.setTotalCount(task.getList().size()).setSuccessCount(saveHandler.getSuccessCount()).setFailCount(saveHandler.getFailureCount()).setElapsedSecond(stopWatch.getTotalTimeSeconds()).setMessages(saveHandler.getMessageList());
            log.info("本次导入共处理{}条数据，成功：{}条,失败：{},耗时：{}秒", new Object[]{result.getTotalCount(), result.getSuccessCount(), result.getFailCount(), result.getElapsedSecond()});
            return result;
        } catch (Exception var8) {
            log.error("数据导入失败", var8);
            throw new BusinessException("导入失败");
        }
    }
}
    