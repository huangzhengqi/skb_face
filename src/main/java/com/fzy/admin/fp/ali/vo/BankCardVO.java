package com.fzy.admin.fp.ali.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 银行卡图片识别VO
 */
@Data
public class BankCardVO {

    @ApiModelProperty(value = "银行名称")
    private String bankName;

    @ApiModelProperty(value = "银行卡号")
    private String cardNum;


}
