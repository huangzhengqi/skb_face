package com.fzy.admin.fp.distribution.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import sun.dc.pr.PRError;

/**
 *  支付宝事务VO
 */
@Data
public class BatchNoVO {

    @ApiModelProperty(value = "事务编号")
    private String batchNo;

    @ApiModelProperty(value = "ISV 代商户操作事务状态")
    private String batchStatus;

    @ApiModelProperty(value = "网关返回码")
    private String code;

    @ApiModelProperty(value = "网关返回码描述")
    private String msg;

    @ApiModelProperty(value = "业务返回码")
    private String subCode;

    @ApiModelProperty(value = "业务返回码描述，参见具体的API接口文档")
    private String subMsg;

    public BatchNoVO(String batchNo, String batchStatus, String code) {
        this.batchNo=batchNo;
        this.batchStatus=batchStatus;
        this.code=code;
    }

    public BatchNoVO(String code, String msg, String subCode, String subMsg) {
        this.code=code;
        this.msg=msg;
        this.subCode=subCode;
        this.subMsg=subMsg;
    }
}
