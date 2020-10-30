package com.fzy.admin.fp.merchant.thirdMchInfo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import com.fzy.admin.fp.merchant.thirdMchInfo.domain.HsfMchInfo;
import com.fzy.admin.fp.merchant.thirdMchInfo.service.HsfMchInfoService;
import com.fzy.admin.fp.merchant.thirdMchInfo.vo.HsfMchInfoDTO;
import com.fzy.admin.fp.sdk.pay.feign.HsfPayServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 11:55 2019/6/25
 * @ Description:
 **/
@Slf4j
@RestController
@RequestMapping("/merchant/hsf_mch_info")
public class HsfMchInfoController extends BaseController<HsfMchInfo> {

    //慧闪付进件请求接口
    private static final String hsfMerchantUrl = "http://internal.congmingpay.com/internal/registernewmerchant.do";

    //慧闪付进件生成签名的key
    private static final String hsfKey = "2542049EF00711E7475F088CC0227966";


    @Resource
    private MchInfoService mchInfoService;


    @Resource
    private HsfMchInfoService hsfMchInfoService;

    @Resource
    private HsfPayServiceFeign hsfPayServiceFeign;


    @Override
    public HsfMchInfoService getService() {
        return hsfMchInfoService;
    }


    /*
     * @author drj
     * @date 2019-06-26 14:14
     * @Description :获取进件详情
     */
    @GetMapping("/detail")
    public Resp detail(String merchantId) {
        if (ParamUtil.isBlank(merchantId)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        HsfMchInfo hsfMchInfo = hsfMchInfoService.getRepository().findByMerchantId(merchantId);
        return Resp.success(hsfMchInfo);
    }


    /*
     * @author drj
     * @date 2019-06-26 11:27
     * @Description :录入进件资料回显
     */
    @GetMapping("/entry")
    public Resp entry(String merchantId) {
        if (ParamUtil.isBlank(merchantId)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        HsfMchInfo hsfMchInfo = hsfMchInfoService.getRepository().findByMerchantId(merchantId);
        //如果慧闪付进件为空,创建一个
        if (ParamUtil.isBlank(hsfMchInfo)) {
            hsfMchInfo = new HsfMchInfo();
            hsfMchInfo.setMerchantId(merchantId); //合作商号写死
            hsfMchInfo.setAgent_id("agent_9650_539319"); //合作商号写死
            hsfMchInfo.setNotify_url(getDomain() + "/call_back"); //回调地址
            hsfMchInfo.setStatus(HsfMchInfo.Status.NOTOPEN.getCode());
        }
        //查询该商户对应的平台进件列表
        MchInfo mchInfo = mchInfoService.getRepository().findByMerchantId(hsfMchInfo.getMerchantId());
        //如果平台进件列表存在
        if (!ParamUtil.isBlank(mchInfo)) {
            //经营联系信息负责人
            if (ParamUtil.isBlank(hsfMchInfo.getShop_keeper()) && !ParamUtil.isBlank(mchInfo.getContact())) {
                hsfMchInfo.setShop_keeper(mchInfo.getContact());
            }
            //经营联系信息负责人电话
            if (ParamUtil.isBlank(hsfMchInfo.getKeeper_phone()) && !ParamUtil.isBlank(mchInfo.getPhone())) {
                hsfMchInfo.setKeeper_phone(mchInfo.getPhone());
            }
            //经营联系信息负责人邮箱
            if (ParamUtil.isBlank(hsfMchInfo.getEmail()) && !ParamUtil.isBlank(mchInfo.getEmail())) {
                hsfMchInfo.setEmail(mchInfo.getEmail());
            }
            //经营基本信息简称
            if (ParamUtil.isBlank(hsfMchInfo.getShop_nickname()) && !ParamUtil.isBlank(mchInfo.getShortName())) {
                hsfMchInfo.setShop_nickname(mchInfo.getShortName());
            }
            //经营基本信息一级经营类别
            if (ParamUtil.isBlank(hsfMchInfo.getType()) && !ParamUtil.isBlank(mchInfo.getBusinessLevOne())) {
                hsfMchInfo.setType(mchInfo.getBusinessLevOne());
            }
            //经营基本信息二级经营类别
            if (ParamUtil.isBlank(hsfMchInfo.getClassify()) && !ParamUtil.isBlank(mchInfo.getBusinessLevTwo())) {
                hsfMchInfo.setClassify(mchInfo.getBusinessLevTwo());
            }
            //营业执照号
            if (ParamUtil.isBlank(hsfMchInfo.getLicence_no()) && !ParamUtil.isBlank(mchInfo.getLicense())) {
                hsfMchInfo.setLicence_no(mchInfo.getLicense());
            }
            //开始营业期限
            if (ParamUtil.isBlank(hsfMchInfo.getLicence_begin_date()) && !ParamUtil.isBlank(mchInfo.getStartBusinessTime())) {
                hsfMchInfo.setLicence_begin_date(mchInfo.getStartBusinessTime());
            }
            //结束营业期限
            if (ParamUtil.isBlank(hsfMchInfo.getLicence_expire_date()) && !ParamUtil.isBlank(mchInfo.getEndBusinessTime())) {
                hsfMchInfo.setLicence_expire_date(mchInfo.getEndBusinessTime());
            }
            //法人姓名
            if (ParamUtil.isBlank(hsfMchInfo.getArtif_name()) && !ParamUtil.isBlank(mchInfo.getRepresentativeName())) {
                hsfMchInfo.setArtif_name(mchInfo.getRepresentativeName());
            }
            //法人身份证号
            if (ParamUtil.isBlank(hsfMchInfo.getArtif_identity()) && !ParamUtil.isBlank(mchInfo.getCertificateNum())) {
                hsfMchInfo.setArtif_identity(mchInfo.getCertificateNum());
            }
            //身份证起始日期
            if (ParamUtil.isBlank(hsfMchInfo.getIdentity_start_time()) && !ParamUtil.isBlank(mchInfo.getStartCertificateTime())) {
                hsfMchInfo.setIdentity_start_time(mchInfo.getStartCertificateTime());
            }
            //身份证截止日期
            if (ParamUtil.isBlank(hsfMchInfo.getIdentity_expire_time()) && !ParamUtil.isBlank(mchInfo.getEndCertificateTime())) {
                hsfMchInfo.setIdentity_expire_time(mchInfo.getEndCertificateTime());
            }
            //结算卡号
            if (ParamUtil.isBlank(hsfMchInfo.getCard()) && !ParamUtil.isBlank(mchInfo.getAccountNumber())) {
                hsfMchInfo.setCard(mchInfo.getAccountNumber());
            }
            //结算银行
            if (ParamUtil.isBlank(hsfMchInfo.getBank_name()) && !ParamUtil.isBlank(mchInfo.getBankName())) {
                hsfMchInfo.setBank_name(mchInfo.getBankName());
            }
            //结算开户行
            if (ParamUtil.isBlank(hsfMchInfo.getBank_address()) && !ParamUtil.isBlank(mchInfo.getBankOutlet())) {
                hsfMchInfo.setBank_address(mchInfo.getBankOutlet());
            }
            //结算联行号
            if (ParamUtil.isBlank(hsfMchInfo.getBank_add_no()) && !ParamUtil.isBlank(mchInfo.getBankCity())) {
                hsfMchInfo.setBank_add_no(mchInfo.getBankCity());
            }
        }

        hsfMchInfoService.save(hsfMchInfo);
        return Resp.success(hsfMchInfo);
    }


    /*
     * @author drj
     * @date 2019-06-25 19:26
     * @Description :将第三方进件资料提交给慧闪付
     */
    @PostMapping("/internal/registernewmerchant")
    public Resp registernewmerchant(String merchantId) {
        HsfMchInfo hsfMchInfo = hsfMchInfoService.getRepository().findByMerchantId(merchantId);
        if (ParamUtil.isBlank(hsfMchInfo)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "进件资料为空,请先完善");
        }
        //判定商户进件是否重复操作
        if (!ParamUtil.isBlank(hsfMchInfo.getShopId())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该商户已经进件,请勿重复操作");
        }
        HsfMchInfoDTO hsfMchInfoDTO = new HsfMchInfoDTO();
        //获取Hutool拷贝实例
        CopyOptions copyOptions = CopyOptions.create();
        //忽略为null值得属性
        copyOptions.setIgnoreNullValue(true);
        //进行属性拷贝
        BeanUtil.copyProperties(hsfMchInfo, hsfMchInfoDTO, copyOptions);
        //将对象为空字段集转成map
        JSONObject jsonObject = JSONUtil.parseObj(hsfMchInfoDTO);
        Map<String, Object> map = new TreeMap<>(jsonObject);

        StringBuilder paramBuilder = new StringBuilder();
        map.forEach((k, v) -> {
            paramBuilder.append(k).append("=");//.append(v).append("&");
            if (v instanceof Date) {
                paramBuilder.append(DateUtil.format((Date) v, "yyyy-MM-dd"));
            } else {
                paramBuilder.append(v);
            }
            paramBuilder.append("&");
        });
        paramBuilder.append("key").append("=").append(hsfKey);
        String paramStr = paramBuilder.toString();
        String paramSignature = md5(paramStr).toUpperCase();
        map.put("sign", paramSignature);
        String result = HttpUtil.post(hsfMerchantUrl, JacksonUtil.toJson(map));
        Map<String, Object> resultMap = JacksonUtil.toObjectMap(result);
        //如果返回成功
        if ("success".equals(resultMap.get("result_code"))) {
            //保存shop_id,并改变签约状态
            hsfMchInfo.setShopId(resultMap.get("shop_id").toString());
            hsfMchInfo.setStatus(HsfMchInfo.Status.AUDIT.getCode());
            hsfMchInfoService.save(hsfMchInfo);
            return Resp.success("调用进件成功");
        }
        if ("fail".equals(resultMap.get("result_code"))) {
            hsfMchInfo.setStatus(HsfMchInfo.Status.SIGNFAIL.getCode());
            hsfMchInfoService.save(hsfMchInfo);
            throw new BaseException(resultMap.get("error_msg").toString(), Resp.Status.PARAM_ERROR.getCode());
        }
        return new Resp().error(Resp.Status.PARAM_ERROR, "调用进件失败");
    }


    /*
     * @author drj
     * @date 2019-06-27 10:46
     * @Description :慧闪付进件回调
     */
    @PostMapping("/call_back")
    public void callBack() throws IOException {
        log.info("进入慧闪付进件");
        ServletInputStream sis = request.getInputStream();
        int size = request.getContentLength();
        byte[] buffer = new byte[size];
        byte[] in_b = new byte[size];
        int count = 0;
        int rbyte = 0;
        while (count < size) {
            rbyte = sis.read(buffer);
            for (int i = 0; i < rbyte; i++) {
                in_b[count + i] = buffer[i];
            }
            count += rbyte;
        }
        //进件返回结果
        String result = new String(in_b, 0, in_b.length);
        log.info("进件返回结果result{}" + result);
        //转map
        Map<String, Object> map = new HashMap<>();
        map = JacksonUtil.toObjectMap(result);

        //如果进件状态成功
        if ("success".equals(map.get("result_code"))) {
            String shop_id = map.get("shop_id").toString();
            //获取进件
            HsfMchInfo hsfMchInfo = hsfMchInfoService.getRepository().findByShopId(shop_id);
            //获取商户号
            if (!ParamUtil.isBlank(hsfMchInfo)) {
                //改变进件状态
                hsfMchInfo.setStatus(HsfMchInfo.Status.SIGNSUCCESS.getCode());
                hsfMchInfoService.save(hsfMchInfo);
                //完善慧闪付进件shopId跟key
                boolean flag = hsfPayServiceFeign.hsfInit(hsfMchInfo.getMerchantId(), shop_id, hsfKey);
                if (!flag) {
                    log.info("慧闪付配置完善失败");
                }
            }
        } else {
            log.info("慧闪付进件审核失败");
        }
    }

    //MD5加密
    private static String md5(String string) {
        if (string == null || string.trim().equals("")) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] data = digest.digest(string.getBytes("utf-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                String result = Integer.toHexString(data[i] & 0xff);
                String temp = null;
                if (result.length() == 1) {
                    temp = "0" + result;
                } else {
                    temp = result;
                }
                sb.append(temp);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
