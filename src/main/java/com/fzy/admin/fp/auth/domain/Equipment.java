package com.fzy.admin.fp.auth.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "equpmient")
@Data
public class Equipment extends CompanyBaseEntity {
    @ApiModelProperty("设备编号")
    private String deviceId;

    @ApiModelProperty("商户id")
    private String merchantId;

    @ApiModelProperty("商店id")
    private String storeId;

    @ApiModelProperty("设备类型 1支付宝 2微信 3商户app 4首页插件 5pos机")
    private Integer deviceType;

    @ApiModelProperty("收银模式 1收银 2收银+押金 3收银+结算 4收银+押金+结算 ")
    @Column(columnDefinition = "int(1) default 0")
    private Integer mode;

    @ApiModelProperty("状态 0未计算设备数 1已计算设备数")
    @Column(columnDefinition = "int(1) default 0")
    private Integer status;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activateTime;

    @Getter
    public enum Status {
        FALSE(0, "0未计算设备数"),
        TRUE(1, "已计算设备数");

        private Integer code;
        private String status;

        Status(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

}
