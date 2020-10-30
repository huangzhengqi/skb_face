package com.fzy.admin.fp.merchant.merchant.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alipay.api.internal.util.StringUtils;
import com.alipay.api.response.AlipayFundAuthOrderFreezeResponse;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.util.TokenUtils;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.MerchantAppletConfig;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.dto.DepositPageDTO;
import com.fzy.admin.fp.merchant.merchant.dto.DepositRefundDTO;
import com.fzy.admin.fp.merchant.merchant.repository.DepositRepository;
import com.fzy.admin.fp.merchant.merchant.repository.StoreRepository;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.dto.OrderDto;
import com.fzy.admin.fp.order.order.repository.OrderRepository;
import com.fzy.admin.fp.order.order.service.OrderService;
import com.fzy.admin.fp.order.order.vo.UnFreezeVO;
import com.fzy.admin.fp.pay.pay.config.MyWxConfig;
import com.fzy.admin.fp.pay.pay.domain.Deposit;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.domain.WxConfig;
import com.fzy.admin.fp.pay.pay.dto.DepositDTO;
import com.fzy.admin.fp.pay.pay.dto.PayDepositDTO;
import com.fzy.admin.fp.pay.pay.dto.RefundDepositDTO;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.pay.pay.repository.WxConfigRepository;
import com.fzy.admin.fp.pay.pay.util.WxDepositUtils;
import com.fzy.admin.fp.sdk.pay.domain.*;
import com.fzy.admin.fp.sdk.pay.feign.AliPayServiceFeign;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
//import java.util.Date;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class DepositService extends Object implements BaseService<Deposit> {
    private static final Logger log = LoggerFactory.getLogger(DepositService.class);


    @Resource
    private OrderService orderService;

    @Resource
    private WxConfigRepository wxConfigRepository;

    @Resource
    private DepositRepository depositRepository;

    @Resource
    private TopConfigRepository topConfigRepository;

    @Resource
    MerchantUserService merchantUserService;

    @Resource
    private AliPayServiceFeign aliPayServiceFeign;

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private MerchantAppletConfigService merchantAppletConfigService;

    @Resource
    private StoreRepository storeRepository;

    @Override
    public DepositRepository getRepository() {
        return this.depositRepository;
    }


