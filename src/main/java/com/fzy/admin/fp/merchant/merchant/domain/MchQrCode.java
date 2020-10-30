package com.fzy.admin.fp.merchant.merchant.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 10:24 2019/5/9
 * @ Description: 商户二维码
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_merchant_mer_qr_code")
public class MchQrCode extends BaseEntity {

    @Getter
    public enum Status {
        ENABLE(1, "启用"),
        DISABLE(2, "注销");
        private Integer code;

        private String status;

        Status(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("门店id")
    private String storeId;

    @ApiModelProperty("门店名称")
    @Transient
    private String storeName;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户名称")
    @Transient
    private String userName;

    @ApiModelProperty("金额")
    @Column(columnDefinition = "decimal(10,2)")
    private BigDecimal money;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("二维码")
    @Column(columnDefinition = "TEXT")
    private String qrCode;


}
