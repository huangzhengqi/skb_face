package com.fzy.admin.fp.auth.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 14:23 2019/7/1
 * @ Description: 服务商基本信息配置表
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_auth_site_info")
public class SiteInfo extends CompanyBaseEntity {

    @Column(nullable = false)
    private String domainName;//域名
    private String logo;//登陆成功后左上角
    private String loginLogo;//登陆页左上角logo
    private String bgmPhoto;//登录页背景图
    private String mctBgmPhoto;//商户登录页背景图
    private String techSupport; //技术支持
    private String servicePhone; //客服电话
    private String wechatQrcode;  //微信二维码
    private String copyrightInfo; //版权说明
    private String icpInfo; //ICP备案号
}
