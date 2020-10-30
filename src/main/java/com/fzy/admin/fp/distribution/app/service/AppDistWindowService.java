package com.fzy.admin.fp.distribution.app.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistWindow;
import com.fzy.admin.fp.distribution.app.domain.DistWindowLog;
import com.fzy.admin.fp.distribution.app.repository.AppDistWindowLogRepository;
import com.fzy.admin.fp.distribution.app.repository.AppDistWindowRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppDistWindowService implements BaseService<DistWindow> {

    @Resource
    private AppDistWindowRepository appDistWindowRepository;

    @Resource
    private AppDistWindowLogRepository appDistWindowLogRepository;

    @Override
    public AppDistWindowRepository getRepository() {
        return appDistWindowRepository;
    }

    public Resp homeView(String serviceProviderId, String userId) {

        List<DistWindow> distWindowList = appDistWindowRepository.findByServiceProviderIdAndStatusAndType(serviceProviderId,DistWindow.Status.DISABLE.getCode(),DistWindow.Type.INLAUNCH.getCode());
        if(distWindowList.size() == 0 || distWindowList == null){
            Resp.success("推送消息不存在");
        }
        List<DistWindowLog> distWindowLogList = new ArrayList<>();

        //显示投放时间内的推送消息,记录哪个用户获取到了推送消息
        for (DistWindow distWindow :distWindowList){

            DistWindowLog distWindowLog1 = appDistWindowLogRepository.findByUserIdAndAndWindowId(userId, distWindow.getId());
            if(ParamUtil.isBlank(distWindowLog1)){
                DistWindowLog distWindowLog = new DistWindowLog();
                distWindowLog.setWindowId(distWindow.getId());
                distWindowLog.setBeginTime(distWindow.getBeginTime());
                distWindowLog.setEndTime(distWindow.getEndTime());
                distWindowLog.setUserId(userId);
                distWindowLog.setImgUrl(distWindow.getImgUrl());
                distWindowLog.setType(1);
                distWindowLog.setIsRead(0);
                distWindowLog.setStatus(1);
                distWindowLog.setServiceProviderId(distWindow.getServiceProviderId());
                distWindowLog.setContents(distWindow.getContents());
                distWindowLog.setGuidance(distWindow.getGuidance());
                distWindowLog.setTitle(distWindow.getTitle());
                distWindowLogList.add(distWindowLog);
                appDistWindowLogRepository.save(distWindowLog);
            }
        }

        return Resp.success(distWindowLogList);
    }
}
