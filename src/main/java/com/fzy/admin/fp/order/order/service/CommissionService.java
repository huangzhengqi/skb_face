package com.fzy.admin.fp.order.order.service;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.Equipment;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.auth.service.EquipmentService;
import com.fzy.admin.fp.auth.utils.DateUtils;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.app.service.DistUserService;
import com.fzy.admin.fp.distribution.commission.domain.CommissionTotal;
import com.fzy.admin.fp.distribution.commission.service.CommissionTotalService;
import com.fzy.admin.fp.distribution.money.domain.AccountDetail;
import com.fzy.admin.fp.distribution.money.domain.Wallet;
import com.fzy.admin.fp.distribution.money.service.AccountDetailService;
import com.fzy.admin.fp.distribution.money.service.WalletService;
import com.fzy.admin.fp.distribution.pc.domain.SystemSetup;
import com.fzy.admin.fp.distribution.pc.service.SystemSetupService;
import com.fzy.admin.fp.merchant.YunlabaUtil;
import com.fzy.admin.fp.merchant.merchant.domain.FeiyuConfig;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.repository.FeiYuConfigRepository;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantRepository;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import com.fzy.admin.fp.order.order.domain.Commission;
import com.fzy.admin.fp.order.order.domain.CommissionDay;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.dto.CreateCommissionDTO;
import com.fzy.admin.fp.order.order.dto.PageSearchDTO;
import com.fzy.admin.fp.order.order.repository.CommissionDayRepository;
import com.fzy.admin.fp.order.order.repository.CommissionRepository;
import com.fzy.admin.fp.order.order.repository.OrderRepository;
import com.fzy.admin.fp.order.order.vo.CommissionSummaryVO;
import com.fzy.admin.fp.order.order.vo.CountDataVO;
import com.fzy.admin.fp.order.order.vo.SummaryVO;
import com.fzy.admin.fp.pay.pay.domain.*;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.pay.pay.service.*;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by wtl on 2019-04-22 19:09:33
 * @description 订单服务层
 */
@Slf4j
@Service
@Transactional
public class CommissionService implements BaseService<Commission> {

    @Resource
    private SystemSetupService systemSetupService;
    @Resource
    private CommissionRepository commissionRepository;
    @Resource
    private CompanyRepository companyRepository;
    @Resource
    private MerchantRepository merchantRepository;
    @Resource
    private CommissionDayRepository commissionDayRepository;
    @Resource
    private OrderRepository orderRepository;
    @Resource
    private TopConfigRepository topConfigRepository;
    @Resource
    private FeiYuConfigRepository feiYuConfigRepository;
    @Resource
    private CommissionDayService commissionDayService;
    @Resource
    private SxfPayService sxfPayService;
    @Resource
    private TqSxfPayService tqSxfPayService;
    @Resource
    private FyPayService fyPayService;
    @Resource
    private AliPayService aliPayService;
    @Resource
    private WxPayService wxPayService;
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private MchInfoService mchInfoService;
    @Resource
    private DistUserService distUserService;
    @Resource
    private CommissionTotalService commissionTotalService;
    @Resource
    private AccountDetailService accountDetailService;
    @Resource
    private WalletService walletService;
    @PersistenceContext
    private EntityManager em;

    /**
     * 重新生成佣金结果
     */
    Cache<String, Integer> CREATECOMMISSION = CacheUtil.newLFUCache(2000, 60 * 60 * 1000);


    /**
     * 佣金总览
     *
     * @param companyId
     * @return
     */
    public CommissionSummaryVO summary(String companyId) {
        CommissionSummaryVO commissionSummaryVO = new CommissionSummaryVO();
        Date lastYears = DateUtil.offset(new Date(), DateField.YEAR, -1);
        List<CommissionDay> commissionDays = commissionDayRepository.findAllByCompanyIdAndCreateTimeAfter(companyId, lastYears);
        BigDecimal orderTotal = new BigDecimal(0);
        BigDecimal commissionTotal = new BigDecimal(0);
        BigDecimal zfbOrderTotal = new BigDecimal(0);
        BigDecimal zfbCommissionTotal = new BigDecimal(0);
        BigDecimal wxOrderTotal = new BigDecimal(0);
        BigDecimal wxCommissionTotal = new BigDecimal(0);
        BigDecimal sxfOrderTotal = new BigDecimal(0);
        BigDecimal sxfCommissionTotal = new BigDecimal(0);
        BigDecimal fyOrderTotal = new BigDecimal(0);
        BigDecimal fyCommissionTotal = new BigDecimal(0);
        BigDecimal settleCommisson = new BigDecimal(0);
        BigDecimal unSettleCommisson = new BigDecimal(0);
        for (CommissionDay commissionDay : commissionDays) {
            orderTotal = orderTotal.add(commissionDay.getOrderTotal());
            commissionTotal = commissionTotal.add(commissionDay.getCommissionTotal());
            //微信
            wxOrderTotal = wxOrderTotal.add(commissionDay.getWxOrderTotal());
            wxCommissionTotal = wxCommissionTotal.add(commissionDay.getWxCommissionTotal());

            //支付宝
            zfbOrderTotal = zfbOrderTotal.add(commissionDay.getZfbOrderTotal());
            zfbCommissionTotal = zfbCommissionTotal.add(commissionDay.getZfbCommissionTotal());

            //随行付
            sxfOrderTotal = sxfOrderTotal.add(commissionDay.getSxfOrderTotal());
            sxfCommissionTotal = sxfCommissionTotal.add(commissionDay.getSxfCommissionTotal());

            //富有
            fyOrderTotal = fyOrderTotal.add(commissionDay.getFyOrderTotal());
            fyCommissionTotal = fyCommissionTotal.add(commissionDay.getFyCommissionTotal());

            //是否结算
            if (commissionDay.getStatus().equals(1)) {
                settleCommisson = settleCommisson.add(commissionDay.getCommissionTotal());
            } else {
                unSettleCommisson = unSettleCommisson.add(commissionDay.getCommissionTotal());
            }
        }
        commissionSummaryVO.setCommissionTotal(commissionTotal);
        commissionSummaryVO.setOrderTotal(orderTotal);
        commissionSummaryVO.setSettleCommisson(settleCommisson);
        commissionSummaryVO.setUnSettleCommisson(unSettleCommisson);
        commissionSummaryVO.setWxCommissionTotal(wxCommissionTotal);
        commissionSummaryVO.setWxOrderTotal(wxOrderTotal);
        commissionSummaryVO.setZfbCommissionTotal(zfbCommissionTotal);
        commissionSummaryVO.setZfbOrderTotal(zfbOrderTotal);
        commissionSummaryVO.setSxfCommissionTotal(sxfCommissionTotal);
        commissionSummaryVO.setSxfOrderTotal(sxfOrderTotal);
        commissionSummaryVO.setFyCommissionTotal(fyCommissionTotal);
        commissionSummaryVO.setFyOrderTotal(fyOrderTotal);
        return commissionSummaryVO;
    }

    @Override
    public CommissionRepository getRepository() {
        return commissionRepository;
    }

