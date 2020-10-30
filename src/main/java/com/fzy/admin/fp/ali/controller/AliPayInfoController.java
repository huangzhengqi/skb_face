package com.fzy.admin.fp.ali.controller;

import com.fzy.admin.fp.ali.domain.AliPayInfo;
import com.fzy.admin.fp.ali.repository.AliPayInfoRepository;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ali_pay_info")
@Api(value = "AliPayInfoController", tags = {"代理版-app用戶支付宝"})
public class AliPayInfoController extends BaseContent {

    @Resource
    private AliPayInfoRepository aliPayInfoRepository;

    @ApiModelProperty("获取支付宝信息")
    @GetMapping("/get_ali_pay_info")
    public Resp getAliPayInfo(String userId){
        AliPayInfo aliPayInfo=aliPayInfoRepository.findAllByUserId(userId);
        if(StringUtils.isEmpty(aliPayInfo)){
            return Resp.success("支付宝信息不存在");
        }
        return Resp.success(aliPayInfo);
    }

    @ApiModelProperty("添加支付宝信息")
    @PostMapping("/add")
    public Resp add(String userId,String name,String aliAccountNumber){
        AliPayInfo aliPayInfo=aliPayInfoRepository.findAllByUserId(userId);
        if(aliPayInfo == null){
            AliPayInfo aliPayInfo1= new AliPayInfo();
            aliPayInfo1.setUserId(userId);
            aliPayInfo1.setAliName(name);
            aliPayInfo1.setAliAccountNumber(aliAccountNumber);
            aliPayInfoRepository.save(aliPayInfo1);
        }else {
            aliPayInfo.setAliName(name);
            aliPayInfo.setAliAccountNumber(aliAccountNumber);
            aliPayInfoRepository.save(aliPayInfo);
        }
        return Resp.success("修改成功");
    }

}
