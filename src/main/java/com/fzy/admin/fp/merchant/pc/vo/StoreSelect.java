package com.fzy.admin.fp.merchant.pc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Created by wtl on 2019-05-02 0:08
 * @description 门店下拉框
 */
@Data
public class StoreSelect {

    @ApiModelProperty(value = "门店id")
    private String storeId;
    @ApiModelProperty(value = "门店名称")
    private String storeName;

    public StoreSelect() {

    }

    public StoreSelect(String storeId, String storeName) {
        this.storeId = storeId;
        this.storeName = storeName;
    }
}
