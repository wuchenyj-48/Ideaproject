
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.model.BatchImportResult;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.thread.BatchSaveTask;
import fortec.common.core.thread.DefaultSaveHandler;
import fortec.mscm.base.dto.MaterialDTO;
import fortec.mscm.base.entity.Material;
import fortec.mscm.base.entity.MaterialCatalog;
import fortec.mscm.base.mapper.MaterialMapper;
import fortec.mscm.base.request.MaterialQueryRequest;
import fortec.mscm.base.service.MaterialCatalogService;
import fortec.mscm.base.service.MaterialService;
import fortec.mscm.base.vo.MaterialVO;
import fortec.mscm.security.utils.UserUtils;
import lombok.AllArgsConstructor;
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
* 商品 service 实现
*
* @author chenchen
* @version 1.0
*/
@AllArgsConstructor
@Slf4j
@Service
public class MaterialServiceImpl extends BaseServiceImpl<MaterialMapper, Material> implements MaterialService {

    private final MaterialCatalogService materialCatalogService;

    @Override
    public IPage<Material> page(MaterialQueryRequest request) {
        request.setSupplierId(UserUtils.getSupplierId());
        return this.baseMapper.page(request.getPage(), request);
    }

    @Override
    public List<Material> list(MaterialQueryRequest request) {
        List<Material> list = this.list(Wrappers.<Material>query()
                .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public List<MaterialVO> importList(MaterialQueryRequest request) {
        request.setSupplierId(UserUtils.getSupplierId());
        return baseMapper.importList(request);
    }

    @Override
    public List<MaterialVO> exportList(MaterialQueryRequest request) {
        request.setSupplierId(UserUtils.getSupplierId());
        return this.baseMapper.exportList(request);
    }


    @Override
    public boolean saveOrUpdate(Material entity) {
        entity.setSupplierId(UserUtils.getSupplierId());
        return super.saveOrUpdate(entity);
    }

    public BatchImportResult batchImport(MultipartFile file) {
        BatchImportResult result = new BatchImportResult();

        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            final Queue<Material> insertDatas = new ConcurrentLinkedQueue();
            final Queue<Material> updateDatas = new ConcurrentLinkedQueue();

            final String supplierId = UserUtils.getSupplierId();
            DefaultSaveHandler<MaterialDTO> saveHandler = new DefaultSaveHandler<MaterialDTO>() {
                @Override
                public void doSave(int currentIndex, MaterialDTO entity) throws BusinessException {

                    MaterialCatalog catalog = materialCatalogService.getOne(Wrappers.<MaterialCatalog>query().eq("code", entity.getCatalogCode()));

                    if (catalog == null){
                        throw new BusinessException("品类不能为空");
                    }

                    Material material = new Material();
                    BeanUtils.copyProperties(entity, material);
                    material.setSupplierId(supplierId).setCatalogId(catalog.getId()).setManufacturerId("1157127181231460353");
                    insertDatas.add(material);
                }

                @Override
                public void doValidate(int currentIndex, MaterialDTO entity) {

                }
            };
            BatchSaveTask<MaterialDTO> task = new BatchSaveTask(file, MaterialDTO.class, saveHandler, 50);

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
    