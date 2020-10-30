package com.fzy.admin.fp.auth.app;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.auth.service.DistribuService;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.Role;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.auth.service.DistribuService;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 14:17 2019/6/1
 * @ Description: app二级代理商管理
 **/
@Slf4j
@RestController
@RequestMapping("/auth/distribute/app")
public class AppDistributeController extends BaseContent {

    @Resource
    private DistribuService distribuService;

    @Resource
    private CompanyService companyService;


    @GetMapping("/list")
    public Resp listRewrite(Company model, PageVo pageVo, @UserId String userId) {
        return Resp.success(distribuService.listRewrite(model, pageVo, userId));
    }

    @PostMapping("/save")
    public Resp saveRewrite(@Valid Company model, @UserId String userId) {
        return Resp.success(companyService.saveRewrite(model, userId, Company.Type.DISTRIBUTUTORS.getCode()));
    }

    @PostMapping("/update")
    public Resp update(Company model) {
        Company company = distribuService.findOne(model.getId());
        if (company == null) {
            throw new BaseException("查无此二级代理商", Resp.Status.PARAM_ERROR.getCode());
        }
        //获取Hutool拷贝实例
        CopyOptions copyOptions = CopyOptions.create();
        //忽略为null值得属性
        copyOptions.setIgnoreNullValue(true);
        //进行属性拷贝
        BeanUtil.copyProperties(model, company, copyOptions);
        distribuService.save(company);
        return Resp.success("修改成功");
    }

    @GetMapping("/detail")
    public Resp detail(String id) {
        return Resp.success(companyService.detail(id));
    }
}
