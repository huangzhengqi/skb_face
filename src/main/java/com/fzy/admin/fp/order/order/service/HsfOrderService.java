package com.fzy.admin.fp.order.order.service;


import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.sdk.pay.domain.CommonPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import com.fzy.admin.fp.sdk.pay.feign.HsfPayServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author Created by wtl on 2019-05-21 21:02
 * @description 惠闪付支付业务
 */
@Service
@Slf4j
@Transactional
public class HsfOrderService extends BaseContent {

    @Resource
    private HsfPayServiceFeign hsfPayServiceFeign;
    @Resource
    private CommissionService commissionService;

    /**
     * @author Created by wtl on 2019/5/21 22:06
     * @Description 惠闪付扫码支付，支付结果是同步返回的，输入密码时候通道接口会自己
     */
    public void hsfScanPay(Order order, CommonPayParam.HsfType hsfType, Order.InterFaceWay interFaceWay) throws Exception {
        // 惠闪付扫码支付
        CommonPayParam commonPayParam = new CommonPayParam();
        commonPayParam.setMerchantId(order.getMerchantId());
        commonPayParam.setOrderNumber(order.getOrderNumber());
        commonPayParam.setType(hsfType.getCode());
        commonPayParam.setTotalFee(order.getActPayPrice());
        commonPayParam.setAuthCode(order.getAuthCode());
        PayRes payRes = hsfPayServiceFeign.hsfScanPay(commonPayParam);
        // 支付失败
        if (PayRes.ResultStatus.FAIL.equals(payRes.getStatus())) {
            throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
        }
        // 保存惠闪付订单号
        order.setTransactionId((String) payRes.getObject());
        // 支付成功或开放接口给第三方，不需要这里进行轮询查询订单
        if (PayRes.ResultStatus.SUCCESS.equals(payRes.getStatus())) {
            // 支付成功
            order.setStatus(Order.Status.SUCCESSPAY.getCode());
            order.setPayTime(new Date());
            return;
        }
        // 开放接口给第三方，不需要这里进行轮询查询订单
        if (Order.InterFaceWay.OTHER.equals(interFaceWay)) {
            order.setPayTime(new Date());
        }
        commissionService.EditCommissionStatus(order.getId(),order.getStatus());
    }
}
