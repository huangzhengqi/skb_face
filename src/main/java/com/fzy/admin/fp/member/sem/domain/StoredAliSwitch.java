package com.fzy.admin.fp.member.sem.domain;


import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_ali_store_switch")
public class StoredAliSwitch extends BaseEntity {
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

    @ApiModelProperty(value = "商户ID")
    @NotBlank(message = "必须传入商户id")
    private String merchantId;
    @ApiModelProperty(value = "开关状态")
    private Integer status;
}
