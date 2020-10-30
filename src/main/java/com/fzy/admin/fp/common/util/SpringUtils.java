package com.fzy.admin.fp.common.util;

import com.fzy.assist.wraps.SpringWrap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;









    public static <T> T getBean(String beanName) {
        isInjected();
        return (T)applicationContext.getBean(beanName);
    }








    public static <T> T getBean(Class<T> requiredType) {
        isInjected();
        return (T)applicationContext.getBean(requiredType);
    }







    public void destroy() { applicationContext = null; }





    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException { SpringUtils.applicationContext = applicationContext; }







    public static void isInjected() {
        if (applicationContext == null) {
            throw new RuntimeException("springUtils APPLICATION_CONTENT is not injected!");
        }
    }


    public static ApplicationContext getApplicationContent() { return applicationContext; }









    public static <T> Map<String, T> getBeanByMap(Class<T> requiredType) {
        isInjected();
        return applicationContext.getBeansOfType(requiredType);
    }









    public static Boolean addBean(String beanName, Object object) {
        isInjected();
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) SpringWrap.getApplicationContent();
        DefaultListableBeanFactory dbf = (DefaultListableBeanFactory)configurableApplicationContext.getBeanFactory();
        if (dbf.containsBeanDefinition(beanName)) {
            return Boolean.valueOf(false);
        }
        dbf.registerSingleton(beanName, object);
        return Boolean.valueOf(true);
    }








    public static Boolean addBean(String beanName, GenericBeanDefinition definition) {
        isInjected();
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext)SpringWrap.getApplicationContent();
        DefaultListableBeanFactory dbf = (DefaultListableBeanFactory)configurableApplicationContext.getBeanFactory();
        if (dbf.containsBeanDefinition(beanName)) {
            return Boolean.valueOf(false);
        }
        dbf.registerBeanDefinition(beanName, definition);
        return Boolean.valueOf(true);
    }







    public static Boolean removeBean(String beanName) {
        isInjected();
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext)SpringWrap.getApplicationContent();
        DefaultListableBeanFactory dbf = (DefaultListableBeanFactory)configurableApplicationContext.getBeanFactory();
        if (!dbf.containsBeanDefinition(beanName)) {
            return Boolean.valueOf(false);
        }
        dbf.removeBeanDefinition(beanName);
        return Boolean.valueOf(true);
    }
}
