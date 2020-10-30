package com.fzy.admin.fp.distribution.app.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.FileItem;
import com.alipay.api.request.AlipayOpenAgentConfirmRequest;
import com.alipay.api.request.AlipayOpenAgentCreateRequest;
import com.alipay.api.request.AlipayOpenAgentFacetofaceSignRequest;
import com.alipay.api.request.AlipayOpenAgentOrderQueryRequest;
import com.alipay.api.response.AlipayOpenAgentConfirmResponse;
import com.alipay.api.response.AlipayOpenAgentCreateResponse;
import com.alipay.api.response.AlipayOpenAgentFacetofaceSignResponse;
import com.alipay.api.response.AlipayOpenAgentOrderQueryResponse;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.controller.AppDistMerchantController;
import com.fzy.admin.fp.distribution.app.vo.BatchNoVO;
import com.fzy.admin.fp.distribution.app.vo.DistMchInfoZfbVO;
import com.fzy.admin.fp.file.upload.service.UploadInfoMd5InfoRelationService;
import com.fzy.admin.fp.file.upload.vo.FileInfoVo;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.repository.MchInfoRepository;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Slf4j
@Service
@Transactional
public class AppDistMerchantZfbService {

    @Resource
    private UploadInfoMd5InfoRelationService uploadInfoMd5InfoRelationService;

    @Resource
    private MchInfoRepository mchInfoRepository;

    @Resource
    private MerchantService merchantService;

    @Value("${file.upload.path}")
    public String prefixPath;

