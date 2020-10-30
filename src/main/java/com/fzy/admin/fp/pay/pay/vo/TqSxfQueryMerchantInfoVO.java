package com.fzy.admin.fp.pay.pay.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 天阙随行付入驻结果查询接口返回VO
 */
@Data
public class TqSxfQueryMerchantInfoVO {

    @ApiModelProperty("返回码")
    private String bizCode;

    @ApiModelProperty("返回信息")
    private String bizMsg;

    @ApiModelProperty("进件申请 ID")
    private String applicationId;

    @ApiModelProperty("商编")
    private String mno;

    @ApiModelProperty("审核状态 取值：0 入驻审核中 1 入驻通过 2 入驻驳回 3 入驻图片驳回")
    private String taskStatus;

    @ApiModelProperty("审核信息，驳回原因")
    private String suggestion;

    @ApiModelProperty("天阙随行付微信子商户号")
    private String subMchId;

    public TqSxfQueryMerchantInfoVO(String bizCode, String bizMsg) {
        this.bizCode=bizCode;
        this.bizMsg=bizMsg;
    }

    public TqSxfQueryMerchantInfoVO(String bizCode, String bizMsg, String applicationId, String mno, String taskStatus, String suggestion) {
        this.bizCode=bizCode;
        this.bizMsg=bizMsg;
        this.applicationId=applicationId;
        this.mno=mno;
        this.taskStatus=taskStatus;
        this.suggestion=suggestion;
    }

    public TqSxfQueryMerchantInfoVO(String bizCode, String bizMsg, String applicationId, String mno, String taskStatus, String suggestion, String subMchId) {
        this.bizCode=bizCode;
        this.bizMsg=bizMsg;
        this.applicationId=applicationId;
        this.mno=mno;
        this.taskStatus=taskStatus;
        this.suggestion=suggestion;
        this.subMchId=subMchId;
    }


    public TqSxfQueryMerchantInfoVO(String bizCode, String bizMsg, String applicationId, String mno, String taskStatus) {
        this.bizCode = bizCode;
        this.bizMsg = bizMsg;
        this.applicationId = applicationId;
        this.mno = mno;
        this.taskStatus = taskStatus;
    }

    public TqSxfQueryMerchantInfoVO(String bizCode, String bizMsg, String applicationId, String mno) {
        this.bizCode = bizCode;
        this.bizMsg = bizMsg;
        this.applicationId = applicationId;
        this.mno = mno;
    }
}
