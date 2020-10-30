package com.fzy.admin.fp.goods.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.goods.domain.Goods;
import com.fzy.admin.fp.goods.domain.GoodsShopCart;
import com.fzy.admin.fp.goods.dto.GoodsShopCartDTO;
import com.fzy.admin.fp.goods.service.GoodsService;
import com.fzy.admin.fp.goods.service.GoodsShopCartService;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Desp 添加购物袋
 **/
@RestController
@RequestMapping("/merchant/goods/shop/cart")
@Api(value = "GoodsShooCartController", tags = {"添加购物袋"})
public class GoodsShopCartController extends BaseContent {

    @Resource
    private GoodsShopCartService goodsShopCartService;

    @Resource
    private GoodsService goodsService;

    @Resource
    private MerchantUserService merchantUserService;


    @PostMapping("/insert")
    @ApiOperation(value = "添加商品到购物袋" ,notes = "添加商品到购物袋")
    public Resp insert(@UserId String userId, @Valid GoodsShopCartDTO goodsShopCartDTO){

        MerchantUser merchantUser=merchantUserService.getRepository().findOne(userId);
        try {
            Goods goods=goodsService.getRepository().findByIdAndStoreId(goodsShopCartDTO.getGoodsId(), merchantUser.getStoreId());
            if(goods == null){
                return new Resp().error(Resp.Status.PARAM_ERROR,"不存在改商品");
            }

            GoodsShopCart goodsShopCart = null;
            goodsShopCart = goodsShopCartService.getRepository().findByUserIdAndGoodsId(userId, goodsShopCartDTO.getGoodsId());

            if(goodsShopCart != null){

                if(goodsShopCartDTO.getNum() <0){
                    return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
                }
                goodsShopCart.setNum(goodsShopCartDTO.getNum());

                if (goodsShopCartDTO.getNum() == 0){
                    goodsShopCartService.delete(goodsShopCart.getId());
                    return Resp.success("删除购物袋成功");
                }
                goodsShopCartService.update(goodsShopCart);
                return Resp.success("添加购物袋成功");
            }

            if(goodsShopCartDTO.getNum() < 0){
                return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
            }
            goodsShopCart = new GoodsShopCart();
            goodsShopCart.setNum(goodsShopCartDTO.getNum());
            goodsShopCart.setGoodsId(goodsShopCartDTO.getGoodsId());
            goodsShopCart.setUserId(userId);
            goodsShopCart.setStoreId(merchantUser.getStoreId());
            goodsShopCart.setProperty(goodsShopCart.getProperty());
            goodsShopCartService.save(goodsShopCart);
            return Resp.success("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Resp().error(Resp.Status.INNER_ERROR,"发生错误");
        }
    }

    @GetMapping("/query")
    @ApiOperation(value = "查询购物袋列表" , notes = "查询购物袋列表")
    public Resp query(@UserId String userId){
        List<GoodsShopCart> page = goodsShopCartService.getRepository().getPage(userId);
        return Resp.success(page);
    }

    @DeleteMapping("/remove")
    @ApiOperation(value = "删除购物袋" ,notes = "删除购物袋")
    public Resp remove(@UserId String userId){
        goodsShopCartService.deleteByUserId(userId);
        return Resp.success("删除成功");
    }


}
