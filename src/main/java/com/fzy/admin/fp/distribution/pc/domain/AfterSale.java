package com.fzy.admin.fp.distribution.pc.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 短信微信推送客服表
 */
@Data
@Table(name = "lysj_dist_after_sale")
@Entity
public class AfterSale extends CompanyBaseEntity {

    @ApiModelProperty(value = "客服电话")
    private String phone;

}
