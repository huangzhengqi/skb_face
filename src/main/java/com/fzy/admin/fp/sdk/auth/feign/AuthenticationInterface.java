package com.fzy.admin.fp.sdk.auth.feign;


import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.web.Resp;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 17:46 2019/4/18
 * @ Description:
 **/

public interface AuthenticationInterface {
    String getIss();

    Resp authentication(String token, String path, String pageUrl);
}
