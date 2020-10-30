package com.fzy.admin.fp.advertise.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fzy.admin.fp.advertise.domain.AdvertiseManagement;
import com.fzy.admin.fp.advertise.dto.CPCDTO;
import com.fzy.admin.fp.advertise.dto.CPMDTO;
import com.fzy.admin.fp.advertise.dto.CPVDTO;
import com.fzy.admin.fp.advertise.service.AdvertiseManagementService;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.Resp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lb
 * @date 2019/7/1 16:04
 * @Description
 */
@RestController
@RequestMapping(value = "/advertise/advertise_management")
public class AdvertiseManagementController extends BaseController<AdvertiseManagement> {

    @Resource
    private AdvertiseManagementService advertiseManagementService;

    @Override
    public BaseService<AdvertiseManagement> getService() {
        return advertiseManagementService;
    }

    @GetMapping(value = "/find_one")
    public Resp findOne(String id) {

        AdvertiseManagement advertiseManagement = advertiseManagementService.findOne(id);
        if (advertiseManagement.getType().equals(AdvertiseManagement.Type.CPC.getCode())) {
            CPCDTO cpcdto = new CPCDTO();
            CopyOptions copyOptions = CopyOptions.create();
            copyOptions.setIgnoreNullValue(true);
            BeanUtil.copyProperties(advertiseManagement, cpcdto, copyOptions);
            cpcdto.setAdvertiseType(AdvertiseManagement.Type.CPC.getMessage());
            return Resp.success(cpcdto);
        } else if (advertiseManagement.getType().equals(AdvertiseManagement.Type.CPM.getCode())) {
            CPMDTO cpmdto = new CPMDTO();
            CopyOptions copyOptions = CopyOptions.create();
            copyOptions.setIgnoreNullValue(true);
            BeanUtil.copyProperties(advertiseManagement, cpmdto, copyOptions);
            cpmdto.setAdvertiseType(AdvertiseManagement.Type.CPM.getMessage());
            return Resp.success(cpmdto);
        } else if (advertiseManagement.getType().equals(AdvertiseManagement.Type.CPV.getCode())) {
            CPVDTO cpvdto = new CPVDTO();
            CopyOptions copyOptions = CopyOptions.create();
            copyOptions.setIgnoreNullValue(true);
            BeanUtil.copyProperties(advertiseManagement, cpvdto, copyOptions);
            cpvdto.setAdvertiseType(AdvertiseManagement.Type.CPV.getMessage());
            return Resp.success(cpvdto);
        }
        return new Resp().error(Resp.Status.PARAM_ERROR, "没有找到该广告");
    }

    @PostMapping(value = "/delete_re")
    public Resp delete(String id) {
        AdvertiseManagement advertiseManagement = advertiseManagementService.findOne(id);
        if (advertiseManagement.getSourceType().equals(AdvertiseManagement.SourceType.NO_USE.getCode())) {
            advertiseManagementService.delete(id);
            return Resp.success("删除成功");
        }
        return new Resp().error(Resp.Status.PARAM_ERROR, "该广告正在使用中无法删除");
    }

    @PostMapping(value = "/update_re")
    @Override
    public Resp update(AdvertiseManagement advertiseManagement) {
        AdvertiseManagement advertiseManagement1 = advertiseManagementService.findOne(advertiseManagement.getId());
        if (advertiseManagement1.getSourceType().equals(AdvertiseManagement.SourceType.NO_USE.getCode())) {
            CopyOptions copyOptions = CopyOptions.create();
            copyOptions.setIgnoreNullValue(true);
            BeanUtil.copyProperties(advertiseManagement, advertiseManagement1, copyOptions);
            advertiseManagementService.update(advertiseManagement1);
            return Resp.success("修改成功");
        } else {
            if (advertiseManagement1.getType().equals(advertiseManagement.getType())) {
                CopyOptions copyOptions = CopyOptions.create();
                copyOptions.setIgnoreNullValue(true);
                BeanUtil.copyProperties(advertiseManagement, advertiseManagement1, copyOptions);
                advertiseManagementService.update(advertiseManagement1);
                return Resp.success("修改成功");
            } else {
                return new Resp().error(Resp.Status.PARAM_ERROR, "该类型广告正在使用中，不能变换类型");
            }
        }
    }

}
