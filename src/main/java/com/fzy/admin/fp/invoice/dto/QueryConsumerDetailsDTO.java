package com.fzy.admin.fp.invoice.dto;

import com.fzy.admin.fp.common.spring.pagination.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Getter
@Setter
@ApiModel("查询消费明细（订单）")
public class QueryConsumerDetailsDTO extends PageVo {
    @ApiModelProperty("查询到最大创建实际")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maxCreateDate;
    @ApiModelProperty("查询到最小创建实际")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date minCreateDate;
    @ApiModelProperty("是否已开票")
    private Boolean invoiced;
    @ApiModelProperty("查询到最大金额")
    private Integer maxTotalPrice;
    @ApiModelProperty("查询到最小金额")
    private Integer minTotalPrice;
    @ApiModelProperty("门店列表")
    private String[] storeIds;
    @ApiModelProperty("ids")
    private String[] orderIds;
}
