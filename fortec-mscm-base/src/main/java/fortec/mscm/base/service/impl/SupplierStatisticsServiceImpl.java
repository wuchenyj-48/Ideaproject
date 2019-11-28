package fortec.mscm.base.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.common.feign.clients.OfficeClient;
import fortec.common.feign.clients.UserClient;
import fortec.mscm.base.entity.SupplierStatistics;
import fortec.mscm.base.mapper.SupplierStatisticsMapper;
import fortec.mscm.base.request.SupplierStatisticsQueryRequest;
import fortec.mscm.base.service.SupplierStatisticsService;
import fortec.mscm.base.vo.SupplierStatisticsVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * @Description:
 * @Author: chen.wu
 * @CreateDate: 2019-11-12 11:44
 * Version:      2.4
 */
@AllArgsConstructor
@Slf4j
@Service
public class SupplierStatisticsServiceImpl extends BaseServiceImpl<SupplierStatisticsMapper, SupplierStatistics> implements SupplierStatisticsService {
   
    private final OfficeClient officeClient;

    private final UserClient userClient;




    @Override
    public IPage<SupplierStatistics> page(SupplierStatisticsQueryRequest request) {
        IPage page = this.baseMapper.page(request.getPage(), request);
        return page;
    }


    @Override
    public List<SupplierStatistics> pageByNameForHospital(SupplierStatisticsQueryRequest request) {
        List<SupplierStatistics> pageByNameForHospital =this.baseMapper.pageByNameForHospital(Wrappers.<SupplierStatistics>query()
                .like(request.getHospitalName()!=null,"hospital_name",request.getHospitalName())
                .orderByDesc("gmt_modified")
        );
        return pageByNameForHospital;

    }

    @Override
    public List<SupplierStatisticsVO> exportList(SupplierStatisticsQueryRequest request) {
        List<SupplierStatisticsVO> list=baseMapper.exportList(request);
        return list;
    }

    @Override
    public List<SupplierStatisticsVO> list(SupplierStatisticsQueryRequest request) {
        List<SupplierStatisticsVO> list = this.baseMapper.list(Wrappers.<SupplierStatistics>query()
                .orderByDesc("gmt_modified")
        );
        return list;
    }



//    @Transactional(rollbackFor = Exception.class)
//    @LcnTransaction
//    @Override
//    public ImportResult excelImport(MultipartFile file){
//        ImportResult result = new ImportResult();
//        try{
//            StopWatch stopwatch = new StopWatch();
//            stopwatch.start();
//            final Queue<SupplierStatistics> insertDatas=new ConcurrentLinkedQueue();
//            final Queue<SupplierStatistics> updateDatas=new ConcurrentLinkedQueue();
//
//            List<SupplierStatisticsVO> list = this.baseMapper.list(Wrappers.<SupplierStatistics>query().select("company_code,name,phone"));
//
//
//            HashSet<String> codeSet = Sets.newHashSetWithExpectedSize(list.size());
//            HashSet<String> mobileSet = Sets.newHashSetWithExpectedSize(list.size());
//            HashSet<String> nameSet = Sets.newHashSetWithExpectedSize(list.size());
//
//            list.stream().forEach(o -> {
//                codeSet.add(o.getCode());
//                mobileSet.add(o.getMobile());
//                nameSet.add(o.getName());
//            });
//
//            DefaultSaveHandler<SupplierStatisticsDTO> saveHandler = new DefaultSaveHandler<SupplierStatisticsDTO>() {
//
//                public void doSave(int currentIndex, SupplierStatisticsDTO dto) throws BusinessException {
//                    SupplierStatistics entity = new SupplierStatistics();
//                    BeanUtils.copyProperties(dto, entity);
//
//                    //编号是否已存在
//                    if (codeSet.contains(entity.getCode())) {
//                        throw new BusinessException("第" + currentIndex + "行统一社会信用代码重复，请检查");
//                    }
//
//                    //供应商名称是否唯一
//                    if (nameSet.contains(entity.getName())) {
//                        throw new BusinessException("第" + currentIndex + "行供应商名称重复，请检查");
//                    }
//
//                    //联系人手机号是否平台唯一
//                    if (mobileSet.contains(entity.getMobile())) {
//                        throw new BusinessException("第" + currentIndex + "行移动电话重复，请检查");
//                    }
//
//
//
//
//
//
//                    // 添加用户,供应商编号作为主账户
//                    String supplierCode = SerialUtils.generateCode(SerialRuleConsts.BASE_SUPPLIER_CODE);
//
//                    UserInfoDTO infoDTO = new UserInfoDTO();
//                    infoDTO.setOfficeId(officeVO.getId())
//                            .setLoginKey(supplierCode)
//                            .setNickname(entity.getName())
//                            .setEmail(entity.getEmail())
//                            .setMobile(entity.getMobile())
//                            .setRoles(new String[]{"suuplier_manager"})
//                            .setRemark("供应商" + entity.getName() + "主账号");
//                    UserInfoVO vo = userClient.addUser(infoDTO);
//                    if (vo == null) {
//                        throw new BusinessException("主用户添加失败");
//                    }
//
//                    entity.setOfficeId(officeVO.getId());
//
//                    insertDatas.add(entity);
//
//                }
//            };
//            BatchSaveTask<Supplier> task = new BatchSaveTask(file, SupplierDTO.class, saveHandler, 50);
//            this.forkJoinPool.invoke(task);
//            if (!insertDatas.isEmpty()) {
//                super.saveOrUpdateBatch(new ArrayList(insertDatas), 500);
//            }
//
//            if (!updateDatas.isEmpty()) {
//                this.updateBatchById(new ArrayList(updateDatas), 500);
//            }
//
//            stopWatch.stop();
//            result.setTotalCount(task.getList().size()).setSuccessCount(saveHandler.getSuccessCount()).setFailCount(saveHandler.getFailureCount()).setElapsedSecond(stopWatch.getTotalTimeSeconds()).setMessages(saveHandler.getMessageList());
//            log.info("本次导入共处理{}条数据，成功：{}条,失败：{},耗时：{}秒", new Object[]{result.getTotalCount(), result.getSuccessCount(), result.getFailCount(), result.getElapsedSecond()});
//            return result;
//        } catch (Exception var8) {
//            log.error("数据导入失败", var8);
//            throw new BusinessException("导入失败");
//        }
//    }



}
