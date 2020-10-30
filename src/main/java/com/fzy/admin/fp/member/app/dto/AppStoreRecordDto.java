package com.fzy.admin.fp.member.app.dto;

import com.fzy.admin.fp.common.enumeration.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 14:23 2019/6/12
 * @ Description: 商户APP查询消费消费记录DTO
 **/
@Data
public class AppStoreRecordDto {
    @Getter
    @AllArgsConstructor
    public enum PayWay implements CodeEnum {
        WECHAT(1, "微信"),
        ALIPAY(2, "阿里"),
        CARDS(3, "会员储值卡"),
        H5(4, "H5"),
        IMPORT(5, "导入"),
        ;
        private Integer code;
        private String description;
    }

    @Getter
    @AllArgsConstructor
    public enum PayStatus implements CodeEnum {
        PLACEORDER(1, "未支付"),
        SUCCESSPAY(2, "支付成功");
        private Integer code;
        private String description;
    }

    private String storeId; //门店id
    private String payStatus;//支付状态
    private String payWay;//支付方式
    private String memberId;//会员id
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date startTime; //开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date endTime; //结束时间


}
