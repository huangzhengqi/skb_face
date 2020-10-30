package com.fzy.admin.fp.sdk.pay.feign;

import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.pay.pay.domain.TqSxfConfig;
import com.fzy.admin.fp.pay.pay.dto.SxfPayFaceAuthInfoDTO;
import com.fzy.admin.fp.pay.pay.vo.SxfPayFaceAuthInfoVO;
import com.fzy.admin.fp.pay.pay.vo.TqSxfIncomeVO;
import com.fzy.admin.fp.pay.pay.vo.TqSxfQueryMerchantInfoVO;
import com.fzy.admin.fp.pay.pay.vo.UploadPictureVO;
import com.fzy.admin.fp.sdk.pay.domain.CommonQueryParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.SxfPayParam;
import com.fzy.admin.fp.sdk.pay.domain.TqSxfPayParam;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * 天阙随行付支付feign接口
 */
public interface TqSxfPayServiceFeign {

    /**
     * 天阙随行付支付接口 （C扫B）
     * @param model
     * @return
     * @throws Exception
     */
    PayRes TqSxfWapPay(TqSxfPayParam model) throws Exception;

    /**
     * 天阙随行付支付接口 （B扫C）
     * @param model
     * @return
     * @throws Exception
     */
    PayRes TqSxfScanPay(TqSxfPayParam model) throws Exception;

    /**
     * 天阙随行付支付查询订单
     * @param model
     * @return
     */
    PayRes TqSxfOrderQuery(CommonQueryParam model) ;

    /**
     * 天阙随行付退款
     * @param model
     * @return
     * @throws Exception
     */
    PayRes TqSxfRefund(TqSxfPayParam model) throws Exception;

    /**
     * 天阙随行付获取微信刷脸调用凭证接口
     * @param sxfPayFaceAuthInfoDTO
     * @param tqSxfConfig
     * @return
     * @throws Exception
     */
    SxfPayFaceAuthInfoVO getTqSxfAuthInfo(SxfPayFaceAuthInfoDTO sxfPayFaceAuthInfoDTO, TqSxfConfig tqSxfConfig) throws Exception;

    /**
     * 天阙随行付上传图片接口
     * @param idCardCopyPath
     * @param pictureType
     * @return
     */
    UploadPictureVO photoUrl(String idCardCopyPath , String pictureType) ;

    /**
     * 天阙随行付退款状态
     * @param commonQueryParam
     * @return
     */
    PayRes TqSxfRefundQuery(CommonQueryParam commonQueryParam);


    /**
     * 天阙随行付首次进件
     * @param params
     * @param merchantId
     * @return
     * @throws Exception
     */
    TqSxfIncomeVO income(Map<String, Object> params, String merchantId) throws Exception;

    /**
     * 天阙随行付修改进件
     * @param params
     * @param merchantId
     * @return
     * @throws Exception
     */
    TqSxfIncomeVO updateMerchantInfo(Map<String, Object> params, String merchantId) throws Exception;

    /**
     * 天阙随行付查询商编
     * @param params
     * @param merchantId
     * @return
     * @throws Exception
     */
    TqSxfQueryMerchantInfoVO queryMerchantInfo(Map<String, Object> params, String merchantId) throws Exception;

    /**
     * 支付撤销订单，订单错误或者超时调用
     * @param merchantId
     * @param orderNumber
     * @throws Exception
     */
    void tqSxfReverse(String merchantId, String orderNumber) throws Exception;
}
