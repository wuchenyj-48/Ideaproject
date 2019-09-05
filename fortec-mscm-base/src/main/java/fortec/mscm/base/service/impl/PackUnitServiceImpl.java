
package fortec.mscm.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import fortec.common.core.service.BaseServiceImpl;

import fortec.common.core.utils.StringUtils;
import fortec.mscm.base.entity.PackUnit;
import fortec.mscm.base.mapper.PackUnitMapper;
import fortec.mscm.base.request.PackUnitQueryRequest;
import fortec.mscm.base.service.PackUnitService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


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
}
    