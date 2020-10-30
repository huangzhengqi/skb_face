package com.fzy.admin.fp.merchant.merchant.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Created by wtl on 2019-04-18 10:19:37
 * @description 商户控制层
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_merchant_store")
public class Store extends BaseEntity {

    @Getter
    public enum Status {
        ENABLE(1, "启用"),
        DISABLE(2, "禁用");

        private Integer code;

        private String status;

        Status(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @Getter
    public enum StoreFlag {
        DEFAULT(1, "默认"),
        NORMAL(2, "普通");

        private Integer code;

        private String status;

        StoreFlag(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    private String merchantId;//商户id

    @NotBlank(message = "请输入店名")
    private String name;

    @NotBlank(message = "请输入编号")
    private String storeNo;


    private String province; //省

    private String city;//市

    @NotBlank(message = "请输入地址")
    private String address; //地址

    private String addressNote; //地址备注

    private String phone; //门店电话


    private String photoId;// 门店logo

    private String paymentDesciption;//支付凭证描述

    private Integer status;//状态

    private Integer storeFlag;//门店标识

    @Transient
    private Integer storeUserNum;//门店员工人数

    @Column(length = 2, columnDefinition = "int(1) default 0")
    private Integer hasTakeNumber;

    private String bannerImg;

    @Column(columnDefinition = "int(11) default 0")
    @ApiModelProperty("行业分类 0超市 1自助点餐 2医药 3加油站 4景区")
    private Integer industryCategory;

}