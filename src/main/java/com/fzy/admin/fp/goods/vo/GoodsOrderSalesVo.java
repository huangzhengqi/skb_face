package com.fzy.admin.fp.goods.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 商品销量vo
 */
@Data
public class GoodsOrderSalesVo {

    private String merchantId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date endTime;
    private int pageNum;
    private int pageSize;
    private Integer salesNumType; //销售类型
    private Integer salesCountNumType ; //销售（份）类型
    private Integer tradingVolumeType; //交易笔 类型
}
