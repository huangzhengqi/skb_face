package com.fzy.admin.fp.distribution.pc.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Table(name = "send_status")
@Entity
public class SendStatus extends CompanyBaseEntity {

    @Getter
    @AllArgsConstructor
    public enum Status{
        OPEN(1,"开启"),
        NOOPEN(2,"关闭");

        private Integer code;
        private String message;
    }


    @ApiModelProperty(value = "微信推送状态 1：开启 2：关闭 ")
    private Integer wxStatus;


    @ApiModelProperty(value = "短信推送状态 1：开启 2：关闭 ")
    private Integer afterStatus;
}
