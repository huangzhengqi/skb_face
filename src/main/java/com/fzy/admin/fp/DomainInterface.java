package com.fzy.admin.fp;

import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.SpringContextUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Created by wtl on 2019-07-03 11:44
 * @description
 */
public interface DomainInterface {

    HttpServletRequest getRequest();

    default String getDomain() {
        return (String) getRequest().getAttribute(CommonConstant.DOMAIN_NAME);
    }

}
