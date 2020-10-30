package com.fzy.admin.fp.sdk.merchant.feign;

import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.sdk.auth.feign.AuthenticationInterface;

/**
 * @author Created by zk on 2019-03-06 16:36
 * @description
 */
public interface MerchantServiceFeign extends AuthenticationInterface {

    Resp authentication(String token, String path, String pageUrl);


}
