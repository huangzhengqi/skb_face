package com.fzy.admin.fp.advertise.controller;

import com.fzy.admin.fp.advertise.domain.StrategicManagement;
import com.fzy.admin.fp.advertise.service.StrategicManagementService;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.service.DistribuService;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author lb
 * @date 2019/7/2 9:09
 * @Description 策略管理
 */
@RestController
@RequestMapping(value = "/advertise/strategic_management")
public class StrategicManagementController extends BaseController<StrategicManagement> {

    @Resource
    private StrategicManagementService strategicManagementService;

    @Resource
    private DistribuService distribuService;

    @Override
    public BaseService<StrategicManagement> getService() {
        return strategicManagementService;
    }

    @GetMapping(value = "/list_re")
    public Resp getAll(StrategicManagement strategicManagement, PageVo pageVo) {
        return Resp.success(strategicManagementService.getAll(strategicManagement, pageVo));
    }

    @PostMapping(value = "/save_re")
    public Resp saveOne(@Valid StrategicManagement strategicManagement, String city, String time, String operators, String distributors) {
        strategicManagementService.saveOne(strategicManagement, city, time, operators, distributors);
        return Resp.success("新增成功");
    }

    @PostMapping(value = "/update_re")
    public Resp updateOne(StrategicManagement strategicManagement, String city, String time, String operators, String distributors) {
        strategicManagementService.updateOne(strategicManagement, city, time, operators, distributors);
        return Resp.success("修改成功");
    }

    @PostMapping(value = "/delete_re")
    public Resp deleteOne(String id) {
        StrategicManagement strategicManagement = strategicManagementService.findOne(id);
        if (strategicManagement.getSourceType().equals(StrategicManagement.SourceType.NO_USE.getCode())) {
            strategicManagementService.deleteOne(strategicManagement);
            return Resp.success("删除成功");
        }
        return new Resp().error(Resp.Status.PARAM_ERROR, "该策略使用中无法删除");
    }

    @GetMapping(value = "/find_one")
    public Resp findOne(String id) {
        return Resp.success(strategicManagementService.findOne(id));
    }

    @GetMapping(value = "/find_parent_id")
    public Resp findByParentId(Company company, PageVo pageVo, @RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId) {
        if (company.getParentId() == null) {
            company.setParentId(serviceProviderId);
        }
        return Resp.success(distribuService.findByParentId(company, pageVo));
    }

}
