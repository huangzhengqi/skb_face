package com.fzy.admin.fp.auth.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Created by zk on 2019-05-06 18:34
 * @description
 */
@Data
public class MerchantCountDetailConditionDTO {
    private String companyId;
    private String merchantId;
    private String merchantName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date endTime;
    private Integer[] payWay;
    private int type;
    private int pageNum = 1;
    private int pageSize = 10;
}
