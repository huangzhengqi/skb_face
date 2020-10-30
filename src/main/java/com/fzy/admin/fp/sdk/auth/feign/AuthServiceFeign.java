package com.fzy.admin.fp.sdk.auth.feign;

import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.sdk.auth.domain.YunhornParam;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.sdk.auth.domain.YunhornParam;

import java.util.List;
import java.util.Map;

/**
 * @author Created by zk on 2019-03-06 16:36
 * @description
 */
public interface AuthServiceFeign extends AuthenticationInterface {

    Resp authentication(String token, String path, String pageUrl);

    String getCompanyId(String username);

    //获取公司名称
    Map<String, String> getCompanyName();

    //获取业务员名称
    Map<String, String> getUserName();

    //获取一级代理商下的二级代理商ids
    List<String> findByOperaId(String companyId);


    //调用云喇叭语音接口
    String yunHornPlay(YunhornParam model);

    //获取分销业务员名称
    Map<String, String>  getDistUserName();
}
