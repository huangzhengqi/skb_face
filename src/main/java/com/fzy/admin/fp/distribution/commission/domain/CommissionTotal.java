package com.fzy.admin.fp.distribution.commission.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author yy
 * @Date 2019-12-26 16:08:01
 * @Desp
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_commission_total")
public class CommissionTotal extends CompanyBaseEntity {

    @ApiModelProperty(value = "交易总额")
    @Column(columnDefinition = "decimal(19,4) default 0")
    @Excel(name = "交易总额",orderNum = "3",isImportField = "true_st", type = 10,height = 20, width = 20)
    BigDecimal orderTotal;

    @ApiModelProperty(value = "佣金总额")
    @Column(columnDefinition = "decimal(19,4) default 0")
    @Excel(name = "佣金总额",orderNum = "4",isImportField = "true_st",type = 10,height = 20, width = 20)
    BigDecimal commissionTotal;
}
