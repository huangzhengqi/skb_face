package com.fzy.admin.fp.merchant.thirdMchInfo.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 10:53 2019/6/27
 * @ Description:
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_merchant_hsf_mch_photo")
public class HsfMchPhoto extends BaseEntity {

    private String merchantId; //平台商户id

    @Column(length = 500)
    private String merchantHead; //门头照
    @Column(length = 500)
    private String merchantCheck; //收银台照
    @Column(length = 500)
    private String otherPhoto3; //经营场所照
    @Column(length = 500)
    private String identityFace; //法人身份证反面照
    @Column(length = 500)
    private String identityBack; //门头照
    @Column(length = 500)
    private String bussinessCard; //开户许可证照
    @Column(length = 500)
    private String bussiness; //营业执照照片
    @Column(length = 500)
    private String identityFaceCopy; //结算人身份证正面照
    @Column(length = 500)
    private String identityBackCopy; //结算人身份证反面照
    @Column(length = 500)
    private String identityBody; //手持身份证照片
    @Column(length = 500)
    private String otherPhoto4; //非法人对私授权函
    @Column(length = 500)
    private String otherPhoto2; //结算人身份证反面照
    @Column(length = 500)
    private String otherPhoto; //其他资料
    @Column(length = 500)
    private String cardFace; //银行卡正面照
}
