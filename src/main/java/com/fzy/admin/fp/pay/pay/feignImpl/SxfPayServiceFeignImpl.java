package com.fzy.admin.fp.pay.pay.feignImpl;

import com.fzy.admin.fp.pay.pay.domain.SxfConfig;
import com.fzy.admin.fp.pay.pay.dto.SxfPayFaceAuthInfoDTO;
import com.fzy.admin.fp.pay.pay.vo.SxfPayFaceAuthInfoVO;
import com.fzy.admin.fp.pay.pay.vo.WxPayFaceAuthInfoVO;
import com.fzy.admin.fp.sdk.pay.domain.CommonQueryParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.SxfPayParam;
import com.fzy.admin.fp.sdk.pay.feign.SxfPayServiceFeign;
import com.fzy.admin.fp.pay.pay.service.SxfPayService;
import com.fzy.admin.fp.sdk.pay.domain.*;
import com.fzy.admin.fp.sdk.pay.feign.SxfPayServiceFeign;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Created by wtl on 2019-05-21 21:21
 * @description 随行付支付feign实现
 */
@Service
public class SxfPayServiceFeignImpl implements SxfPayServiceFeign {

    @Resource
    private SxfPayService sxfPayService;


    @Override
    public PayRes sxfScanPay(SxfPayParam model) throws Exception {
        return sxfPayService.micropay(model);
    }

    @Override
    public PayRes sxfWapPay(SxfPayParam model) throws Exception {
        return sxfPayService.jsapiPay(model);
    }

    @Override
    public PayRes sxfOrderQuery(CommonQueryParam model) {
        try {
            return sxfPayService.query(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PayRes sxfRefund(SxfPayParam model) throws Exception{
        return sxfPayService.refund(model);
    }

    @Override
    public SxfPayFaceAuthInfoVO getsxfAuthInfo(SxfPayFaceAuthInfoDTO sxfPayFaceAuthInfoDTO , SxfConfig sxfConfig) throws Exception {
       return sxfPayService.getsxfAuthInfo(sxfPayFaceAuthInfoDTO,sxfConfig);
    }

    @Override
    public SxfPayFaceAuthInfoVO getSxfSubopenid(String authCode, SxfConfig sxfConfig) throws Exception {
        return sxfPayService.getSxfSubopenid(authCode,sxfConfig);
    }
}
