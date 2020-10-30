package com.fzy.admin.fp.auth.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 10:57 2019/6/4
 * @ Description:
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_auth_sms_message")
public class SmsMessage extends CompanyBaseEntity {


    private String accessKeyId;

    private String secret;

    private String signName; //签名

    private String smsTemplateCode; //短信验证模板

    private String smsInfoTemplateCode; //注册成功后短信模板


}
