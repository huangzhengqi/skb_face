package com.fzy.admin.fp.distribution.app.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.dto.ShopCartDTO;
import com.fzy.admin.fp.distribution.shop.domain.DistGoods;
import com.fzy.admin.fp.distribution.shop.domain.ShopCart;
import com.fzy.admin.fp.distribution.shop.service.DistGoodsService;
import com.fzy.admin.fp.distribution.shop.service.ShopCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author yy
 * @Date 2019-12-2 10:44:01
 * @Desp 用户购物车
 **/
@RestController
@RequestMapping("/dist/app/cart")
    @Api(value = "DistGoodsController", tags = {"分销-app商品管理"})
public class ShopCartController extends BaseContent{

    @Resource
    private ShopCartService shopCartService;

    @Resource
    private DistGoodsService distGoodsService;


    @PostMapping("/insert")
    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车")
    public Resp insert(@TokenInfo(property="serviceProviderId")String serviceProviderId,@UserId String userId,@Valid ShopCartDTO shopCartDTO){
        try {
            DistGoods disGoods = distGoodsService.getRepository().findByServiceProviderIdAndId(serviceProviderId, shopCartDTO.getGoodsId());
            if(disGoods==null){
                return new Resp().error(Resp.Status.TOKEN_ERROR,"不存在该商品");
            }
            ShopCart shopCart =null;
            if(StringUtil.isEmpty(shopCartDTO.getProperty())){
                shopCart = shopCartService.getRepository().findByUserIdAndGoodsId(userId,shopCartDTO.getGoodsId());
            }else {
                shopCart = shopCartService.getRepository().findByUserIdAndGoodsIdAndProperty(userId,shopCartDTO.getGoodsId(), shopCartDTO.getProperty());
            }
            if(shopCart!=null){
                //数量不能出现负数
                if(shopCartDTO.getNum()<0){
                    return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
                }
                shopCart.setNum(shopCartDTO.getNum());
                //如果数量为
                if(shopCart.getNum()==0){
                    shopCartService.delete(shopCart.getId());
                    return Resp.success("删除成功");
                }
                shopCartService.update(shopCart);
                return Resp.success("添加成功");
            }
            if(shopCartDTO.getNum()<0){
                return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
            }
            shopCart=new ShopCart();
            shopCart.setNum(shopCartDTO.getNum());
            shopCart.setGoodsId(shopCartDTO.getGoodsId());
            shopCart.setUserId(userId);
            shopCart.setProperty(shopCartDTO.getProperty());
            shopCartService.save(shopCart);
            return Resp.success("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Resp().error(Resp.Status.INNER_ERROR,"发生错误");
        }
    }

    @GetMapping("/query")
    @ApiOperation(value = "查询购物车列表", notes = "查询购物车列表")
    public Resp query(@UserId String userId){
        //Pageable pageable = PageUtil.initPage(pageVo);
        List<ShopCart> page = shopCartService.getRepository().getPage(userId);
        return Resp.success(page);
    }

    @PostMapping("/remove")
    @ApiOperation(value = "清空购物车", notes = "清空购物车")
    public Resp remove(@UserId String userId){
        shopCartService.deleteByUserId(userId);
        return Resp.success("清空成功");
    }
}
