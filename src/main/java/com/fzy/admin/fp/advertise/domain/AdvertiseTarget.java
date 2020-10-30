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
 * @Description 广告投放目标表
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_advertise_target")
public class AdvertiseTarget extends BaseEntity {


    /**
     * 广告id
     */
    private String advertiseId;

    /**
     * 目标id,一二级代理商id 或者商户id
     */
    private String targetId;

    /**
     * 投放城市id
     */
    private String cityIds;

    private Integer type; //（广告默认 0不默认  1默认）

    @ApiModelProperty("设备类型 1支付宝 2微信")
    private Integer deviceType;



}
