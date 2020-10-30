package com.fzy.admin.fp.sdk.member.feign;

import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.sdk.auth.feign.AuthenticationInterface;

/**
 * @author Created by zk on 2019-05-19 11:22
 * @description
 */
public interface MemberServiceFeign extends AuthenticationInterface {
    Resp authentication(String token, String path, String pageUrl);

    /**
     * @author Created by wtl on 2019/6/26 23:36
     * @Description 根据公众号openid和商户id获取会员
     */
    String findByOpenIdAndMerchantId(String officialOpenId, String merchantId);

}
