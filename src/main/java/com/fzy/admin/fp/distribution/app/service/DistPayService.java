package com.fzy.admin.fp.distribution.app.service;

import com.aliyuncs.exceptions.ClientException;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.auth.utils.DateUtils;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.controller.NoteController;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.money.domain.AccountDetail;
import com.fzy.admin.fp.distribution.money.domain.Wallet;
import com.fzy.admin.fp.distribution.money.service.AccountDetailService;
import com.fzy.admin.fp.distribution.money.service.WalletService;
import com.fzy.admin.fp.distribution.order.domain.OrderGoods;
import com.fzy.admin.fp.distribution.order.domain.ShopOrder;
import com.fzy.admin.fp.distribution.order.service.AfterSaleService;
import com.fzy.admin.fp.distribution.order.service.OrderGoodsService;
import com.fzy.admin.fp.distribution.order.service.ShopOrderService;
import com.fzy.admin.fp.distribution.order.service.WxUserService;
import com.fzy.admin.fp.distribution.pc.domain.*;
import com.fzy.admin.fp.distribution.pc.repository.SendStatusRepository;
import com.fzy.admin.fp.distribution.pc.service.NoteConfigService;
import com.fzy.admin.fp.distribution.pc.service.SystemSetupService;
import com.fzy.admin.fp.distribution.shop.service.DistGoodsService;
import com.fzy.admin.fp.distribution.shop.service.ShopCartService;
import com.fzy.admin.fp.pay.pay.config.MyWxConfig;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.schedule.model.OrderJob;
import com.fzy.admin.fp.schedule.utils.ScheduleUtils;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author yy
 * @Date 2019-11-27 11:55:27
 * @Desp
 **/
@Slf4j
@Service
public class DistPayService {

    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;
    @Resource
    private DistUserService distUserService;
    @Resource
    private WalletService walletService;
    @Resource
    private AccountDetailService accountDetailService;
    @Resource
    private ShopOrderService shopOrderService;
    @Resource
    private OrderGoodsService orderGoodsService;
    @Resource
    private ShopCartService shopCartService;
    @Resource
    private DistGoodsService distGoodsService;
    @Resource
    protected TopConfigRepository topConfigRepository;
    @Resource
    private SystemSetupService systemSetupService;
    @Autowired
    private AfterSaleService afterSaleService;
    @Resource
    private WxUserService wxUserService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private NoteConfigService noteConfigService;
    @Resource
    private SendStatusRepository sendStatusRepository;

    private final String SUCCESS = "SUCCESS";

   /* @Transactional
    public String refund(ShopOrder shopOrder){
        //如果订单存在余额支付，则给余额退款
        if(shopOrder.getBalancePay().compareTo(new BigDecimal("0"))==1){
            setWallet(shopOrder.getUserId(),shopOrder.getServiceProviderId(),"商城退款",0,0,shopOrder.getBalancePay(),null);

          *//*Wallet wallet=walletService.getRepository().findByUserId(shopOrder.getUserId());
            wallet.setBalance(wallet.getBalance().add(shopOrder.getBalancePay()));
            walletService.update(wallet);
            AccountDetail accountDetail=new AccountDetail();
            accountDetail.setBalance(wallet.getBalance());
            accountDetail.setSum(shopOrder.getBalancePay());
            accountDetail.setType(0);
            accountDetail.setUserId(wallet.getUserId());
            accountDetail.setServiceProviderId(wallet.getServiceProviderId());
            accountDetail.setStatus(0);
            //记录账户流水
            accountDetailService.save(accountDetail);*//*
        }
        shopOrder.setStatus(5);
        shopOrder.setRefundTime(new Date());
        shopOrderService.update(shopOrder);
        //给支付宝退款
        if(shopOrder.getAliPay().compareTo(new BigDecimal("0"))==1){
            TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(shopOrder.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
            AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, topConfig.getAliMobilePayId(), topConfig.getAliMobilePrivateKey(), AlipayConfig.FORMAT, AlipayConfig.CHARSET, topConfig.getAliMobilePublicKey(),AlipayConfig.SIGNTYPE);
            AlipayTradeRefundRequest requests = new AlipayTradeRefundRequest();
            AlipayTradeRefundModel bizModel = new AlipayTradeRefundModel();
            bizModel.setOutTradeNo(shopOrder.getOrderNumber());
            bizModel.setRefundAmount(shopOrder.getAliPay().toString());
            bizModel.setRefundReason("商品退款");
            requests.setBizModel(bizModel);
            AlipayTradeRefundResponse response = null;
            try {
                response = client.execute(requests);
                if (!response.isSuccess()) {
                    return response.getBody();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new BaseException("系统出错", Resp.Status.PARAM_ERROR.getCode());
            }
        }
        return "退款成功";
    }
*/

