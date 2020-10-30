package com.fzy.admin.fp.member.coupon.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 卡券核销列表入参
 * <p>
 *
 * @since: 2019/7/17 16:50
 * @author: wsj
 */
@Data
public class CancelAfterVerificationListDTO {

    private String coupon;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start_createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end_createTime;

    private String merchantId;
}
