package com.fzy.admin.fp.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 15:10 2019/7/1
 * @ Description:
 **/
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class CompanyBaseEntity extends BaseEntity implements Serializable {

    /**
     * 服务商id
     */
    private String serviceProviderId;
}
