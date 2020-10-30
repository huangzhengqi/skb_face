package com.fzy.admin.fp.order.third.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.validation.Validation;
import com.fzy.admin.fp.common.validation.domain.BindingResult;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.order.third.util.ThirdPayUtil;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.validation.Validation;
import com.fzy.admin.fp.common.validation.domain.BindingResult;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.dto.OrderDto;
import com.fzy.admin.fp.order.order.dto.OrderRefundDTO;
import com.fzy.admin.fp.order.order.service.OrderService;
import com.fzy.admin.fp.order.third.dto.ThirdPayDTO;
import com.fzy.admin.fp.order.third.dto.ThirdQueryDTO;
import com.fzy.admin.fp.order.third.dto.ThirdRefundDTO;
import com.fzy.admin.fp.order.third.util.ThirdPayUtil;
import com.fzy.admin.fp.order.third.vo.ThirdMerchantVO;
import com.fzy.admin.fp.order.third.vo.ThirdPayVO;
import com.fzy.admin.fp.order.third.vo.ThirdQueryVO;
import com.fzy.admin.fp.order.third.vo.ThirdRefundVO;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserDTO;
import com.fzy.admin.fp.sdk.merchant.domain.SnConfigVO;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantBusinessService;
import com.fzy.admin.fp.sdk.pay.config.PayChannelConstant;
import com.fzy.admin.fp.sdk.pay.domain.CommonQueryParam;
import com.fzy.admin.fp.sdk.pay.domain.FyPayParam;
import com.fzy.admin.fp.sdk.pay.domain.PayRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Map;

/**
 * @author Created by wtl on 2019-06-03 17:32
 * @description 第三方支付业务
 */
@Service
@Transactional
@Slf4j
public class ThirdPayService {


    @Resource
    private OrderService orderService;

    @Resource
    private MerchantBusinessService merchantBusinessService;