    /**
     * 添加订单
     *
     * @param shopOrder
     * @param oderPropertyList
     */
    @Transactional(rollbackOn = Exception.class)
    public void addOrder(ShopOrder shopOrder, List<OrderGoods> oderPropertyList) {
        //保存订单
        ShopOrder save = shopOrderService.save(shopOrder);
        //保存订单商品属性信息
        oderPropertyList.stream().forEach(s -> s.setShopOrderId(save.getId()));
        orderGoodsService.save(oderPropertyList);
        OrderJob orderJob = new OrderJob();
        orderJob.setShopOrderId(save.getId());
        ScheduleUtils.createScheduleJob(scheduler, save.getId(), "OrderJob", DateUtils.getCron(DateUtils.getDayByNum(new Date(), 1)), orderJob);
    }


    /**
     * 设置库存
     * @param oderPropertyList
     */
   /* public void setGoods(List<OrderGoods> oderPropertyList){
        for (OrderGoods orderGoods:oderPropertyList){
            DistGoods one = distGoodsService.findOne(orderGoods.getGoodsId());
            if(one.getNum()!=-1){
                one.setNum(one.getNum()-orderGoods.getNum());
            }
            distGoodsService.update(one);
        }
    }
*/
    /**
     * 用余额全款购买设备
     * @param shopOrder
     * @param wallet
     */
    /*@Transactional
    public void balancePay(ShopOrder shopOrder, Wallet wallet,List<OrderGoods> oderPropertyList){
        //保存订单
        ShopOrder save = shopOrderService.save(shopOrder);
        //保存订单商品属性信息
        oderPropertyList.stream().forEach(s->s.setShopOrderId(save.getId()));
        orderGoodsService.save(oderPropertyList);
        //修改用户的余额
        setWallet(shopOrder.getUserId(),shopOrder.getServiceProviderId(),"商城购物",1,1,shopOrder.getPrice().multiply(new BigDecimal(-1)),null);

       *//* wallet.setBalance(wallet.getBalance().subtract(shopOrder.getPrice()));
        walletService.update(wallet);
        AccountDetail accountDetail=new AccountDetail();
        accountDetail.setBalance(wallet.getBalance());
        accountDetail.setSum(shopOrder.getPrice());
        accountDetail.setType(1);
        accountDetail.setUserId(wallet.getUserId());
        accountDetail.setServiceProviderId(wallet.getServiceProviderId());
        accountDetail.setStatus(1);
        //记录账户流水
        accountDetailService.save(accountDetail);*//*
        //清除购物车
        shopCartService.getRepository().deleteByUserId(shopOrder.getUserId());
        setGoods(oderPropertyList);
    }
*/

    /**
     * 支付宝购买商品
     *
     * @param shopOrder
     */
    @Transactional(rollbackOn = Exception.class)
    public void shopAliPay(ShopOrder shopOrder, String totalAmount) {

        //只对未支付的订单进行处理
        if (shopOrder.getStatus() != null && shopOrder.getStatus() != 0) {
            return;
        }

        //订单支付的金额是否和付款的金额相同,如果不同则为异常订单
        if (!shopOrder.getPrice().toString().equals(totalAmount)) {
            shopOrder.setStatus(2);
            shopOrder.setRecord("支付宝支付金额与订单金额不符");
            shopOrderService.update(shopOrder);
            return;
        }
        shopOrder.setStatus(1);
        //支付完成时间
        shopOrder.setPayTime(new Date());
        shopOrderService.update(shopOrder);

        //查询订单总共卖出的商品数量，如果是刷脸设备
        List<OrderGoods> allByShopOrderId = orderGoodsService.getRepository().findAllByShopOrderId(shopOrder.getId());
        int num=0;
        for(OrderGoods orderGoods:allByShopOrderId){
            Integer type = distGoodsService.findOne(orderGoods.getGoodsId()).getType();
            if(type==1){
                num=num+orderGoods.getNum();
            }
        }
        if(num>0){
            SystemSetup systemSetup = systemSetupService.getRepository().findByServiceProviderId(shopOrder.getServiceProviderId());
            DistUser distUser = distUserService.findOne(shopOrder.getUserId());
            distUser.setBuyNum(distUser.getBuyNum()+num);
            //购买rise台设备就成为合伙人
            if(distUser.getGrade()==0&&distUser.getBuyNum()>=systemSetup.getRise()){
                distUser.setGrade(1);
                distUser.setBecomeTime(new Date());
            }
            distUserService.update(distUser);
        }

        //清除购物车
        shopCartService.deleteByUserId(shopOrder.getUserId());
        ScheduleUtils.deleteScheduleJob(scheduler, shopOrder.getId(), "OrderJob");

        log.info("支付宝支付开始发短信通知   ----- ");
        //发短信进行通知
        try {

            sendOrder(shopOrder);

        } catch (Exception e) {
            log.info("短信异常信息 ---------  " + e.getMessage());
            new Resp().error(Resp.Status.PARAM_ERROR, "发送短信异常");
        }
    }

