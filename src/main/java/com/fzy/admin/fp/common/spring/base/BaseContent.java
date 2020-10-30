package com.fzy.admin.fp.common.spring.base;


import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.validation.exception.ValidatorException;
import com.fzy.admin.fp.DomainInterface;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.validation.exception.ValidatorException;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class BaseContent implements DomainInterface {

    @PersistenceContext
    public EntityManager em;


    @ExceptionHandler(ValidatorException.class)
    public Resp validationExHandle(ValidatorException e) {
        e.printStackTrace();
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        log.error(stackTraceElements[0] + "--" + e.toString());
        return new Resp().error(Resp.Status.PARAM_ERROR, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Resp handleException(Exception e) {
        e.printStackTrace();
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        log.error(stackTraceElements[0] + "--" + e.toString());
        if (e instanceof BaseException) {
            BaseException myException = (BaseException) e;
            Resp resp = new Resp().error(ParamUtil.isBlank(myException.getCode()) ? Resp.Status.INNER_ERROR
                    : Resp.getStatusByCode(myException.getCode()), myException.getMessage());
            return resp;
        }
        return new Resp().error(Resp.Status.INNER_ERROR, e.getMessage() == null ? "oops" : e.getMessage());
    }

    @Resource
    protected HttpServletRequest request;
    @Resource
    protected HttpServletResponse response;


    @Override
    public HttpServletRequest getRequest() {
        return request;
    }
}
