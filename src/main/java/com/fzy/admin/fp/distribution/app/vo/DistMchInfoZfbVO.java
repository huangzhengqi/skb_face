package com.fzy.admin.fp.distribution.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * 分销支付宝进件vo
 */
@Data
public class DistMchInfoZfbVO {

    @ApiModelProperty("进件id")
    private String id;

    @ApiModelProperty("支付宝账号")
    private String aliAccountName;

    @ApiModelProperty(value = "支付宝经营类目编号")
    private String mccCode;

    @ApiModelProperty(value="支付宝经营类目名称")
    private String mccName;

    @ApiModelProperty(value = "支付宝费率")
    private BigDecimal zfbRate;

    @ApiModelProperty("支付宝特殊资质截图")
    private String specialQualificationPhotoIdZfb;
}
