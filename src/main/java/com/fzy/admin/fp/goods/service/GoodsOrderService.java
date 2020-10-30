package com.fzy.admin.fp.goods.service;

import cn.hutool.core.date.DateUtil;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.util.TokenUtils;
import com.alibaba.fastjson.JSON;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.goods.bo.GoodsOrderBO;
import com.fzy.admin.fp.goods.bo.GoodsOrderDetailsBO;
import com.fzy.admin.fp.goods.domain.*;
import com.fzy.admin.fp.goods.dto.GoodsOrderDTO;
import com.fzy.admin.fp.goods.dto.GoodsOrderSalesDTO;
import com.fzy.admin.fp.goods.repository.GoodsOrderItemsRepository;
import com.fzy.admin.fp.goods.repository.GoodsOrderRepository;
import com.fzy.admin.fp.goods.repository.GoodsRepository;
import com.fzy.admin.fp.goods.vo.GoodsOrderSalesVo;
import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.coupon.service.PersonCouponService;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.domain.MemberLevel;
import com.fzy.admin.fp.member.member.repository.MemberRepository;
import com.fzy.admin.fp.member.member.service.MemberLevelService;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantRepository;
import com.fzy.admin.fp.merchant.merchant.repository.StoreRepository;
import com.fzy.admin.fp.order.app.dto.PreOrderBindDTO;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.assist.wraps.BeanWrap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GoodsOrderService extends Object implements BaseService<GoodsOrder> {

    @Resource
    private GoodsOrderRepository goodsOrderRepository;
    @Resource
    private MemberRepository memberRepository;
    @Resource
    private StoreRepository storeRepository;
    @Resource
    private GoodsOrderItemsRepository goodsOrderItemsRepository;
    @Resource
    private PersonCouponService personCouponService;
    @Resource
    private MemberRuleService memberRuleService;
    @Resource
    private MemberLevelService memberLevelService;
    @Resource
    private PayRuleService payRuleService;
    @Resource
    private GoodsRepository goodsRepository;

    public BaseRepository<GoodsOrder> getRepository() { return this.goodsOrderRepository; }

    public Resp<Page<GoodsOrderBO>> getPage(GoodsOrderDTO goodsOrderDTO, Pageable pageable,String storeId) {
        if (StringUtils.isBlank(goodsOrderDTO.getStoreOrOrder())) {
            goodsOrderDTO.setStoreOrOrder("");
        }
        if (goodsOrderDTO.getStartTime() == null) {
            goodsOrderDTO.setStartTime(DateUtil.parse("2000-01-01"));
        }
        if (goodsOrderDTO.getEndTime() == null) {
            goodsOrderDTO.setEndTime(DateUtil.parse("2100-01-01"));
        }
        Page<GoodsOrderBO> page = null;
        if (goodsOrderDTO.getPayType() == null) {
            page = this.goodsOrderRepository.getPage(
                    TokenUtils.getMerchantId(), goodsOrderDTO
                            .getStoreOrOrder(), goodsOrderDTO
                            .getStartTime(), goodsOrderDTO
                            .getEndTime(),goodsOrderDTO.getIndustryCategory(),goodsOrderDTO.getIndustryCategory(),storeId, pageable);
        } else {
            page = this.goodsOrderRepository.getPageByPayType(
                    TokenUtils.getMerchantId(), goodsOrderDTO
                            .getStoreOrOrder(), goodsOrderDTO
                            .getStartTime(), goodsOrderDTO
                            .getEndTime(), goodsOrderDTO
                            .getPayType(), goodsOrderDTO.getIndustryCategory(),goodsOrderDTO.getIndustryCategory(),storeId, pageable);
        }

        return Resp.success(page);
    }


    public Resp<GoodsOrderDetailsBO> details(String orderId) {
        GoodsOrderDetailsBO goodsOrderDetailsBO = new GoodsOrderDetailsBO();
        GoodsOrder goodsOrder = (GoodsOrder)this.goodsOrderRepository.findOne(orderId);
        BeanUtils.copyProperties(goodsOrder, goodsOrderDetailsBO);
        Store store = (Store)this.storeRepository.findOne(goodsOrder.getStoreId());
        if (store != null) {
            goodsOrderDetailsBO.setStoreName(store.getName());
        }

        if(goodsOrder.getMemberId() != null && !goodsOrder.getMemberId().equals("")){
            Member member = (Member)this.memberRepository.findOne(goodsOrder.getMemberId());
            if (member != null) {
                goodsOrderDetailsBO.setNickname(member.getNickname());
                goodsOrderDetailsBO.setHead(member.getHead());
                goodsOrderDetailsBO.setMemberLevelId(member.getMemberLevelId());
            }
        }else {
            goodsOrderDetailsBO.setNickname("");
            goodsOrderDetailsBO.setHead("");
            goodsOrderDetailsBO.setMemberLevelId("");
        }
        List<GoodsOrderItems> goodsOrderItemsList = this.goodsOrderItemsRepository.findByGoodsOrderId(orderId);
        goodsOrderDetailsBO.setGoodsOrderItemsList(goodsOrderItemsList);
        return Resp.success(goodsOrderDetailsBO);
    }

    public GoodsOrder createGoodsOrder(Order order, List<Goods> goods, String memberRuleId, BigDecimal memberRuleMoney, BigDecimal totalPrice, BigDecimal actPrice) {
        GoodsOrder goodsOrder = new GoodsOrder();
        BeanWrap.copyProperties(order, goodsOrder);
        goodsOrder.setOrderNo(order.getOrderNumber());
        goodsOrder.setOrderPrice(totalPrice);
        goodsOrder.setPayPrice(actPrice);
        goodsOrder.setPayType(order.getPayWay());
        if (!StringUtils.isEmpty(order.getCode())) {

            PersonCoupon personCoupon = this.personCouponService.findByMerchantIdAndStatusAndCode(order.getMerchantId(), PersonCoupon.Status.NO_USE.getCode(), order.getCode());
            goodsOrder.setCouponName(personCoupon.getName());
            goodsOrder.setCouponPrice(personCoupon.getMoney());
        }
        if (StringUtils.isNotEmpty(memberRuleId)) {
            MemberRule memberRule = (MemberRule)this.memberRuleService.findOne(memberRuleId);
            goodsOrder.setFullReductionName(memberRule.getRuleName());
            goodsOrder.setFullReductionPrice(memberRuleMoney);
        }
        if (StringUtils.isNotEmpty(order.getMemberId())) {
            Member member = (Member)this.memberRepository.findOne(order.getMemberId());
            MemberLevel level = (MemberLevel)this.memberLevelService.findOne(member.getMemberLevelId());
            goodsOrder.setDiscountName(level.getName());
            goodsOrder.setDiscountPrice(totalPrice.multiply(level.getDiscount()));
        }
        if (order.getScore() != null && order.getScore().intValue() != 0) {

            PayRule payRule = this.payRuleService.findByCompanyId(order.getMerchantId());
            payRule = this.payRuleService.getPayRule(payRule);
            Map mapType = (Map)JSON.parseObject(payRule.getIntegralRule(), Map.class);
            BigDecimal deduction = BigDecimal.valueOf(Double.parseDouble(mapType.get("deduction").toString()));
            BigDecimal integralNum = BigDecimal.valueOf(Double.parseDouble(mapType.get("integralNum").toString()));
            BigDecimal integralPrice = BigDecimal.valueOf(order.getScore().intValue()).multiply(deduction).divide(integralNum);
            goodsOrder.setIntegralName(order.getScore().toString());
            goodsOrder.setIntegralPrice(integralPrice);
        }
        goodsOrder.setStatus(Integer.valueOf(2));
        //暂时先写死，后续要和安卓做对接才优化此值
        goodsOrder.setIndustryCategory(Integer.valueOf(0));
        goodsOrder.setUseType(0);
        GoodsOrder save = (GoodsOrder)this.goodsOrderRepository.save(goodsOrder);
        goods.forEach(goods1 -> {
            GoodsOrderItems goodsOrderItems = new GoodsOrderItems();
            goodsOrderItems.setGoodsOrderId(save.getId());
            BeanWrap.copyProperties(goods1, goodsOrderItems);
            goodsOrderItems.setId(null);
            goodsOrderItems.setCreateTime(null);
            goodsOrderItems.setGoodsId(goods1.getId());
            BigDecimal goodsTotalPrice = BigDecimal.valueOf(goods1.getSalesNum().intValue()).multiply(goods1.getGoodsPrice());
            goodsOrderItems.setTotalPrice(goodsTotalPrice);
            this.goodsOrderItemsRepository.save(goodsOrderItems);
            int saleNum = (goods1.getSalesNum() == null) ? 0 : goods1.getSalesNum().intValue();
            Goods realGoods = (Goods)this.goodsRepository.findOne(goods1.getId());
            int realSaleNum = (realGoods.getSalesNum() == null) ? 0 : realGoods.getSalesNum().intValue();
            goods1.setSalesNum(Integer.valueOf(saleNum + realSaleNum));
            goods1.setStockNum(Integer.valueOf(goods1.getStockNum().intValue() - saleNum));
            this.goodsRepository.saveAndFlush(goods1);
        });
        return save;
    }

    public List<GoodsOrderSalesDTO> getGoodsOrderSales(GoodsOrderSalesVo goodsOrderSalesVo) {

        List<GoodsOrderSalesDTO> goodsOrderSalesDtoList = new ArrayList<>();
//
//        //查询当前商户
//        Merchant merchant = merchantRepository.findOne(goodsOrderSalesVo.getMerchantId());
//        if(merchant == null || merchant.equals("")){
//            throw new BaseException("当前商户不存在", Resp.Status.PARAM_ERROR.getCode());
//        }
//
//        DateTime startTime = DateUtil.beginOfDay(goodsOrderSalesVo.getStartTime());
//        DateTime endTime = DateUtil.endOfDay(goodsOrderSalesVo.getEndTime());
//
//        //查询商户下的所有商品
//        //List<Goods> goodsList = goodsRepository.findByMerchantIdAndCreateTimeBetween(merchant.getId(), startTime, endTime);
//        List<Goods> goodsList = goodsRepository.findByMerchantId(merchant.getId());
//        if(goodsList.size() <= 0){
//            throw new BaseException("当前商户没有商品", Resp.Status.PARAM_ERROR.getCode());
//        }
//
//        for (Goods goods:goodsList) {
//            GoodsOrderSalesDto goodsOrderSalesDto = new GoodsOrderSalesDto();
//            goodsOrderSalesDto.setMerchantId(goods.getMerchantId()); //商户id
//            goodsOrderSalesDto.setGoodsName(goods.getGoodsName());  //商品名称
//            goodsOrderSalesDto.setGoodsImg(goods.getGoodsPic());   //商品图片
//            GoodsOrderItemsDto goodsOrderItemsDto = goodsOrderItemsRepository.findByCountTotalPrice(goods.getGoodsName(), startTime, endTime);
//            if(goodsOrderItemsDto.getTotalPrice() == null){
//                goodsOrderSalesDto.setSalesNum(BigDecimal.ZERO.doubleValue());
//            }else {
//                goodsOrderSalesDto.setSalesNum(goodsOrderItemsDto.getTotalPrice().doubleValue()); //销售额
//            }
//            goodsOrderSalesDto.setSalesCountNum(goods.getSalesNum());//销量份
//
//            //该商品时间内的交易笔数
//            List<GoodsOrderItems> itemsList = goodsOrderItemsRepository.findByGoodsNameAndCreateTimeBetween(goods.getGoodsName(), startTime, endTime);
//            if(itemsList.size() <= 0){
//                goodsOrderSalesDto.setTradingVolume(0); //交易笔数
//            }else {
//                goodsOrderSalesDto.setTradingVolume(itemsList.size()); //交易笔数
//            }
//
//            goodsOrderSalesDtoList.add(goodsOrderSalesDto);
//        }

        return  goodsOrderSalesDtoList;
    }


    @Transactional
    public GoodsOrder preGoodsOrder(PreOrderBindDTO preOrderBindDTO) {
        GoodsOrder goodsOrder = new GoodsOrder();
        BeanWrap.copyProperties(preOrderBindDTO, goodsOrder);
        goodsOrder.setOrderPrice(preOrderBindDTO.getTotalPrice());
        goodsOrder.setStatus(Integer.valueOf(1));
        GoodsOrder save = this.goodsOrderRepository.save(goodsOrder);
        preOrderBindDTO.getGoods().forEach(goods1 -> {
            GoodsOrderItems goodsOrderItems = new GoodsOrderItems();
            goodsOrderItems.setGoodsOrderId(save.getId());
            BeanWrap.copyProperties(goods1, goodsOrderItems);
            goodsOrderItems.setId(null);
            goodsOrderItems.setCreateTime(null);
            goodsOrderItems.setGoodsId(goods1.getId());
            BigDecimal goodsTotalPrice = BigDecimal.valueOf(goods1.getSalesNum().intValue()).multiply(goods1.getGoodsPrice());
            goodsOrderItems.setTotalPrice(goodsTotalPrice);
            this.goodsOrderItemsRepository.save(goodsOrderItems);
            int saleNum = (goods1.getSalesNum() == null) ? 0 : goods1.getSalesNum().intValue();
            Goods realGoods = (Goods)this.goodsRepository.findOne(goods1.getId());
            int realSaleNum = (realGoods.getSalesNum() == null) ? 0 : realGoods.getSalesNum().intValue();
            goods1.setSalesNum(Integer.valueOf(saleNum + realSaleNum));
            goods1.setStockNum(Integer.valueOf(goods1.getStockNum().intValue() - saleNum));
            this.goodsRepository.save(goods1);
        });
        return save;
    }


}