    /**
     * 计算提成
     *
     * @param userId
     * @param price
     * @param commissions
     * @param serviceProviderId
     */
    @Transactional(rollbackOn = Exception.class)
    public void calculateBonus(String userId, BigDecimal price, Integer commissions, String serviceProviderId) {
        AccountDetail accountDetail = new AccountDetail();
        //拿到二级代理的钱包，并算出该拿的提成
        //使用悲观锁避免在并发情形下出现出现其他线程把数据覆盖
        Wallet wallet = walletService.getRepository().findByUserId(userId);
        BigDecimal bonus = price.multiply(new BigDecimal(commissions * 0.01));
        BigDecimal oldBalance = wallet.getBalance();
        BigDecimal oldBonus = wallet.getBalance();
        wallet.setBalance(wallet.getBalance().add(bonus));
        wallet.setBonus(wallet.getBonus().add(bonus));
        walletService.getRepository().updateWallet(new Date(), wallet.getBalance(), wallet.getBonus(), wallet.getId(), oldBalance, oldBonus);
        accountDetail.setSum(bonus);
        accountDetail.setBalance(wallet.getBalance());
        accountDetail.setUserId(userId);
        accountDetail.setType(0);
        accountDetail.setServiceProviderId(serviceProviderId);
        accountDetail.setName("代理提成");
        accountDetail.setStatus(0);
        accountDetailService.save(accountDetail);
    }

    /**
     * DES 计算用户余额与流水
     *
     * @param userId            用户id
     * @param serviceProviderId 服务商id
     * @param accountName       流水的名称
     * @param accountType       流水的类型 0 提成 1消费 2申请提现 3提现驳回
     * @param accountStatus     0入账 1提现
     * @param price             金额
     * @param commissions       提成
     */
    public void setWallet(String userId, String serviceProviderId, String accountName, Integer accountType, Integer accountStatus, BigDecimal price, Integer commissions) {
        Wallet wallet = walletService.getRepository().findByUserId(userId);
        BigDecimal oldBonus = wallet.getBalance();
        BigDecimal oldBalance = wallet.getBalance();
        //计算提成
        if (commissions != null) {
            BigDecimal bonus = price.multiply(new BigDecimal(commissions * 0.01));
            wallet.setBonus(wallet.getBonus().add(bonus));
            price = bonus;
        }
        wallet.setBalance(wallet.getBalance().add(price));

        walletService.getRepository().updateWallet(new Date(), wallet.getBalance(), wallet.getBonus(), wallet.getId(), oldBalance, oldBonus);

        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setSum(price);
        accountDetail.setBalance(wallet.getBalance());
        accountDetail.setUserId(userId);
        accountDetail.setType(accountType);
        accountDetail.setServiceProviderId(serviceProviderId);
        accountDetail.setName(accountName);
        accountDetail.setStatus(accountStatus);
        accountDetailService.save(accountDetail);
    }

