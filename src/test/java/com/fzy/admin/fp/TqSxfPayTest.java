//package com.fzy.admin.fp;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.parser.Feature;
//import com.fzy.admin.fp.common.constant.CommonConstant;
//import com.fzy.admin.fp.common.exception.BaseException;
//import com.fzy.admin.fp.common.json.JacksonUtil;
//import com.fzy.admin.fp.common.web.ParamUtil;
//import com.fzy.admin.fp.common.web.Resp;
//import com.fzy.admin.fp.distribution.utils.RSASignature;
//import com.fzy.admin.fp.file.upload.service.UploadInfoMd5InfoRelationService;
//import com.fzy.admin.fp.file.upload.vo.FileInfoVo;
//import com.fzy.admin.fp.pay.pay.domain.TopConfig;
//import com.fzy.admin.fp.pay.pay.domain.TqSxfConfig;
//import com.fzy.admin.fp.pay.pay.util.SxfHttpUtils;
//import com.fzy.admin.fp.sdk.pay.domain.PayRes;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.Resource;
//import java.io.File;
//import java.io.FileInputStream;
//import java.security.spec.InvalidKeySpecException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * 天阙随行付支付测试
// */
//@Slf4j
//public class TqSxfPayTest {
//
//    @Autowired
//    private UploadInfoMd5InfoRelationService uploadInfoMd5InfoRelationService;
//
//    @Value("${file.upload.path}")
//    public String prefixPath;
//
//    //正式接口域名
//    private final String URL = "https://icm-management.suixingpay.com/";
//
//    //天阙随行付生产公钥
//    String tqSxfPublic ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrpcbJhduWqdRWiaeDnIxUaNOG\n" +
//            "6mZeLjGNWOaqkvOyS4QKxFPNZfKqoboZRwkXjFViqSv5B7gVYCFX45HmR3r7t5f4\n" +
//            "Yqak1/BpBQzqd3gAiX4lyARk3bhkurPx/Ai0vDNiqCGbkYKEBrAPGW5hFj9v04XF\n" +
//            "RuBmKvj0ljpTpDURMQIDAQAB";
//
//
//    //正式密钥
//    private String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKulxsmF25ap1FaJ\n" +
//            "p4OcjFRo04bqZl4uMY1Y5qqS87JLhArEU81l8qqhuhlHCReMVWKpK/kHuBVgIVfj\n" +
//            "keZHevu3l/hipqTX8GkFDOp3eACJfiXIBGTduGS6s/H8CLS8M2KoIZuRgoQGsA8Z\n" +
//            "bmEWP2/ThcVG4GYq+PSWOlOkNRExAgMBAAECgYBTtXgZpY8ujcpBHca09660J58p\n" +
//            "B70+pvaBgV6Uxy/TvuI5Qf/AhrxunIqeczcq5cRqZihMvYf33LbGHvRzQl7mAOLk\n" +
//            "QZREqvl7OZ8sbB6N2CfEzcFf/gj2OVLS82ZMJfAEcMOtVml/mJYPa1qRwlZMErje\n" +
//            "sVQXGecAF2XpP4pAxQJBANZvBtdkxxdmXuCs1aY3+TnHEmB2KJaJrfDe3TC4mmQn\n" +
//            "MDVNrKMsZ0XL8ml6FvmZuxxNxcVcRiC+gvzt2Nycox8CQQDM64wtOfuN8sgb2Yhx\n" +
//            "p17sq2XcXljckqpvYLJ7B3Ek0uqN/vwnhwMIkK6tjSm0hRpX6ToMNPYTrdMM51PA\n" +
//            "o5GvAkBo/K0V0lKjw0xapmRoYGlWf4Ag2Fkg80HFd+hGDWwR8xnoTyJiNcBObP8/\n" +
//            "4zXSeREiV3WoHnh7WataWL1frhUzAkEAi/enu4yLQh2+iSdAh0DnGYjI/oiC8cZM\n" +
//            "G99Uiaw/oANgqrSrzuPtMoCAj5KIFgGESN/JAmV6X16vdXTcRAx1iQJBAKG80GIH\n" +
//            "cqszJ7+Lppmglxw4cAmyzwLc2nqjPa657QmVW/uqRYWcv0aIFJ4x866nXqRz8sYY\n" +
//            "wgg6o95SXTVcW0g=";
//    @Test
//    public void uploadPicture() throws Exception {
//        Map<String, Object> params=new HashMap<>();
//        params.put("orgId","66122339");
//        params.put("reqId",ParamUtil.uuid());
//        params.put("pictureType","02");
//        //图片
//        FileInfoVo idCardCopyVo=uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo("1274981699995095040");
//        String idCardCopyPath=prefixPath + idCardCopyVo.getPath();
//        File idCardCopyFile=new File(idCardCopyPath);
//        MultipartFile idCardCopyMultipartFile=new MockMultipartFile("file", new FileInputStream(idCardCopyFile));
//        params.put("file",idCardCopyMultipartFile);
//        uploadPicture(params,"merchant/uploadPicture","图片上传","0000");
//    }
//
//
//
//
//
//
//
//    private void uploadPicture(Map<String, Object> params, String payUrl, String msg, String code) throws Exception {
//
//        HashMap reqMap = JSON.parseObject(JSON.toJSONString(params), LinkedHashMap.class, Feature.OrderedField);
//        //组装加密串
//        String signContent = RSASignature.getOrderContent(reqMap);
//        log.info("==配置密钥【"+privateKey+"】=====");
//        //sign
//        String sign = null;
//        try {
//            sign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
//        } catch (InvalidKeySpecException e) {
//            log.info("密钥配置异常");
//        }
//        reqMap.put("sign", sign);
//        String reqStr = JSON.toJSONString(reqMap);
//        log.info("req" + reqStr);
//        String resultJson = SxfHttpUtils.connectPostUrl(URL + payUrl, reqStr);
//        HashMap<String, Object> resultMap = JSON.parseObject(resultJson, LinkedHashMap.class, Feature.OrderedField);
//        log.info("支付-结果，{}", resultMap);
//
//        if ("SXF0000".equals(resultMap.get("code"))) {
//            //验签
//            String signResult = resultMap.get("sign").toString();
//            resultMap.remove("sign");
//            String resultStr = RSASignature.getOrderContent(resultMap);
//            log.info("resultStr -----------1  " + resultStr);
//            //sign
//            String resultSign = RSASignature.encryptBASE64(RSASignature.sign(signContent, privateKey));
//            log.info("resultSign密匙          " + resultSign);
//            //组装加密串
//            if (RSASignature.doCheck(resultStr, signResult, tqSxfPublic)) {
//                log.info("验签成功");
//                Object respData = resultMap.get("respData");
//                HashMap<String, String> map = JSON.parseObject(JSON.toJSONString(respData), LinkedHashMap.class, Feature.OrderedField);
//                log.info("map------------  " + map);
//                if (code.equals(map.get("bizCode"))) {
//                    if ("订单查询".equals(msg)) {
//                        String status = map.get("tranSts");
//                        if ("SUCCESS".equals(status)) {
//                            log.info("支付成功");
//                        }
//                        if ("PAYING".equals(status)) {
//                            log.info("支付中");
//                        }
//                        if ("REFUND".equals(status)) {
//                            log.info("已退款");
//                        }
//                    }
//                    log.info("成功");
//                }
//                if ("0001".equals(code)) {
//                    log.info("失败");
//                }
//                log.info("失败");
//            } else {
//                log.info("验签失败");
//            }
//        } else {
//            if ("0001".equals(code)) {
//                log.info("失败");
//            }
//            log.info("失败");
//        }
//
//    }
//
//
//    @Test
//    public void merchantInfoQuery() throws Exception {
//        Map<String, Object> params=new HashMap<>();
//        params.put("mno","399200630950538");
//        payLaunch("1",params,"merchant/merchantInfoQuery","商户查询","0000");
//    }
//
//    /**
//     * 天阙随行付统一发起请求
//     * @param merchantId
//     * @param params
//     * @param payUrl
//     * @param msg
//     * @param code
//     * @return
//     * @throws Exception
//     */
//    private PayRes payLaunch(String merchantId, Map<String, Object> params, String payUrl, String msg, String code) throws Exception {
//
//        Map<String, String> req;
//        req = createParam(merchantId);
//        params.put("mno", "399200630950538");
//        req.put("reqData", JSON.toJSONString(params));
//        log.info("==========pay" + req);
//        HashMap reqMap = JSON.parseObject(JSON.toJSONString(req), LinkedHashMap.class, Feature.OrderedField);
//        //组装加密串
//        String signContent = com.fzy.admin.fp.pay.pay.util.RSASignature.getOrderContent(reqMap);
//        log.info("==配置密钥【"+privateKey+"】=====");
//        //sign
//        String sign = null;
//        try {
//            sign = com.fzy.admin.fp.pay.pay.util.RSASignature.encryptBASE64(com.fzy.admin.fp.pay.pay.util.RSASignature.sign(signContent, privateKey));
//        } catch (InvalidKeySpecException e) {
//            return new PayRes( "密钥配置异常" , PayRes.ResultStatus.FAIL);
//        }
//        reqMap.put("sign", sign);
//        String reqStr = JSON.toJSONString(reqMap);
//        log.info("req" + reqStr);
//        String resultJson = SxfHttpUtils.connectPostUrl(URL + payUrl, reqStr);
//        HashMap<String, Object> resultMap = JSON.parseObject(resultJson, LinkedHashMap.class, Feature.OrderedField);
//        log.info("支付-结果，{}", resultMap);
//
//        if ("0000".equals(resultMap.get("code"))) {
//            //验签
//            String signResult = resultMap.get("sign").toString();
//            resultMap.remove("sign");
//            String resultStr = com.fzy.admin.fp.pay.pay.util.RSASignature.getOrderContent(resultMap);
//            System.out.println(resultStr);
//            log.info("resultStr -----------  " + resultStr);
//            //sign
//            String resultSign = com.fzy.admin.fp.pay.pay.util.RSASignature.encryptBASE64(com.fzy.admin.fp.pay.pay.util.RSASignature.sign(signContent, privateKey));
//            System.out.println("resultSign:" + resultSign);
//            log.info("resultSign密匙          " + resultSign);
//            //组装加密串
//            if (com.fzy.admin.fp.pay.pay.util.RSASignature.doCheck(resultStr, signResult, tqSxfPublic)) {
//                log.info("验签成功");
//                Object respData = resultMap.get("respData");
//                HashMap<String, String> map = JSON.parseObject(JSON.toJSONString(respData), LinkedHashMap.class, Feature.OrderedField);
//                log.info("map------------  " + map);
//                if (code.equals(map.get("bizCode"))) {
//                    log.info("订单查询");
//                    if ("订单查询".equals(msg)) {
//                        String status = map.get("tranSts");
//                        if ("SUCCESS".equals(status)) {
//                            return new PayRes("支付成功了", PayRes.ResultStatus.SUCCESS);
//                        }
//                        if ("PAYING".equals(status)) {
//                            return new PayRes("支付中", PayRes.ResultStatus.PAYING);
//                        }
//                        if ("REFUND".equals(status)) {
//                            return new PayRes("已退款", PayRes.ResultStatus.REFUND);
//                        }
//                    }
//                    return new PayRes(msg + "成功", PayRes.ResultStatus.SUCCESS, JacksonUtil.toJson(map));
//                }
//                if ("0001".equals(code)) {
//                    return new PayRes(msg + "失败，" + map.get("bizMsg"), PayRes.ResultStatus.FAIL);
//                }
//                return new PayRes(msg + "失败，" + map.get("bizCode"), PayRes.ResultStatus.FAIL);
//            } else {
//                return new PayRes("验签失败", PayRes.ResultStatus.FAIL);
//            }
//        } else {
//            if ("0001".equals(code)) {
//                return new PayRes(msg + "失败，" + resultMap.get("msg"), PayRes.ResultStatus.FAIL);
//            }
//            return new PayRes(msg + "失败，" + resultMap.get("code"), PayRes.ResultStatus.FAIL);
//        }
//
//    }
//
//    /**
//     * 统一创建构建参数
//     * @param merchantId
//     * @return
//     */
//    private Map<String,String> createParam(String merchantId) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        Map<String, String> params = new TreeMap<>();
//        params.put("orgId", "66122339");
//        params.put("reqId", ParamUtil.uuid());
//        params.put("signType", "RSA");
//        params.put("timestamp", sdf.format(new Date()));
//        params.put("version", "1.0");
//        return params;
//    }
//}