    /**
     * 根据订单生成佣金抽成明细
     *
     * @param orderOld
     */
    public void crateCommission(Order orderOld, boolean voiceSend) {
        Order order = new Order();
        BeanUtil.copyProperties(orderOld, order);
        try {
            if (voiceSend) {
                //到账通知
                this.sendSuccessMsg(order);
            }
            //判断该订单状态，如果是退款，则将原来commission设为-1,并重新插入
            if (order.getStatus().equals(Order.Status.REFUNDTOTAL.getCode())) {
                //全额退款，直接将旧数据状态设置为-1,return
                Commission oldCom = commissionRepository.findByOrderIdAndOrderStatus(order.getId(), Order.Status.SUCCESSPAY.getCode());
                if (oldCom != null) {
                    oldCom.setOrderStatus(-1);
                    oldCom.setRemarks("全额退款");
                    commissionRepository.save(oldCom);
                }
                return;

            } else if (order.getStatus().equals(Order.Status.REFUNDPART.getCode())) {
                //部分退款，直接将旧数据状态设置为-1,并继续生成一条新纪录
                Commission oldCom = commissionRepository.findByOrderIdAndOrderStatus(order.getId(), Order.Status.SUCCESSPAY.getCode());
                if (oldCom != null) {
                    oldCom.setOrderStatus(-1);
                    oldCom.setRemarks("部分退款");
                    commissionRepository.save(oldCom);
                }
                order.setStatus(Order.Status.SUCCESSPAY.getCode());
                order.setActPayPrice(order.getActPayPrice().subtract(order.getRefundPayPrice()));
            } else {
                //非退款订单
                Commission oldCom = commissionRepository.findByOrderIdAndOrderStatusNot(order.getId(), -1);
                if (oldCom != null) {
                    log.info("订单佣金记录已存在，删除并重新生成{}", order.getId());
                    commissionRepository.delete(oldCom);
                }
            }

            Commission commission = new Commission();
            commission.setOrderId(order.getId());
            commission.setOrderStatus(order.getStatus());
            commission.setCreateTime(order.getCreateTime());
            commission.setServiceProviderId(order.getServiceProviderId());
            commission.setOrderPrice(order.getActPayPrice());
            commission.setPayChannel(order.getPayChannel());
            commission.setPayWay(order.getPayWay());
            commission.setMerchantId(order.getMerchantId());
            //默认是不设置
            commission.setRebateType(Integer.valueOf(1));
            if (order.getMerchantId() == null) {
                log.info("商户不存在");
                return;
            }
            //获取店铺id
            Merchant merchant = merchantRepository.findOne(order.getMerchantId());

            //定义贴牌/一二三级代理
            Company thirdCompany = null;
            Company sercondCompany = null;
            Company firstCompany = null;
            Company oemCompany = null;

            if (!merchant.getType().equals(CommonConstant.distType)) {

                if (merchant == null || merchant.getCompanyId() == null) {
                    log.info("商户/公司不存在：{}", order.getMerchantId());
                    return;
                }

                //获取上级id
                Company company = companyRepository.findOne(merchant.getCompanyId());
                if (company == null) {
                    log.info("开通商户的公司不存在：{}", merchant.getCompanyId());
                    return;
                }
                //通过idpath获取所有上级
                String idPath = company.getIdPath();
                switch (company.getType()) {
                    case 1:
                        oemCompany = company;
                        break;
                    case 2:
                        firstCompany = company;
                        break;
                    case 3:
                        sercondCompany = company;
                        break;
                    case 4:
                        thirdCompany = company;
                        break;
                    default:
                        log.info("开通商户的公司不存在：{}", company);
                        return;
                }
                //如果idpath为空 则为贴牌商
                if (!StringUtils.isEmpty(idPath)) {
                    String[] ids = idPath.split("\\|");
                    for (Integer i = 0; i < ids.length; i++) {
                        if (!StringUtils.isEmpty(ids[i])) {
                            Company c = companyRepository.findOne(ids[i]);
                            if (c != null) {
                                switch (c.getType()) {
                                    case 1:
                                        oemCompany = c;
                                        break;
                                    case 2:
                                        firstCompany = c;
                                        break;
                                    case 3:
                                        sercondCompany = c;
                                        break;
                                    case 4:
                                        thirdCompany = c;
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                    }
                }

                commission.setOemId(oemCompany.getId());
            } else {
                //获取上级id
                oemCompany = companyRepository.findOne(order.getServiceProviderId());
                if (oemCompany == null) {
                    log.info("开通商户的公司不存在：{}", merchant.getCompanyId());
                    return;
                }
                commission.setOemId(oemCompany.getId());
            }


            TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(order.getServiceProviderId(), CommonConstant.NORMAL_FLAG);

            /**
             * 通过PayWay，PayChannel 确定支付方式，并获取对应的抽佣比例
             */
            if (order.getPayWay().equals(Order.PayWay.WXPAY.getCode()) && order.getPayChannel().equals(Order.InterFaceWay.SELF.getCode())) {
                //微信支付官方通道
                if (oemCompany != null) {
                    commission.setOemPayProrata(oemCompany.getWxPayProrata() == null ? BigDecimal.ZERO : oemCompany.getWxPayProrata());
                }
                commission.setServiceRate(topConfig.getWxRate() == null ? BigDecimal.ZERO : topConfig.getWxRate());
                if (firstCompany != null) {
                    commission.setFirstId(firstCompany.getId());
                    commission.setFirstPayProrata(firstCompany.getWxPayProrata() == null ? BigDecimal.ZERO : firstCompany.getWxPayProrata());
                }
                if (sercondCompany != null) {
                    commission.setSecondId(sercondCompany.getId());
                    commission.setSecondPayProrata(sercondCompany.getWxPayProrata() == null ? BigDecimal.ZERO : sercondCompany.getWxPayProrata());
                }
                if (thirdCompany != null) {
                    commission.setThirdId(thirdCompany.getId());
                    commission.setThirdPayProrata(thirdCompany.getWxPayProrata() == null ? BigDecimal.ZERO : thirdCompany.getWxPayProrata());
                }
                WxConfig wxConfig = wxPayService.getWxConfigRepository().findByMerchantIdAndDelFlag(merchant.getId(), CommonConstant.NORMAL_FLAG);
                //微信利率
                if (wxConfig != null && wxConfig.getInterestRate() != null) {
                    commission.setMerchantProrata(wxConfig.getInterestRate());
                } else {
                    commission.setMerchantProrata(BigDecimal.ZERO);
                }


            } else if (order.getPayWay().equals(Order.PayWay.ALIPAY.getCode()) && order.getPayChannel().equals(Order.InterFaceWay.SELF.getCode())) {
                //支付宝支付官方通道
                if (oemCompany != null) {
                    commission.setOemPayProrata(oemCompany.getZfbPayProrata() == null ? BigDecimal.ZERO : oemCompany.getZfbPayProrata());
                }
                commission.setServiceRate(topConfig.getZfbRate() == null ? BigDecimal.ZERO : topConfig.getZfbRate());
                if (firstCompany != null) {
                    commission.setFirstId(firstCompany.getId());
                    commission.setFirstPayProrata(firstCompany.getZfbPayProrata() == null ? BigDecimal.ZERO : firstCompany.getZfbPayProrata());
                }
                if (sercondCompany != null) {
                    commission.setSecondId(sercondCompany.getId());
                    commission.setSecondPayProrata(sercondCompany.getZfbPayProrata() == null ? BigDecimal.ZERO : sercondCompany.getZfbPayProrata());
                }
                if (thirdCompany != null) {
                    commission.setThirdId(thirdCompany.getId());
                    commission.setThirdPayProrata(thirdCompany.getZfbPayProrata() == null ? BigDecimal.ZERO : thirdCompany.getZfbPayProrata());
                }
                AliConfig aliConfig = aliPayService.getAliConfigRepository().findByMerchantIdAndDelFlag(merchant.getId(), CommonConstant.NORMAL_FLAG);
                if (aliConfig != null && aliConfig.getInterestRate() != null) {
                    commission.setMerchantProrata(aliConfig.getInterestRate());
                } else {
                    commission.setMerchantProrata(BigDecimal.ZERO);
                }


            } else if (order.getPayChannel().equals(Order.InterFaceWay.FUYOU.getCode())) {
                //富有支付通道
                if (oemCompany != null) {
                    commission.setOemPayProrata(oemCompany.getFyPayProrata() == null ? BigDecimal.ZERO : oemCompany.getFyPayProrata());
                }
                if (firstCompany != null) {
                    commission.setFirstId(firstCompany.getId());
                    commission.setFirstPayProrata(firstCompany.getFyPayProrata() == null ? BigDecimal.ZERO : firstCompany.getFyPayProrata());
                }
                if (sercondCompany != null) {
                    commission.setSecondId(sercondCompany.getId());
                    commission.setSecondPayProrata(sercondCompany.getFyPayProrata() == null ? BigDecimal.ZERO : sercondCompany.getFyPayProrata());
                }
                if (thirdCompany != null) {
                    commission.setThirdId(thirdCompany.getId());
                    commission.setThirdPayProrata(thirdCompany.getFyPayProrata() == null ? BigDecimal.ZERO : thirdCompany.getFyPayProrata());
                }
                FyConfig fyConfig = fyPayService.getFyConfigRepository().findByMerchantIdAndDelFlag(merchant.getId(), CommonConstant.NORMAL_FLAG);

                //支付方式，微信/支付宝
                if (order.getPayWay().equals(Order.PayWay.WXPAY.getCode())) {
                    commission.setServiceRate(topConfig.getFyWxRate() == null ? BigDecimal.ZERO : topConfig.getFyWxRate());
                    if (fyConfig != null && fyConfig.getWxInterestRate() != null) {
                        commission.setMerchantProrata(fyConfig.getWxInterestRate());
                    } else {
                        commission.setMerchantProrata(BigDecimal.ZERO);
                    }
                } else {
                    commission.setServiceRate(topConfig.getFyAliRate() == null ? BigDecimal.ZERO : topConfig.getFyAliRate());
                    if (fyConfig != null && fyConfig.getAliInterestRate() != null) {
                        commission.setMerchantProrata(fyConfig.getAliInterestRate());
                    } else {
                        commission.setMerchantProrata(BigDecimal.ZERO);
                    }
                }
            } else if (order.getPayChannel().equals(Order.InterFaceWay.SUIXINGFU.getCode())) {
                //随行支付付通道
                if (oemCompany != null) {
                    commission.setOemPayProrata(oemCompany.getSxfPayProrata() == null ? BigDecimal.ZERO : oemCompany.getSxfPayProrata());
                }
                if (firstCompany != null) {
                    commission.setFirstId(firstCompany.getId());
                    commission.setFirstPayProrata(firstCompany.getSxfPayProrata() == null ? BigDecimal.ZERO : firstCompany.getSxfPayProrata());
                }
                if (sercondCompany != null) {
                    commission.setSecondId(sercondCompany.getId());
                    commission.setSecondPayProrata(sercondCompany.getSxfPayProrata() == null ? BigDecimal.ZERO : sercondCompany.getSxfPayProrata());
                }
                if (thirdCompany != null) {
                    commission.setThirdId(thirdCompany.getId());
                    commission.setThirdPayProrata(thirdCompany.getSxfPayProrata() == null ? BigDecimal.ZERO : thirdCompany.getSxfPayProrata());
                }
                SxfConfig sxfConfig = sxfPayService.getSxfConfigRepository().findByMerchantIdAndDelFlag(merchant.getId(), CommonConstant.NORMAL_FLAG);
                if (order.getPayWay().equals(Order.PayWay.WXPAY.getCode())) {
                    commission.setServiceRate(topConfig.getSxfWxRate() == null ? BigDecimal.ZERO : topConfig.getSxfWxRate());
                    if (sxfConfig != null && sxfConfig.getWxInterestRate() != null) {
                        commission.setMerchantProrata(sxfConfig.getWxInterestRate());
                    } else {
                        commission.setMerchantProrata(BigDecimal.ZERO);
                    }
                } else {
                    commission.setServiceRate(topConfig.getSxfAliRate() == null ? BigDecimal.ZERO : topConfig.getSxfAliRate());
                    if (sxfConfig != null && sxfConfig.getAliInterestRate() != null) {
                        commission.setMerchantProrata(sxfConfig.getAliInterestRate());
                    } else {
                        commission.setMerchantProrata(BigDecimal.ZERO);
                    }
                }
            } else if (order.getPayChannel().equals(Order.InterFaceWay.TQSXF.getCode())) {
                //天阙随行付通道
                if (oemCompany != null) {
                    commission.setOemPayProrata(oemCompany.getTqSxfPayProrata() == null ? BigDecimal.ZERO : oemCompany.getTqSxfPayProrata());
                }
                if (firstCompany != null) {
                    commission.setFirstId(firstCompany.getId());
                    commission.setFirstPayProrata(firstCompany.getTqSxfPayProrata() == null ? BigDecimal.ZERO : firstCompany.getTqSxfPayProrata());
                }
                if (sercondCompany != null) {
                    commission.setSecondId(sercondCompany.getId());
                    commission.setSecondPayProrata(sercondCompany.getTqSxfPayProrata() == null ? BigDecimal.ZERO : sercondCompany.getTqSxfPayProrata());
                }
                if (thirdCompany != null) {
                    commission.setThirdId(thirdCompany.getId());
                    commission.setThirdPayProrata(thirdCompany.getTqSxfPayProrata() == null ? BigDecimal.ZERO : thirdCompany.getTqSxfPayProrata());
                }
                TqSxfConfig tqSxfConfig = tqSxfPayService.getSxfConfigRepository().findByMerchantIdAndDelFlag(merchant.getId(), CommonConstant.NORMAL_FLAG);
                if (order.getPayWay().equals(Order.PayWay.WXPAY.getCode())) {
                    commission.setServiceRate(topConfig.getTqSxfWxRate() == null ? BigDecimal.ZERO : topConfig.getTqSxfWxRate());
                    if (tqSxfConfig != null && tqSxfConfig.getWxInterestRate() != null) {
                        commission.setMerchantProrata(tqSxfConfig.getWxInterestRate());
                    } else {
                        commission.setMerchantProrata(BigDecimal.ZERO);
                    }
                } else {
                    commission.setServiceRate(topConfig.getTqSxfWxRate() == null ? BigDecimal.ZERO : topConfig.getTqSxfWxRate());
                    if (tqSxfConfig != null && tqSxfConfig.getAliInterestRate() != null) {
                        commission.setMerchantProrata(tqSxfConfig.getAliInterestRate());
                    } else {
                        commission.setMerchantProrata(BigDecimal.ZERO);
                    }
                }
            } else {
                log.info("未知支付通道：{}", order);
                return;
            }

            BigDecimal comissionMoney = new BigDecimal("0");
            //计算参与分佣的钱 流水*（店铺抽佣比例-服务商利率） 注意这里如果通道不一样拿到的费率会有所改变 (商户不返佣的算法)
            if (merchant.getRebateType().equals(Integer.valueOf(1))) {

                comissionMoney = order.getActPayPrice().multiply(commission.getMerchantProrata().subtract(commission.getServiceRate()));
            } else {
                //商户设置分佣-商户设置返佣  不等于0剩下来的继续参与服务商的分佣
                BigDecimal balance = commission.getMerchantProrata().subtract(merchant.getSiteRate() == null ? new BigDecimal("0") : merchant.getSiteRate());
                if (balance.compareTo(new BigDecimal("0")) == 1) {
                    //  计算参与分佣的钱 流水*（店铺抽佣比例-服务商利率） 注意这里如果通道不一样拿到的费率会有所改变 (商户参与返佣的算法)
                    comissionMoney = order.getActPayPrice().multiply(balance.subtract(commission.getServiceRate()));
                    log.info("服务商抽佣 ------> {} ", comissionMoney);
                }
                if (comissionMoney.compareTo(new BigDecimal("0")) == -1) {
                    log.info("修改服务商抽佣");
                    comissionMoney = new BigDecimal("0");
                }
                commission.setOpenId(merchant.getOpenId() == null ? null : merchant.getOpenId());
                commission.setRebateType(merchant.getRebateType());
                //商户返佣公式 订单金额的钱*（商户设置抽佣比例）
                commission.setMerchantCommission(order.getActPayPrice().multiply(merchant.getSiteRate() == null ? new BigDecimal("0") : merchant.getSiteRate()));
                log.info("商户返佣 ------> {} ", order.getActPayPrice().multiply(merchant.getSiteRate() == null ? new BigDecimal("0") : merchant.getSiteRate()));
            }

            //各级佣金都从上一级获取
            BigDecimal oemCommission = comissionMoney;
            commission.setOemCommission(oemCommission);

            //if true是分润的 else 是普通服务商 分销的分润要实现计算用户（添加商户的代理）的激活设备数与用户及其上级分流水的润。上级级别不同，分润的费率不同
            if (merchant.getType().equals(CommonConstant.distType)) {
                Equipment equipment = null;
                String equipmentId = null;
                if (StringUtil.isNotEmpty(order.getEquipmentId())) {
                    equipmentId = order.getEquipmentId();
                    equipment = equipmentService.findOne(equipmentId);
                }
                //当前用户
                DistUser oldDistUser = distUserService.findOne(merchant.getManagerId());
                String serviceProviderId = oldDistUser.getServiceProviderId();
                //状态 0未计算设备数 1已计算设备数
                //没放入统计里的青蛙
                if (equipment != null && equipment.getStatus() == 0 && (equipment.getDeviceType() == 1 || equipment.getDeviceType() == 2)) {
                    //计算设备流水
                    CountDataVO countByEquipmentIdAndStatus = this.orderRepository.getCountByEquipmentIdAndStatus(equipmentId, Order.Status.SUCCESSPAY.getCode(), order.getMerchantId());
                    BigDecimal monthCommission = countByEquipmentIdAndStatus.getCommissionAmount();
                    //设备刷过的总额超过1元则算激活设备
                    if (monthCommission != null && monthCommission.compareTo(BigDecimal.ONE) != -1) {
                        equipment.setStatus(1);
                        equipment.setActivateTime(new Date());
                        equipmentService.save(equipment);
                        MchInfo mchInfo = mchInfoService.findByMerchantId(equipment.getMerchantId());
                        //如果产生流水，则表示激活
                        if (mchInfo.getStatus() == 3) {
                            mchInfo.setStatus(5);
                            mchInfoService.save(mchInfo);
                        }
                        DistUser distUser = new DistUser();
                        BeanUtils.copyProperties(oldDistUser, distUser);

                        Integer oldTeamActivate = distUser.getTeamActivate();
                        distUser.setTeamActivate(distUser.getTeamActivate() + 1);
                        distUser.setActivate(distUser.getActivate() + 1);
                        int a = 0;
                        while (distUserService.updateUserActivate(distUser, oldTeamActivate) == 0) {
                            oldDistUser = distUserService.findOne(merchant.getManagerId());
                            log.info("oldDistUser =========== >" + oldDistUser.toString());
                            if (oldDistUser == null) {
                                break;
                            }
                            BeanUtils.copyProperties(oldDistUser, distUser);
                            oldTeamActivate = distUser.getTeamActivate();
                            distUser.setTeamActivate(distUser.getTeamActivate() + 1);
                            distUser.setActivate(distUser.getActivate() + 1);
                            a++;
                            if (a > 3) {
                                break;
                            }
                            log.info("1111111111111111111111");
                        }

                        //给代理商的所有上级的团队激活设备+1
                        if (StringUtil.isNotEmpty(distUser.getParentId())) {
                            String[] split = distUser.getParentId().split("/");
                            for (int i = split.length - 1; i >= 0; i--) {
                                DistUser oldParentUser = distUserService.getRepository().findByInviteNumAndServiceProviderId(split[i], serviceProviderId);
                                DistUser parentUser = new DistUser();
                                BeanUtil.copyProperties(oldParentUser, parentUser);

                                oldTeamActivate = parentUser.getTeamActivate();
                                parentUser.setTeamActivate(parentUser.getTeamActivate() + 1);
                                log.info("222222222222222222222");
                                //agentUserService.updateUserActivate(parentUser,oldTeamActivate);
                                log.info("parentUser =========== >" + parentUser.toString());
                                while (distUserService.updateUserActivate(parentUser, oldTeamActivate) == 0) {
                                    oldParentUser = distUserService.getRepository().findByInviteNumAndServiceProviderId(split[i], serviceProviderId);
                                    log.info("oldParentUser =========== >" + oldParentUser.toString());
                                    if (oldParentUser == null) {
                                        break;
                                    }
                                    log.info("33333333333333333333");
                                    BeanUtil.copyProperties(oldParentUser, parentUser);
                                    oldTeamActivate = parentUser.getTeamActivate();
                                    parentUser.setTeamActivate(parentUser.getTeamActivate() + 1);
                                    a++;
                                    if (a > 3) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                commission.setType(CommonConstant.distType);
                commission.setDirectId(oldDistUser.getId());

                //得到服务商的分润设置
                SystemSetup systemSetup = systemSetupService.getRepository().findByServiceProviderId(order.getServiceProviderId());

                //不是运营中心 需要给其他人分佣
                if (oldDistUser.getGrade() < 2) {
                    double direct = systemSetup.getDirect() * 0.01;
                    double operationOneLevel = systemSetup.getOperationOneLevel() * 0.01;
                    double operationTwoLevel = systemSetup.getOperationTwoLevel() * 0.01;
                    double operationThreeLevel = systemSetup.getOperationThreeLevel() * 0.01;
                    double operationFourLevel = systemSetup.getOperationFourLevel() * 0.01;

                    //当前人如果是游客就没有分润只有合伙人和团长才能享受分润
                    if (!oldDistUser.getGrade().equals(Integer.valueOf(0))) {
                        //直接邀请人id的分润
                        commission.setDirectCommission(comissionMoney.multiply(new BigDecimal(direct)));
                    }
                    String parentId = oldDistUser.getParentId();
                    String[] parentIds = parentId.split("/");
                    //通过邀请码得到该用户级别，根据不同级别给运营中心分润
                    Integer userLevel = parentIds.length;
                    switch (userLevel) {
                        case 1:
                            commission.setOperationCommission(comissionMoney.multiply(new BigDecimal(operationOneLevel)));
                            break;
                        case 2:
                            commission.setOperationCommission(comissionMoney.multiply(new BigDecimal(operationTwoLevel)));
                            break;
                        case 3:
                            commission.setOperationCommission(comissionMoney.multiply(new BigDecimal(operationThreeLevel)));
                            break;
                        default:
                            commission.setOperationCommission(comissionMoney.multiply(new BigDecimal(operationFourLevel)));
                            break;
                    }
                    DistUser oldParentUser = distUserService.getRepository().findByInviteNumAndServiceProviderId(parentIds[0], serviceProviderId);
                    commission.setOperationId(oldParentUser.getId());
                    //给上级合伙人分佣
                    double oneLevel = systemSetup.getOneLevel() * 0.01;
                    double TwoLevel = systemSetup.getTwoLevel() * 0.01;
                    double ThreeLevel = systemSetup.getThreeLevel() * 0.01;

                    if (oldDistUser.getOneLevelId() != null) {
                        //查询一级是否是合伙人
                        DistUser oneDistUser = distUserService.findOne(oldDistUser.getOneLevelId());
                        //当前合伙人不是游客也不是一级合伙人才给上级分润
                        if (!oneDistUser.getGrade().equals(Integer.valueOf(0)) && !oneDistUser.getLevel().equals(Integer.valueOf(1))) {
                            commission.setOneLevelCommission(comissionMoney.multiply(new BigDecimal(oneLevel)));
                        }
                        commission.setOneLevelId(oldDistUser.getOneLevelId());

                    }
                    if (oldDistUser.getTwoLevelId() != null) {
                        //查询二级是否是合伙人
                        DistUser twoDistUser = distUserService.findOne(oldDistUser.getTwoLevelId());
                        if (!twoDistUser.getGrade().equals(Integer.valueOf(0))) {
                            commission.setTwoLevelCommission(comissionMoney.multiply(new BigDecimal(TwoLevel)));
                        }
                        commission.setTwoLevelId(oldDistUser.getTwoLevelId());

                    }
                    if (oldDistUser.getThreeLevelId() != null) {
                        //查询三级是否是合伙人
                        DistUser threeDistUser = distUserService.findOne(oldDistUser.getThreeLevelId());
                        if (!threeDistUser.getGrade().equals(Integer.valueOf(0))) {
                            commission.setThreeLevelCommission(comissionMoney.multiply(new BigDecimal(ThreeLevel)));
                        }
                        commission.setThreeLevelId(oldDistUser.getThreeLevelId());
                    }
                } else {
                    double operationDirect = systemSetup.getOperationDirect() * 0.01;
                    commission.setDirectCommission(comissionMoney.multiply(new BigDecimal(operationDirect)));
                }

            } else {

                List<Company> companies = new ArrayList<>();
                companies.add(oemCompany);
                if (firstCompany != null) {
                    BigDecimal firstCommission = comissionMoney.multiply(commission.getFirstPayProrata());
                    commission.setFirstCommission(firstCommission);
                    companies.add(firstCompany);
                }
                if (sercondCompany != null) {
                    BigDecimal secondCommission = comissionMoney.multiply(commission.getSecondPayProrata());
                    commission.setSecondCommission(secondCommission);
                    companies.add(sercondCompany);

                }
                if (thirdCompany != null) {
                    BigDecimal thirdCommission = comissionMoney.multiply(commission.getThirdPayProrata());
                    commission.setThirdCommission(thirdCommission);
                    companies.add(thirdCompany);
                }

                BigDecimal tempCommission = new BigDecimal(0);
                for (Integer i = companies.size() - 1; i >= 0; i--) {

                    switch (companies.get(i).getType()) {
                        case 1:
                            BigDecimal childCommission = tempCommission;
                            tempCommission = commission.getOemCommission();
                            if (i < companies.size() - 1) {
                                commission.setOemCommission(tempCommission.subtract(childCommission));
                            }
                            break;
                        case 2:
                            BigDecimal childCommission1 = tempCommission;
                            tempCommission = commission.getFirstCommission();
                            if (i < companies.size() - 1) {
                                commission.setFirstCommission(tempCommission.subtract(childCommission1));
                            }
                            break;
                        case 3:
                            BigDecimal childCommission2 = tempCommission;
                            tempCommission = commission.getSecondCommission();
                            if (i < companies.size() - 1) {
                                commission.setSecondCommission(tempCommission.subtract(childCommission2));
                            }
                            break;
                        case 4:
                            tempCommission = commission.getThirdCommission();
                            break;
                        default:
                            break;
                    }
                }
            }

            commissionRepository.save(commission);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("生成订单佣金失败：{}", order);
        }
    }


    /**
     * 到账通知
     *
     * @param order
     */
    private void sendSuccessMsg(Order order) {
        log.info("到账通知：{}", order);
        try {
            //到账
            if (order.getStatus().equals(Order.Status.SUCCESSPAY.getCode()) && !StringUtils.isEmpty(order.getStoreId())) {
                List<FeiyuConfig> feiyuConfigs = feiYuConfigRepository.findAllByStoreIdAndStatus(order.getStoreId(), 1);
                for (FeiyuConfig feiyuConfig : feiyuConfigs) {
                    String price = order.getActPayPrice().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString();
                    if (order.getPayWay().equals(Order.PayWay.WXPAY.getCode())) {
                        YunlabaUtil.sendVoice(feiyuConfig.getDeviceId(), price, "2", feiyuConfig.getIsSuffix());
                    } else if (order.getPayWay().equals(Order.PayWay.ALIPAY.getCode())) {
                        //支付宝
                        YunlabaUtil.sendVoice(feiyuConfig.getDeviceId(), price, "1", feiyuConfig.getIsSuffix());
                    } else if (order.getPayWay().equals(Order.PayWay.BANKCARD.getCode())) {
                        YunlabaUtil.sendVoice(feiyuConfig.getDeviceId(), price, "7", feiyuConfig.getIsSuffix());
                    } else if (order.getPayWay().equals(Order.PayWay.MEMBERCARD.getCode())) {
                        YunlabaUtil.sendVoice(feiyuConfig.getDeviceId(), price, "8", feiyuConfig.getIsSuffix());
                    } else {
                        YunlabaUtil.sendVoice(feiyuConfig.getDeviceId(), price, "0", feiyuConfig.getIsSuffix());
                    }
                }
            }
            //退款
            if ((order.getStatus().equals(Order.Status.REFUNDPART.getCode()) || order.getStatus().equals(Order.Status.REFUNDTOTAL.getCode())) && !StringUtils.isEmpty(order.getStoreId())) {
                List<FeiyuConfig> feiyuConfigs = feiYuConfigRepository.findAllByStoreIdAndStatus(order.getStoreId(), 1);
                for (FeiyuConfig feiyuConfig : feiyuConfigs) {
                    String price = order.getRefundPayPrice().multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString();
                    if (order.getPayWay().equals(Order.PayWay.WXPAY.getCode())) {
                        YunlabaUtil.sendVoice(feiyuConfig.getDeviceId(), price, "13", feiyuConfig.getIsSuffix());
                    } else if (order.getPayWay().equals(Order.PayWay.ALIPAY.getCode())) {
                        //支付宝
                        YunlabaUtil.sendVoice(feiyuConfig.getDeviceId(), price, "12", feiyuConfig.getIsSuffix());
                    } else if (order.getPayWay().equals(Order.PayWay.BANKCARD.getCode())) {
                        YunlabaUtil.sendVoice(feiyuConfig.getDeviceId(), price, "14", feiyuConfig.getIsSuffix());
                    } else {
                        YunlabaUtil.sendVoice(feiyuConfig.getDeviceId(), price, "11", feiyuConfig.getIsSuffix());
                    }
                }
            }
        } catch (Exception e) {
            log.info("语音通知失败");
        }
    }


    /**
     * 根据订单修改佣金状态
     *
     * @param status
     */
    public void EditCommissionStatus(String orderId, Integer status) {
        try {
            Commission commission = commissionRepository.findByOrderIdAndOrderStatusNot(orderId, -1);
            commission.setOrderStatus(status);
            commissionRepository.save(commission);
            if (status.equals(Order.Status.SUCCESSPAY.getCode())) {
                //支付成功到账通知
                Order order = orderRepository.findOne(orderId);
                this.sendSuccessMsg(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("修改佣金状态失败，订单id{}：状态{}", orderId, status);
        }

    }

    /**
     * 每日凌晨两点归档昨日佣金记录
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void createComissionDay() {
        //归档昨日数据，所以时间传昨天
        try {
            createComissionDay(DateUtil.yesterday(), null);
            log.info("佣金归档成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("归档失败");
        }
    }

    /**
     * 每月25日将上个月的佣金放入用户的钱包，用户提现是提钱包的现
     */
    @Scheduled(cron = "0 0 2 25 * ? ")
    @Transactional(rollbackOn = Exception.class)
    public void createAccountDetail() {
        try {
            Date dateByMonth = DateUtils.initDateByMonth();
            Date lastMonth = DateUtils.getLastMonth(dateByMonth);
            List<CommissionDay> commissionDayList = commissionDayRepository.findAllByCreateTimeBetweenAndType(lastMonth, dateByMonth, 1);
            List<String> firstIds = commissionDayList.stream().map(CommissionDay::getCompanyId).distinct().filter(i -> !StringUtils.isEmpty(i)).collect(Collectors.toList());
            //获取所有的分销id
            for (String id : firstIds) {
                Wallet wallet = walletService.getRepository().findByUserId(id);
                AccountDetail accountDetail = new AccountDetail();
                accountDetail.setBalance(wallet.getBalance());
                accountDetail.setType(0);
                accountDetail.setUserId(wallet.getUserId());
                accountDetail.setStatus(0);
                BigDecimal orderTotal = new BigDecimal(0);
                //记录账户流水
                for (CommissionDay commissionDay : commissionDayList) {
                    if (id.equals(commissionDay.getCompanyId())) {
                        orderTotal = orderTotal.add(commissionDay.getCommissionTotal());
                    }
                }
                wallet.setBalance(wallet.getBalance().add(orderTotal));
                wallet.setBonus(wallet.getBonus().add(orderTotal));
                accountDetail.setSum(orderTotal);
                accountDetail.setBalance(wallet.getBalance());
                walletService.save(wallet);
                accountDetailService.save(accountDetail);
            }
            log.info("每月25日流水归档成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("归档失败");
        }
    }


    /**
     * 按日期生成/公司id重新生成佣金
     *
     * @param comissionDate 日期
     * @param comId         公司id集合 为null则生成全部佣金
     */
    public void createComissionDay(Date comissionDate, List<String> comId) {
        Date begin = DateUtil.beginOfDay(comissionDate);
        Date end = DateUtil.endOfDay(comissionDate);
        //TODO
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Date begin = null;
//        Date end = null;
//        try {
//            begin = simpleDateFormat.parse("2020-07-06 00:00:00");
//            end = simpleDateFormat.parse("2020-07-07 02:00:00");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        //如果之前有归档过，删除旧的归档记录
        //获取到今日的佣金明细
        List<Commission> commissions;

        List<CommissionDay> oldCommissionDays;
        if (comId == null) {
            oldCommissionDays = commissionDayRepository.findAllByCreateTime(begin);
            commissions = commissionRepository.findAllByCreateTimeBetweenAndOrderStatusAndRebateTypeOrderByPayChannelDescPayWayDesc(begin, end, Order.Status.SUCCESSPAY.getCode(), Integer.valueOf(1));
        } else {
            oldCommissionDays = commissionDayRepository.findAllByCreateTimeAndCompanyIdIn(begin, comId);
            commissions = commissionRepository.findAllByCreateTimeBetweenAndOrderStatusAndOemIdInAndRebateTypeOrderByPayChannelDescPayWayDesc(begin, end, Order.Status.SUCCESSPAY.getCode(), comId, Integer.valueOf(1));
        }
        if (oldCommissionDays.size() > 0) {
            commissionDayRepository.deleteInBatch(oldCommissionDays);
        }
        //获取所有服务商
        List<String> oemIds = commissions.stream().map(Commission::getOemId).distinct().filter(i -> !StringUtils.isEmpty(i)).collect(Collectors.toList());
        //获取所有一级代理
        List<String> firstIds = commissions.stream().map(Commission::getFirstId).distinct().filter(i -> !StringUtils.isEmpty(i)).collect(Collectors.toList());
        //获取所有二级代理
        List<String> secondIds = commissions.stream().map(Commission::getSecondId).distinct().filter(i -> !StringUtils.isEmpty(i)).collect(Collectors.toList());
        //获取所有三级代理
        List<String> thirdIds = commissions.stream().map(Commission::getThirdId).distinct().filter(i -> !StringUtils.isEmpty(i)).collect(Collectors.toList());
        //当天总流水
        BigDecimal orderTotalDay = new BigDecimal(0);
        //当天总佣金金额
        BigDecimal commissionTotalDay = new BigDecimal(0);

        //type=1为分销的佣金
        List<Commission> distList = commissions.stream().filter(i -> i.getType() != null && i.getType() == 1).collect(Collectors.toList());
        log.info("佣金列表 ---------->> {}", distList.size());
        List<String> ids = new ArrayList<>();
        List<String> operationIds = distList.stream().map(Commission::getOperationId).distinct().filter(i -> !StringUtils.isEmpty(i)).collect(Collectors.toList());
        ids.addAll(operationIds);
        List<String> directIds = distList.stream().map(Commission::getDirectId).distinct().filter(i -> !StringUtils.isEmpty(i)).collect(Collectors.toList());
        ids.addAll(directIds);
        List<String> oneLevelIds = distList.stream().map(Commission::getOneLevelId).distinct().filter(i -> !StringUtils.isEmpty(i)).collect(Collectors.toList());
        ids.addAll(oneLevelIds);
        List<String> twoLevelId = distList.stream().map(Commission::getTwoLevelId).distinct().filter(i -> !StringUtils.isEmpty(i)).collect(Collectors.toList());
        ids.addAll(twoLevelId);
        List<String> threeLevelId = distList.stream().map(Commission::getThreeLevelId).distinct().filter(i -> !StringUtils.isEmpty(i)).collect(Collectors.toList());
        ids.addAll(threeLevelId);
        ids = ids.stream().distinct().collect(Collectors.toList());
        //如果分销app之前有归档过，删除旧的归档记录
        List<CommissionDay> commissionDays;
        //获取到今日的佣金明细
        if (ids.size() == 0) {
            commissionDays = commissionDayRepository.findAllByCreateTime(begin);
            commissions = commissionRepository.findAllByCreateTimeBetweenAndOrderStatusOrderByPayChannelDescPayWayDesc(begin, end, Order.Status.SUCCESSPAY.getCode());
        } else {
            commissionDays = commissionDayRepository.findAllByCreateTimeAndCompanyIdIn(begin, ids);
            //commissions = commissionRepository.findAllByCreateTimeBetweenAndOrderStatusAndOemIdInOrderByPayChannelDescPayWayDesc(begin, end, Order.Status.SUCCESSPAY.getCode(), comId);
        }
        if (commissionDays.size() > 0) {
            commissionDayRepository.deleteInBatch(commissionDays);
        }
        log.info("分销ids =============> {}", ids.toString());
        //获取所有的分销id
        for (String id : ids) {
            CommissionDay commissionDay = new CommissionDay();
            //分销用户
            commissionDay.setCompanyId(id);
            commissionDay.setCreateTime(begin);
            commissionDay.setStatus(0);
            commissionDay.setType(1);
            //等级 0游客 1普通代理 2运营中心
            Integer grade = distUserService.findOne(id).getGrade();
            if (grade.equals(Integer.valueOf(0))) {
                commissionDay.setCompanyName("游客");
            } else if (grade.equals(Integer.valueOf(1))) {
                commissionDay.setCompanyName("普通代理");
            } else if (grade.equals(Integer.valueOf(2))) {
                commissionDay.setCompanyName("运营中心");
            }

            BigDecimal orderTotal = new BigDecimal(0);
            BigDecimal commissionTotal = new BigDecimal(0);
            BigDecimal zfbOrderTotal = new BigDecimal(0);
            BigDecimal zfbCommissionTotal = new BigDecimal(0);
            BigDecimal wxOrderTotal = new BigDecimal(0);
            BigDecimal wxCommissionTotal = new BigDecimal(0);
            BigDecimal sxfOrderTotal = new BigDecimal(0);
            BigDecimal sxfCommissionTotal = new BigDecimal(0);
            BigDecimal fyOrderTotal = new BigDecimal(0);
            BigDecimal fyCommissionTotal = new BigDecimal(0);
            BigDecimal tqSxfOrderTotal = new BigDecimal(0);
            BigDecimal tqSxfCommissionTotal = new BigDecimal(0);
            for (Commission commission : distList) {
                orderTotal = orderTotal.add(commission.getOrderPrice());
                orderTotalDay = orderTotal;
                log.info("orderTotal总流水 =========> {}", orderTotal);
                log.info("orderTotalDay当天总流水 ===========> {}", orderTotalDay);
                if (id.equals(commission.getOneLevelId())) {
                    if (null != commission.getOneLevelCommission()) {
                        commissionTotal = commissionTotal.add(commission.getOneLevelCommission());
                        commissionTotalDay = commissionTotalDay.add(commission.getOneLevelCommission());
                    }
                    //支付宝
                    if (Order.PayWay.ALIPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        zfbOrderTotal = zfbOrderTotal.add(commission.getOrderPrice());
                        if (null != commission.getOneLevelCommission()) {
                            zfbCommissionTotal = zfbCommissionTotal.add(commission.getOneLevelCommission());
                        }
                    }
                    //微信
                    if (Order.PayWay.WXPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        wxOrderTotal = wxOrderTotal.add(commission.getOrderPrice());
                        if (null != commission.getOneLevelCommission()) {
                            wxCommissionTotal = wxCommissionTotal.add(commission.getOneLevelCommission());
                        }
                    }
                    //随行付
                    if (Order.InterFaceWay.SUIXINGFU.getCode().equals(commission.getPayChannel())) {
                        sxfOrderTotal = sxfOrderTotal.add(commission.getOrderPrice());
                        sxfCommissionTotal = sxfCommissionTotal.add(commission.getOneLevelCommission());
                    }
                    //富有
                    if (Order.InterFaceWay.FUYOU.getCode().equals(commission.getPayChannel())) {
                        fyOrderTotal = fyOrderTotal.add(commission.getOrderPrice());
                        fyCommissionTotal = fyCommissionTotal.add(commission.getOneLevelCommission());
                    }
                    //天阙随行付
                    if (Order.InterFaceWay.TQSXF.equals(commission.getPayChannel())) {
                        tqSxfOrderTotal = tqSxfOrderTotal.add(commission.getOrderPrice());
                        tqSxfCommissionTotal = tqSxfCommissionTotal.add(commission.getOneLevelCommission());
                    }
                } else if (id.equals(commission.getTwoLevelId())) {
                    commissionTotal = commissionTotal.add(commission.getTwoLevelCommission());
                    commissionTotalDay = commissionTotalDay.add(commission.getTwoLevelCommission());
                    //支付宝
                    if (Order.PayWay.ALIPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        zfbOrderTotal = zfbOrderTotal.add(commission.getOrderPrice());
                        zfbCommissionTotal = zfbCommissionTotal.add(commission.getTwoLevelCommission());
                    }
                    //微信
                    if (Order.PayWay.WXPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        wxOrderTotal = wxOrderTotal.add(commission.getOrderPrice());
                        wxCommissionTotal = wxCommissionTotal.add(commission.getTwoLevelCommission());
                    }
                    //随行付
                    if (Order.InterFaceWay.SUIXINGFU.getCode().equals(commission.getPayChannel())) {
                        sxfOrderTotal = sxfOrderTotal.add(commission.getOrderPrice());
                        sxfCommissionTotal = sxfCommissionTotal.add(commission.getTwoLevelCommission());
                    }
                    //富有
                    if (Order.InterFaceWay.FUYOU.getCode().equals(commission.getPayChannel())) {
                        fyOrderTotal = fyOrderTotal.add(commission.getOrderPrice());
                        fyCommissionTotal = fyCommissionTotal.add(commission.getTwoLevelCommission());
                    }
                    //天阙随行付
                    if (Order.InterFaceWay.TQSXF.equals(commission.getPayChannel())) {
                        tqSxfOrderTotal = tqSxfOrderTotal.add(commission.getOrderPrice());
                        tqSxfCommissionTotal = tqSxfCommissionTotal.add(commission.getOneLevelCommission());
                    }
                } else if (id.equals(commission.getThirdId())) {
                    commissionTotal = commissionTotal.add(commission.getThirdCommission());
                    commissionTotalDay = commissionTotalDay.add(commission.getThirdCommission());
                    //支付宝
                    if (Order.PayWay.ALIPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        zfbOrderTotal = zfbOrderTotal.add(commission.getOrderPrice());
                        zfbCommissionTotal = zfbCommissionTotal.add(commission.getThirdCommission());
                    }
                    //微信
                    if (Order.PayWay.WXPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        wxOrderTotal = wxOrderTotal.add(commission.getOrderPrice());
                        wxCommissionTotal = wxCommissionTotal.add(commission.getThirdCommission());
                    }
                    //随行付
                    if (Order.InterFaceWay.SUIXINGFU.getCode().equals(commission.getPayChannel())) {
                        sxfOrderTotal = sxfOrderTotal.add(commission.getOrderPrice());
                        sxfCommissionTotal = sxfCommissionTotal.add(commission.getThirdCommission());
                    }
                    //富有
                    if (Order.InterFaceWay.FUYOU.getCode().equals(commission.getPayChannel())) {
                        fyOrderTotal = fyOrderTotal.add(commission.getOrderPrice());
                        fyCommissionTotal = fyCommissionTotal.add(commission.getThirdCommission());
                    }
                    //天阙随行付
                    if (Order.InterFaceWay.TQSXF.equals(commission.getPayChannel())) {
                        tqSxfOrderTotal = tqSxfOrderTotal.add(commission.getOrderPrice());
                        tqSxfCommissionTotal = tqSxfCommissionTotal.add(commission.getOneLevelCommission());
                    }
                } else if (id.equals(commission.getDirectId())) {
                    if (null != commission.getDirectCommission()) {
                        commissionTotal = commissionTotal.add(commission.getDirectCommission());
                        commissionTotalDay = commissionTotalDay.add(commission.getDirectCommission());
                    }
                    //支付宝
                    if (Order.PayWay.ALIPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        zfbOrderTotal = zfbOrderTotal.add(commission.getOrderPrice());
                        if (null != commission.getDirectCommission()) {
                            zfbCommissionTotal = zfbCommissionTotal.add(commission.getDirectCommission());
                        }
                    }
                    //微信
                    if (Order.PayWay.WXPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        wxOrderTotal = wxOrderTotal.add(commission.getOrderPrice());
                        if (null != commission.getDirectCommission()) {
                            wxCommissionTotal = wxCommissionTotal.add(commission.getDirectCommission());
                        }
                    }
                    //随行付
                    if (Order.InterFaceWay.SUIXINGFU.getCode().equals(commission.getPayChannel())) {
                        sxfOrderTotal = sxfOrderTotal.add(commission.getOrderPrice());
                        if (null != commission.getDirectCommission()) {
                            sxfCommissionTotal = sxfCommissionTotal.add(commission.getDirectCommission());
                        }
                    }
                    //富有
                    if (Order.InterFaceWay.FUYOU.getCode().equals(commission.getPayChannel())) {
                        fyOrderTotal = fyOrderTotal.add(commission.getOrderPrice());
                        if (null != commission.getDirectCommission()) {
                            fyCommissionTotal = fyCommissionTotal.add(commission.getDirectCommission());
                        }
                    }
                    //天阙随行付
                    if (Order.InterFaceWay.TQSXF.getCode().equals(commission.getPayChannel())) {
                        tqSxfOrderTotal = tqSxfOrderTotal.add(commission.getOrderPrice());
                        if (null != commission.getDirectCommission()) {
                            tqSxfCommissionTotal = tqSxfCommissionTotal.add(commission.getDirectCommission());
                        }
                    }
                } else if (id.equals(commission.getOperationId())) {
                    commissionTotal = commissionTotal.add(commission.getOperationCommission());
                    commissionTotalDay = commissionTotalDay.add(commission.getOperationCommission());
                    //支付宝
                    if (Order.PayWay.ALIPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        zfbOrderTotal = zfbOrderTotal.add(commission.getOrderPrice());
                        if (null != commission.getOperationCommission()) {
                            zfbCommissionTotal = zfbCommissionTotal.add(commission.getOperationCommission());
                        }
                    }
                    //微信
                    if (Order.PayWay.WXPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        wxOrderTotal = wxOrderTotal.add(commission.getOrderPrice());
                        wxCommissionTotal = wxCommissionTotal.add(commission.getOperationCommission());
                    }
                    //随行付
                    if (Order.InterFaceWay.SUIXINGFU.getCode().equals(commission.getPayChannel())) {
                        sxfOrderTotal = sxfOrderTotal.add(commission.getOrderPrice());
                        sxfCommissionTotal = sxfCommissionTotal.add(commission.getOperationCommission());
                    }
                    //富有
                    if (Order.InterFaceWay.FUYOU.getCode().equals(commission.getPayChannel())) {
                        fyOrderTotal = fyOrderTotal.add(commission.getOrderPrice());
                        fyCommissionTotal = fyCommissionTotal.add(commission.getOperationCommission());
                    }
                    //天阙随行付
                    if (Order.InterFaceWay.TQSXF.getCode().equals(commission.getPayChannel())) {
                        tqSxfOrderTotal = tqSxfOrderTotal.add(commission.getOrderPrice());
                        if (null != commission.getOperationCommission()) {
                            tqSxfCommissionTotal = tqSxfCommissionTotal.add(commission.getOperationCommission());
                        }

                    }
                }
            }
            commissionDay.setOrderTotal(orderTotal);
            commissionDay.setCommissionTotal(commissionTotal);
            commissionDay.setZfbCommissionTotal(zfbCommissionTotal);
            commissionDay.setZfbOrderTotal(zfbOrderTotal);
            commissionDay.setWxOrderTotal(wxOrderTotal);
            commissionDay.setWxCommissionTotal(wxCommissionTotal);
            commissionDay.setSxfCommissionTotal(sxfCommissionTotal);
            commissionDay.setSxfOrderTotal(sxfOrderTotal);
            commissionDay.setFyOrderTotal(fyOrderTotal);
            commissionDay.setFyCommissionTotal(fyCommissionTotal);
            commissionDay.setTqSxfOrderTotal(tqSxfOrderTotal);
            commissionDay.setTqSxfCommissionTotal(tqSxfCommissionTotal);
            commissionDayRepository.saveAndFlush(commissionDay);
        }
        //统计分销下，给其他人分出去多少钱
        CommissionTotal commissionTotalByDay = new CommissionTotal();
        commissionTotalByDay.setCommissionTotal(commissionTotalDay);
        commissionTotalByDay.setOrderTotal(orderTotalDay);
        commissionTotalService.save(commissionTotalByDay);

        for (String id : oemIds) {
            CommissionDay commissionDay = new CommissionDay();
            commissionDay.setCompanyId(id);
            commissionDay.setCreateTime(begin);
            commissionDay.setStatus(0);
            String companyName = companyRepository.findOne(id).getName();
            commissionDay.setCompanyName(companyName);
            BigDecimal orderTotal = new BigDecimal(0);
            BigDecimal commissionTotal = new BigDecimal(0);
            BigDecimal zfbOrderTotal = new BigDecimal(0);
            BigDecimal zfbCommissionTotal = new BigDecimal(0);
            BigDecimal wxOrderTotal = new BigDecimal(0);
            BigDecimal wxCommissionTotal = new BigDecimal(0);
            BigDecimal sxfOrderTotal = new BigDecimal(0);
            BigDecimal sxfCommissionTotal = new BigDecimal(0);
            BigDecimal fyOrderTotal = new BigDecimal(0);
            BigDecimal fyCommissionTotal = new BigDecimal(0);
            BigDecimal tqSxfOrderTotal = new BigDecimal(0);
            BigDecimal tqSxfCommissionTotal = new BigDecimal(0);
            for (Commission commission : commissions) {
                if (id.equals(commission.getOemId())) {
                    orderTotal = orderTotal.add(commission.getOrderPrice());
                    commissionTotal = commissionTotal.add(commission.getOemCommission());
                    //支付宝
                    if (Order.PayWay.ALIPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        zfbOrderTotal = zfbOrderTotal.add(commission.getOrderPrice());
                        if (null != commission.getOemCommission()) {
                            zfbCommissionTotal = zfbCommissionTotal.add(commission.getOemCommission());
                        }
                    }
                    //微信
                    if (Order.PayWay.WXPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        wxOrderTotal = wxOrderTotal.add(commission.getOrderPrice());
                        if (null != commission.getOperationCommission()) {
                            wxCommissionTotal = wxCommissionTotal.add(commission.getOemCommission());
                        }
                    }
                    //随行付
                    if (Order.InterFaceWay.SUIXINGFU.getCode().equals(commission.getPayChannel())) {
                        sxfOrderTotal = sxfOrderTotal.add(commission.getOrderPrice());
                        if (null != commission.getOemCommission()) {
                            sxfCommissionTotal = sxfCommissionTotal.add(commission.getOemCommission());
                        }
                    }
                    //富有
                    if (Order.InterFaceWay.FUYOU.getCode().equals(commission.getPayChannel())) {
                        fyOrderTotal = fyOrderTotal.add(commission.getOrderPrice());
                        if (null != commission.getOemCommission()) {
                            fyCommissionTotal = fyCommissionTotal.add(commission.getOemCommission());
                        }

                    }
                    //天阙随行付
                    if (Order.InterFaceWay.TQSXF.getCode().equals(commission.getPayChannel())) {
                        tqSxfOrderTotal = tqSxfOrderTotal.add(commission.getOrderPrice());
                        if (null != commission.getOemCommission()) {
                            tqSxfCommissionTotal = tqSxfCommissionTotal.add(commission.getOemCommission());
                        }
                    }
                }
            }
            commissionDay.setOrderTotal(orderTotal);
            commissionDay.setCommissionTotal(commissionTotal);
            commissionDay.setZfbCommissionTotal(zfbCommissionTotal);
            commissionDay.setZfbOrderTotal(zfbOrderTotal);
            commissionDay.setWxOrderTotal(wxOrderTotal);
            commissionDay.setWxCommissionTotal(wxCommissionTotal);
            commissionDay.setSxfCommissionTotal(sxfCommissionTotal);
            commissionDay.setSxfOrderTotal(sxfOrderTotal);
            commissionDay.setFyOrderTotal(fyOrderTotal);
            commissionDay.setFyCommissionTotal(fyCommissionTotal);
            commissionDay.setTqSxfCommissionTotal(tqSxfCommissionTotal);
            commissionDay.setTqSxfOrderTotal(tqSxfOrderTotal);
            commissionDayRepository.saveAndFlush(commissionDay);
            log.info("服务商保存" + commissionDay.toString() + "-------ID" + commissionDay.getId());
        }
        for (String id : firstIds) {
            CommissionDay commissionDay = new CommissionDay();
            commissionDay.setCompanyId(id);
            commissionDay.setCreateTime(begin);
            commissionDay.setStatus(0);
            String companyName = companyRepository.findOne(id).getName();
            commissionDay.setCompanyName(companyName);
            BigDecimal orderTotal = new BigDecimal(0);
            BigDecimal commissionTotal = new BigDecimal(0);
            BigDecimal zfbOrderTotal = new BigDecimal(0);
            BigDecimal zfbCommissionTotal = new BigDecimal(0);
            BigDecimal wxOrderTotal = new BigDecimal(0);
            BigDecimal wxCommissionTotal = new BigDecimal(0);
            BigDecimal sxfOrderTotal = new BigDecimal(0);
            BigDecimal sxfCommissionTotal = new BigDecimal(0);
            BigDecimal fyOrderTotal = new BigDecimal(0);
            BigDecimal fyCommissionTotal = new BigDecimal(0);
            BigDecimal tqSxfOrderTotal = new BigDecimal(0);
            BigDecimal tqSxfCommissionTotal = new BigDecimal(0);
            for (Commission commission : commissions) {
                if (id.equals(commission.getFirstId())) {
                    orderTotal = orderTotal.add(commission.getOrderPrice());
                    commissionTotal = commissionTotal.add(commission.getFirstCommission());
                    //支付宝
                    if (Order.PayWay.ALIPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        zfbOrderTotal = zfbOrderTotal.add(commission.getOrderPrice());
                        zfbCommissionTotal = zfbCommissionTotal.add(commission.getFirstCommission());
                    }
                    //微信
                    if (Order.PayWay.WXPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        wxOrderTotal = wxOrderTotal.add(commission.getOrderPrice());
                        wxCommissionTotal = wxCommissionTotal.add(commission.getFirstCommission());
                    }
                    //随行付
                    if (Order.InterFaceWay.SUIXINGFU.getCode().equals(commission.getPayChannel())) {
                        sxfOrderTotal = sxfOrderTotal.add(commission.getOrderPrice());
                        sxfCommissionTotal = sxfCommissionTotal.add(commission.getFirstCommission());
                    }
                    //富有
                    if (Order.InterFaceWay.FUYOU.getCode().equals(commission.getPayChannel())) {
                        fyOrderTotal = fyOrderTotal.add(commission.getOrderPrice());
                        fyCommissionTotal = fyCommissionTotal.add(commission.getFirstCommission());
                    }
                    //天阙随行付
                    if (Order.InterFaceWay.TQSXF.getCode().equals(commission.getPayChannel())) {
                        tqSxfOrderTotal = tqSxfOrderTotal.add(commission.getOrderPrice());
                        tqSxfCommissionTotal = tqSxfCommissionTotal.add(commission.getFirstCommission());
                    }
                }
            }
            commissionDay.setOrderTotal(orderTotal);
            commissionDay.setCommissionTotal(commissionTotal);
            commissionDay.setZfbCommissionTotal(zfbCommissionTotal);
            commissionDay.setZfbOrderTotal(zfbOrderTotal);
            commissionDay.setWxOrderTotal(wxOrderTotal);
            commissionDay.setWxCommissionTotal(wxCommissionTotal);
            commissionDay.setSxfCommissionTotal(sxfCommissionTotal);
            commissionDay.setSxfOrderTotal(sxfOrderTotal);
            commissionDay.setFyOrderTotal(fyOrderTotal);
            commissionDay.setFyCommissionTotal(fyCommissionTotal);
            commissionDay.setTqSxfCommissionTotal(tqSxfCommissionTotal);
            commissionDay.setTqSxfOrderTotal(tqSxfOrderTotal);
            commissionDayRepository.saveAndFlush(commissionDay);
            log.info("一级代理" + commissionDay.toString() + "-------ID" + commissionDay.getId());
        }
        for (String id : secondIds) {
            CommissionDay commissionDay = new CommissionDay();
            commissionDay.setCompanyId(id);
            commissionDay.setCreateTime(begin);
            commissionDay.setStatus(0);
            String companyName = companyRepository.findOne(id).getName();
            commissionDay.setCompanyName(companyName);
            BigDecimal orderTotal = new BigDecimal(0);
            BigDecimal commissionTotal = new BigDecimal(0);
            BigDecimal zfbOrderTotal = new BigDecimal(0);
            BigDecimal zfbCommissionTotal = new BigDecimal(0);
            BigDecimal wxOrderTotal = new BigDecimal(0);
            BigDecimal wxCommissionTotal = new BigDecimal(0);
            BigDecimal sxfOrderTotal = new BigDecimal(0);
            BigDecimal sxfCommissionTotal = new BigDecimal(0);
            BigDecimal fyOrderTotal = new BigDecimal(0);
            BigDecimal fyCommissionTotal = new BigDecimal(0);
            BigDecimal tqSxfOrderTotal = new BigDecimal(0);
            BigDecimal tqSxfCommissionTotal = new BigDecimal(0);
            for (Commission commission : commissions) {
                if (id.equals(commission.getSecondId())) {
                    orderTotal = orderTotal.add(commission.getOrderPrice());
                    commissionTotal = commissionTotal.add(commission.getSecondCommission());
                    //支付宝
                    if (Order.PayWay.ALIPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        zfbOrderTotal = zfbOrderTotal.add(commission.getOrderPrice());
                        if (null != commission.getSecondCommission()) {
                            zfbCommissionTotal = zfbCommissionTotal.add(commission.getSecondCommission());
                        }
                    }
                    //微信
                    if (Order.PayWay.WXPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        wxOrderTotal = wxOrderTotal.add(commission.getOrderPrice());
                        if (null != commission.getSecondCommission()) {
                            wxCommissionTotal = wxCommissionTotal.add(commission.getSecondCommission());
                        }
                    }
                    //随行付
                    if (Order.InterFaceWay.SUIXINGFU.getCode().equals(commission.getPayChannel())) {
                        sxfOrderTotal = sxfOrderTotal.add(commission.getOrderPrice());
                        sxfCommissionTotal = sxfCommissionTotal.add(commission.getSecondCommission());
                    }
                    //富有
                    if (Order.InterFaceWay.FUYOU.getCode().equals(commission.getPayChannel())) {
                        fyOrderTotal = fyOrderTotal.add(commission.getOrderPrice());
                        fyCommissionTotal = fyCommissionTotal.add(commission.getSecondCommission());
                    }
                    //天阙随行付
                    if (Order.InterFaceWay.TQSXF.getCode().equals(commission.getPayChannel())) {
                        tqSxfOrderTotal = tqSxfOrderTotal.add(commission.getOrderPrice());
                        if (null != commission.getSecondCommission()) {
                            tqSxfCommissionTotal = tqSxfCommissionTotal.add(commission.getSecondCommission());
                        }
                    }
                }
            }
            commissionDay.setOrderTotal(orderTotal);
            commissionDay.setCommissionTotal(commissionTotal);
            commissionDay.setZfbCommissionTotal(zfbCommissionTotal);
            commissionDay.setZfbOrderTotal(zfbOrderTotal);
            commissionDay.setWxOrderTotal(wxOrderTotal);
            commissionDay.setWxCommissionTotal(wxCommissionTotal);
            commissionDay.setSxfCommissionTotal(sxfCommissionTotal);
            commissionDay.setSxfOrderTotal(sxfOrderTotal);
            commissionDay.setFyOrderTotal(fyOrderTotal);
            commissionDay.setFyCommissionTotal(fyCommissionTotal);
            commissionDay.setTqSxfCommissionTotal(tqSxfCommissionTotal);
            commissionDay.setTqSxfOrderTotal(tqSxfOrderTotal);
            commissionDayRepository.saveAndFlush(commissionDay);
            log.info("二级代理" + commissionDay.toString() + "-------ID" + commissionDay.getId());

        }

        for (String id : thirdIds) {
            CommissionDay commissionDay = new CommissionDay();
            commissionDay.setCompanyId(id);
            commissionDay.setCreateTime(begin);
            commissionDay.setStatus(0);
            String companyName = companyRepository.findOne(id).getName();
            commissionDay.setCompanyName(companyName);
            BigDecimal orderTotal = new BigDecimal(0);
            BigDecimal commissionTotal = new BigDecimal(0);
            BigDecimal zfbOrderTotal = new BigDecimal(0);
            BigDecimal zfbCommissionTotal = new BigDecimal(0);
            BigDecimal wxOrderTotal = new BigDecimal(0);
            BigDecimal wxCommissionTotal = new BigDecimal(0);
            BigDecimal sxfOrderTotal = new BigDecimal(0);
            BigDecimal sxfCommissionTotal = new BigDecimal(0);
            BigDecimal fyOrderTotal = new BigDecimal(0);
            BigDecimal fyCommissionTotal = new BigDecimal(0);
            BigDecimal tqSxfOrderTotal = new BigDecimal(0);
            BigDecimal tqSxfCommissionTotal = new BigDecimal(0);
            for (Commission commission : commissions) {
                if (id.equals(commission.getThirdId())) {
                    orderTotal = orderTotal.add(commission.getOrderPrice());
                    commissionTotal = commissionTotal.add(commission.getThirdCommission());
                    //支付宝
                    if (Order.PayWay.ALIPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        zfbOrderTotal = zfbOrderTotal.add(commission.getOrderPrice());
                        zfbCommissionTotal = zfbCommissionTotal.add(commission.getThirdCommission());
                    }
                    //微信
                    if (Order.PayWay.WXPAY.getCode().equals(commission.getPayWay()) && Order.InterFaceWay.SELF.getCode().equals(commission.getPayChannel())) {
                        wxOrderTotal = wxOrderTotal.add(commission.getOrderPrice());
                        wxCommissionTotal = wxCommissionTotal.add(commission.getThirdCommission());
                    }
                    //随行付
                    if (Order.InterFaceWay.SUIXINGFU.getCode().equals(commission.getPayChannel())) {
                        sxfOrderTotal = sxfOrderTotal.add(commission.getOrderPrice());
                        sxfCommissionTotal = sxfCommissionTotal.add(commission.getThirdCommission());
                    }
                    //富有
                    if (Order.InterFaceWay.FUYOU.getCode().equals(commission.getPayChannel())) {
                        fyOrderTotal = fyOrderTotal.add(commission.getOrderPrice());
                        fyCommissionTotal = fyCommissionTotal.add(commission.getThirdCommission());
                    }
                    //随行付
                    if (Order.InterFaceWay.TQSXF.getCode().equals(commission.getPayChannel())) {
                        tqSxfOrderTotal = tqSxfOrderTotal.add(commission.getOrderPrice());
                        tqSxfCommissionTotal = tqSxfCommissionTotal.add(commission.getThirdCommission());
                    }
                }
            }
            commissionDay.setOrderTotal(orderTotal);
            commissionDay.setCommissionTotal(commissionTotal);
            commissionDay.setZfbCommissionTotal(zfbCommissionTotal);
            commissionDay.setZfbOrderTotal(zfbOrderTotal);
            commissionDay.setWxOrderTotal(wxOrderTotal);
            commissionDay.setWxCommissionTotal(wxCommissionTotal);
            commissionDay.setSxfCommissionTotal(sxfCommissionTotal);
            commissionDay.setSxfOrderTotal(sxfOrderTotal);
            commissionDay.setFyOrderTotal(fyOrderTotal);
            commissionDay.setFyCommissionTotal(fyCommissionTotal);
            commissionDay.setTqSxfCommissionTotal(tqSxfCommissionTotal);
            commissionDay.setTqSxfOrderTotal(tqSxfOrderTotal);
            commissionDayRepository.saveAndFlush(commissionDay);
            log.info("三级代理" + commissionDay.toString() + "-------ID" + commissionDay.getId());
        }
    }


    public Page<CommissionDay> getPage(String companyId, Pageable pageable, PageSearchDTO dto) {
        List<Company> companies = companyRepository.findByParentId(companyId);
        List<String> companyIds = companies.stream().map(Company::getId).collect(Collectors.toList());
        if (StringUtils.isEmpty(dto.getEnd())) {
            dto.setEnd(DateUtil.tomorrow());
        }
        if (StringUtils.isEmpty(dto.getBegin())) {
            dto.setBegin(DateUtil.offset(new Date(), DateField.YEAR, -100));
        }
        if (StringUtils.isEmpty(dto.getCompanyName())) {
            dto.setCompanyName("");
        }
        Page<CommissionDay> pageVOS;
        if (StringUtils.isEmpty(dto.getStatus())) {
            pageVOS = commissionDayRepository.findAllByCompanyIdInAndCreateTimeBetweenAndCompanyNameLike(pageable, companyIds, dto.getBegin(), dto.getEnd(), "%" + dto.getCompanyName() + "%");
        } else {
            pageVOS = commissionDayRepository.findAllByCompanyIdInAndCreateTimeBetweenAndCompanyNameLikeAndStatus(pageable, companyIds, dto.getBegin(), dto.getEnd(), "%" + dto.getCompanyName() + "%", dto.getStatus());
        }
        return pageVOS;
    }

    /**
     * 补充之前订单的佣金记录
     */
    public void reCreate(CreateCommissionDTO createCommissionDTO) {
        CREATECOMMISSION.put(createCommissionDTO.getId(), 2);
        List<Order> orders;
        //需要重新生成佣金的公司id
        List<String> companyIds = new ArrayList<>();
        //全局
        if (createCommissionDTO.getType().equals(6)) {
            orders = orderRepository.findByPayTimeBetween(createCommissionDTO.getBeginTime(), createCommissionDTO.getEndTime());
        } else {
            Company company = companyRepository.findOne(createCommissionDTO.getId());
            if (company == null) {
                log.info("公司不存在：{}", createCommissionDTO.getId());
                return;
            }
            companyIds.add(createCommissionDTO.getId());
            String idPath;
            //查找当前服务所有下级企业
            if (createCommissionDTO.getType() == 4) {
                idPath = '|' + createCommissionDTO.getId();
            } else {
                idPath = company.getIdPath() + company.getId();
            }

            List<Company> childCompanies = companyRepository.findByIdPathStartingWith(idPath);
            if (childCompanies.size() > 0) {
                companyIds.addAll(childCompanies.stream().map(Company::getId).collect(Collectors.toList()));
            }
            List<Merchant> merchants = merchantRepository.findByCompanyIdIn(companyIds);
            List<String> merchantIds = merchants.stream().map(Merchant::getId).collect(Collectors.toList());
            orders = orderRepository.findByMerchantIdInAndPayTimeBetween(merchantIds, createCommissionDTO.getBeginTime(), createCommissionDTO.getEndTime());
        }
        for (Order order : orders) {
            crateCommission(order, false);
        }
        if (orders.size() == 0) {
            CREATECOMMISSION.put(createCommissionDTO.getId(), 1);
            return;
        }
        //重新按日期归档
        for (Date begin = createCommissionDTO.getBeginTime(); begin.before(createCommissionDTO.getEndTime()); ) {
            if (createCommissionDTO.getType().equals(6)) {
                createComissionDay(begin, null);
            } else {
                createComissionDay(begin, companyIds);
            }
            begin = DateUtil.offset(begin, DateField.DAY_OF_YEAR, 1);
        }
        log.info("111111111111111");
        CREATECOMMISSION.put(createCommissionDTO.getId(), 1);
    }

    public void changeCreateStatus(String id) {
        CREATECOMMISSION.put(id, 1);
    }

    public Resp<Integer> commissionStatus(String id) {
        if (CREATECOMMISSION.get(id) == null || CREATECOMMISSION.get(id) == 1) {
            return Resp.success(1, "生成结束");
        } else {
            return Resp.success(2, "生成中");
        }

    }

    public SummaryVO commission(String companyId) {
        List<Company> companies = companyRepository.findByParentId(companyId);
        if (companies.size() == 0) {
            return new SummaryVO(BigDecimal.ZERO, BigDecimal.ZERO);
        }
        List<String> companyIds = companies.stream().map(Company::getId).collect(Collectors.toList());
        List<CommissionDay> allData = commissionDayRepository.findAllByCompanyIdIn(companyIds);
        SummaryVO summaryVO = new SummaryVO();
        BigDecimal settleCommisson = BigDecimal.ZERO;
        BigDecimal unSettleCommisson = BigDecimal.ZERO;
        for (CommissionDay commissionDay : allData) {
            if (commissionDay.getStatus() == 1) {
                settleCommisson = settleCommisson.add(commissionDay.getCommissionTotal());
            } else {
                unSettleCommisson = unSettleCommisson.add(commissionDay.getCommissionTotal());
            }
        }
        summaryVO.setSettleCommisson(settleCommisson);
        summaryVO.setUnSettleCommisson(unSettleCommisson);
        return summaryVO;
    }

    public List<CommissionDay> getList(String companyId, Pageable pageable, PageSearchDTO dto, String[] ids) {

        List<CommissionDay> list;
        if (ids == null || ids.length <= 0) {

            List<Company> companies = companyRepository.findByParentId(companyId);
            List<String> companyIds = companies.stream().map(Company::getId).collect(Collectors.toList());
            if (StringUtils.isEmpty(dto.getBegin())) { //开始时间
                dto.setBegin(DateUtil.offset(new Date(), DateField.YEAR, -100));
            }
            if (StringUtils.isEmpty(dto.getEnd())) { //结束时间
                dto.setEnd(DateUtil.tomorrow());
            }
            if (StringUtils.isEmpty(dto.getCompanyName())) {
                dto.setCompanyName("");
            }
            Page<CommissionDay> pageVOS;
            if (StringUtils.isEmpty(dto.getStatus())) {  //结算状态
                pageVOS = commissionDayRepository.findAllByCompanyIdInAndCreateTimeBetweenAndCompanyNameLike(pageable, companyIds, dto.getBegin(), dto.getEnd(), "%" + dto.getCompanyName() + "%");
            } else {
                pageVOS = commissionDayRepository.findAllByCompanyIdInAndCreateTimeBetweenAndCompanyNameLikeAndStatus(pageable, companyIds, dto.getBegin(), dto.getEnd(), "%" + dto.getCompanyName() + "%", dto.getStatus());
            }
            //获取分页里面的集合数据
            list = pageVOS.getContent().stream().collect(Collectors.toList());
        } else {
            //根据ids获取集合数据
            List<String> idList = Arrays.asList(ids);
            list = commissionDayRepository.findByIdInOrderByCreateTimeDesc(idList);
        }
        return list;
    }

    public BigDecimal getLeftCommission(Company company) {
        BigDecimal comissionMoney = new BigDecimal(0);
        if (!company.getType().equals(Company.Type.ADMIN.getCode())) {
            StringBuilder sb = new StringBuilder();
            //服务商
            if (company.getType().equals(Company.Type.PROVIDERS.getCode())) {
                sb.append("SELECT IFNULL(sum(oem_commission),0) AS commission  from  order_commission where oem_id = :companyId and order_status=2 and type is null");
            } else if (company.getType().equals(Company.Type.OPERATOR.getCode())) {
                sb.append("SELECT IFNULL(sum(first_commission),0) AS commission  from  order_commission where first_id = :companyId and order_status=2 and type is null");
            } else if (company.getType().equals(Company.Type.DISTRIBUTUTORS.getCode())) {
                sb.append("SELECT IFNULL(sum(second_commission),0) AS commission  from order_commission WHERE second_id = :companyId  and order_status = 2 and type is null");
            } else if (company.getType().equals(Company.Type.THIRDAGENT.getCode())) {
                sb.append("SELECT IFNULL(sum(third_commission),0) AS commission  from order_commission WHERE third_id = :companyId  and order_status = 2 and type is null");
            }
            Query query = em.createNativeQuery(sb.toString());
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            query.setParameter("companyId", company.getId());
            List<Map> list = query.getResultList();
            for (Map map : list) {
                comissionMoney = new BigDecimal(map.get("commission").toString());
            }
        }

        return comissionMoney;
    }

    public BigDecimal getLeftCommissionCreateTime(String begin, String end, Company company) {
        BigDecimal comissionMoney = new BigDecimal(0);
        if (!company.getType().equals(Company.Type.ADMIN.getCode())) {
            StringBuilder sb = new StringBuilder();
            //服务商
            if (company.getType().equals(Company.Type.PROVIDERS.getCode())) {
                sb.append("SELECT IFNULL(sum(oem_commission),0) AS commission  from  order_commission where oem_id = :companyId and order_status=2 and create_time BETWEEN :begin and :end and type is null  ");
            } else if (company.getType().equals(Company.Type.OPERATOR.getCode())) {
                sb.append("SELECT IFNULL(sum(first_commission),0) AS commission  from  order_commission where first_id = :companyId and order_status=2 and create_time BETWEEN :begin and :end and type is null");
            } else if (company.getType().equals(Company.Type.DISTRIBUTUTORS.getCode())) {
                sb.append("SELECT IFNULL(sum(second_commission),0) AS commission  from order_commission WHERE second_id = :companyId  and order_status = 2 and create_time BETWEEN :begin and :end and type is null ");
            } else if (company.getType().equals(Company.Type.THIRDAGENT.getCode())) {
                sb.append("SELECT IFNULL(sum(third_commission),0) AS commission  from order_commission WHERE third_id = :companyId  and order_status = 2 and create_time BETWEEN :begin and :end and type is null ");
            }
            Query query = em.createNativeQuery(sb.toString());
            query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            query.setParameter("companyId", company.getId());
            query.setParameter("begin", begin);
            query.setParameter("end", end);
            List<Map> list = query.getResultList();
            for (Map map : list) {
                String commission = map.get("commission").toString();
                comissionMoney = new BigDecimal(map.get("commission").toString());
            }
        }
        return comissionMoney;
    }

    /**
     * 获取规定时间内的佣金
     *
     * @param dateTime
     */
    public BigDecimal commissionTrend(String dateTime) {
        String startTime = dateTime + " 00:00:00";
        String endTime = dateTime + " 23:59:59";
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT\n" +
                "\tIFNULL( sum( oem_commission ), 0 ) AS oemCommission,\n" +
                "\tIFNULL( sum( operation_commission ), 0 ) AS operationCommission,\n" +
                "\tIFNULL( sum( direct_commission ), 0 ) AS directCommission,\n" +
                "\tIFNULL( sum( one_level_commission ), 0 ) AS oneLevelCommission,\n" +
                "\tIFNULL( sum( two_level_commission ), 0 ) AS twoLevelCommission,\n" +
                "\tIFNULL( sum( three_level_commission ), 0 ) AS threeLevelCommission \n" +
                "FROM\n" +
                "\torder_commission \n" +
                "WHERE\n" +
                "\tcreate_time BETWEEN :startTime \n" +
                "\tAND :endTime \n" +
                "\tAND type = 1 \n" +
                "\tAND order_status = 2");
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("startTime", startTime);
        query.setParameter("endTime", endTime);
        List<Map> list = query.getResultList();
        BigDecimal price=BigDecimal.ZERO;
        for (Map map : list){
            BigDecimal getCommission = new BigDecimal(map.get("oemCommission").toString());
            BigDecimal operationCommission = new BigDecimal(map.get("operationCommission").toString());
            BigDecimal directCommission = new BigDecimal(map.get("directCommission").toString());
            BigDecimal oneLevelCommission = new BigDecimal(map.get("oneLevelCommission").toString());
            BigDecimal twoLevelCommission = new BigDecimal(map.get("twoLevelCommission").toString());
            BigDecimal threeLevelCommission = new BigDecimal(map.get("threeLevelCommission").toString());
            BigDecimal expend=operationCommission.add(directCommission).add(oneLevelCommission).add(twoLevelCommission).add(threeLevelCommission);
            BigDecimal subtract = getCommission.subtract(expend);
            price=price.add(subtract);
        }
        return price;
    }
}