package fortec.mscm.cert.request;

import fortec.common.core.model.PageRequest;
import lombok.Data;

/**
 * @Description: 全景视图查询请求
 * @Author: yuntao.zhou
 * @CreateDate: 2019/8/6 16:31
 * @Version: 1.0
 */
@Data
public class OverallViewerQueryRequest  extends PageRequest {

    private String hospitalId;

    private String businessTypeCode;


    private String targetDescribeId;

}