//    public Resp deposit(PayDepositDTO payDepositDTO) throws Exception {
//        String officeUserId;
//        if (TokenUtils.getUserId() == null) {
//            return (new Resp()).error(Resp.Status.PARAM_ERROR, "无效token");
//        }
//        MerchantUser user = (MerchantUser)this.merchantUserService.findOne(TokenUtils.getUserId());
//        Store store = (Store)this.storeRepository.findOne(user.getStoreId());
//        if (user == null) {
//            return (new Resp()).error(Resp.Status.PARAM_ERROR, "无效token");
//        }
//
//        if (payDepositDTO.getType().equals(Deposit.TYPE.WX.getCode())) {
//            officeUserId = payDepositDTO.getOpenId();
//        } else {
//            officeUserId = payDepositDTO.getBuyerId();
//        }
//
//        Deposit oldDeposit = this.depositRepository.findTop1ByOfficeUserIdAndMerchantIdAndStatusOrderByCreateTimeDesc(officeUserId, user.getMerchantId(), Deposit.STATUS.FREEZED.getCode());
//        if (oldDeposit != null) {
//            return (new Resp()).error(Resp.Status.PARAM_ERROR, "当前用户有未解冻押金");
//        }
//
//
//        DepositDTO depositDTO = new DepositDTO();
//        depositDTO.setDisCountPrice(new BigDecimal(0));
//        depositDTO.setTotalPrice((new BigDecimal(payDepositDTO.getTotalFee().intValue())).divide(new BigDecimal(100)));
//        depositDTO.setStoreName((store == null || store.getName() == null) ? "" : store.getName());
//        depositDTO.setUsername(user.getUsername());
//
//        if (!StringUtils.isEmpty(payDepositDTO.getOrderNumber())) {
//            this.orderRepository.deleteByOrderNumber(payDepositDTO.getOrderNumber());
//        }
//
//        Deposit deposit = new Deposit();
//        deposit.setMoney((new BigDecimal(payDepositDTO.getTotalFee().intValue())).divide(new BigDecimal(100)));
//        deposit.setLeftMoney((new BigDecimal(payDepositDTO.getTotalFee().intValue())).divide(new BigDecimal(100)));
//        deposit.setUserId(user.getId());
//        deposit.setStoreId(user.getStoreId());
//        deposit.setMerchantId(user.getMerchantId());
//        deposit.setStatus(Deposit.STATUS.FREEZEING.getCode());
//        deposit.setType(payDepositDTO.getType());
//        deposit.setEquipmentId(payDepositDTO.getEquipmentId());
//
//
//        if (payDepositDTO.getType().equals(Deposit.TYPE.WX.getCode())) {
//            deposit.setOfficeUserId(payDepositDTO.getOpenId());
//            this.depositRepository.save(deposit);
//
//
//            Map<String, String> map = payDepositDTO.ininMap();
//            TopConfig topConfig = this.topConfigRepository.findByServiceProviderIdAndDelFlag(user.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
//            if (topConfig == null) {
//                return (new Resp()).error(Resp.Status.PARAM_ERROR, "未配置服务商参数");
//            }
//            map.put("out_trade_no", deposit.getId());
//            WxDepositUtils.sign(map, topConfig.getWxAppKey());
//            MyWxConfig myWxConfig = new MyWxConfig(topConfig.getWxAppId(), topConfig.getWxMchId(), topConfig.getWxAppKey(), topConfig.getWxCertPath());
//            Map<String, String> resultMap = WxDepositUtils.facePay(map, myWxConfig);
//
//            if (((String)resultMap.get("return_code")).equals("SUCCESS") && ((String)resultMap.get("result_code")).equals("SUCCESS")) {
//                deposit.setStatus(Deposit.STATUS.FREEZED.getCode());
//                deposit.setTransactionId((String)resultMap.get("transaction_id"));
//                this.depositRepository.save(deposit);
//                depositDTO.setPayTime(deposit.getCreateTime());
//                depositDTO.setPayWay(Order.PayWay.WXPAY.getCode());
//                depositDTO.setOrderNumber(deposit.getId());
//                return Resp.success(depositDTO, "支付押金成功");
//            }  if (((String)resultMap.get("result_code")).equals("FAIL")) {
//                log.info(JSONUtil.toJsonStr(resultMap));
//
//
//                if (((String)resultMap.get("err_code")).equals("SYSTEMERROR") || ((String)resultMap.get("err_code")).equals("USERPAYING")) {
//
//                    Map<String, String> orderResultMap, orderMap = payDepositDTO.ininOrderMap();
//                    orderMap.put("out_trade_no", deposit.getId());
//                    WxDepositUtils.sign(orderMap, topConfig.getWxAppKey());
//
//                    if (((String)resultMap.get("err_code")).equals("SYSTEMERROR")) {
//                        orderResultMap = WxDepositUtils.orderqQuery(orderMap, myWxConfig);
//                    } else {
//
//                        Thread.sleep(5000L);
//                        orderResultMap = WxDepositUtils.orderqQuery(orderMap, myWxConfig);
//                    }
//                    if (((String)orderResultMap.get("return_code")).equals("SUCCESS") && ((String)orderResultMap.get("result_code")).equals("SUCCESS")) {
//                        deposit.setStatus(Deposit.STATUS.FREEZED.getCode());
//                        deposit.setTransactionId((String)resultMap.get("transaction_id"));
//                        this.depositRepository.save(deposit);
//                        depositDTO.setPayTime(deposit.getCreateTime());
//                        depositDTO.setPayWay(Order.PayWay.WXPAY.getCode());
//                        depositDTO.setOrderNumber(deposit.getId());
//                        return Resp.success(depositDTO, "支付押金成功");
//                    }
//                }
//                deposit.setStatus(Deposit.STATUS.FREEZEFAIL.getCode());
//                this.depositRepository.save(deposit);
//                return (new Resp()).error(Resp.Status.PARAM_ERROR, (String)resultMap.get("err_code_des"));
//            }
//            log.info(JSONUtil.toJsonStr(resultMap));
//            deposit.setStatus(Deposit.STATUS.FREEZEFAIL.getCode());
//            this.depositRepository.save(deposit);
//            return (new Resp()).error(Resp.Status.PARAM_ERROR, (String)resultMap.get("return_msg"));
//        }
//
//
//        if (payDepositDTO.getType().equals(Deposit.TYPE.ALI.getCode())) {
//            deposit.setOfficeUserId(payDepositDTO.getBuyerId());
//            this.depositRepository.save(deposit);
//
//            AlifreezeParam model = new AlifreezeParam();
//            model.setAuth_code(payDepositDTO.getAuthCode());
//            model.setOut_request_no(deposit.getId());
//            model.setOut_order_no(deposit.getId());
//            model.setMerchantId(TokenUtils.getMerchantId());
//
//            String amount = (new BigDecimal(payDepositDTO.getTotalFee().intValue())).divide(new BigDecimal(100)).toPlainString();
//            model.setAmount(amount);
//            PayRes payRes = this.aliPayServiceFeign.freeze(model);
//            if (payRes.getStatus().equals(PayRes.ResultStatus.FAIL)) {
//                deposit.setStatus(Deposit.STATUS.FREEZEFAIL.getCode());
//                this.depositRepository.save(deposit);
//                return (new Resp()).error(Resp.Status.PARAM_ERROR, payRes.getMsg());
//            }
//            deposit.setStatus(Deposit.STATUS.FREEZED.getCode());
//            AlipayFundAuthOrderFreezeResponse alipayFundAuthOrderFreezeResponse = (AlipayFundAuthOrderFreezeResponse)payRes.getObject();
//            deposit.setTransactionId(alipayFundAuthOrderFreezeResponse.getOperationId());
//            deposit.setAuthNo(alipayFundAuthOrderFreezeResponse.getAuthNo());
//            this.depositRepository.save(deposit);
//
//            depositDTO.setPayTime(deposit.getCreateTime());
//            depositDTO.setPayWay(Order.PayWay.ALIPAY.getCode());
//            depositDTO.setOrderNumber(deposit.getId());
//            return Resp.success(depositDTO, "支付押金成功");
//        }
//
//        return Resp.success("成功");
//    }

    public Resp deposit(PayDepositDTO payDepositDTO) throws Exception {

        log.info("payDepositDTO{}", payDepositDTO.toString());
        String officeUserId;
        if (TokenUtils.getUserId() == null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "无效token");
        }
        MerchantUser user = (MerchantUser) this.merchantUserService.findOne(TokenUtils.getUserId());
        Store store = (Store) this.storeRepository.findOne(user.getStoreId());
        if (user == null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "无效token");
        }

        if (payDepositDTO.getType().equals(Deposit.TYPE.WX.getCode())) {
            officeUserId = payDepositDTO.getOpenId();
        } else {
            officeUserId = payDepositDTO.getBuyerId();
        }
        Deposit oldDeposit = this.depositRepository.findTop1ByOfficeUserIdAndMerchantIdAndStatusOrderByCreateTimeDesc(officeUserId, user.getMerchantId(), Deposit.STATUS.FREEZED.getCode());
        if (oldDeposit != null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "当前用户有未解冻押金");
        }
        DepositDTO depositDTO = new DepositDTO();
        depositDTO.setDisCountPrice(new BigDecimal(0));
        depositDTO.setTotalPrice((new BigDecimal(payDepositDTO.getTotalFee())).divide(new BigDecimal(100)));
        depositDTO.setStoreName((store == null || store.getName() == null) ? "" : store.getName());
        depositDTO.setUsername(user.getUsername());

        if (!StringUtils.isEmpty(payDepositDTO.getOrderNumber())) {
            this.orderRepository.deleteByOrderNumber(payDepositDTO.getOrderNumber());
        }
        Deposit deposit = new Deposit();
        deposit.setMoney((new BigDecimal(payDepositDTO.getTotalFee())).divide(new BigDecimal(100)));
        deposit.setLeftMoney((new BigDecimal(payDepositDTO.getTotalFee())).divide(new BigDecimal(100)));
        deposit.setUserId(user.getId());
        deposit.setStoreId(user.getStoreId());
        deposit.setMerchantId(user.getMerchantId());
        deposit.setStatus(Deposit.STATUS.FREEZEING.getCode());
        deposit.setType(payDepositDTO.getType());
        deposit.setEquipmentId(payDepositDTO.getEquipmentId());
        log.info("支付的类型 payDepositDTO.getType() {}", payDepositDTO.getType());
        //微信收押金
        if (payDepositDTO.getType().equals(Deposit.TYPE.WX.getCode())) {
            deposit.setOfficeUserId(payDepositDTO.getOpenId());
            this.depositRepository.save(deposit);
            Map<String, String> map = payDepositDTO.ininMap();
            TopConfig topConfig = this.topConfigRepository.findByServiceProviderIdAndDelFlag(user.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
            if (topConfig == null) {
                return (new Resp()).error(Resp.Status.PARAM_ERROR, "未配置服务商参数");
            }
            map.put("out_trade_no", deposit.getId());
            WxDepositUtils.sign(map, topConfig.getWxAppKey());
            MyWxConfig myWxConfig = new MyWxConfig(topConfig.getWxAppId(), topConfig.getWxMchId(), topConfig.getWxAppKey(), topConfig.getWxCertPath());
            Map<String, String> resultMap = WxDepositUtils.facePay(map, myWxConfig);
            log.info("微信返回的数据（resultMap）{}", resultMap.toString());
            if (((String) resultMap.get("return_code")).equals("SUCCESS") && ((String) resultMap.get("result_code")).equals("SUCCESS")) {
                deposit.setStatus(Deposit.STATUS.FREEZED.getCode());
                deposit.setTransactionId((String) resultMap.get("transaction_id"));
                this.depositRepository.save(deposit);
                depositDTO.setPayTime(deposit.getCreateTime());
                depositDTO.setPayWay(Order.PayWay.WXPAY.getCode());
                depositDTO.setOrderNumber(deposit.getId());
                return Resp.success(depositDTO, "支付押金成功");
            } else if (((String) resultMap.get("return_code")).equals("FAIL")) {
                if(((String) resultMap.get("return_msg")).equals("参数错误")){
                    deposit.setStatus(Deposit.STATUS.FREEZEFAIL.getCode());
                    this.depositRepository.save(deposit);
                    return (new Resp()).error(Resp.Status.PARAM_ERROR, (String) resultMap.get("return_msg"));
                }
                if (((String) resultMap.get("err_code")).equals("SYSTEMERROR") || ((String) resultMap.get("err_code")).equals("USERPAYING")) {
                    Map<String, String> orderResultMap, orderMap = payDepositDTO.ininOrderMap();
                    orderMap.put("out_trade_no", deposit.getId());
                    WxDepositUtils.sign(orderMap, topConfig.getWxAppKey());
                    if (((String) resultMap.get("err_code")).equals("SYSTEMERROR")) {
                        orderResultMap = WxDepositUtils.orderqQuery(orderMap, myWxConfig);
                    } else {
                        Thread.sleep(5000L);
                        orderResultMap = WxDepositUtils.orderqQuery(orderMap, myWxConfig);
                    }
                    if (((String) orderResultMap.get("return_code")).equals("SUCCESS") && ((String) orderResultMap.get("result_code")).equals("SUCCESS")) {
                        deposit.setStatus(Deposit.STATUS.FREEZED.getCode());
                        deposit.setTransactionId((String) resultMap.get("transaction_id"));
                        this.depositRepository.save(deposit);
                        depositDTO.setPayTime(deposit.getCreateTime());
                        depositDTO.setPayWay(Order.PayWay.WXPAY.getCode());
                        depositDTO.setOrderNumber(deposit.getId());
                        return Resp.success(depositDTO, "支付押金成功");
                    }
                }
                deposit.setStatus(Deposit.STATUS.FREEZEFAIL.getCode());
                this.depositRepository.save(deposit);
                return (new Resp()).error(Resp.Status.PARAM_ERROR, (String) resultMap.get("err_code_des"));
            }
            log.info(JSONUtil.toJsonStr(resultMap));
            deposit.setStatus(Deposit.STATUS.FREEZEFAIL.getCode());
            this.depositRepository.save(deposit);
            return (new Resp()).error(Resp.Status.PARAM_ERROR, (String) resultMap.get("return_msg"));
        }


        //支付宝收押金
        if (payDepositDTO.getType().equals(Deposit.TYPE.ALI.getCode())) {
            deposit.setOfficeUserId(payDepositDTO.getBuyerId());
            this.depositRepository.save(deposit);

            AlifreezeParam model = new AlifreezeParam();
            model.setAuth_code(payDepositDTO.getAuthCode());
            model.setOut_request_no(deposit.getId());
            model.setOut_order_no(deposit.getId());
            model.setMerchantId(TokenUtils.getMerchantId());

            String amount = (new BigDecimal(payDepositDTO.getTotalFee())).divide(new BigDecimal(100)).toPlainString();
            model.setAmount(amount);
            PayRes payRes = this.aliPayServiceFeign.freeze(model);
            log.info("PayRes ---------  " + payRes.getMsg());
            if (payRes.getStatus().equals(PayRes.ResultStatus.FAIL)) {
                deposit.setStatus(Deposit.STATUS.FREEZEFAIL.getCode());
                this.depositRepository.save(deposit);
                return (new Resp()).error(Resp.Status.PARAM_ERROR, payRes.getMsg());
            }
            deposit.setStatus(Deposit.STATUS.FREEZED.getCode());
            AlipayFundAuthOrderFreezeResponse alipayFundAuthOrderFreezeResponse = (AlipayFundAuthOrderFreezeResponse) payRes.getObject();
            log.info("alipayFundAuthOrderFreezeResponse -----   " + alipayFundAuthOrderFreezeResponse.getMsg());
            deposit.setTransactionId(alipayFundAuthOrderFreezeResponse.getOperationId());
            deposit.setAuthNo(alipayFundAuthOrderFreezeResponse.getAuthNo());
            this.depositRepository.save(deposit);

            depositDTO.setPayTime(deposit.getCreateTime());
            depositDTO.setPayWay(Order.PayWay.ALIPAY.getCode());
            depositDTO.setOrderNumber(deposit.getId());
            return Resp.success(depositDTO, "支付押金成功");
        }
        log.info("8888888888888888888888");
        return Resp.success("成功");
    }


    public Page<Deposit> getPage(Pageable pageable, DepositPageDTO depositPageDTO) {                                                //TODO
        DateTime dateTime1 = (depositPageDTO.getBeginTime() == null) ? DateUtil.offset(new Date(), DateField.YEAR, -100) : DateTime.of(depositPageDTO.getBeginTime());
        DateTime dateTime2 = (depositPageDTO.getEndTime() == null) ? DateUtil.offset(new Date(), DateField.YEAR, 100) : DateTime.of(depositPageDTO.getEndTime());
//        DateTime dateTime1 = (depositPageDTO.getBeginTime() == null) ? DateUtil.offset(new Date(), DateField.YEAR, -100) : (DateTime) depositPageDTO.getBeginTime();
//        DateTime dateTime2 = (depositPageDTO.getEndTime() == null) ? DateUtil.offset(new Date(), DateField.YEAR, 100) : (DateTime) depositPageDTO.getEndTime();
        if (depositPageDTO.getStatus() == null) {
            return this.depositRepository.getPage(dateTime1, dateTime2, depositPageDTO.getMerchantId(), pageable);
        }
        return this.depositRepository.getPage(dateTime1, dateTime2, depositPageDTO.getStatus(), depositPageDTO.getMerchantId(), pageable);
    }


    public Resp<UnFreezeVO> refundDeposit(RefundDepositDTO refundDepositDTO) throws Exception {
        String officeUserId;
        MerchantUser user = (MerchantUser) this.merchantUserService.findOne(TokenUtils.getUserId());
        if (user == null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "无效token");
        }

        AliUnFreezeParam model = new AliUnFreezeParam();
        BeanUtil.copyProperties(refundDepositDTO, model);


        if (refundDepositDTO.getType().equals(Deposit.TYPE.WX.getCode())) {
            officeUserId = refundDepositDTO.getOpenId();
        } else {
            officeUserId = refundDepositDTO.getBuyerId();
        }

        Deposit deposit = this.depositRepository.findTop1ByOfficeUserIdAndMerchantIdAndStatusOrderByCreateTimeDesc(officeUserId, user.getMerchantId(), Deposit.STATUS.FREEZED.getCode());
        if (deposit == null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "当前用户没有押金");
        }

        BigDecimal used = (new BigDecimal(refundDepositDTO.getUsedFee().intValue())).divide(new BigDecimal(100));
        BigDecimal left = deposit.getMoney().subtract(used);
        if (left.compareTo(new BigDecimal(0)) == -1) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "消费金额不能超过押金，用户缴纳押金为：" + deposit.getMoney().toPlainString() + "元");
        }


        UnFreezeVO unFreezeVO = new UnFreezeVO();
        unFreezeVO.setAllMoney(deposit.getMoney());
        unFreezeVO.setUsedMoney(used);
        unFreezeVO.setLeftMoney(deposit.getMoney().subtract(used));

        unFreezeVO.setDisCountPrice(new BigDecimal(0));
        unFreezeVO.setTotalPrice(used);
        Store store = (Store) this.storeRepository.findOne(user.getStoreId());
        unFreezeVO.setStoreName((store == null || store.getName() == null) ? "" : store.getName());
        unFreezeVO.setUsername(user.getUsername());

        Order order = new Order();

        if (refundDepositDTO.getUsedFee().intValue() > 0) {
            OrderDto orderDto = new OrderDto();
            orderDto.setPayWay(refundDepositDTO.getType());
            orderDto.setMerchantId(user.getMerchantId());
            orderDto.setStoreId(user.getStoreId());
            orderDto.setStatus(Order.Status.SUCCESSPAY.getCode());
            orderDto.setTotalPrice(used);
            orderDto.setUserId(user.getId());
            orderDto.setUserName(user.getUsername());
            orderDto.setStoreName(user.getStoreName());
            orderDto.setPayClient(Order.PayClient.APP.getCode());
            orderDto.setRemarks("押金消费");
            orderDto.setOpenId(refundDepositDTO.getOpenId());
            orderDto.setOrderNumber(refundDepositDTO.getOrderNumber());
            orderDto.setEquipmentId(refundDepositDTO.getEquipmentId());
            order = this.orderService.createOrder(orderDto);
        }


        if (refundDepositDTO.getType().equals(Deposit.TYPE.WX.getCode())) {
            TopConfig topConfig = this.topConfigRepository.findByServiceProviderIdAndDelFlag(user.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
            MyWxConfig myWxConfig = new MyWxConfig(topConfig.getWxAppId(), topConfig.getWxMchId(), topConfig.getWxAppKey(), topConfig.getWxCertPath());

            if (refundDepositDTO.getUsedFee().intValue() > 0) {
                Map<String, String> map = refundDepositDTO.ininMap();

                if (topConfig == null) {
                    return (new Resp()).error(Resp.Status.PARAM_ERROR, "未配置服务商参数");
                }
                map.put("total_fee", deposit.getMoney().multiply(new BigDecimal(100)).setScale(0).toPlainString());
                map.put("consume_fee", refundDepositDTO.getUsedFee().toString());
                map.put("transaction_id", deposit.getTransactionId());
                WxDepositUtils.sign(map, topConfig.getWxAppKey());
                Map<String, String> resultMap = WxDepositUtils.consume(map, myWxConfig);

                if (((String) resultMap.get("return_code")).equals("FAIL"))
                    return (new Resp()).error(Resp.Status.PARAM_ERROR, (String) resultMap.get("return_msg"));
                if (((String) resultMap.get("result_code")).equals("FAIL")) {
                    return (new Resp()).error(Resp.Status.PARAM_ERROR, (String) resultMap.get("err_code_des"));
                }
                order.setStatus(Order.Status.SUCCESSPAY.getCode());
                this.orderRepository.saveAndFlush(order);
                deposit.setLeftMoney(deposit.getMoney().subtract(used));
                deposit.setOrderId(order.getId());
                deposit.setStatus(Deposit.STATUS.UNFREEZE.getCode());
                this.depositRepository.save(deposit);

                unFreezeVO.setPayTime(order.getCreateTime());
                unFreezeVO.setPayWay(Order.PayWay.WXPAY.getCode());
                unFreezeVO.setOrderNumber(order.getOrderNumber());
                return Resp.success(unFreezeVO, "退还押金成功");
            }


            return Resp.success(unFreezeVO, "退还押金不能小于0");
        }


        if (refundDepositDTO.getUsedFee().intValue() > 0) {

            AlifreezePayParam payParam = new AlifreezePayParam();
            payParam.setMerchantId(user.getMerchantId());
            payParam.setAuth_no(deposit.getAuthNo());
            payParam.setTotal_amount(used.toPlainString());
            payParam.setOut_trade_no(deposit.getTransactionId());
            payParam.setBuyer_id(refundDepositDTO.getBuyerId());

            payParam.setAuth_confirm_mode("COMPLETE");
            PayRes payResult = this.aliPayServiceFeign.freezePay(payParam);
            if (payResult.getStatus().equals(PayRes.ResultStatus.FAIL)) {
                throw new Exception("消费押金失败");
            }
            order.setStatus(Order.Status.SUCCESSPAY.getCode());
            this.orderRepository.saveAndFlush(order);
            deposit.setLeftMoney(deposit.getMoney().subtract(used));
            deposit.setOrderId(order.getId());
            deposit.setStatus(Deposit.STATUS.UNFREEZE.getCode());
            this.depositRepository.save(deposit);

            unFreezeVO.setPayTime(order.getCreateTime());
            unFreezeVO.setPayWay(Order.PayWay.ALIPAY.getCode());
            unFreezeVO.setOrderNumber(order.getOrderNumber());

            return Resp.success(unFreezeVO, "退还押金成功");
        }


        model.setAmount(left.toPlainString());
        model.setMerchantId(user.getMerchantId());
        model.setAuth_no(deposit.getAuthNo());
        model.setOut_request_no(deposit.getTransactionId());
        PayRes payRes = this.aliPayServiceFeign.unfreeze(model);

        if (payRes.getStatus().equals(PayRes.ResultStatus.FAIL)) {
            throw new Exception("退还押金失败");
        }
        order.setStatus(Order.Status.SUCCESSPAY.getCode());
        this.orderService.save(order);
        deposit.setLeftMoney(deposit.getMoney().subtract(used));
        deposit.setOrderId(order.getId());
        deposit.setStatus(Deposit.STATUS.UNFREEZE.getCode());
        this.depositRepository.save(deposit);

        unFreezeVO.setPayTime(order.getCreateTime());
        unFreezeVO.setPayWay(Order.PayWay.ALIPAY.getCode());
        unFreezeVO.setOrderNumber(order.getOrderNumber());
        return Resp.success(unFreezeVO, "退还押金成功");
    }


    public void wxRefundDepositUsed(Order order, Deposit deposit, BigDecimal refundFee) throws Exception {
        TopConfig topConfig = this.topConfigRepository.findByServiceProviderIdAndDelFlag(order.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
        MyWxConfig myWxConfig = new MyWxConfig(topConfig.getWxAppId(), topConfig.getWxMchId(), topConfig.getWxAppKey(), topConfig.getWxCertPath());
        WxConfig wxConfig = this.wxConfigRepository.findByMerchantIdAndDelFlag(order.getMerchantId(), CommonConstant.NORMAL_FLAG);
        MerchantAppletConfig merchantAppletConfig = this.merchantAppletConfigService.findByMerchantId(order.getMerchantId());
        Map<String, String> refundMap = new HashMap<String, String>();
        refundMap.put("appid", topConfig.getWxAppId());
        if (merchantAppletConfig != null && !StringUtil.isEmpty(merchantAppletConfig.getAppId())) {
            refundMap.put("sub_appid", merchantAppletConfig.getAppId());
        }
        refundMap.put("mch_id", topConfig.getWxMchId());
        refundMap.put("sub_mch_id", wxConfig.getSubMchId());
        refundMap.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        refundMap.put("sign_type", "HMAC-SHA256");
        refundMap.put("total_fee", deposit.getMoney().multiply(new BigDecimal(100)).setScale(0).toPlainString());
        refundMap.put("refund_fee", refundFee.multiply(new BigDecimal(100)).setScale(0).toPlainString());
        refundMap.put("transaction_id", deposit.getTransactionId());
        refundMap.put("out_refund_no", deposit.getId());
        WxDepositUtils.sign(refundMap, topConfig.getWxAppKey());

        Map<String, String> refundResultMap = WxDepositUtils.refund(refundMap, myWxConfig);

        if (((String) refundResultMap.get("return_code")).equals("FAIL")) {
            log.info(JSONUtil.toJsonStr(refundResultMap));
            throw new BaseException((String) refundResultMap.get("return_msg"));
        }
        if (((String) refundResultMap.get("result_code")).equals("FAIL")) {
            log.info(JSONUtil.toJsonStr(refundResultMap));
            throw new BaseException((String) refundResultMap.get("err_code_des"));
        }

        order.setRefundPayPrice(order.getRefundPayPrice().add(refundFee));

        if (order.getActPayPrice().compareTo(order.getRefundPayPrice()) == 0) {
            order.setStatus(Order.Status.REFUNDTOTAL.getCode());
        } else {
            order.setStatus(Order.Status.REFUNDPART.getCode());
        }
        this.orderService.save(order);
    }


    public void aliRefundDepositUsed(Order order, Deposit deposit, BigDecimal refundPayPrice) throws Exception {
        AliRefundDepoistParam aliRefundDepoistParam = new AliRefundDepoistParam();
        aliRefundDepoistParam.setMerchantId(order.getMerchantId());
        aliRefundDepoistParam.setOut_trade_no(deposit.getTransactionId());
        aliRefundDepoistParam.setRefund_amount(refundPayPrice.toPlainString());
        PayRes payRes = this.aliPayServiceFeign.refundDeposit(aliRefundDepoistParam);
        if (payRes.getStatus().equals(PayRes.ResultStatus.FAIL)) {
            throw new BaseException(payRes.getMsg());
        }

        order.setRefundPayPrice(order.getRefundPayPrice().add(refundPayPrice));

        if (order.getActPayPrice().compareTo(order.getRefundPayPrice()) == 0) {
            order.setStatus(Order.Status.REFUNDTOTAL.getCode());
        } else {
            order.setStatus(Order.Status.REFUNDPART.getCode());
        }
        this.orderService.save(order);
    }


    /**
     * 商户后台退押金
     *
     * @param deposit
     * @param user
     * @param depositRefundDTO
     * @return
     */
    public Resp<UnFreezeVO> refund(Deposit deposit, MerchantUser user, DepositRefundDTO depositRefundDTO) throws Exception {

        AliUnFreezeParam model = new AliUnFreezeParam();
        BeanUtil.copyProperties(deposit, model);

        BigDecimal used = (new BigDecimal(depositRefundDTO.getUsedFee().intValue())).divide(new BigDecimal(100)); //剩余金额
        BigDecimal left = deposit.getMoney().subtract(used); //押金金额-剩余金额

        log.info("前端传过来的参数------------" + depositRefundDTO.toString());
        log.info("剩余押金used----------    " + used);
        log.info("消费押金left----------    " + left);

        if (left.compareTo(new BigDecimal(0)) == -1) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "消费金额不能超过押金，用户缴纳押金为：" + deposit.getMoney().toPlainString() + "元");
        }

        UnFreezeVO unFreezeVO = new UnFreezeVO();
        unFreezeVO.setAllMoney(deposit.getMoney());
        unFreezeVO.setUsedMoney(used);
        unFreezeVO.setLeftMoney(deposit.getMoney().subtract(used));

        unFreezeVO.setDisCountPrice(new BigDecimal(0));
        unFreezeVO.setTotalPrice(used);
        Store store = (Store) this.storeRepository.findOne(user.getStoreId());
        unFreezeVO.setStoreName((store == null || store.getName() == null) ? "" : store.getName());
        unFreezeVO.setUsername(user.getUsername());

        Order order = new Order();

        log.info("depositRefundDTO.getUsedFee().intValue() -------------  " + depositRefundDTO.getUsedFee().intValue());

        if (depositRefundDTO.getUsedFee().intValue() > 0) {
            OrderDto orderDto = new OrderDto();
            orderDto.setPayWay(deposit.getType());
            orderDto.setMerchantId(user.getMerchantId());
            orderDto.setStoreId(user.getStoreId());
            orderDto.setStatus(Order.Status.SUCCESSPAY.getCode());
            orderDto.setTotalPrice(used);
            orderDto.setUserId(user.getId());
            orderDto.setUserName(user.getUsername());
            orderDto.setStoreName(user.getStoreName());
            orderDto.setPayClient(Order.PayClient.APP.getCode());
            orderDto.setRemarks("押金消费");
//          orderDto.setOpenId(refundDepositDTO.getOpenId());
            orderDto.setOrderNumber(deposit.getOrderNumber());
            orderDto.setEquipmentId(deposit.getEquipmentId());
            order = this.orderService.createOrder(orderDto);
        }

        //微信退押金
        if (deposit.getType().equals(Deposit.TYPE.WX.getCode())) {
            TopConfig topConfig = this.topConfigRepository.findByServiceProviderIdAndDelFlag(user.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
            MyWxConfig myWxConfig = new MyWxConfig(topConfig.getWxAppId(), topConfig.getWxMchId(), topConfig.getWxAppKey(), topConfig.getWxCertPath());

            //商户参数
            WxConfig wxConfig = wxConfigRepository.findByMerchantIdAndDelFlag(deposit.getMerchantId(), CommonConstant.NORMAL_FLAG);
            //商户小程序配置
            MerchantAppletConfig merchantAppletConfig = merchantAppletConfigService.findByMerchantId(deposit.getMerchantId());

            if (wxConfig == null) {
                return (new Resp()).error(Resp.Status.PARAM_ERROR, "当前商户未配置参数");
            }
            if (topConfig == null) {
                return (new Resp()).error(Resp.Status.PARAM_ERROR, "未配置服务商参数");
            }
            if (ParamUtil.isBlank(merchantAppletConfig)) {
                throw new BaseException("还未配置小程序参数", Resp.Status.PARAM_ERROR.getCode());
            }

            if (depositRefundDTO.getUsedFee().intValue() > 0) {
//                Map<String, String> map = refundDepositDTO.ininMap();
                Map<String, String> map = new HashMap<String, String>();
                map.put("appid", topConfig.getWxAppId());
                map.put("sub_appid", merchantAppletConfig.getAppId());
                map.put("mch_id", topConfig.getWxMchId());
                map.put("sub_mch_id", wxConfig.getSubMchId());
                map.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
                map.put("fee_type", "CNY");
                map.put("sign_type", "HMAC-SHA256");
                map.put("total_fee", deposit.getMoney().multiply(new BigDecimal(100)).setScale(0).toPlainString());
                map.put("consume_fee", depositRefundDTO.getUsedFee().toString());
                map.put("transaction_id", deposit.getTransactionId());
                log.info("map数据 ---------------" + map);
                WxDepositUtils.sign(map, topConfig.getWxAppKey());
                Map<String, String> resultMap = WxDepositUtils.consume(map, myWxConfig);
                log.info("resultMap ----    " + resultMap.toString());

                if (((String) resultMap.get("return_code")).equals("FAIL"))
                    return (new Resp()).error(Resp.Status.PARAM_ERROR, (String) resultMap.get("return_msg"));
                if (((String) resultMap.get("result_code")).equals("FAIL")) {
                    return (new Resp()).error(Resp.Status.PARAM_ERROR, (String) resultMap.get("err_code_des"));
                }
                order.setStatus(Order.Status.SUCCESSPAY.getCode());
                this.orderRepository.saveAndFlush(order);
                deposit.setLeftMoney(deposit.getMoney().subtract(used));
                deposit.setOrderId(order.getId());
                deposit.setStatus(Deposit.STATUS.UNFREEZE.getCode());
                this.depositRepository.save(deposit);

                unFreezeVO.setPayTime(order.getCreateTime());
                unFreezeVO.setPayWay(Order.PayWay.WXPAY.getCode());
                unFreezeVO.setOrderNumber(order.getOrderNumber());
                return Resp.success(unFreezeVO, "退还押金成功");
            }
            return Resp.success(unFreezeVO, "退还押金不能小于0");
        }

        //支付宝退押金
        if (depositRefundDTO.getUsedFee().intValue() > 0) {

            AlifreezePayParam payParam = new AlifreezePayParam();
            payParam.setMerchantId(user.getMerchantId());
            payParam.setAuth_no(deposit.getAuthNo());
            payParam.setTotal_amount(used.toPlainString());
            payParam.setOut_trade_no(deposit.getTransactionId());
            payParam.setBuyer_id(deposit.getOfficeUserId());

            payParam.setAuth_confirm_mode("COMPLETE");
            PayRes payResult = this.aliPayServiceFeign.freezePay(payParam);
            if (payResult.getStatus().equals(PayRes.ResultStatus.FAIL)) {
                order.setStatus(Order.Status.FAILPAY.getCode());
                order.setRemarks("消费押金失败");
                this.orderRepository.saveAndFlush(order);
                throw new Exception("消费押金失败");
            }
            order.setStatus(Order.Status.SUCCESSPAY.getCode());
            this.orderRepository.saveAndFlush(order);
            deposit.setLeftMoney(deposit.getMoney().subtract(used));
            deposit.setOrderId(order.getId());
            deposit.setStatus(Deposit.STATUS.UNFREEZE.getCode());
            this.depositRepository.save(deposit);

            unFreezeVO.setPayTime(order.getCreateTime());
            unFreezeVO.setPayWay(Order.PayWay.ALIPAY.getCode());
            unFreezeVO.setOrderNumber(order.getOrderNumber());

            return Resp.success(unFreezeVO, "退还押金成功");
        }

        model.setAmount(left.toPlainString());
        model.setMerchantId(user.getMerchantId());
        model.setAuth_no(deposit.getAuthNo());
        model.setOut_request_no(deposit.getTransactionId());
        PayRes payRes = this.aliPayServiceFeign.unfreeze(model);
        log.info("payRes   " + payRes.getMsg());

        if (payRes.getStatus().equals(PayRes.ResultStatus.FAIL)) {
            throw new Exception("退还押金失败");
        }
        order.setStatus(Order.Status.SUCCESSPAY.getCode());
        this.orderService.save(order);
        deposit.setLeftMoney(deposit.getMoney().subtract(used));
        deposit.setOrderId(order.getId());
        deposit.setStatus(Deposit.STATUS.UNFREEZE.getCode());
        this.depositRepository.save(deposit);

        unFreezeVO.setPayTime(order.getCreateTime());
        unFreezeVO.setPayWay(Order.PayWay.ALIPAY.getCode());
        unFreezeVO.setOrderNumber(order.getOrderNumber());
        log.info("退还押金成功");
        return Resp.success(unFreezeVO, "退还押金成功");
    }
}
