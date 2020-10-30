package com.fzy.admin.fp.merchant.thirdMchInfo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 16:29 2019/6/27
 * @ Description:
 **/
@Data
public class HsfMchInfoDTO {

    //--------------经营联系信息-------------------
    private String shop_keeper; //负责人
    private String keeper_phone; //负责人电话
    private String shop_phone; //客服电话(额外字段)
    private String email; //邮箱


    //--------------经营基本信息-------------------
    private String shop_nickname; //商户简称
    private String type; //经营范围 (对应平台一级经营类别)
    private String classify; //经营范围 (对应平台二级经营类别)


    //--------------营业执照-------------------

    private String licence_no; //营业执照号

    private String licence_begin_date; //起始时间
    //
    private String licence_expire_date; //截止时间

    //--------------企业法人/经办人-------------------
    private String artif_name; //法人姓名
    private String artif_phone; //法人手机号 (额外字段)
    private String artif_identity; //法人身份证号
    private String identity_start_time; //身份证起始日期
    private String identity_expire_time; //身份证截止日期


    //--------------结算账户-------------------
    private String card; //结算卡号
    private String bank_name; //结算银行
    private String bank_address; //结算开户行
    private String bank_add_no; //结算联行号
    private String card_phone; //结算人手机号(额外字段)
    private String identity; //结算人身份证号(额外字段)
    private String card_name; //结算人姓名(额外字段)


    //--------------商户基本信息-------------------
    private String shop_name; //商户全称
    private String province; //订单编号 (额外字段)
    private String city; //省 (额外字段)
    private String area; //市 (额外字段)
    private String pay_type; //渠道 (额外字段)
    private String business_type; //商户类型 (额外字段)
    private String rate_wx; //微信费率 (额外字段)
    private String rate_alipay; //支付宝费率 (额外字段)
    private String description; //备注信息 (额外字段)
    private String agent_id; //合作商号 (额外字段)
    private String signin_typ; //快速进件 (额外字段)
    private String shop_address; //详细地址


    private String order_id; //订单编号(额外字段)
    private String notify_url; //回调地址(额外字段)
    private String sign; //签名(额外字段)
}
