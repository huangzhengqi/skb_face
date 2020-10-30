package com.fzy.admin.fp.distribution.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 分销进件特殊保存
 */
@Data
public class DistMchInfoSpecialVO {

    @ApiModelProperty("进件id")
    private String id;

    @ApiModelProperty(value = "微信  ---  特殊资质截图")
    private String specialQualificationPhotoId;

    @ApiModelProperty("微信  ---  补充材料")
    private String businessAdditionPics;

    @ApiModelProperty("微信  ---  费率结算规则Id")
    private String settlementId;

    @ApiModelProperty("微信  ---  所属行业")
    private String qualificationType;

    @ApiModelProperty("支付宝 --- 支付宝账号")
    private String aliAccountName;

    @ApiModelProperty(value = "支付宝 --- 支付宝经营类目编号")
    private String mccCode;

    @ApiModelProperty(value="支付宝 --- 支付宝经营类目名称")
    private String mccName;

    @ApiModelProperty(value = "支付宝 --- 支付宝费率")
    private BigDecimal zfbRate;

    @ApiModelProperty("支付宝 --- 支付宝特殊资质截图")
    private String specialQualificationPhotoIdZfb;

    @ApiModelProperty("天阙随行付mccId")
    private String tqSxfMccId;

    @ApiModelProperty("天阙随行付经营类目名称")
    private String tqSxfMccName;

    @ApiModelProperty(value = "天阙随行付微信费率")
    private BigDecimal tqsxfWxRate;

    @ApiModelProperty(value = "天阙随行付支付宝费率")
    private BigDecimal tqsxfZfbRate;

    @ApiModelProperty("开户许可证 天阙随行付对公结算必传")
    private String openingPermit;
}
