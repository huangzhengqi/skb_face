package com.fzy.admin.fp.sdk.order.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by zk on 2019-04-29 19:43
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {
    @Getter
    public enum PayWay {
        WXPAY(1, "微信"),
        ALIPAY(2, "支付宝"),
        BANKCARD(3, "银行卡"),
        MEMBERCARD(4, "会员卡"),
        OTHER(99, "未知");
        private Integer code;

        private String status;

        PayWay(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @Getter
    public enum PayClient {
        PC(1, "PC端"),
        APP(2, "App端");
        private Integer code;

        private String status;

        PayClient(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    @Getter
    public enum Status {
        PLACEORDER(1, "未支付"),
        SUCCESSPAY(2, "支付成功"),
        FAILPAY(3, "支付失败"),
        CANCELPAY(4, "已撤销"),
        REFUNDTOTAL(5, "全额退款成功"),
        REFUNDPART(6, "部分退款成功"),
        REFUNDFAIL(7, "退款失败");
        private Integer code;

        private String status;

        Status(Integer code, String status) {
            this.code = code;
            this.status = status;
        }
    }

    private String orderNumber;//订单编号
    private String merchantId; // 商户id
    private String storeId; // 门店id
    private String storeName;//门店名
    private String userName; // 收银员名称
    private BigDecimal totalPrice; //订单金额
    private BigDecimal disCountPrice; //优惠金额
    private BigDecimal actPayPrice;//实付金额
    private BigDecimal refundPayPrice;//退款金额
    private Integer payWay;//支付方式
    private Integer payChannel; // 支付通道
    private Integer payClient; // 支付端
    private BigDecimal interestRate; // 支付通道利率
    private String authCode; // 扫码支付授权码
    private Date payTime;// 支付时间
    private String description; // 基本描述
    private String detail; // 描述详细
    private String remark; // 订单备注
    private Integer status;//订单状态
    private Date refundTime;//退款时间

    //-----------以下为业务需要设置的冗余字段-------
    private BigDecimal commissionAmount = BigDecimal.ZERO;//佣金金额
    private BigDecimal agentCommissionAmount = BigDecimal.ZERO;//一级代理商佣金
    private BigDecimal subAgentCommissionAmount = BigDecimal.ZERO;//二级代理商佣金
    private String merchantName;
    private String agentName;
    private String subAgentName;
    private String thirdAgentName;
    private String formatPayTime;

    // 小程序支付回调地址不能携带参数，生成订单后在订单里面存储会员id和储值规则参数json，以便回调后生成储值记录等操作
    private String appletStore;

    private String error;
}
