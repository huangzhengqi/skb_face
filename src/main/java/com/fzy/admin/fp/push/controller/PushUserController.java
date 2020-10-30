package com.fzy.admin.fp.push.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistWindow;
import com.fzy.admin.fp.distribution.app.domain.DistWindowLog;
import com.fzy.admin.fp.push.domain.Push;
import com.fzy.admin.fp.push.domain.PushUser;
import com.fzy.admin.fp.push.repository.PushRepository;
import com.fzy.admin.fp.push.repository.PushUserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author hzq
 * @Date 2020/10/8 10:16
 * @Version 1.0
 * @description 个推用户消息层
 */
@RestController
@Slf4j
@RequestMapping("/push/user")
@Api(value = "个推用户消息层", tags = {"个推用户消息层"})
public class PushUserController extends BaseContent {

    @Resource
    private PushRepository pushRepository;
    @Resource
    private PushUserRepository pushUserRepository;

    @GetMapping("/details")
    @ApiOperation(value = "app消息中心", notes = "app消息中心")
    public Resp details(@TokenInfo(property="serviceProviderId") String serviceProviderId, @UserId String userId) {
        if(ParamUtil.isBlank(serviceProviderId)){
            throw new BaseException("serviceProviderId不能为空,toekn有误", Resp.Status.TOKEN_ERROR.getCode());
        }
        if(ParamUtil.isBlank(userId)){
            throw new BaseException("userId不能为空,toekn有误", Resp.Status.TOKEN_ERROR.getCode());
        }
        List<Push> pushList = pushRepository.findByServiceProviderId(serviceProviderId);
        if(pushList.size() == 0 || pushList == null){
            Resp.success("推送消息不存在");
        }
        List<PushUser> pushUserList = pushUserRepository.findAllByUserIdAndType(userId, Integer.valueOf(1));
        if(pushList != null || pushList.size() > 0){
            for (PushUser pushUser :pushUserList){
                pushUser.setType(Integer.valueOf(2));
                pushUserRepository.saveAndFlush(pushUser);
            }
        }
        return Resp.success(pushList, "查询成功");
    }

    @GetMapping("/unread/message")
    @ApiOperation(value = "未读消息", notes = "未读消息")
    public Resp unreadMessage(@TokenInfo(property="serviceProviderId") String serviceProviderId, @UserId String userId) {
        if(ParamUtil.isBlank(serviceProviderId)){
            throw new BaseException("serviceProviderId不能为空,toekn有误", Resp.Status.TOKEN_ERROR.getCode());
        }
        if(ParamUtil.isBlank(userId)){
            throw new BaseException("userId不能为空,toekn有误", Resp.Status.TOKEN_ERROR.getCode());
        }
        List<Push> pushList = pushRepository.findByServiceProviderId(serviceProviderId);
        if(pushList.size() == 0 || pushList == null){
            Resp.success("推送消息不存在");
        }
        //显示投放时间内的推送消息,记录哪个用户获取到了推送消息
        for (Push push :pushList){
            PushUser pushUser = pushUserRepository.findByUserIdAndPushId(userId, push.getId());
            if(ParamUtil.isBlank(pushUser)){
                PushUser newPushUser = new PushUser();
                newPushUser.setPushId(push.getId());
                newPushUser.setCreateTime(push.getCreateTime());
                newPushUser.setUserId(userId);
                newPushUser.setType(1);
                newPushUser.setServiceProviderId(push.getServiceProviderId());
                pushUserRepository.save(newPushUser);
            }
        }
        return Resp.success(pushUserRepository.findAllByUserIdAndType(userId, Integer.valueOf(1)).size());
    }


}
