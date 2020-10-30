package com.fzy.admin.fp;

import cn.hutool.core.util.ReflectUtil;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.common.spring.SpringContextUtil;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.common.spring.SpringContextUtil;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Created by zk on 2019-07-01 21:39
 * @description
 */
@Component
@Slf4j
public class LysjPayMasterApplicationRunner implements ApplicationRunner {

    @Resource
    private CompanyService companyService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        List<Company> companies = companyService.findByIdPathIsBlank();
        final List<Company> collect = companies.parallelStream()
                .filter(company -> !Company.Type.ADMIN.getCode().equals(company.getType()) && !Company.Type.PROVIDERS.getCode().equals(company.getType()))
                .collect(Collectors.toList());
        collect.parallelStream().forEach(company -> company = companyService.fillIdPath(company));
        if (!ParamUtil.isBlank(collect)) {
            companyService.save(collect);
        }
        //initServiceProviderId();
    }

    private void initServiceProviderId() {
        List<Company> companies = companyService.findByType(Company.Type.PROVIDERS.getCode());
        if (ParamUtil.isBlank(companies)) {
            log.error("could not find service provider");
            return;
        }
        Company company = companies.get(0);
        Reflections reflections = new Reflections("com.fzy.admin.fp");
        Set<Class<? extends CompanyBaseEntity>> subTypes = reflections.getSubTypesOf(CompanyBaseEntity.class);
        List<BaseRepository> baseServiceList = new ArrayList<>();
        for (Class<? extends CompanyBaseEntity> subType : subTypes) {
            BaseRepository baseService = (BaseRepository) SpringContextUtil.getBean(ParamUtil.first2LowerCase(subType.getSimpleName()) + "Repository");
            baseServiceList.add(baseService);
        }
        for (BaseRepository baseRepository : baseServiceList) {
            final List all = baseRepository.findAll();
            for (Object o : all) {
                CompanyBaseEntity companyBaseEntity = (CompanyBaseEntity) o;
                if (ParamUtil.isBlank(companyBaseEntity.getServiceProviderId())) {
                    companyBaseEntity.setServiceProviderId(company.getId());
                }
            }
            baseRepository.save(all);
        }
    }
}
