package com.fzy.admin.fp.sdk.merchant.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Created by zk on 2019-04-30 9:46
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataStatisticsLineDetailVO {

    @ApiModelProperty(value = "支付类型")
    private String payName;

    List<KeyValueVO> list;


}
