
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;
import fortec.mscm.base.entity.MaterialApplicantItem;
import fortec.mscm.base.mapper.MaterialApplicantItemMapper;
import fortec.mscm.base.request.MaterialApplicantItemQueryRequest;
import fortec.mscm.base.service.MaterialApplicantItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MaterialApplicantItem service 实现
 *
 * @author chenchen
 * @version 1.0
 */
@AllArgsConstructor
@Slf4j
@Service
public class MaterialApplicantItemServiceImpl extends BaseServiceImpl<MaterialApplicantItemMapper, MaterialApplicantItem> implements MaterialApplicantItemService {


    @Override
    public List<MaterialApplicantItem> list(MaterialApplicantItemQueryRequest request) {

        return this.baseMapper.list(request);
    }

    @Override
    public IPage<MaterialApplicantItem> page(MaterialApplicantItemQueryRequest request) {
        IPage page = this.page(request.getPage(), Wrappers.<MaterialApplicantItem>query()
                .orderByDesc("gmt_modified")
        );
        return page;
    }
}
    