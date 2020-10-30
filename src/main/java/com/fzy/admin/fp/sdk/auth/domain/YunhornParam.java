package com.fzy.admin.fp.sdk.auth.domain;

import com.fzy.admin.fp.order.order.EnumInterface;
import com.fzy.admin.fp.order.order.EnumInterface;
import lombok.Data;
import lombok.Getter;

import javax.transaction.Transactional;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 14:35 2019/7/6
 * @ Description: 云喇叭传输数据dto
 **/
@Data
public class YunhornParam {

    @Getter
    public enum Pt implements EnumInterface {
        ALIPAY(1, "支付宝支付"),
        WEPAY(2, "微信支付"),
        YUNPAY(3, "云支付"),
        BALANCEPAY(4, "余额支付"),
        WESTORED(5, "微信储值"),
        WEBILL(6, "微信买单"),
        UNIONPAY(7, "银联刷卡"),
        MEMCONSUME(8, "会员卡消费"),
        MEMRECHARGE(9, "会员卡充值"),
        WINGPAY(10, "翼支付"),
        REFUND(11, "退款"),
        ALIREFUND(12, "支付宝退款"),
        WEREFUND(13, "微信退款"),
        BANKREFUND(14, "银行卡退款"),
        UNIONPAYREFUND(15, "银联退款"),
        ICBCREFUND(16, "工行e支付");

        private Integer code;

        private String status;

        Pt(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    private String serviceProviderId;//服务商id
    private Integer price; //支付金额,单位:分
    private Integer pt; //支付类型
}
