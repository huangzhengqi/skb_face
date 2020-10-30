package com.fzy.admin.fp.merchant.pc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;

/**
 * @author Created by wtl on 2019-05-02 0:03
 * @description 商户模块账户信息
 */
@Data
public class AccountInfo {

    @ApiModelProperty(value = "商户名称")
    private String merchantName;
    @ApiModelProperty(value = "账户")
    private String username;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "门店列表")
    private List<StoreSelect> storeSelectItem;
    @ApiModelProperty(value = "门店id")
    private String storeId;
    @ApiModelProperty(value = "门店名称")
    private String storeName;
    @ApiModelProperty("是否绑定 1未绑定 2已绑定")
    private Integer bind;
    @ApiModelProperty("设置返佣金 1不设置 2设置")
    private Integer rebateType;
    @ApiModelProperty("用户类型，1：商户；2：店长；3：员工")
    private String userType;
}
