package com.fzy.admin.fp.auth.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author fyz123
 * @create 2020/7/23 8:56
 * @Description: 交易数据
 */
@Data
public class DisTranDataVO {

    @ApiModelProperty("门店名称")
    private String storeName;

    @ApiModelProperty("所属商家")
    private String merchantName;

    @ApiModelProperty("门店Id")
    private String storeId;

    @ApiModelProperty("交易笔数")
    private String orderCount;

    @ApiModelProperty("交易金额")
    private String orderActPayPrice;

    @ApiModelProperty("商户ID")
    private String merchantId;

    @ApiModelProperty("刷脸笔数")
    private String faceNum;

    @ApiModelProperty("刷脸笔数大于2")
    private String face2Num;

    @ApiModelProperty("刷脸交易金额")
    private String facePrice;

    @ApiModelProperty("所属业务员")
    private String managerName;
}
