package com.fzy.admin.fp.push.controller;

import com.fzy.admin.fp.common.spring.base.BaseContent;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author hzq
 * @Date 2020/10/7 14:22
 * @Version 1.0
 * @description 个推clientid用户控制层
 */
@RestController
@Slf4j
@RequestMapping("/push/user/clientId")
@Api(value = "个推clientid用户控制层", tags = {"个推clientid用户控制层"})
public class PushUserClientController extends BaseContent {

//    @ApiOperation(value = "添加clientid", notes = "添加clientid")
//    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "token", dataType = "String", value = "token"),
//            @ApiImplicitParam(paramType = "query", name = "clientid", dataType = "String", value = "clientid"),
//            @ApiImplicitParam(paramType = "query", name = "appid", dataType = "String", value = "appid"),
//            @ApiImplicitParam(paramType = "query", name = "appkey", dataType = "String", value = "appkey"),
//            @ApiImplicitParam(paramType = "query", name = "clientType", dataType = "String", value = "客户端类型 1苹果 2安卓")
//    })
//    @PostMapping("/add/cid")
//    public Resp addCid(@UserId String userId, String token, String clientid, String appid, String appkey,Integer clientType) {
//        if(ParamUtil.isBlank(userId)){
//            return new Resp().error(Resp.Status.PARAM_ERROR, "当前用户还未登录，请登录");
//        }
//        if(ParamUtil.isBlank(token)){
//            return new Resp().error(Resp.Status.PARAM_ERROR, "获取不到token，请联系开发人员");
//        }
//        if(ParamUtil.isBlank(clientid)){
//            return new Resp().error(Resp.Status.PARAM_ERROR, "获取不到cid，请联系开发人员");
//        }
//        if(ParamUtil.isBlank(appid)){
//            return new Resp().error(Resp.Status.PARAM_ERROR, "获取不到appid，请联系开发人员");
//        }
//        if(ParamUtil.isBlank(appkey)){
//            return new Resp().error(Resp.Status.PARAM_ERROR, "获取不到appkey，请联系开发人员");
//        }
//        PushUserClient pushUserClient = pushUserClientIdImpl.getRepository().findByClientid(clientid);
//        if(pushUserClient == null){
//            MerchantUser merchantUser = merchantUserService.findOne(userId);
//            PushUserClient newPushUserClient = new PushUserClient();
//            newPushUserClient.setToken(token);
//            newPushUserClient.setClientid(clientid);
//            newPushUserClient.setAppid(appid);
//            newPushUserClient.setAppkey(appkey);
//            newPushUserClient.setUserId(userId);
//            newPushUserClient.setMerchantId(merchantUser.getMerchantId());
//            newPushUserClient.setStoreId(merchantUser.getStoreId());
//            newPushUserClient.setServiceProviderId(merchantUser.getServiceProviderId());
//            newPushUserClient.setClientType(clientType);
//            pushUserClient = pushUserClientIdImpl.save(newPushUserClient);
//        }
//        return Resp.success(pushUserClient,"添加成功");
//    }


}
