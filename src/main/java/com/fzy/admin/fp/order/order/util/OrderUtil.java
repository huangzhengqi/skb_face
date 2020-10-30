package com.fzy.admin.fp.order.order.util;


import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Created by wtl on 2019-04-26 10:30
 * @description 订单处理工具
 */
@Slf4j
public class OrderUtil {

    /**
     * @author Created by wtl on 2019/4/26 10:26
     * @Description 支付回调参数转map
     */
    public static Map<String, String> params2Map(Map<String, String[]> requestParams) {
        Map<String, String> params = new HashMap<>();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == (values.length - 1)) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }

    /**
     * @author Created by wtl on 2019/5/4 23:05
     * @Description 根据订单状态code获取value
     */
    public static String getValueByCode(Integer code) {
        for (Order.PayWay payWay : Order.PayWay.values()) {
            if (code.equals(payWay.getCode())) {
                return payWay.getStatus();
            }
        }
        return null;
    }

    /**
     * @author Created by wtl on 2019/6/3 23:44
     * @Description 扫码（刷卡）支付轮询查询
     */
    public static boolean payQuery(Supplier<PayRes> function, PayRes payRes) {
        /**
         * 需要输入支付密码
         * 客户端轮询查询当前支付订单的状态
         */
        // 查询次数，45次，45秒
        int queryCount = 45;
        boolean queryFlag = false;
        while (queryCount > 0) {
            // 查询支付结果
            PayRes ps = function.get();
            if (PayRes.ResultStatus.FAIL.equals(ps.getStatus())) {
                log.info("订单失败原因 ：============== {}",payRes.getMsg());
                throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
            }
            // 订单支付中，一直查询直到成功支付或者超时
            if (PayRes.ResultStatus.PAYING.equals(ps.getStatus())) {
                queryCount = queryCount - 1;
            } else {
                queryCount = 0;
                queryFlag = true;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return queryFlag;
    }


    /**
     * @Description 退款订单轮询查询
     */
    public static boolean refundPayQuery(Supplier<PayRes> function, PayRes payRes) {
        /**
         *  有时候退款查询没有那么快查询到
         */
        int queryCount = 100; // 查询次数，100次，100秒
        boolean queryFlag = false;
        while (queryCount > 0) {
            // 查询退款结果
            PayRes ps = function.get();
            // 退款失败
            if (PayRes.ResultStatus.REFUNDFAIL.equals(ps.getStatus())) {
                throw new BaseException(payRes.getMsg(), Resp.Status.PARAM_ERROR.getCode());
            }
            // 订单退款中，一直查询直到退款成功或者超时
            if (PayRes.ResultStatus.REFUNDING.equals(ps.getStatus())) {
                queryCount = queryCount - 1;
            } else {
                queryCount = 0;
                queryFlag = true;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return queryFlag;
    }

}
