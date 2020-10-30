package com.fzy.admin.fp.distribution.app.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分销微信进件vo
 */
@Data
public class DistMchInfoWxVO {

    @ApiModelProperty("进件id")
    private String id;

    @ApiModelProperty(value = "特殊资质截图")
    private String specialQualificationPhotoId;

    @ApiModelProperty("补充材料")
    private String businessAdditionPics;

    @ApiModelProperty("费率结算规则Id")
    private String settlementId;

    @ApiModelProperty("所属行业")
    private String qualificationType;
}
