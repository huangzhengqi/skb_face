package com.fzy.admin.fp.pay.pay.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.pay.pay.repository.TtsConfigRepository;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.TtsPayParam;
import com.fzy.admin.fp.DomainInterface;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.pay.pay.domain.TtsConfig;
import com.fzy.admin.fp.pay.pay.dto.TtsPayDTO;
import com.fzy.admin.fp.pay.pay.repository.TtsConfigRepository;
import com.fzy.admin.fp.pay.pay.util.PayUtil;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.domain.TtsPayParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Created by wtl on 2019-05-31 9:54
 * @description 统统收 支付业务
 */
@Slf4j
@Service
public class TtsPayService extends PayService {

    // 接口域名
    private final String URL = "http://39.100.6.242:8070/goblin/api/";

    @Resource
    private TtsConfigRepository ttsConfigRepository;

    /**
     * @author Created by wtl on 2019/5/31 10:07
     * @Description 构建通用请求参数
     */
    public TtsPayDTO createParam(TtsPayParam model) {
        // 根据商户id查询易融码支付参数
        TtsConfig config = ttsConfigRepository.findByMerchantIdAndDelFlag(model.getMerchantId(), CommonConstant.NORMAL_FLAG);
        Map<String, Object> params = new TreeMap<>();
        params.put("outTradeNo", model.getOutTradeNo());
        params.put("reqTime", DateUtil.format(new Date(), "yyyyMMddHHmmss"));
        params.put("partnerId", config.getPartnerId());
        params.put("mchId", config.getMchId());
        // 2个支付接口的额外参数
        if (model.isPayFlag()) {
            params.put("businessCode", model.getPayType());
            params.put("goodsId", System.currentTimeMillis() + "" + RandomUtil.randomInt(4));
            params.put("goodsType", "1");
            params.put("orderAmount", model.getTotalFee().stripTrailingZeros().toPlainString());
            params.put("provinceCode", config.getProvinceCode());
            params.put("cityCode", config.getCityCode());
            params.put("regionCode", config.getRegionCode());
            params.put("notifyUrl", getDomain() + "/order/callback/tts_order_callback");
        }
        TtsPayDTO ttsPayDTO = new TtsPayDTO();
        //获取Hutool拷贝实例
        CopyOptions copyOptions = CopyOptions.create();
        //忽略为null值得属性
        copyOptions.setIgnoreNullValue(true);
        //进行属性拷贝
        BeanUtil.copyProperties(config, ttsPayDTO, copyOptions);
        ttsPayDTO.setParams(params);
        return ttsPayDTO;
    }


    /**
     * @author Created by wtl on 2019/5/31 10:06
     * @Description 扫码支付，用户扫商家
     */
    public PayRes qrCode(TtsPayParam ttsPayParam) {
        TtsPayDTO ttsPayDTO = createParam(ttsPayParam);
        Map<String, Object> params = ttsPayDTO.getParams();
        params.put("goodsName", "秒到扫码支付");
        // 参数签名
        params.put("signature", PayUtil.ttsSign(params, ttsPayDTO.getPrivateKey()));
        // 请求接口
        String result = HttpUtil.post(URL + "/pay/qrcode", params);
        log.info("扫码支付结果，{}", result);
        return apiResult(1, result, ttsPayDTO.getPublicKey());
    }

    /**
     * @author Created by wtl on 2019/5/31 10:37
     * @Description 条形码支付
     */
    public PayRes barCode(TtsPayParam ttsPayParam) {
        TtsPayDTO ttsPayDTO = createParam(ttsPayParam);
        Map<String, Object> params = ttsPayDTO.getParams();
        params.put("goodsName", "秒到付款码支付");
        params.put("scene", "1");
        params.put("authCode", "1"); // 条形码
        params.put("signature", PayUtil.ttsSign(params, ttsPayDTO.getPrivateKey()));
        String result = HttpUtil.post(URL + "/pay/barcode", params);
        log.info("条形码支付结果，{}", result);
        return apiResult(2, result, ttsPayDTO.getPublicKey());

    }

    /**
     * @author Created by wtl on 2019/5/31 11:17
     * @Description 查询订单
     */
    public PayRes query(TtsPayParam ttsPayParam) {
        TtsPayDTO ttsPayDTO = createParam(ttsPayParam);
        Map<String, Object> params = ttsPayDTO.getParams();
        params.put("signature", PayUtil.ttsSign(params, ttsPayDTO.getPrivateKey()));
        String result = HttpUtil.post(URL + "/pay/query", params);
        return apiResult(3, result, ttsPayDTO.getPublicKey());
    }

    /**
     * 接口调用结果
     */
    public PayRes apiResult(Integer type, String result, String publicKey) {
        Map<String, Object> resultMap = JacksonUtil.toObjectMap(result);
        if ((Integer) resultMap.get("code") != 200) {
            log.info("支付失败，{}", resultMap.get("message"));
            return new PayRes("接口调用失败，" + resultMap.get("message"), PayRes.ResultStatus.FAIL);
        }
        // 返回的数据，需要校验签名
        Map<String, Object> dataMap = JacksonUtil.toObjectMap(resultMap.get("data"));
        if (!PayUtil.ttsVerify(dataMap, (String) dataMap.get("signature"), publicKey)) {
            return new PayRes("返回数据签名错误", PayRes.ResultStatus.FAIL);
        }
        if (type == 1) {
            // 扫码支付
            return new PayRes("下单成功", PayRes.ResultStatus.SUCCESS, dataMap.get("codeUrl"));
        }
        // 付款码支付和查询订单，都要根据状态判断
        Integer orderStatus = (Integer) dataMap.get("orderStatus");
        // 处理中
        if (orderStatus == 1) {
            return new PayRes("处理中", PayRes.ResultStatus.PAYING);
        }
        if (orderStatus == 3) {
            return new PayRes("支付失败", PayRes.ResultStatus.FAIL);
        }
        return new PayRes("支付成功", PayRes.ResultStatus.SUCCESS);

    }

}
