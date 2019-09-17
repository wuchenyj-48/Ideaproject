
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.google.common.collect.Sets;
import fortec.common.core.exceptions.BusinessException;
import fortec.common.core.model.BatchImportResult;
import fortec.common.core.serial.SerialUtils;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.core.thread.BatchSaveTask;
import fortec.common.core.thread.DefaultSaveHandler;
import fortec.common.core.utils.StringUtils;
import fortec.common.feign.clients.OfficeClient;
import fortec.common.feign.clients.UserClient;
import fortec.common.upms.feign.dto.OfficeDTO;
import fortec.common.upms.feign.dto.UserInfoDTO;
import fortec.common.upms.feign.vo.OfficeVO;
import fortec.common.upms.feign.vo.UserInfoVO;
import fortec.mscm.base.dto.SupplierDTO;
import fortec.mscm.base.entity.Supplier;
import fortec.mscm.base.mapper.SupplierMapper;
import fortec.mscm.base.request.SupplierQueryRequest;
import fortec.mscm.base.service.SupplierService;
import fortec.mscm.core.consts.SerialRuleConsts;
import fortec.mscm.security.utils.UserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * 供应商 service 实现
 *
 * @author chenchen
 * @version 1.0
 */
@AllArgsConstructor
@Slf4j
@Service
public class SupplierServiceImpl extends BaseServiceImpl<SupplierMapper, Supplier> implements SupplierService {

    private final OfficeClient officeClient;

    private final UserClient userClient;

    @Override
    public Supplier findByOfficeId(String officeId) {
        return this.getOne(Wrappers.<Supplier>query().eq("office_id", officeId));
    }

    @Override
    public IPage<Supplier> page(SupplierQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<Supplier>query()
                .like(StringUtils.isNotBlank(request.getCompanyCode()), "company_code", request.getCompanyCode())
                .like(StringUtils.isNotBlank(request.getName()), "name", request.getName())
                .eq(request.getIsDrug() != null, "is_drug", request.getIsDrug())
                .eq(request.getIsConsumable() != null, "is_consumable", request.getIsConsumable())
                .eq(request.getIsReagent() != null, "is_reagent", request.getIsReagent())
                .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public IPage<Supplier> pageForSupplier(SupplierQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<Supplier>query()
                .eq(StringUtils.isNotBlank(UserUtils.getSupplierId()), "id", UserUtils.getSupplierId())
                .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Override
    public List<Supplier> list(SupplierQueryRequest request) {
        List<Supplier> list = this.list(Wrappers.<Supplier>query()
                .orderByDesc("gmt_modified")
        );
        return list;
    }

    @Override
    public IPage<Supplier> pageByKeywords(SupplierQueryRequest request, String keywords) {
        IPage page = this.page(request.getPage(), Wrappers.<Supplier>query()
                .like(StringUtils.isNotBlank(keywords), "company_code", keywords)
                .or()
                .like(StringUtils.isNotBlank(keywords), "code", keywords)
                .or()
                .like(StringUtils.isNotBlank(keywords), "name", keywords)
                .orderByDesc("gmt_modified")
        );
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @LcnTransaction
    @Override
    public BatchImportResult batchImport(MultipartFile file) {
        BatchImportResult result = new BatchImportResult();

        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            final Queue<Supplier> insertDatas = new ConcurrentLinkedQueue();
            final Queue<Supplier> updateDatas = new ConcurrentLinkedQueue();

            List<Supplier> suppliers = this.list(Wrappers.<Supplier>query().select("company_code,name,phone"));

            HashSet<String> companyCodeSet = Sets.newHashSetWithExpectedSize(suppliers.size());
            HashSet<String> phoneSet = Sets.newHashSetWithExpectedSize(suppliers.size());
            HashSet<String> nameSet = Sets.newHashSetWithExpectedSize(suppliers.size());

            suppliers.stream().forEach(o -> {
                companyCodeSet.add(o.getCompanyCode());
                phoneSet.add(o.getPhone());
                nameSet.add(o.getName());
            });

            DefaultSaveHandler<SupplierDTO> saveHandler = new DefaultSaveHandler<SupplierDTO>() {

                public void doSave(int currentIndex, SupplierDTO dto) throws BusinessException {
                    Supplier entity = new Supplier();
                    BeanUtils.copyProperties(dto, entity);

                    //统一社会信用代码是否已存在
                    if (companyCodeSet.contains(entity.getCompanyCode())) {
                        throw new BusinessException("第" + currentIndex + "行统一社会信用代码重复，请检查");
                    }

                    //供应商名称是否唯一
                    if (nameSet.contains(entity.getName())) {
                        throw new BusinessException("第" + currentIndex + "行供应商名称重复，请检查");
                    }

                    //联系人手机号是否平台唯一
                    if (phoneSet.contains(entity.getPhone())) {
                        throw new BusinessException("第" + currentIndex + "行电话重复，请检查");
                    }

                    //至少选择一个类型供应商
                    if ((entity.getIsConsumable() | entity.getIsDrug() | entity.getIsReagent()) == 0) {
                        throw new BusinessException("药品/耗材/试剂供应商 至少选中一个");
                    }

                    // 添加机构
                    OfficeDTO officeDTO = new OfficeDTO();
                    officeDTO.setCode(entity.getCompanyCode())
                            .setName(entity.getName());
                    OfficeVO officeVO = officeClient.addForSupplier(officeDTO);
                    if (officeVO == null) {
                        throw new BusinessException("机构添加失败");
                    }


                    // 添加用户,供应商编号作为主账户
                    String supplierCode = SerialUtils.generateCode(SerialRuleConsts.BASE_SUPPLIER_CODE);

                    UserInfoDTO infoDTO = new UserInfoDTO();
                    infoDTO.setOfficeId(officeVO.getId())
                            .setLoginKey(supplierCode)
                            .setNickname(entity.getName())
                            .setEmail(entity.getEmail())
                            .setMobile(entity.getMobile())
                            .setRemark("供应商" + entity.getName() + "主账号");
                    UserInfoVO vo = userClient.addUser(infoDTO);
                    if (vo == null) {
                        throw new BusinessException("主用户添加失败");
                    }

                    entity.setOfficeId(officeVO.getId());

                    insertDatas.add(entity);

                }
            };
            BatchSaveTask<Supplier> task = new BatchSaveTask(file, SupplierDTO.class, saveHandler, 50);
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
    