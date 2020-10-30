package com.fzy.admin.fp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huxiangqiang
 * @since 2019/7/15
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket auth_api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/auth/**")).build().groupName("1管理后台相关").pathMapping("/")
                .apiInfo(apiInfo("advertise", "auth", "1.0")).globalOperationParameters(getHeader());
    }
    @Bean
    public Docket merchant_api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/merchant/**")).build().groupName("2商户后台相关").pathMapping("/")
                .apiInfo(apiInfo("merchant", "merchant", "1.0")).globalOperationParameters(getHeader());
    }

    @Bean
    public Docket order_api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/order/**")).build().groupName("3订单相关").pathMapping("/")
                .apiInfo(apiInfo("order", "order", "1.0")).globalOperationParameters(getHeader());
    }

    @Bean
    public Docket member_api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/member/**")).build().groupName("4会员相关").pathMapping("/")
                .apiInfo(apiInfo("member", "member", "1.0")).globalOperationParameters(getHeader());
    }

    @Bean
    public Docket pay_api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/pay/**")).build().groupName("5支付相关").pathMapping("/")
                .apiInfo(apiInfo("pay", "pay", "1.0")).globalOperationParameters(getHeader());
    }
    @Bean
    public Docket advertise_api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/advertise/**")).build().groupName("6广告相关").pathMapping("/")
                .apiInfo(apiInfo("advertise", "advertise", "1.0")).globalOperationParameters(getHeader());
    }

    @Bean
    public Docket fms_api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/fms/**")).build().groupName("7文件相关").pathMapping("/")
                .apiInfo(apiInfo("fms", "fms", "1.0")).globalOperationParameters(getHeader());
    }


    @Bean
    public Docket applet_api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/applet/**")).build().groupName("8小程序相关（新）").pathMapping("/")
                .apiInfo(apiInfo("applet", "applet", "1.0")).globalOperationParameters(getHeader());
    }

    @Bean
    public Docket common_api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/common/**")).build().groupName("9公共接口").pathMapping("/")
                .apiInfo(apiInfo("common", "common", "1.0")).globalOperationParameters(getHeader());
    }

    @Bean
    public Docket dist_api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/dist/**")).build().groupName("10分销接口").pathMapping("/")
                .apiInfo(apiInfo("dist", "dist", "1.0")).globalOperationParameters(getHeader());
    }

    @Bean
    public Docket invoice_api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/billing/**")).build().groupName("11电子发票接口").pathMapping("/")
                .apiInfo(apiInfo("invoice", "invoice", "1.0")).globalOperationParameters(getHeader());
    }

    @Bean
    public Docket ali_api(){
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/ali_pay_info/**")).build().groupName("12阿里接口").pathMapping("/")
                .apiInfo(apiInfo("ali", "ali", "1.0")).globalOperationParameters(getHeader());
    }

    @Bean
    public Docket push_api(){
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/push/**")).build().groupName("13推送接口").pathMapping("/")
                .apiInfo(apiInfo("push", "push", "1.0")).globalOperationParameters(getHeader());
    }




    private List<Parameter> getHeader() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("authorized").description("密钥").modelRef(new ModelRef("string")).parameterType("header").required(false);
        pars.add(tokenPar.build());
        return pars;
    }

    private ApiInfo apiInfo(String name, String description, String version) {
        ApiInfo apiInfo = new ApiInfoBuilder().title(name).description(description).version(version).build();
        return apiInfo;
    }
}
