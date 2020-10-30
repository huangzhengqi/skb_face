/*
package com.fzy.admin.fp;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.google.common.collect.Sets;
import com.fzy.admin.fp.advertise.controller.AdvertiseManagementController;
import com.fzy.admin.fp.codegen.LengthProcessor;
import com.fzy.admin.fp.codegen.NotBlankProcessor;
import com.fzy.admin.fp.codegen.NotNullProcessor;
import com.fzy.admin.fp.common.spring.SpringContextUtil;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.validation.annotation.Length;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.MemberConstant;
import com.fzy.admin.fp.sdk.merchant.domain.AppletConfigVO;
import com.wuxp.codegen.core.CodeGenerator;
import com.wuxp.codegen.core.parser.JavaClassParser;
import com.wuxp.codegen.dragon.strategy.TypescriptPackageMapStrategy;
import com.wuxp.codegen.enums.CodeRuntimePlatform;
import com.wuxp.codegen.model.CommonCodeGenClassMeta;
import com.wuxp.codegen.model.LanguageDescription;
import com.wuxp.codegen.model.languages.java.JavaClassMeta;
import com.wuxp.codegen.model.languages.typescript.TypescriptClassMeta;
import com.wuxp.codegen.model.mapping.AbstractTypeMapping;
import com.wuxp.codegen.swagger2.Swagger2CodeGenerator;
import com.wuxp.codegen.swagger2.Swagger2FeignSdkGenMatchingStrategy;
import com.wuxp.codegen.swagger2.annotations.ApiParamProcessor;
import com.wuxp.codegen.swagger2.builder.Swagger2FeignJavaCodegenBuilder;
import com.wuxp.codegen.swagger2.builder.Swagger2FeignTypescriptCodegenBuilder;
import com.wuxp.codegen.swagger2.languages.Swagger2FeignSdkTypescriptParser;
import com.wuxp.codegen.templates.FreemarkerTemplateLoader;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.wuxp.codegen.languages.AbstractLanguageParser.ANNOTATION_PROCESSOR_MAP;

*/
/**
 * 测试swagger 生成  typescript的 feign api sdk
 *//*

@Slf4j
public class SwaggerFeignSdkCodegenTypescriptTest {


    private CodeGenerator codeGenerator;

    @Test
    public void testCodeGenTypescriptApiByStater() {

        //设置基础数据类型的映射关系
        Map<Class<?>, CommonCodeGenClassMeta> baseTypeMapping = new HashMap<>();
        //设置基础数据类型的映射关系
        AbstractTypeMapping.BASE_TYPE_MAPPING.put(Resp.class, TypescriptClassMeta.PROMISE);

        //自定义的类型映射
        Map<Class<?>, Class<?>[]> customTypeMapping = new HashMap<>();
//        customTypeMapping.put(ServiceQueryResponse.class, new Class<?>[]{ServiceResponse.class, PageInfo.class});

        //包名映射关系
        Map<String, String> packageMap = new LinkedHashMap<>();

        //控制器的包所在
        packageMap.put("com.fzy.admin.fp.**.controller", "{0}services");
        packageMap.put("com.fzy.admin.fp.**.domain", "{0}domain");
        //其他类（DTO、VO等）所在的包
//        packageMap.put("com.fzy.admin.fp.**.domain","domain");


//        String language = LanguageDescription.TYPESCRIPT.getName();
        String[] outPaths = {"../", "vma-api-sdk", "src", "feign"};

        //要进行生成的源代码包名列表
        String[] packagePaths = {
                "com.fzy.admin.fp.**.controller",
                "com.vma.wechatopen.gateway.controller.wechat.mini"
        };
        String[] ignorePackages = {
                "com.fzy.admin.fp.common.annotation",
                "com.fzy.admin.fp.common.constant",
//                "com.fzy.admin.fp.common.controller",
                "com.fzy.admin.fp.common.conver",
                "com.fzy.admin.fp.common.enumeration",
                "com.fzy.admin.fp.common.exception",
                "com.fzy.admin.fp.common.json",
                "com.fzy.admin.fp.common.log",
                "com.fzy.admin.fp.common.repository",
                "com.fzy.admin.fp.common.resolver",
                "com.fzy.admin.fp.common.service",
                "com.fzy.admin.fp.common.snowflake",
//                "com.fzy.admin.fp.common.spring",
                "com.fzy.admin.fp.common.spring.configuration",
                "com.fzy.admin.fp.common.spring.jpa",
//                "com.fzy.admin.fp.common.spring.pagination",
                "com.fzy.admin.fp.common.validation",
                "com.fzy.admin.fp.common.vo",
                "com.fzy.admin.fp.common.web",
                "com.fzy.admin.fp.pay.pay",
                "com.fasterxml",
                "springfox.documentation"
        };

        ANNOTATION_PROCESSOR_MAP.put(NotBlank.class, new NotBlankProcessor());
        ANNOTATION_PROCESSOR_MAP.put(NotNull.class, new NotNullProcessor());
        ANNOTATION_PROCESSOR_MAP.put(Length.class, new LengthProcessor());

        Class<?>[] ignoreClasses = new Class[]{
                BaseController.class,
                BaseContent.class,
                PageUtil.class,
                SpringContextUtil.class,
        };


        Swagger2FeignTypescriptCodegenBuilder.builder()
                .baseTypeMapping(baseTypeMapping)
                .languageDescription(LanguageDescription.TYPESCRIPT)
                .codeRuntimePlatform(CodeRuntimePlatform.WE_CHAT_APPLETS)
                .isDeletedOutputDirectory(true)
                .customJavaTypeMapping(customTypeMapping)
                .ignorePackages(Sets.newHashSet(ignorePackages))
                .ignoreClasses(ignoreClasses)
                .packageMapStrategy(new TypescriptPackageMapStrategy(packageMap))
                .outPath(Paths.get(System.getProperty("user.dir")).resolveSibling(String.join(File.separator, outPaths)).toString())
                .scanPackages(packagePaths)
                .buildCodeGenerator()
                .generate();

    }

    @Test
    public void testCodeGenJavaApiByStater(){
        //设置基础数据类型的映射关系
        Map<Class<?>, CommonCodeGenClassMeta> baseTypeMapping = new HashMap<>();
        //设置基础数据类型的映射关系
        AbstractTypeMapping.BASE_TYPE_MAPPING.put(Resp.class, TypescriptClassMeta.PROMISE);

        //自定义的类型映射
        Map<Class<?>, Class<?>[]> customTypeMapping = new HashMap<>();
//        customTypeMapping.put(ServiceQueryResponse.class, new Class<?>[]{ServiceResponse.class, PageInfo.class});

        //包名映射关系
        Map<String, String> packageMap = new LinkedHashMap<>();

        //控制器的包所在
        packageMap.put("com.fzy.admin.fp.**.controller", "{0}services");
        packageMap.put("com.fzy.admin.fp.**.domain", "{0}domain");
        //其他类（DTO、VO等）所在的包
//        packageMap.put("com.fzy.admin.fp.**.domain","domain");


//        String language = LanguageDescription.TYPESCRIPT.getName();
        String[] outPaths = {"../", "vma-api-sdk", "src", "feign"};

        //要进行生成的源代码包名列表
        String[] packagePaths = {
                "com.fzy.admin.fp.**.controller"
        };
        String[] ignorePackages = {
                "com.fzy.admin.fp.common.annotation",
                "com.fzy.admin.fp.common.constant",
//                "com.fzy.admin.fp.common.controller",
                "com.fzy.admin.fp.common.conver",
                "com.fzy.admin.fp.common.enumeration",
                "com.fzy.admin.fp.common.exception",
                "com.fzy.admin.fp.common.json",
                "com.fzy.admin.fp.common.log",
                "com.fzy.admin.fp.common.repository",
                "com.fzy.admin.fp.common.resolver",
                "com.fzy.admin.fp.common.service",
                "com.fzy.admin.fp.common.snowflake",
//                "com.fzy.admin.fp.common.spring",
                "com.fzy.admin.fp.common.spring.configuration",
                "com.fzy.admin.fp.common.spring.jpa",
//                "com.fzy.admin.fp.common.spring.pagination",
                "com.fzy.admin.fp.common.validation",
                "com.fzy.admin.fp.common.vo",
                "com.fzy.admin.fp.common.web",
                "com.fzy.admin.fp.pay.pay",
                "com.fasterxml",
                "springfox.documentation"
        };

        ANNOTATION_PROCESSOR_MAP.put(NotBlank.class, new NotBlankProcessor());
        ANNOTATION_PROCESSOR_MAP.put(NotNull.class, new NotNullProcessor());
        ANNOTATION_PROCESSOR_MAP.put(Length.class, new LengthProcessor());

        Class<?>[] ignoreClasses = new Class[]{
                BaseController.class,
                BaseContent.class,
                PageUtil.class,
                SpringContextUtil.class,
        };




        Swagger2FeignJavaCodegenBuilder.builder().build()
                .baseTypeMapping(baseTypeMapping)
                .languageDescription(LanguageDescription.TYPESCRIPT)
                .codeRuntimePlatform(CodeRuntimePlatform.WE_CHAT_APPLETS)
                .isDeletedOutputDirectory(true)
                .customJavaTypeMapping(customTypeMapping)
                .ignorePackages(Sets.newHashSet(ignorePackages))
                .ignoreClasses(ignoreClasses)
                .packageMapStrategy(new TypescriptPackageMapStrategy(packageMap))
                .outPath(Paths.get(System.getProperty("user.dir")).resolveSibling(String.join(File.separator, outPaths)).toString())
                .scanPackages(packagePaths)
                .buildCodeGenerator()
                .generate();
    }


    @Test
    public void testJavaParser() {

        JavaClassMeta parse = new JavaClassParser(true).parse(AdvertiseManagementController.class);

        log.debug("{}", parse);
    }

    @Test
    public void test1() throws Exception {
        WxMaService wxMaService = new WxMaServiceImpl();


        WxMaInMemoryConfig wxMaConfig = new WxMaInMemoryConfig();
        String appId = "wx473991bd67c67a48";
        wxMaConfig.setAppid(appId);
        wxMaConfig.setSecret("82bc8d2fdb816b708fe0819648607559");
        wxMaService.setWxMaConfig(wxMaConfig);
        WxMaJscode2SessionResult wxMaJscode2SessionResult = wxMaService.jsCode2SessionInfo("023hdxO20NmbcK1DBKO20hQRO20hdxO8");

        System.out.println(wxMaJscode2SessionResult);
    }
}
*/
