package com.fzy.admin.fp.distribution.app.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.shop.domain.Address;
import com.fzy.admin.fp.distribution.shop.domain.DistGoods;
import com.fzy.admin.fp.distribution.shop.dto.AddressDTO;
import com.fzy.admin.fp.distribution.shop.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @Date 2019-12-2 11:52:01
 * @Desp 收货地址
 **/
@RestController
@RequestMapping("/dist/app/address")
@Api(value = "AddressController", tags = {"分销-app用戶地址管理"})
public class AddressController extends BaseContent {

    @Resource
    private AddressService addressService;


    @GetMapping("/query")
    @ApiOperation(value = "地址列表", notes = "地址列表")
    public Resp query(@UserId String userId, PageVo pageVo){
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<Address> goodsPage = addressService.getRepository().findAllByUserId(userId, pageable);
        return Resp.success(goodsPage);
    }

    @GetMapping("/default")
    @ApiOperation(value = "获取默认地址", notes = "获取默认地址")
    public Resp defaultAddress(@UserId String userId){
        Address address = addressService.getRepository().findByUserIdAndStatus(userId, 1);
        if(address==null){
            List<Address> addressList = addressService.getRepository().findAllByUserId(userId);
            if(addressList.size()>0){
                return Resp.success(addressList.get(0));
            }
        }
        return Resp.success(address);
    }

    @PostMapping("/insert")
    @ApiOperation(value="添加地址",notes = "添加地址")
    public Resp insert(@Valid AddressDTO addressDTO, @UserId String userId){
        Address address=new Address();
        BeanUtil.copyProperties(addressDTO,address);
        address.setUserId(userId);
        addressService.saveAddress(address);
        return Resp.success("添加成功");
    }

    @PostMapping("/update")
    @ApiOperation(value="修改地址",notes = "修改地址")
    public Resp update(@Valid AddressDTO addressDTO, @UserId String userId){
        Address address = addressService.getRepository().findByUserIdAndId(userId, addressDTO.getId());
        if(address==null){
            return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        address.setName(addressDTO.getName());
        address.setStatus(addressDTO.getStatus());
        address.setDetailPlace(addressDTO.getDetailPlace());
        address.setPhone(addressDTO.getPhone());
        address.setPlace(addressDTO.getPlace());
        addressService.updateAddress(address);
        return Resp.success("修改成功");
    }

    @PostMapping("/delete")
    @ApiOperation(value="删除地址",notes = "删除地址")
    public Resp delete(@UserId String userId,String id){
        Address address = addressService.getRepository().findByUserIdAndId(userId, id);
        if(address==null){
            return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        addressService.delete(id);
        return Resp.success("删除成功");
    }
}
