package com.fzy.admin.fp.advertise.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author huxiangqiang
 * @date 2019/7/15 19:51
 * @Description 广告投放曝光目标表
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_advertise_view_log")
public class AdvertiseViewLog extends BaseEntity {

    @ApiModelProperty(value = "广告id")
    private String advertiseId;

    @ApiModelProperty(value = "类型 1曝光 2点击")
    private Integer status;

    @ApiModelProperty(value = "商户id")
    private String merchantId;

    @ApiModelProperty(value = "1平台所有商户 2一级代理所有商户 3二级代理所有商户 " +
            "4指定商户 5指定城市 6客户小程序 7客户付完款页面 8启动页 9会员支付页 10支付成功页")
    private Integer fromRange;


}
