package com.fzy.admin.fp.distribution.money.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.money.domain.SalesmanRate;
import com.fzy.admin.fp.distribution.money.service.SalesmanRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-11-18 11:17:41
 * @Desp 服务商业务员的六级抽成费率管理
 **/

@Api(value = "SalesmanRateController", tags = {"分销-业务员费率管理"})
@RequestMapping("/dist/salesmanRate")
@RestController
public class SalesmanRateController extends BaseContent {
    @Resource
    private SalesmanRateService salesmanRateService;

    @GetMapping("/query")
    @ApiOperation(value = "招商管理", notes = "业务提成查询")
    public Resp find(@TokenInfo(property="serviceProviderId")String serviceProviderId){
        SalesmanRate salesmanRate = salesmanRateService.getRepository().findByServiceProviderId(serviceProviderId);
        if(salesmanRate==null){
            salesmanRate=new SalesmanRate();
            salesmanRate.setRate(8);
            salesmanRate.setServiceProviderId(serviceProviderId);
            salesmanRateService.save(salesmanRate);
        }
        return Resp.success(salesmanRate);
    }

    @PostMapping("/set")
    @ApiOperation(value = "招商管理", notes = "业务提成设置")
    public Resp set(@TokenInfo(property="serviceProviderId")String serviceProviderId,Integer rate){
        if(rate==null){
            return new Resp().error(Resp.Status.PARAM_ERROR,"请填写业务提成");
        }
        SalesmanRate salesmanRate = salesmanRateService.getRepository().findByServiceProviderId(serviceProviderId);
        salesmanRate.setRate(rate);
        salesmanRateService.update(salesmanRate);
        return Resp.success("修改成功");
    }
}
