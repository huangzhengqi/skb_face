package com.fzy.admin.fp.distribution.app.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.shop.domain.GoodsProperty;
import com.fzy.admin.fp.distribution.pc.service.GoodsPropertyService;
import com.fzy.admin.fp.distribution.shop.domain.DistGoods;
import com.fzy.admin.fp.distribution.shop.service.DistGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
/**
 * @Author yy
 * @Date 2019-12-2 10:26:12
 * @Desp
 **/

@RestController
@RequestMapping("/dist/app/shop")
@Api(value = "DistGoodsController", tags = {"分销-app商品管理"})
public class AppGoodsController {

    @Resource
    private DistGoodsService distGoodsService;

    @GetMapping("/query")
    @ApiOperation(value = "商品列表", notes = "商品列表")
    public Resp query(@TokenInfo(property="serviceProviderId")String serviceProviderId, PageVo pageVo){
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<DistGoods> goodsPage = distGoodsService.getRepository().findAllByStatusAndServiceProviderIdOrderByWeightDesc(0,serviceProviderId, pageable);
        return Resp.success(goodsPage);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "商品详细信息", notes = "商品详细信息")
    public Resp detail(String id){
        return Resp.success(distGoodsService.findOne(id));
    }
}