    @Transactional
    public Resp<MchInfo> into(TopConfig topConfig, MchInfo mchInfo, DistMchInfoZfbVO distMchInfoZfbVO) {

        mchInfo.setAliAccountName(distMchInfoZfbVO.getAliAccountName());//商家支付宝账号
        mchInfo.setZfbRate(distMchInfoZfbVO.getZfbRate()); //支付宝费率
        mchInfo.setMccCode(distMchInfoZfbVO.getMccCode()); //支付宝经营类目
        mchInfo.setMccName(distMchInfoZfbVO.getMccName()); //支付宝经营类目名称
        //特殊资质
        if (StringUtil.isNotEmpty(distMchInfoZfbVO.getSpecialQualificationPhotoIdZfb())) {
            mchInfo.setSpecialQualificationPhotoIdZfb(distMchInfoZfbVO.getSpecialQualificationPhotoIdZfb());
        }

        try {
            String batchNo;
            //获取支付宝事务
            BatchNoVO batchNoVO=getBatchNoInterface(topConfig.getAliAppId(), topConfig.getAliPublicKey(), topConfig.getAliPrivateKey(), mchInfo.getAliAccountName(), mchInfo.getRepresentativeName(), mchInfo.getPhone(), mchInfo.getEmail());
            if (batchNoVO.getCode().equals("10000")) {
                batchNo = batchNoVO.getBatchNo();
            } else {
                mchInfo.setZfbSuccess(Integer.valueOf(0));
                mchInfo.setZfbMsg(batchNoVO.getSubMsg());
                mchInfoRepository.saveAndFlush(mchInfo);
                return new Resp().error(Resp.Status.PARAM_ERROR, batchNoVO.getSubMsg());
            }
            log.info("支付宝的事务编号 ---------------{}",batchNo);
            //调起支付宝
            AlipayClient alipayClient=new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", topConfig.getAliAppId(), topConfig.getAliPrivateKey(), "json", "GBK", topConfig.getAliPublicKey(), "RSA2");
            AlipayOpenAgentFacetofaceSignRequest request=new AlipayOpenAgentFacetofaceSignRequest();
            request.setBatchNo(batchNo);
            //通过三级经营范围得到经营类目编号
            request.setMccCode(distMchInfoZfbVO.getMccCode());
            //特殊资质，有则传
            if (StringUtil.isNotEmpty(distMchInfoZfbVO.getSpecialQualificationPhotoIdZfb())) {
                FileInfoVo specialQualificationPhotoVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(distMchInfoZfbVO.getSpecialQualificationPhotoIdZfb());
                String specialQualificationPhotoPath=prefixPath + specialQualificationPhotoVo.getPath();
                FileItem SpecialLicensePic=new FileItem(specialQualificationPhotoPath);
                request.setSpecialLicensePic(SpecialLicensePic);//企业特殊资质照片
            }
            //营业执照图片
            FileInfoVo businessLicensePicFileInfoVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getBusinessLicensePhotoId());
            if (businessLicensePicFileInfoVo == null) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "营业执照照片不存在");
            }
            String businessLicensePicPath=prefixPath + businessLicensePicFileInfoVo.getPath();
            FileItem BusinessLicensePic=new FileItem(businessLicensePicPath);
            request.setBusinessLicensePic(BusinessLicensePic);
            //支付宝费率
            String rate=String.valueOf(distMchInfoZfbVO.getZfbRate().multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN).toPlainString());
            request.setRate(rate);
            //签约且授权标识
            request.setSignAndAuth(true);
            //营业执照号码
            request.setBusinessLicenseNo(mchInfo.getLicense());
            //门头照片
            FileInfoVo doorPhotoFileInfoVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(mchInfo.getDoorPhoto());
            if (doorPhotoFileInfoVo == null) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "门头照片不存在");
            }
            String doorPhotoPath=prefixPath + doorPhotoFileInfoVo.getPath();
            FileItem ShopSignBoardPic=new FileItem(doorPhotoPath);
            request.setShopSignBoardPic(ShopSignBoardPic);
            AlipayOpenAgentFacetofaceSignResponse response=alipayClient.execute(request);
            if (response.isSuccess()) {
                AlipayOpenAgentConfirmRequest alipayOpenAgentConfirmRequest=new AlipayOpenAgentConfirmRequest();
                alipayOpenAgentConfirmRequest.setBizContent("{" +
                        "\"batch_no\":\"" + batchNo + "\"" +
                        "  }");
                AlipayOpenAgentConfirmResponse alipayOpenAgentConfirmResponse=alipayClient.execute(alipayOpenAgentConfirmRequest);
                if (alipayOpenAgentConfirmResponse.isSuccess()) {
                    //支付宝的事务编号：提交事务
                    mchInfo.setBatchNo(batchNo);
                } else {
                    mchInfo.setZfbSuccess(Integer.valueOf(0));
                    mchInfo.setZfbMsg(response.getSubMsg());
                    mchInfoRepository.saveAndFlush(mchInfo);
                    return new Resp().error(Resp.Status.PARAM_ERROR, response.getSubMsg());
                }
            } else {
                mchInfo.setZfbSuccess(Integer.valueOf(0));
                mchInfo.setZfbMsg(response.getSubMsg());
                mchInfoRepository.saveAndFlush(mchInfo);
                return new Resp().error(Resp.Status.PARAM_ERROR, response.getSubMsg());

            }
            mchInfoRepository.saveAndFlush(mchInfo);
            return Resp.success(mchInfo, "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("报错异常类---------{}", e.getMessage());
            log.info("报错异常信息---------{}", e);
        }
        return Resp.success(mchInfo, "FAILURE");
    }


    public MchInfo getZfbStatus(String app_id, String private_key, String alipay_public_key, MchInfo mchInfo) throws AlipayApiException {
        AlipayClient alipayClient=new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", app_id, private_key, "json", "GBK", alipay_public_key, "RSA2");
        AlipayOpenAgentOrderQueryRequest request=new AlipayOpenAgentOrderQueryRequest();
        request.setBizContent("{" +
                "\"batch_no\":\"" + mchInfo.getBatchNo() + "\"" +
                "  }");
        AlipayOpenAgentOrderQueryResponse response=alipayClient.execute(request);
        log.info("支付审核返回参数（response）  ----  " + response.getBody());
        if (!response.getCode().equals("10000")) {
            mchInfo.setZfbMsg(response.getBody());
            return mchInfo;
        }
        //暂存，提交事务出现业务校验异常时，会暂存申请单信息，可以调用业务接口修正参数，并重新提交
        if (response.getOrderStatus().equals("MERCHANT_INFO_HOLD")) {
            if (!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))) {
                mchInfo.setStatus(Integer.valueOf(1));
            }
            mchInfo.setZfbMsg(response.getRejectReason());
            mchInfo.setZfbSuccess(Integer.valueOf(1));
        }
        //审核中，申请信息正在人工审核中
        if (response.getOrderStatus().equals("MERCHANT_AUDITING")) {
            if (!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))) {
                mchInfo.setStatus(Integer.valueOf(2));
            }
            mchInfo.setZfbMsg("审核中，申请信息正在人工审核中");
            mchInfo.setZfbSuccess(Integer.valueOf(2));
        }
        //待商户确认，申请信息审核通过，等待联系人确认签约或授权
        if (response.getOrderStatus().equals("MERCHANT_CONFIRM")) {
            String confirmUrl=response.getConfirmUrl();
            mchInfo.setConfirmUrl(confirmUrl);
            mchInfo.setZfbMsg("待商户授权");
            mchInfo.setZfbSuccess(Integer.valueOf(3));
            if (!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))) {
                mchInfo.setStatus(Integer.valueOf(2));
            }
        }
        //商户确认成功，商户同意签约或授权
        if (response.getOrderStatus().equals("MERCHANT_CONFIRM_SUCCESS")) {
            mchInfo.setZfbMsg("商户确认成功，商户同意签约或授权");
            mchInfo.setZfbSuccess(Integer.valueOf(4));
            mchInfo.setMerchantPid(response.getMerchantPid());

            if (!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))) {
                mchInfo.setStatus(Integer.valueOf(3));
            }

            //完成帮商户自动启用
            Merchant merchant=merchantService.findOne(mchInfo.getMerchantId());
            //如果禁用就启用
            if (merchant.getStatus().equals(Integer.valueOf(2))) {
                merchant.setStatus(Integer.valueOf(1));//启用
                merchantService.update(merchant);
            }
        }
        //商户超时未确认，如果商户受到确认信息15天内未确认，则需要重新提交申请信息
        if (response.getOrderStatus().equals("MERCHANT_CONFIRM_TIME_OUT")) {
            if (!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))) {
                mchInfo.setStatus(Integer.valueOf(4));
            }
            mchInfo.setZfbSuccess(Integer.valueOf(5));
            mchInfo.setZfbMsg("商户超时未确认，如果商户受到确认信息15天内未确认，则需要重新提交申请信息");
        }
        //审核失败或商户拒绝，申请信息审核被驳回，或者商户选择拒绝签约或授权
        if (response.getOrderStatus().equals("MERCHANT_APPLY_ORDER_CANCELED")) {
            mchInfo.setZfbSuccess(Integer.valueOf(6));
            //失败原因
            mchInfo.setZfbMsg(response.getRejectReason());
            if (!mchInfo.getStatus().equals(Integer.valueOf(5)) && !mchInfo.getStatus().equals(Integer.valueOf(3))) {
                mchInfo.setStatus(Integer.valueOf(4));
            }
        }
        return mchInfo;
    }

    /**
     * 获取支付宝的事务编号
     *
     * @param aliAppId
     * @param aliPublicKey
     * @param aliPrivateKey
     * @param accountName
     * @param contactName
     * @param contactMobile
     * @param contactEmail
     * @return
     */
    public BatchNoVO getBatchNoInterface(String aliAppId, String aliPublicKey, String aliPrivateKey, String accountName, String contactName, String contactMobile, String contactEmail) {
        AlipayClient alipayClient=new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", aliAppId, aliPrivateKey, "json", "GBK", aliPublicKey, "RSA2");
        AlipayOpenAgentCreateRequest request=new AlipayOpenAgentCreateRequest();
        request.setBizContent("{" +
                "\"account\":\"" + accountName + "\"," +
                "\"contact_info\":{" +
                "\"contact_name\":\"" + contactName + "\"," +
                "\"contact_mobile\":\"" + contactMobile + "\"," +
                "\"contact_email\":\"" + contactEmail + "\"" +
                "    }," +
                "\"order_ticket\":\"\"" +
                "  }");
        AlipayOpenAgentCreateResponse response=null;
        try {
            response=alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            return new BatchNoVO(response.getBatchNo(),response.getBatchStatus(),response.getCode());
        } else {
            return new BatchNoVO(response.getCode(), response.getMsg(), response.getSubCode(), response.getSubMsg());
        }
    }
}
