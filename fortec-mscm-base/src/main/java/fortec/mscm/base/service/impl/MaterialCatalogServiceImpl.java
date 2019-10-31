
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.model.BatchImportResult;
import fortec.common.core.model.TreeModel;
import fortec.common.core.model.TreeNode;
import fortec.common.core.service.TreeServiceImpl;
import fortec.common.core.utils.BeanValidators;
import fortec.common.core.utils.CacheUtils;
import fortec.common.core.utils.StringUtils;
import fortec.common.core.utils.excel.ImportExcel;
import fortec.mscm.base.dto.MaterialCatalogDTO;
import fortec.mscm.base.entity.MaterialCatalog;
import fortec.mscm.base.mapper.MaterialCatalogMapper;
import fortec.mscm.base.request.MaterialCatalogQueryRequest;
import fortec.mscm.base.service.MaterialCatalogService;
import fortec.mscm.base.vo.MaterialCatalogVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.text.MessageFormat;
import java.util.List;


/**
 * 商品品类 service 实现
 *
 * @author chenchen
 * @version 1.0
 */
@Slf4j
@Service
public class MaterialCatalogServiceImpl extends TreeServiceImpl<MaterialCatalogMapper, MaterialCatalog> implements MaterialCatalogService {

    @Override
    public boolean saveCascadeById(MaterialCatalog entity) {
        List<MaterialCatalog> codeList = this.list(Wrappers.<MaterialCatalog>query().eq("code", entity.getCode()));
        if (codeList.size() > 0){
            throw new BusinessException("品类代码不能重复");
        }
        return super.saveCascadeById(entity);
    }

    @Override
    public boolean deleteById(String id) {
        //是否有下属商品
        int count = this.count(Wrappers.<MaterialCatalog>query().eq("parent_id", id));
        if (count > 0) {
            throw new BusinessException("已经有下属商品的品类不能删除");
        }
        return this.removeCascadeById(id);
    }

    @Override
    public IPage pageForTree(MaterialCatalogQueryRequest request) {
        return this.baseMapper.pageForTree(request.getPage(), request);
    }

    @Override
    public List<MaterialCatalog> list(MaterialCatalogQueryRequest request) {
        List<MaterialCatalog> list = this.list(Wrappers.<MaterialCatalog>query()
                .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public List<MaterialCatalogVO> exportList(MaterialCatalogQueryRequest request) {
        List<MaterialCatalogVO> list = baseMapper.exportList(request);
        return list;
    }

    public BatchImportResult batchImport(MultipartFile file) {
        BatchImportResult result = new BatchImportResult();
        try {

            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            // 读取Excel数据
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<MaterialCatalogDTO> list = ei.getDataList(MaterialCatalogDTO.class);
            if (CollectionUtils.isEmpty(list)) {
                return result;
            }

            List<MaterialCatalogDTO> filtedList = Lists.newArrayList();

            List<String> messageList = Lists.newArrayList();

            // 初步数据校验
            int currentIndex = 2;
            for (MaterialCatalogDTO materialCatalogDTO : list) {
                try {
                    BeanValidators.validateWithException(validator, materialCatalogDTO);
                    filtedList.add(materialCatalogDTO);
                } catch (ConstraintViolationException e) {
                    messageList.add(MessageFormat.format("第 [{0}] 行数据校验错误：{1}", currentIndex++, StringUtils.join(BeanValidators.extractMessage(e.getConstraintViolations()))));
                }
            }


            List<MaterialCatalog> catalogs = this.list();
            for (MaterialCatalog catalog : catalogs) {
                CacheUtils.hashPut("data:base:materialCatalog:code", catalog.getCode(), catalog);
            }

            TreeModel<MaterialCatalogDTO> treeModel = new TreeModel<MaterialCatalogDTO>(filtedList, "code", "name", "parentCode", "parentCode", null) {
                @Override
                protected void addExtraProperties(TreeNode node, MaterialCatalogDTO entity) {
                    node.addProperty("dto", entity);
                }
            };
            int successCount = 0;
            List<TreeNode> nodeList = treeModel.asList();
            for (TreeNode node : nodeList) {
                try {
                    addCatalogByNode(node);
                    successCount++;
                } catch (ConstraintViolationException e) {
                    messageList.add(MessageFormat.format("品类编码[{0}],数据校验错误", node.getId(), StringUtils.join(BeanValidators.extractMessage(e.getConstraintViolations()))));
                }
            }

            stopWatch.stop();

            result.setTotalCount(list.size())
                    .setSuccessCount(successCount)
                    .setFailCount(messageList.size())
                    .setElapsedSecond(stopWatch.getTotalTimeSeconds())
                    .setMessages(messageList);

            log.info("本次导入共处理{}条数据，成功：{}条,失败：{},耗时：{}秒",
                    result.getTotalCount(), result.getSuccessCount(), result.getFailCount(), result.getElapsedSecond());
        } catch (Exception e) {
            log.error("品类导入失败", e);
            throw new BusinessException("品类导入失败");
        }

        return result;
    }


    private void addCatalogByNode(TreeNode node) {

        MaterialCatalogDTO dto = (MaterialCatalogDTO) node.getProperties().get("dto");

        MaterialCatalog entity = CacheUtils.hashGet("data:base:materialCatalog:code", dto.getCode());
        if(entity == null){
            entity = new MaterialCatalog();
        }
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setMaterialTypeCode(dto.getMaterialTypeCode());
        entity.setParentName(dto.getParentName());
        entity.setFullName(dto.getFullName());
        String parentCode = dto.getParentCode();

        if(StringUtils.isNotBlank(parentCode)){
            MaterialCatalog parent = CacheUtils.hashGet("data:base:materialCatalog:code", parentCode);
            if (parent == null) {
                synchronized (MaterialCatalogService.class) {
                    parent = baseMapper.selectOne(Wrappers.<MaterialCatalog>query().eq("code", parentCode));
                    CacheUtils.hashPut("data:base:materialCatalog:code", dto.getCode(), parent);
                }
            }

            entity.setParentId(parent == null ? "0" : parent.getId());
            entity.setParentIds(parent == null ? ",0," : parent.getParentIds() + parent.getId() + ",");
        }

        /**
         * 通过缓存获取 子级机构的数量，避免发送大量SQL
         */
        String parentId = StringUtils.defaultIfBlank(entity.getParentId(), "0");
        Integer childCount = CacheUtils.hashGet("data:base:materialCatalog:childSort", parentId);

        if (childCount == null) {
            synchronized (MaterialCatalogService.class) {
                childCount = this.count(Wrappers.<MaterialCatalog>query().eq("parent_id", parentId));
                childCount = childCount == null ? 0 : childCount;
            }
        }
        entity.setSort((childCount + 1L));
//        entity.setSorts(parent == null ? String.valueOf(entity.getSort()) : parent.getSorts() + "_" + entity.getSort());
        CacheUtils.hashPut("data:base:materialCatalog:childSort", parentId,childCount + 10);

        BeanValidators.validateWithException(validator, entity);
        this.saveOrUpdate(entity);

        List<TreeNode> children = node.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            for (TreeNode child : children) {
                addCatalogByNode(child);
            }
        }

    }

}
    