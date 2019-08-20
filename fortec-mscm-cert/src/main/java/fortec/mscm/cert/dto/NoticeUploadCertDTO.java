package fortec.mscm.cert.dto;

import lombok.Data;

/**
 * @ClassName NoticeUploadCertDTO
 * @Description TODO
 * @Author Chenchen
 * @Date 2019/8/14 17:57
 */
@Data
public class NoticeUploadCertDTO {

    /**
     * 目标id
     */
    private String targetDescribeId;

    /**
     * 供应商id
     */
    private String supplierId;

    /**
     * 厂商id
     */
    private String manufacturerId;

    /**
     * 资质id
     */
    private String certificateId;

    /**
     * 资质类型编码
     */
    private String businessTypeCode;
}
