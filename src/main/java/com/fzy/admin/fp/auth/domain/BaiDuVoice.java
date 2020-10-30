package com.fzy.admin.fp.auth.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 14:33 2019/7/1
 * @ Description:百度语音表
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_auth_service_provider")
public class BaiDuVoice extends CompanyBaseEntity {

    private String appId;
    private String apiKey;
    private String secretKey;

}
