package com.fzy.admin.fp.member.rule.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 16:06 2019/5/14
 * @ Description:
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_store_switch")
public class StoredSwitch extends BaseEntity {

    @Getter
    public enum Status {
        ENABLE(1, "启用"),
        DISABLE(2, "禁用");

        private Integer code;

        private String status;

        Status(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @NotBlank(message = "必须传入商户id")
    private String merchantId;//商户id
    private Integer status; //开关状态
}