    /**
     * @author Created by wtl on 2019/6/12 10:20
     * @Description 根据sn号获取商户号和密钥
     */
    public ThirdMerchantVO downloadConfig(String sn) {
        if (ParamUtil.isBlank(sn)) {
            throw new BaseException("sn序列号不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        // 判断sn号是否已被系统录入
        SnConfigVO snConfigVO = merchantBusinessService.findBySn(sn);
        if (ParamUtil.isBlank(snConfigVO)) {
            throw new BaseException("sn对应的设备还未录入系统", Resp.Status.PARAM_ERROR.getCode());
        }
        // 判断支付参数是否已被下载过
        if (SnConfigVO.Flag.FINISH.getCode().equals(snConfigVO.getFlag())) {
            throw new BaseException("已经获取过密钥", Resp.Status.PARAM_ERROR.getCode());
        }
        // 根据sn号返回商户对应的商户号和appKey
        ThirdMerchantVO thirdMerchantVO = new ThirdMerchantVO();
        thirdMerchantVO.setMid(snConfigVO.getMid());
        thirdMerchantVO.setAppKey(snConfigVO.getAppKey());
        // 该sn对应的配置设置为已下载
        merchantBusinessService.editSnConfig(sn, SnConfigVO.Flag.FINISH.getCode());
        return thirdMerchantVO;
    }


    /**
     * @author Created by wtl on 2019/6/3 17:35
     * @Description 被扫支付
     */
    public ThirdPayVO microPay(ThirdPayDTO model) throws Exception {
        if (model.getTotalFee() == null) {
            throw new BaseException("订单金额为空", Resp.Status.PARAM_ERROR.getCode());
        }
        // 请求参数校验
        if (model.getTotalFee().scale() > 2) {
            throw new BaseException("订单金额最小为0.01元", Resp.Status.PARAM_ERROR.getCode());
        }
        checkParam(model);
        // 签名校验
        MerchantUserDTO merchantUserDTO = checkSign(model);
        if (orderService.getRepository().countByOrderNumberOrOutTradeNo(null, model.getOutTradeNo()) > 0) {
            throw new BaseException("订单号已存在", Resp.Status.PARAM_ERROR.getCode());
        }
        // 调用支付
        Order order = scanPay(model, merchantUserDTO);
        // 返回值处理
        return payResult(order, merchantUserDTO.getAppKey());
    }

    /**
     * @author Created by wtl on 2019/6/4 14:50
     * @Description 订单查询
     */
    public ThirdQueryVO orderQuery(ThirdQueryDTO model) {
        // 请求参数校验
        checkParam(model);
        // 签名校验
        MerchantUserDTO merchantUserDTO = checkSign(model);
        Order order = orderService.getRepository().findByOutTradeNo(model.getOutTradeNo());
        if (ParamUtil.isBlank(order)) {
            throw new BaseException("查无此订单", Resp.Status.PARAM_ERROR.getCode());
        }
        // 只有支付中和支付成功的订单才需要调用对应支付通道平台接口轮询查询，其他直接查询本地数据库返回结果
        if (!Order.Status.PLACEORDER.getCode().equals(order.getStatus()) && !Order.Status.SUCCESSPAY.getCode().equals(order.getStatus())) {
            ThirdQueryVO thirdQueryVO = createThirdQueryVO(order);
            thirdQueryVO.setStatus(order.getStatus());
            // 参数签名
            String sign = ThirdPayUtil.sign(ThirdPayUtil.createTreeMap(thirdQueryVO), merchantUserDTO.getAppKey());
            thirdQueryVO.setSign(sign);
            return thirdQueryVO;
        }

        PayRes payRes = null;
        // 查询订单，支付宝/微信/易融码
        if (PayChannelConstant.Channel.OFFICIAL.getCode().equals(order.getPayChannel())) {
            if (PayChannelConstant.PayWay.WXPAY.getCode().equals(order.getPayWay())) {
                // 微信
                payRes = orderService.getWxOrderService().getWxPayServiceFeign().wxOrderQuery(order.getMerchantId(), order);
            } else {
                // 支付宝
                payRes = orderService.getAliOrderService().getAliPayServiceFeign().aliOrderQuery(order.getMerchantId(), order.getOrderNumber());
            }
        }
        // 易融码通道查询订单
        else if (PayChannelConstant.Channel.YRM.getCode().equals(order.getPayChannel())) {
            payRes = orderService.getYrmOrderService().getYrmPayServiceFeign().yrmOrderQuery(order.getMerchantId(), order.getOrderNumber());
        }
        //随行付通道查询订单
        else if (PayChannelConstant.Channel.SXF.getCode().equals(order.getPayChannel())) {
            CommonQueryParam commonQueryParam = new CommonQueryParam();
            commonQueryParam.setMerchantId(order.getMerchantId());
            commonQueryParam.setOrderNumber(order.getOrderNumber());
            payRes = orderService.getSxfOrderService().getSxfPayServiceFeign().sxfOrderQuery(commonQueryParam);
        }
        //富友通道查询订单
        else if (PayChannelConstant.Channel.FY.getCode().equals(order.getPayChannel())) {
            FyPayParam fyPayParam = new FyPayParam();
            fyPayParam.setMerchantId(order.getMerchantId());
            if (order.getPayWay().equals(Order.PayWay.WXPAY.getCode())) {
                fyPayParam.setPayType(FyPayParam.PayType.WECHAT.getCode());
            } else if (order.getPayWay().equals(Order.PayWay.ALIPAY.getCode())) {
                fyPayParam.setPayType(FyPayParam.PayType.ALIPAY.getCode());
            } else {
                throw new BaseException("订单支付方式异常" + order, Resp.Status.PARAM_ERROR.getCode());
            }
            fyPayParam.setOutTradeNo(order.getOrderNumber());
            payRes = orderService.getFyOrderService().getFyPayServiceFeign().fyOrderQuery(fyPayParam);
        } else {
            throw new BaseException("查询订单失败", Resp.Status.PARAM_ERROR.getCode());
        }
        return queryResult(payRes, order, merchantUserDTO.getAppKey());
    }

    /**
     * @author Created by wtl on 2019/6/10 14:29
     * @Description 退款
     */
    public ThirdRefundVO refund(ThirdRefundDTO model) throws Exception {
        if (model.getRefundPrice() == null) {
            throw new BaseException("退款金额为空", Resp.Status.PARAM_ERROR.getCode());
        }
        // 请求参数校验
        if (model.getRefundPrice().scale() > 2) {
            throw new BaseException("订单金额最小为0.01元", Resp.Status.PARAM_ERROR.getCode());
        }
        checkParam(model);
        // 签名校验
        MerchantUserDTO merchantUserDTO = checkSign(model);
        // 商户密码校验
        if (!BCrypt.checkpw(model.getPassword(), merchantUserDTO.getPassword())) {
            throw new BaseException("密码错误", Resp.Status.PARAM_ERROR.getCode());
        }
        OrderRefundDTO orderRefundDTO = new OrderRefundDTO();
        orderRefundDTO.setOrderNumber(model.getOutTradeNo());
        orderRefundDTO.setRefundPayPrice(model.getRefundPrice());
        orderRefundDTO.setPassword(model.getPassword());
        orderRefundDTO.setRefundUserId(merchantUserDTO.getId());
        // 调用退款业务
        Order orderResult = orderService.refund(orderRefundDTO);

        ThirdRefundVO thirdRefundVO = new ThirdRefundVO();
        thirdRefundVO.setStatus(orderResult.getStatus());
        thirdRefundVO.setNonceStr(RandomUtil.randomString(15));
        thirdRefundVO.setOutTradeNo(orderResult.getOutTradeNo());
        thirdRefundVO.setOrderNumber(orderResult.getOrderNumber());
        thirdRefundVO.setRefundPrice(orderRefundDTO.getRefundPayPrice()); // 本次退款金额
        thirdRefundVO.setRemainPrice(orderResult.getActPayPrice().subtract(orderResult.getRefundPayPrice())); // 剩余可退款金额
        thirdRefundVO.setSign(ThirdPayUtil.sign(ThirdPayUtil.createTreeMap(thirdRefundVO), merchantUserDTO.getAppKey()));
        return thirdRefundVO;
    }

    // 请求参数校验
    private void checkParam(Object model) {
        BindingResult bindingResult = Validation.valid(model);
        if (!bindingResult.isFlag()) {
            throw new BaseException(bindingResult.getMessage().get(0), Resp.Status.PARAM_ERROR.getCode());
        }
    }

    // 签名校验
    private MerchantUserDTO checkSign(Object model) {
        Map<String, String> map = JacksonUtil.toStringMap(JacksonUtil.toJson(model));
        MerchantUserDTO merchantUserDTO = orderService.getMerchantUserFeign().findKey(map.get("mid"));
        if ("error".equals(merchantUserDTO.getAppKey())) {
            throw new BaseException("当前设备还未绑定商户", Resp.Status.PARAM_ERROR.getCode());
        }
        // 支付参数转成TreeMap
        Map<String, Object> params = ThirdPayUtil.createTreeMap(model);
        // 签名校验
        boolean flag = ThirdPayUtil.verify(params, map.get("sign"), merchantUserDTO.getAppKey());
        if (!flag) {
            throw new BaseException("签名错误", Resp.Status.PARAM_ERROR.getCode());
        }
        return merchantUserDTO;
    }

    // 订单查询返回值处理
    private ThirdQueryVO queryResult(PayRes payRes, Order order, String appKey) {
        ThirdQueryVO thirdQueryVO = createThirdQueryVO(order);
        // payRes：1：支付成功；2：支付失败；3：支付中；4：退款中；5：已退款
        if (PayRes.ResultStatus.SUCCESS.equals(payRes.getStatus())) {
            thirdQueryVO.setStatus(Order.Status.SUCCESSPAY.getCode());
            order.setStatus(Order.Status.SUCCESSPAY.getCode());
        } else if (PayRes.ResultStatus.PAYING.equals(payRes.getStatus())) {
            thirdQueryVO.setStatus(Order.Status.PLACEORDER.getCode());
        } else {
            thirdQueryVO.setStatus(Order.Status.FAILPAY.getCode());
        }
        // 参数签名
        String sign = ThirdPayUtil.sign(ThirdPayUtil.createTreeMap(thirdQueryVO), appKey);
        thirdQueryVO.setSign(sign);
        // 更新平台的订单状态
        order.setStatus(thirdQueryVO.getStatus());
        orderService.save(order);
        return thirdQueryVO;
    }

    // 查询返回对象填充，不包括签名
    private ThirdQueryVO createThirdQueryVO(Order order) {
        ThirdQueryVO thirdQueryVO = new ThirdQueryVO();
        thirdQueryVO.setNonceStr(RandomUtil.randomString(15));
        thirdQueryVO.setOutTradeNo(order.getOutTradeNo());
        thirdQueryVO.setOrderNumber(order.getOrderNumber());
        thirdQueryVO.setMid(order.getMerchantId());
        thirdQueryVO.setTotalFee(order.getActPayPrice());
        thirdQueryVO.setRemainFee(order.getActPayPrice().subtract(order.getRefundPayPrice()));
        return thirdQueryVO;
    }


    // 调用订单模块的扫码支付
    private Order scanPay(ThirdPayDTO model, MerchantUserDTO merchantUserDTO) throws Exception {
        // 构建扫码支付需要的参数
        OrderDto orderDto = new OrderDto();
        orderDto.setPayClient(Order.PayClient.OTHER.getCode());
        orderDto.setUserId(merchantUserDTO.getId());
        orderDto.setTotalPrice(model.getTotalFee());
        orderDto.setAuthCode(model.getAuthCode());
        orderDto.setOutTradeNo(model.getOutTradeNo());
        return orderService.scanPay(orderDto, Order.InterFaceWay.OTHER);
    }


    // 支付返回值处理
    private ThirdPayVO payResult(Order order, String appKey) {
        ThirdPayVO thirdPayVO = new ThirdPayVO();
        thirdPayVO.setStatus(order.getStatus());
        thirdPayVO.setNonceStr(RandomUtil.randomString(15));
        thirdPayVO.setOutTradeNo(order.getOutTradeNo());
        thirdPayVO.setOrderNumber(order.getOrderNumber());
        thirdPayVO.setTotalFee(order.getActPayPrice());
        // 参数签名
        String sign = ThirdPayUtil.sign(ThirdPayUtil.createTreeMap(thirdPayVO), appKey);
        thirdPayVO.setSign(sign);
        return thirdPayVO;
    }

}
