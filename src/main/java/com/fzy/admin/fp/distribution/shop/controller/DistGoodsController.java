package com.fzy.admin.fp.distribution.shop.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.shop.domain.DistGoods;
import com.fzy.admin.fp.distribution.shop.domain.GoodsProperty;
import com.fzy.admin.fp.distribution.shop.dto.DistGoodsDTO;
import com.fzy.admin.fp.distribution.shop.service.DistGoodsService;
import com.fzy.admin.fp.distribution.pc.service.GoodsPropertyService;
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
 * @Date 2019-11-30 10:06:29
 * @Desp 商品
 **/

@RestController
@RequestMapping("/dist/pc/shop")
@Api(value = "DistGoodsController", tags = {"分销-后台商品管理"})
public class DistGoodsController extends BaseContent {

    @Resource
    private DistGoodsService distGoodsService;

    @PostMapping("/insert")
    @ApiOperation(value = "添加商品", notes = "添加商品")
    public Resp insert(@TokenInfo(property="serviceProviderId")String serviceProviderId,DistGoodsDTO distGoodsDTO){
        DistGoods distGoods=new DistGoods();
        BeanUtil.copyProperties(distGoodsDTO,distGoods);
        distGoods.setServiceProviderId(serviceProviderId);
        distGoods.setStatus(0);
        //查询设备有多少台
        int count = distGoodsService.getRepository().countByServiceProviderId(serviceProviderId);
        distGoods.setWeight(count + 0);//排序
        distGoodsService.save(distGoods);
        return Resp.success("新增成功");
    }

    @PostMapping("/update")
    @ApiOperation(value = "编辑商品", notes = "编辑商品")
    public Resp update(@TokenInfo(property="serviceProviderId")String serviceProviderId, @RequestBody DistGoodsDTO distGoodsDTO){
        DistGoods distGoods = distGoodsService.getRepository().findByServiceProviderIdAndId(serviceProviderId, distGoodsDTO.getId());
        if(distGoods==null){
            return new Resp().error(Resp.Status.TOKEN_ERROR,"参数错误,请重新登录");
        }

        if(!ParamUtil.isBlank(distGoodsDTO.getWeight())){
            //查询商品表的排序值是否存在
            if(distGoodsService.getRepository().countByServiceProviderIdAndWeight(serviceProviderId,distGoodsDTO.getWeight())!=0){
                return new Resp().error(Resp.Status.PARAM_ERROR,"该排序数值已存在，不可重复！");
            }

        }
        BeanUtil.copyProperties(distGoodsDTO,distGoods);
        distGoodsService.update(distGoods);
        return Resp.success("编辑成功");
    }

    @GetMapping("/query")
    @ApiOperation(value = "商品列表", notes = "商品列表")
    public Resp query(@TokenInfo(property="serviceProviderId")String serviceProviderId, PageVo pageVo){
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<DistGoods> goodsPage = distGoodsService.getRepository().findAllByServiceProviderIdOrderByWeightDesc(serviceProviderId, pageable);
        return Resp.success(goodsPage);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "商品详细信息", notes = "商品详细信息")
    public Resp detail(String id){
        return Resp.success(distGoodsService.findOne(id));
    }

//    @PostMapping("/set")
//    @ApiOperation(value = "估清/无限", notes = "估清/无限")
//    public Resp set(@TokenInfo(property="serviceProviderId")String serviceProviderId,String id,Boolean type){
//        DistGoods distGoods = distGoodsService.getRepository().findByServiceProviderIdAndId(serviceProviderId, id);
//        if(distGoods==null){
//            return new Resp().error(Resp.Status.TOKEN_ERROR,"参数错误，请重新登录");
//        }
//        if(type){//无限
//            distGoods.setNum(-1);
//        }else if(!type){//估清
//            distGoods.setNum(0);
//        }
//        distGoodsService.update(distGoods);
//        return Resp.success("设置成功");
//    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除", notes = "删除")
    public Resp delete(@TokenInfo(property="serviceProviderId")String serviceProviderId,String id){
        DistGoods distGoods = distGoodsService.getRepository().findByServiceProviderIdAndId(serviceProviderId, id);
        if(distGoods==null){
            return new Resp().error(Resp.Status.TOKEN_ERROR,"参数错误，请重新登录");
        }
        distGoodsService.deleteById(id);
        return Resp.success("删除成功");
    }

    @PostMapping("/put")
    @ApiOperation(value = "上架/下架", notes = "上架/下架")
    public Resp putAway(@TokenInfo(property="serviceProviderId")String serviceProviderId,String id){
        DistGoods distGoods = distGoodsService.getRepository().findByServiceProviderIdAndId(serviceProviderId, id);
        if(distGoods==null){
            return new Resp().error(Resp.Status.TOKEN_ERROR,"参数错误，请重新登录");
        }
        distGoods.setStatus(distGoods.getStatus()==1?0:1);
        distGoodsService.update(distGoods);
        return Resp.success("设置成功");
    }

//    @PostMapping("/inventory")
//    @ApiOperation(value = "设置库存", notes = "设置库存")
//    public Resp inventory(@TokenInfo(property="serviceProviderId")String serviceProviderId,String id,Integer num){
//        DistGoods distGoods = distGoodsService.getRepository().findByServiceProviderIdAndId(serviceProviderId, id);
//        if(distGoods==null){
//            return new Resp().error(Resp.Status.TOKEN_ERROR,"参数错误，请重新登录");
//        }
//        distGoods.setNum(num);
//        distGoodsService.update(distGoods);
//        return Resp.success("设置成功");
//    }


}
