package com.fzy.admin.fp.pay.pay.feignImpl;

import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.pay.pay.domain.TqSxfConfig;
import com.fzy.admin.fp.pay.pay.dto.SxfPayFaceAuthInfoDTO;
import com.fzy.admin.fp.pay.pay.service.TqSxfPayService;
import com.fzy.admin.fp.pay.pay.vo.SxfPayFaceAuthInfoVO;
import com.fzy.admin.fp.pay.pay.vo.TqSxfIncomeVO;
import com.fzy.admin.fp.pay.pay.vo.TqSxfQueryMerchantInfoVO;
import com.fzy.admin.fp.pay.pay.vo.UploadPictureVO;
import com.fzy.admin.fp.sdk.pay.domain.CommonQueryParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.SxfPayParam;
import com.fzy.admin.fp.sdk.pay.domain.TqSxfPayParam;
import com.fzy.admin.fp.sdk.pay.feign.TqSxfPayServiceFeign;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @description  天阙随行付支付feign实现
 */
@Service
public class TqSxfPayServiceFeignImpl implements TqSxfPayServiceFeign {

    @Resource
    private TqSxfPayService tqSxfPayService;

    @Override
    public PayRes TqSxfWapPay(TqSxfPayParam model) throws Exception {
        return tqSxfPayService.jsapiPay(model);
    }

    @Override
    public PayRes TqSxfScanPay(TqSxfPayParam model) throws Exception {
        return tqSxfPayService.micropay(model);
    }

    @Override
    public PayRes TqSxfOrderQuery(CommonQueryParam model) {
        try {
            return tqSxfPayService.query(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PayRes TqSxfRefund(TqSxfPayParam model) throws Exception {
        return tqSxfPayService.refund(model);
    }

    @Override
    public SxfPayFaceAuthInfoVO getTqSxfAuthInfo(SxfPayFaceAuthInfoDTO sxfPayFaceAuthInfoDTO, TqSxfConfig tqSxfConfig) throws Exception {
        return tqSxfPayService.getTqSxfAuthInfo(sxfPayFaceAuthInfoDTO,tqSxfConfig);
    }

    @Override
    public UploadPictureVO photoUrl(String idCardCopyPath , String pictureType) {
        return tqSxfPayService.upLoadPicture(idCardCopyPath ,pictureType);
    }

    @Override
    public PayRes TqSxfRefundQuery(CommonQueryParam model){
        try {
            return tqSxfPayService.TqSxfRefundQuery(model);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TqSxfIncomeVO income(Map<String, Object> params, String merchantId) throws Exception {
        return tqSxfPayService.income(params,merchantId);
    }

    @Override
    public TqSxfIncomeVO updateMerchantInfo(Map<String, Object> params, String merchantId) throws Exception {
        return tqSxfPayService.updateMerchantInfo(params,merchantId);
    }

    @Override
    public TqSxfQueryMerchantInfoVO queryMerchantInfo(Map<String, Object> params, String merchantId) throws Exception {
        return tqSxfPayService.queryMerchantInfo(params,merchantId);
    }

    @Override
    public void tqSxfReverse(String merchantId, String orderNumber) throws Exception {
        tqSxfPayService.tqSxfReverse(merchantId,orderNumber);
    }
}