    /**
     * 微信购买商品
     *
     * @param config
     * @param notifyData
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    public String payBack(MyWxConfig config, String notifyData) {

        WXPay wxpay = new WXPay(config);
        String xmlBack = "";
        Map<String, String> notifyMap = null;
        try {
            // 调用官方SDK转换成map类型数据
            notifyMap = WXPayUtil.xmlToMap(notifyData);

            log.info("(调用官方SDK转换成map类型数据)notifyMap   ---" + notifyMap.toString());
            //验证签名是否有效，有效则进一步处理
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {

                //状态
                String returnCode = notifyMap.get("return_code");
                //商户订单号
                String outTradeNo = notifyMap.get("out_trade_no");
                //订单金额
                String totalFee = notifyMap.get("total_fee");
                //微信支付订单号
                String transactionId = notifyMap.get("transaction_id");

                ShopOrder shopOrder = shopOrderService.getRepository().findByOrderNumber(outTradeNo);

                if (returnCode.equals(SUCCESS)) {
                    if (returnCode != null && transactionId != null) {
                        // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户的订单状态从退款改成支付成功
                        // 注意特殊情况：微信服务端同样的通知可能会多次发送给商户系统，所以数据持久化之前需要检查是否已经处理过了，处理了直接返回成功标志
                        //业务数据持久化
                        log.info("进入回调逻辑处理");
                        shopOrder.setTransactionId(transactionId);

                        shopWxPay(shopOrder, totalFee, outTradeNo);
                        log.info("     微信完成支付   ");
                        xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    } else {
                        log.info("微信手机支付回调失败订单号:{}", outTradeNo);
                        xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    }
                }
                return xmlBack;
            } else {
                // 签名错误，如果数据里没有sign字段，也认为是签名错误
                //失败的数据要不要存储？
                log.error("手机支付回调通知签名错误");
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                return xmlBack;
            }
        } catch (Exception e) {
            log.error("手机支付回调通知失败", e);
            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        return xmlBack;
    }

    /**
     * 微信购买商品
     *
     * @param shopOrder
     * @param totalAmount
     */
    @Transactional(rollbackOn = Exception.class)
    public void shopWxPay(ShopOrder shopOrder, String totalAmount, String outTradeNo) {

        //只对未支付的订单进行处理
        if (shopOrder.getStatus() != null && shopOrder.getStatus() != 0) {
            return;
        }

        log.info("微信付款完之后的商品订单 ----- " + shopOrder.toString());
        //由于微信支付传参金额是分，返回金额就要除100
        int i = new BigDecimal(totalAmount).compareTo(shopOrder.getPrice().multiply(new BigDecimal("100")).stripTrailingZeros());
        //订单金额与支付金额/订单号是否相同，如果不同则为异常订单
        if (0 == i && outTradeNo.equals(shopOrder.getOrderNumber())) {
            shopOrder.setStatus(1);
            //支付完成时间
            shopOrder.setPayTime(new Date());
            shopOrderService.update(shopOrder);

            //查询订单总共卖出的商品数量，如果是刷脸设备
            List<OrderGoods> allByShopOrderId = orderGoodsService.getRepository().findAllByShopOrderId(shopOrder.getId());
            int num=0;
            for(OrderGoods orderGoods:allByShopOrderId){
                Integer type = distGoodsService.findOne(orderGoods.getGoodsId()).getType();
                if(type==1){
                    num=num+orderGoods.getNum();
                }
            }
            if(num>0){
                SystemSetup systemSetup = systemSetupService.getRepository().findByServiceProviderId(shopOrder.getServiceProviderId());
                DistUser distUser = distUserService.findOne(shopOrder.getUserId());
                distUser.setBuyNum(distUser.getBuyNum()+num);
                //购买rise台设备就成为合伙人
                if(distUser.getGrade()==0&&distUser.getBuyNum()>=systemSetup.getRise()){
                    distUser.setGrade(1);
                    distUser.setBecomeTime(new Date());
                }
                distUserService.update(distUser);
            }

            //清除购物车
            shopCartService.deleteByUserId(shopOrder.getUserId());
            ScheduleUtils.deleteScheduleJob(scheduler, shopOrder.getId(), "OrderJob");
            log.info("微信支付开始发短信通知   ----- ");
            //发短信和微信推送进行通知
            try {

                sendOrder(shopOrder);

            } catch (Exception e) {
                log.info("短信异常信息 ---------  " + e.getMessage());
                new Resp().error(Resp.Status.PARAM_ERROR, "发送短信异常");
            }
        } else {
            shopOrder.setStatus(2);
            shopOrder.setRecord("微信支付金额与订单金额不符");
            shopOrderService.update(shopOrder);
            return;
        }

    }

