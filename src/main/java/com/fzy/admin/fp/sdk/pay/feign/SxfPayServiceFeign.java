package com.fzy.admin.fp.sdk.pay.feign;


import com.fzy.admin.fp.pay.pay.domain.SxfConfig;
import com.fzy.admin.fp.pay.pay.domain.TqSxfConfig;
import com.fzy.admin.fp.pay.pay.dto.SxfPayFaceAuthInfoDTO;
import com.fzy.admin.fp.pay.pay.vo.SxfPayFaceAuthInfoVO;
import com.fzy.admin.fp.pay.pay.vo.WxPayFaceAuthInfoVO;
import com.fzy.admin.fp.sdk.pay.domain.CommonQueryParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.SxfPayParam;
import com.fzy.admin.fp.sdk.pay.domain.*;

/**
 * @author Created by wsj on 2019-07-22 14:06
 * @description 随行付支付feign接口
 */
public interface SxfPayServiceFeign {

    /**
     * 随行付扫码支付接口
     */
    PayRes sxfScanPay(SxfPayParam model) throws Exception;
    /**
     * 随行付支付，用户扫商户
     */
    PayRes sxfWapPay(SxfPayParam model) throws Exception;


    /**
     * 随行付支付查询订单
     */
    PayRes sxfOrderQuery(CommonQueryParam model) ;

    /**
     *  随行付退款
     */
    PayRes sxfRefund(SxfPayParam model) throws Exception;


    /**
     * 获取微信刷脸调用凭证接口
     */
    SxfPayFaceAuthInfoVO getsxfAuthInfo(SxfPayFaceAuthInfoDTO sxfPayFaceAuthInfoDTO, SxfConfig sxfConfig) throws Exception;

    SxfPayFaceAuthInfoVO getSxfSubopenid(String authCode, SxfConfig sxfConfig) throws Exception;
}
