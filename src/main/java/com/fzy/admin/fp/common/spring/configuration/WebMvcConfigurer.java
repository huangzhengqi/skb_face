package com.fzy.admin.fp.common.spring.configuration;

import com.fzy.admin.fp.common.resolver.TokenInfoResolver;
import com.fzy.admin.fp.common.resolver.UserIdResolver;
import com.fzy.admin.fp.common.validation.ValidatorResolver;
import com.fzy.admin.fp.gateway.intercept.AuthIntercept;
import com.fzy.admin.fp.interceptors.DomainInterceptor;
import com.fzy.admin.fp.common.resolver.TokenInfoResolver;
import com.fzy.admin.fp.common.resolver.UserIdResolver;
import com.fzy.admin.fp.common.validation.ValidatorResolver;
import com.fzy.admin.fp.gateway.intercept.AuthIntercept;
import com.fzy.admin.fp.interceptors.DomainInterceptor;
import com.fzy.admin.fp.invoice.feign.MyMappingJackson2XmlHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Created by zk on 2018-12-13 10:43
 * @description 配置参数解析器
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Resource
    private AuthIntercept authIntercept;

    @Autowired
    DomainInterceptor domainInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(domainInterceptor);
        registry.addInterceptor(authIntercept);
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resource/**").addResourceLocations("file:WorkDir/temp/lypay/");
        registry.addResourceHandler("/music/**").addResourceLocations("file:WorkDir/music/");
        registry.addResourceHandler("/filepath/**").addResourceLocations("file:WorkDir/temp/md5/");
        super.addResourceHandlers(registry);
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(validatorResolver());
        argumentResolvers.add(userIdResolver());
        argumentResolvers.add(tokenInfoResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) { converters.add(new MyMappingJackson2XmlHttpMessageConverter()); }

    @Bean
    public ValidatorResolver validatorResolver() {
        return new ValidatorResolver();
    }

    @Bean
    public UserIdResolver userIdResolver() {
        return new UserIdResolver();
    }

    @Bean
    public TokenInfoResolver tokenInfoResolver() {
        return new TokenInfoResolver();
    }


}
