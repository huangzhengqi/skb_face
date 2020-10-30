package com.fzy.admin.fp.pay.pay.vo;

import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 天阙随行付商户入驻返回结果
 */
@Data
public class TqSxfIncomeVO {

    @ApiModelProperty("进件申请ID")
    private String applicationId;

    @ApiModelProperty(value="错误码 成功：0000  失败：0001")
    private String bizCode;

    @ApiModelProperty(value="对错误码的描述")
    private String bizMsg;

    @ApiModelProperty(value="商编")
    private String mno;


    public TqSxfIncomeVO(String applicationId, String bizCode, String bizMsg, String mno) {
        this.applicationId=applicationId;
        this.bizCode=bizCode;
        this.bizMsg=bizMsg;
        this.mno=mno;
    }

    public TqSxfIncomeVO(String applicationId, String bizCode, String bizMsg) {
        this.applicationId=applicationId;
        this.bizCode=bizCode;
        this.bizMsg=bizMsg;
    }

    public TqSxfIncomeVO(String bizCode) {
        this.bizCode=bizCode;
    }

    public TqSxfIncomeVO(String bizCode, String bizMsg) {
        this.bizCode=bizCode;
        this.bizMsg=bizMsg;
    }

    public TqSxfIncomeVO() {
    }
}
