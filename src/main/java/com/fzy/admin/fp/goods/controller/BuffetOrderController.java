package com.fzy.admin.fp.goods.controller;


import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.goods.domain.GoodsCategory;
import com.fzy.admin.fp.goods.domain.GoodsOrder;
import com.fzy.admin.fp.goods.service.BuffetOrderService;
import com.fzy.admin.fp.merchant.merchant.vo.StoreNameMerchantLogoVO;
import com.fzy.admin.fp.order.app.dto.PreOrderBindDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "BuffetOrderController" ,tags = "自助点餐")
@RequestMapping("/merchant/goods/order")
public class BuffetOrderController {

    @Autowired
    private BuffetOrderService buffetOrderService;

    @ApiOperation(value = "获取门店名称/商户logo" ,notes = "获取门店名称/商户logo")
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",name="merchantId",dataType="int",value="商户ID")})
    @GetMapping("/storename_merchantlogo")
    public Resp<StoreNameMerchantLogoVO> storeNameMerchantLogo(@UserId String userId,@TokenInfo(property = "merchantId") String merchantId){
        return Resp.success(buffetOrderService.storeNameMerchantLogo(userId,merchantId));
    }

    @ApiOperation(value = "获取菜单分类" , notes = "获取菜单分类")
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",name="industryCategory",dataType="int",value="行业分类 0超市 1自助点餐 2医药 3加油站 4景区")})
    @GetMapping("/get_category")
    public List<GoodsCategory>  getCategory(@UserId String userId ,  Integer industryCategory){
        return buffetOrderService.getCategory(userId ,industryCategory);
    }

    @ApiOperation(value = "获取商品" ,notes = "获取商品")
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",name="industryCategory",dataType="int",required=true,value="行业分类 0超市 1自助点餐 2医药 3加油站 4景区")})
    @GetMapping("/get_goods")
    public Resp getGoods(@UserId String userId , Integer industryCategory){
        return Resp.success(buffetOrderService.getGoods(userId ,industryCategory));
    }

    @PostMapping({"/pre_goods_order"})
    @ApiOperation(value = "菜品预下单", notes = "菜品预下单")
    @ApiImplicitParams({@ApiImplicitParam(paramType="query",name="useType",dataType="int",required=true,value="使用方式  0默认  1外带 2堂食 3堂食餐号",defaultValue = "0"),
            @ApiImplicitParam(paramType="query",name="takeNumber",dataType="String",required=true,value="取号")})
    public Resp<GoodsOrder> preGoodsOrder(@UserId String userId,Integer useType,String takeNumber) {
        Resp resp=this.buffetOrderService.preGoodsOrder(userId, useType, takeNumber);
        return resp;
    }
}
