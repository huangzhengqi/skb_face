package com.fzy.admin.fp.common.log;


import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONUtil;
import com.fzy.admin.fp.common.annotation.SystemLog;
import com.fzy.admin.fp.common.web.IpUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.ThreadPoolUtil;
import com.fzy.admin.fp.common.annotation.SystemLog;
import com.fzy.admin.fp.common.web.IpUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.ThreadPoolUtil;
import com.fzy.admin.fp.log.domain.OperationLog;
import com.fzy.admin.fp.log.service.OperationLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * Spring AOP实现日志管理
 *
 * @author Exrickx
 */
@Aspect
@Component
@Slf4j
public class OperationLogAspect {

    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");

    @Resource
    private OperationLogService operationLogService;

    /**
     * Controller层切点,注解方式
     */
    //@Pointcut("execution(* *..controller..*Controller*.*(..))")
    @Pointcut("@annotation(com.fzy.admin.fp.common.annotation.SystemLog)")
    public void controllerAspect() {

    }

    /**
     * 前置通知 (在方法执行之前返回)用于拦截Controller层记录用户的操作的开始时间
     *
     * @param joinPoint 切点
     * @throws InterruptedException
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException {
        //线程绑定变量（该数据只有当前请求的线程可见）
        Date beginTime = new Date();
        beginTimeThreadLocal.set(beginTime);
    }

    /**
     * 后置通知(在方法执行之后返回) 用于拦截Controller层操作
     *
     * @param joinPoint 切点
     */
    @After("controllerAspect()")
    public void after(JoinPoint joinPoint) {
        try {
            SystemLog systemLog = getControllerMethodDescription(joinPoint);
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();
            OperationLog operationLog = new OperationLog();
            if (systemLog != null) {
                String token = request.getHeader(systemLog.tokenName());
                if (!ParamUtil.isBlank(token)) {
                    String[] tokenParts = token.split("\\.");//将token切割成三部分
                    if (tokenParts.length == 3) {
                        operationLog.setTokenLoad(Base64.decodeStr(tokenParts[1]));
                    }
                }
            }
            //日志标题
            operationLog.setName(systemLog.description());
            //日志请求url
            operationLog.setRequestUrl(request.getRequestURI());
            //请求方式
            operationLog.setRequestType(request.getMethod());
            //请求参数
            Map<String, String[]> logParams = request.getParameterMap();
            String param = JSONUtil.toJsonStr(logParams);
            if (param != null) {
                operationLog.setRequestParam(param);
            }

            operationLog.setIp(IpUtil.getIpAdrress(request));

            long beginTime = beginTimeThreadLocal.get().getTime();
            long endTime = System.currentTimeMillis();
            //请求耗时
            Long logElapsedTime = endTime - beginTime;
            operationLog.setCostTime(logElapsedTime.intValue());
            ThreadPoolUtil.getPool().execute(new Thread(() -> operationLogService.save(operationLog)));

        } catch (Exception e) {
            log.error("AOP后置通知异常", e);
        }
    }


    /**
     * 保存日志至数据库
     */
    @AllArgsConstructor
    private static class SaveSystemLogThread implements Runnable {

        private OperationLog operationLog;

        @Override
        public void run() {

        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    private static SystemLog getControllerMethodDescription(JoinPoint joinPoint) throws Exception {

        //获取目标类名
        String targetName = joinPoint.getTarget().getClass().getName();
        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        //获取相关参数
        Object[] arguments = joinPoint.getArgs();
        //生成类对象
        Class targetClass = Class.forName(targetName);
        //获取该类中的方法
        Method[] methods = targetClass.getMethods();

        SystemLog log = null;

        for (Method method : methods) {
            if (!method.getName().equals(methodName)) {
                continue;
            }
            Class[] clazzs = method.getParameterTypes();
            if (clazzs.length != arguments.length) {
                //比较方法中参数个数与从切点中获取的参数个数是否相同，方法可以重载
                continue;
            }
            log = method.getAnnotation(SystemLog.class);
        }
        return log;
    }

}
