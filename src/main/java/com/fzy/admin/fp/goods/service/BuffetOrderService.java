package com.fzy.admin.fp.goods.service;

import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.goods.domain.*;
import com.fzy.admin.fp.goods.repository.GoodsRepository;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.merchant.merchant.service.StoreService;
import com.fzy.admin.fp.goods.vo.GoodsCategoryNameVO;
import com.fzy.admin.fp.merchant.merchant.vo.StoreNameMerchantLogoVO;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BuffetOrderService {

    @Autowired
    private MerchantUserService merchantUserService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @Autowired
    private GoodsService goodsService;

    @Resource
    private GoodsRepository goodsRepository;

    @Resource
    private GoodsShopCartService goodsShopCartService;

    @Resource
    private GoodsOrderService goodsOrderService;

    @Resource
    private GoodsOrderItemsService goodsOrderItemsService;

    public StoreNameMerchantLogoVO storeNameMerchantLogo(String userId, String merchantId) {

        StoreNameMerchantLogoVO storeNameMerchantLogoVO=new StoreNameMerchantLogoVO();
        MerchantUser merchantUser=merchantUserService.getRepository().findOne(userId);
        Store store=storeService.findOne(merchantUser.getStoreId());
        Merchant merchant=merchantService.findOne(merchantId);
        storeNameMerchantLogoVO.setPhotoId(merchant.getPhotoId());
        storeNameMerchantLogoVO.setStoreName(store.getName());
        return storeNameMerchantLogoVO;
    }

    public List<GoodsCategory> getCategory(String userId, Integer industryCategory) {
        MerchantUser merchantUser=merchantUserService.getRepository().findOne(userId);
        return goodsCategoryService.getRepository().findByStoreIdAndIndustryCategoryOrderBySort(merchantUser.getStoreId(), industryCategory);
    }

    public List<GoodsCategoryNameVO> getGoods(String userId, Integer industryCategory) {

        List<GoodsCategoryNameVO> GoodsCategoryNameVOList=new ArrayList<>();
        MerchantUser merchantUser=merchantUserService.getRepository().findOne(userId);
        List<GoodsCategory> goodsCategoryList=goodsCategoryService.getRepository().findByStoreIdAndIndustryCategoryOrderBySort(merchantUser.getStoreId(), industryCategory);
        if (goodsCategoryList.size() <= 0) {
            return GoodsCategoryNameVOList;
        }

        for (GoodsCategory goodsCategory : goodsCategoryList) {
            GoodsCategoryNameVO goodsCategoryNameVO=new GoodsCategoryNameVO();
            goodsCategoryNameVO.setCategoryName(goodsCategory.getCagegoryName());
            //根据分类id查询出商品名称
            List<Goods> goodsList=goodsService.getRepository().findByStoreIdAndGoodsCategoryAndIndustryCategoryOrderByCreateTimeDesc(merchantUser.getStoreId(), goodsCategory.getId(), industryCategory);
            if (goodsList.size() <= 0) {
                return GoodsCategoryNameVOList;
            }
            goodsCategoryNameVO.setGoods(goodsList);
            GoodsCategoryNameVOList.add(goodsCategoryNameVO);
        }

        return GoodsCategoryNameVOList;
    }

    @Transactional(rollbackFor = Exception.class)
    public Resp preGoodsOrder(String userId, Integer useType, String takeNumber) {

        MerchantUser merchantUser=merchantUserService.findOne(userId);
        Store store=storeService.findOne(merchantUser.getStoreId());
        GoodsOrder goodsOrder=new GoodsOrder();
        try {
            //查询购物袋
            List<GoodsShopCart> goodsShopCartList=goodsShopCartService.getRepository().findAllByUserId(userId);
            if (goodsShopCartList == null || goodsShopCartList.size() == 0) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "请选择商品");
            }

            //算出购物车总共的金额
            BigDecimal price=new BigDecimal("0");
            List<GoodsOrderItems> oderPropertyList=new ArrayList<>();
            StringBuilder shopInfo=new StringBuilder();
            for (GoodsShopCart data : goodsShopCartList) {
                GoodsOrderItems goodsOrderItems=new GoodsOrderItems(); //商品明细
                Goods goods=goodsService.findOne(data.getGoodsId());
                shopInfo.append(goods.getGoodsName() + (StringUtil.isEmpty(data.getProperty()) ? "" : "(" + data.getProperty() + ")") + "*" + data.getNum() + ";");
                if (goods == null) {//商品不存在时，清除掉购物车里的商品
                    goodsShopCartService.getRepository().deleteByGoodsId(data.getGoodsId());
                    continue;
                }
                if (goods.getStockNum() == 0) {//商品已估清
                    return new Resp().error(Resp.Status.PARAM_ERROR, goods.getName() + "，商品已估清");
                }
                if (goods.getIsShelf() == 0) {//商品已下架
                    return new Resp().error(Resp.Status.PARAM_ERROR, goods.getName() + "，商品已下架");
                }
                if (data.getNum() > goods.getStockNum()) {//商品库存小于需要购买的数量
                    return new Resp().error(Resp.Status.PARAM_ERROR, goods.getName() + "，商品库存小于需要购买的数量");
                }
                price=price.add(goods.getGoodsPrice().multiply(new BigDecimal(data.getNum())));
                goodsOrderItems.setGoodsPic(goods.getGoodsPic() == null ? "" : goods.getGoodsPic().split(",")[0]);
                goodsOrderItems.setSalesNum(data.getNum());
                goodsOrderItems.setGoodsPrice(goods.getGoodsPrice());
                goodsOrderItems.setGoodsId(data.getGoodsId());
                goodsOrderItems.setGoodsName(goods.getGoodsName());
                oderPropertyList.add(goodsOrderItems);

                //统计商品销量和减少库存
                int saleNum=data.getNum();
                Goods realGoods=(Goods) this.goodsRepository.findOne(goods.getId());
                int realSaleNum=(realGoods.getSalesNum() == null) ? 0 : realGoods.getSalesNum().intValue();
                goods.setSalesNum(Integer.valueOf(saleNum + realSaleNum));
                goods.setStockNum(Integer.valueOf(goods.getStockNum().intValue() - saleNum));
                this.goodsRepository.save(goods);

            }
            if (price.compareTo(new BigDecimal("0")) == 0) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "请在购物车添加需要购买的商品");
            }

            goodsOrder.setName(shopInfo.toString().substring(0, shopInfo.length() - 1));
            goodsOrder.setOrderPrice(price);
            goodsOrder.setStatus(Integer.valueOf(1));
            goodsOrder.setUserId(userId);
            goodsOrder.setStoreId(merchantUser.getStoreId());
            goodsOrder.setIndustryCategory(store.getIndustryCategory());
            //等于2是堂食
            if (useType.equals(Integer.valueOf(2))) {
                goodsOrder.setTakeNumber(takeNumber);
            } else {
                //TODO 超过十二点自动清零
                goodsOrder.setTakeNumber("1");

            }
            addOrder(goodsOrder, oderPropertyList);
        } catch (Exception e) {
            new Resp().error(Resp.Status.PARAM_ERROR, "预下单失败");
        }
        return Resp.success(goodsOrder);
    }

    /**
     * 添加商品订单
     *
     * @param goodsOrder
     * @param oderPropertyList
     */
    @Transactional(rollbackFor =Exception.class )
    public void addOrder(GoodsOrder goodsOrder, List<GoodsOrderItems> oderPropertyList) {
        //保存订单
        GoodsOrder save=goodsOrderService.save(goodsOrder);
        //保存订单商品属性信息
        oderPropertyList.stream().forEach(s -> s.setGoodsOrderId(save.getId()));
        goodsOrderItemsService.save(oderPropertyList);
//        OrderJob orderJob=new OrderJob();
//        orderJob.setShopOrderId(save.getId());
//        ScheduleUtils.createScheduleJob(scheduler,save.getId(),"OrderJob", DateUtils.getCron(DateUtils.getDayByNum(new Date(),1)),orderJob);
    }
}
