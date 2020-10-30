package com.fzy.admin.fp.pay.pay.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Created by wtl on 2019-05-31 9:56
 * @description 统统收支付通道配置
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_pay_tts_config")
public class TtsConfig extends BaseEntity {

    private String merchantId; // 商户id

    // --------------秒到----------------
    private String privateKey; // 商户私钥
    private String publicKey; // 平台公钥
    private String mchId; // 商户号
    private String partnerId; // 渠道号

    private String provinceCode; // 省级地区编码
    private String cityCode; // 市级地区编码
    private String regionCode; // 区级地区编码

}
