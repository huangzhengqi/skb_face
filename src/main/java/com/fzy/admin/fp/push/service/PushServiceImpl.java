package com.fzy.admin.fp.push.service;

import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.push.domain.Push;
import com.fzy.admin.fp.push.repository.PushRepository;
import com.fzy.admin.fp.push.util.AppPushUtils;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.exceptions.PushAppException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author hzq
 * @Date 2020/10/8 13:45
 * @Version 1.0
 * @description
 */
@Slf4j
@Transactional(rollbackOn = Exception.class)
@Service
public class PushServiceImpl implements BaseService<Push> {

    @Resource
    private PushRepository pushRepository;

    @Override
    public PushRepository getRepository() {
        return pushRepository;
    }

    public Push pushMessageToApp(String serviceProviderId, String userId ,Map<String, String> msg, Integer pushType, Integer platForm) {
        Map<String, Object> response = new HashMap<String, Object>(10);
        IPushResult ret = null;
        String appId = null;
        String appKey = null;
        String masterSecret = null;
        if (pushType.equals(Integer.valueOf(1))) {
            //商户app
            appId = "ox2YXTifv06NkP5fM6JpW";
            appKey = "rkdjx2rrvm9RQCCpcdQhz3";
            masterSecret = "PKAqpgf4Pl7PWTSISqNU07";
//           appId = "7z1ls8ZkZ09V1P41BRB5N3";
//           appKey = "tPe1q425i99gUHmfaG3W52";
//           masterSecret = "l8FjISLdiw6oKZe7GmqDC2";
        } else if (pushType.equals(Integer.valueOf(2))) {
            appId = "mXppf1UQEY8TnosJXE2pr9";
            appKey = "idRjPuwgmL8zUnhqQvXBk8";
            masterSecret = "64yYUtHFGI9Q3Jsk5ib7j";
        } else {
            appId = "7vVOKb5dmh50Afqw85VQa1";
            appKey = "s7o8o5xj4A940t4RpkeqS2";
            masterSecret = "ro72NcnWmH8ggegXtB7ni1";
        }
        // 代表在个推注册的一个 app，调用该类实例的方法来执行对个推的请求
        IGtPush iGtPush = new IGtPush(appKey, masterSecret);
        // 创建信息模板
        TransmissionTemplate template = AppPushUtils.getNotifacationTemplate(appId, appKey, msg, platForm);
        //定义消息推送方式为，推全部用户
        AppMessage message = new AppMessage();
        // 设置推送消息的内容
        message.setData(template);
        message.setOffline(true);
        //离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        // 厂商下发策略；1: 个推通道优先，在线经个推通道下发，离线经厂商下发(默认);2: 在离线只经厂商下发;3: 在离线只经个推通道下发;4: 优先经厂商下发，失败后经个推通道下发;
        message.setStrategyJson("{\"default\":4,\"ios\":4,\"st\":4}");
        List<String> appIdList = new ArrayList<String>();
        appIdList.add(appId);
        message.setAppIdList(appIdList);
        try {
            //全量推送
            ret = iGtPush.pushMessageToApp(message);
        } catch (PushAppException e) {
            e.printStackTrace();
        }
        /*
         * 1. 失败：{result=sign_error}
         * 2. 成功：{result=ok, taskId=OSS-0212_1b7578259b74972b2bba556bb12a9f9a, status=successed_online}
         * 3. 异常
         */
        if (ret != null) {
            response = ret.getResponse();
            String result = response.get("result").toString();
            if(result.equals("RepeatedContent")){
                throw new BaseException("推送的内容不能重复", Resp.Status.PARAM_ERROR.getCode());
            }
            if (!result.equals("ok")) {
                throw new BaseException("推送失败原因:" + result, Resp.Status.PARAM_ERROR.getCode());
            }
        } else {
            throw new BaseException("推送失败", Resp.Status.PARAM_ERROR.getCode());
        }
        Push push = new Push();
        push.setTitle(msg.get("title"));
        push.setTitleText(msg.get("titleText"));
        push.setTaskId(response.get("contentId").toString());
        push.setUserId(userId);
        push.setServiceProviderId(serviceProviderId);
        push.setPushType(pushType);
        push.setPlatForm(platForm);
        return pushRepository.save(push);
    }
}