    /**
     * 发短信通知发货
     *
     * @param shopOrder
     */
    public void sendOrder(ShopOrder shopOrder) {

        try {
            NoteConfig noteConfig = noteConfigService.getRepository().findByServiceProviderId(shopOrder.getServiceProviderId());
            if (noteConfig == null) {
                throw new BaseException("未配置短信通道", Resp.Status.PARAM_ERROR.getCode());
            }

            Company company = companyService.findOne(shopOrder.getServiceProviderId());
            if (company == null) {
                throw new BaseException("当前服务商不存在", Resp.Status.PARAM_ERROR.getCode());
            }
            //联系人
            String contact = company.getContact();

            //获取状态
            SendStatus sendStatus = sendStatusRepository.findByServiceProviderId(shopOrder.getServiceProviderId());

            //发短信
            String smsNews = sendSMS(shopOrder, contact, noteConfig, sendStatus);
            log.info("短信状态-----  " + smsNews);

            //微信推送
            String wxNews = sendWx(shopOrder, sendStatus);
            log.info("微信状态-----  " + wxNews);

        } catch (Exception e) {
            log.info("订单完成发送短信通知发货异常信息 -----------  " + e.getMessage());
            new Resp().error(Resp.Status.PARAM_ERROR, "订单完成发送短信通知发货异常信息");
        }
    }


    /**
     * 发短信
     *
     * @param shopOrder
     * @param contact
     * @param noteConfig
     * @return
     */
    public String sendSMS(ShopOrder shopOrder, String contact, NoteConfig noteConfig, SendStatus sendStatus) throws IOException, ClientException {
        //获取售后电话
        AfterSale afterSale = afterSaleService.getRepository().findByServiceProviderId(shopOrder.getServiceProviderId());
        if (afterSale == null) {
            throw new BaseException("未配置售后电话", Resp.Status.PARAM_ERROR.getCode());
        }

        //如果售后短信关闭状态则不需要发送
        if (sendStatus.getAfterStatus().equals(SendStatus.Status.NOOPEN.getCode()) || afterSale.getPhone() == null || afterSale.getPhone().equals("")) {
            return "短信推送关闭";
        }
        //售后电话
        String phones = afterSale.getPhone();

        NoteController.sendSmsOrder(shopOrder, contact, phones, noteConfig);
        return "发送短信成功";
    }

    /**
     * 微信推送
     *
     * @param shopOrder
     * @return
     */
    public String sendWx(ShopOrder shopOrder, SendStatus sendStatus) {

        List<WxUser> wxUserList = wxUserService.getRepository().findAllByServiceProviderId(shopOrder.getServiceProviderId());
        if (wxUserList == null || wxUserList.size() == 0 || sendStatus.getWxStatus().equals(SendStatus.Status.NOOPEN.getCode())) {
            return "当前并没设置微信推送人/状态关闭";
        }
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(shopOrder.getServiceProviderId(), CommonConstant.NORMAL_FLAG);
        if (topConfig == null) {
            new Resp().error(Resp.Status.PARAM_ERROR, "当前服务商未配置微信支付相关参数");
        }

        //配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(topConfig.getWxAppId());
        wxStorage.setSecret(topConfig.getWxAppSecret());
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);

        for (WxUser wxUser : wxUserList) {

            //实例化模板对象
            WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
            //设置模板ID
            wxMpTemplateMessage.setTemplateId("qAMFUZWc71NnLjtO0UAQBfemcauAjry0scH1OBOOBTs");
            //设置发送给哪个用户
            wxMpTemplateMessage.setToUser(wxUser.getOpenid());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String payTime = formatter.format(shopOrder.getPayTime());

            //构建消息格式
            List<WxMpTemplateData> wxMpTemplateData = Arrays.asList(
                    new WxMpTemplateData("first", "您有一个新的待发货订单！", "#173177"),
                    new WxMpTemplateData("keyword1", shopOrder.getOrderNumber(), "#173177"),
                    new WxMpTemplateData("keyword2", shopOrder.getPrice().stripTrailingZeros().toPlainString(), "#173177"),
                    new WxMpTemplateData("keyword3", shopOrder.getConsignee(), "#173177"),
                    new WxMpTemplateData("keyword4", payTime, "#173177"),
                    new WxMpTemplateData("remark", "顾客已付款,尽快发货吧", "#173177")
            );
            //放进模板对象。准备发送
            wxMpTemplateMessage.setData(wxMpTemplateData);
            try {
                String wxTemplateResult = wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
                log.info(wxTemplateResult);
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        }
        return "微信推送成功";
    }

}
