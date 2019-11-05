
package fortec.mscm.base.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.handler.inter.IExcelDataHandler;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import fortec.common.core.excel.handler.AggregationDataHandler;
import fortec.common.core.excel.handler.ExcelDictHandlerImpl;
import fortec.common.core.exceptions.ImportException;
import fortec.common.core.model.ImportResult;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.utils.BeanUtils;
import fortec.mscm.base.dto.MaterialDTO;
import fortec.mscm.base.dto.MaterialSpecDTO;
import fortec.mscm.base.entity.Material;
import fortec.mscm.base.entity.MaterialSpec;
import fortec.mscm.base.excel.handler.ManufacturerDataHandler;
import fortec.mscm.base.excel.handler.MaterialCatalogDataHandler;
import fortec.mscm.base.excel.handler.MaterialDataHandler;
import fortec.mscm.base.mapper.MaterialMapper;
import fortec.mscm.base.request.MaterialQueryRequest;
import fortec.mscm.base.service.MaterialCatalogService;
import fortec.mscm.base.service.MaterialService;
import fortec.mscm.base.service.MaterialSpecService;
import fortec.mscm.base.vo.MaterialVO;
import fortec.mscm.security.utils.UserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


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

    private final MaterialSpecService materialSpecService;

    @Override
    public IPage<MaterialVO> page(MaterialQueryRequest request) {
        request.setSupplierId(UserUtils.getSupplierId());
        return this.baseMapper.page(request.getPage(), request);
    }

    @Override
    public List<MaterialVO> list(MaterialQueryRequest request) {
        List<MaterialVO> list = this.baseMapper.list(request);
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


   /* public ImportResult excelImport(MultipartFile file) {
        ImportResult result = new ImportResult();

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
    }*/

    public ImportResult excelImport(MultipartFile file) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ImportParams params = new ImportParams();
        params.setDictHandler(ExcelDictHandlerImpl.getInstance());
        params.setTitleRows(1);
        params.setHeadRows(2);
        params.setNeedVerify(true);
        params.setDataHandler(new AggregationDataHandler(Lists.newArrayList(new IExcelDataHandler[]{new ManufacturerDataHandler(), new MaterialCatalogDataHandler(), new MaterialDataHandler()})));

        try {
            ExcelImportResult importResult = ExcelImportUtil.importExcelMore(file.getInputStream(), MaterialDTO.class, params);
            List<MaterialDTO> list = importResult.getList();
            List<Material> entityList = Lists.newArrayListWithExpectedSize(list.size());
            BeanUtils.copyProperties(list, entityList, Material.class);

            for (Material material : entityList) {
                material.setSupplierId(UserUtils.getSupplierId());
                material.setSupplierName(UserUtils.getSupplier().getName());
            }

            if (!entityList.isEmpty()) {
                this.saveOrUpdateBatch(entityList);
                ArrayList<MaterialSpecDTO> specDTOS = new ArrayList<>();
                for (MaterialDTO materialDTO : list) {
                    for (MaterialSpecDTO spec : materialDTO.getSpecs()) {
                        spec.setMaterialId(materialDTO.getId());
                        specDTOS.add(spec);
                    }
                }

                List<MaterialSpec> specs = Lists.newArrayListWithCapacity(specDTOS.size());
                BeanUtils.copyProperties(specDTOS, specs, MaterialSpec.class);
                if (!specs.isEmpty()){
                    materialSpecService.saveOrUpdateBatch(specs);
                }

            }

            stopWatch.stop();
            return ImportResult.build(stopWatch.getTotalTimeSeconds(), importResult);
        } catch (Exception var9) {
            log.error("商品数据导入失败", var9);
            throw new ImportException("导入失败");
        }
    }

}
    