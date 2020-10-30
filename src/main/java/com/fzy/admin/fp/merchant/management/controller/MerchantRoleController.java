package com.fzy.admin.fp.merchant.management.controller;


import com.fzy.admin.fp.common.annotation.SystemLog;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.management.domain.MerchantPermission;
import com.fzy.admin.fp.merchant.management.domain.MerchantRole;
import com.fzy.admin.fp.merchant.management.domain.MerchantRolePermission;
import com.fzy.admin.fp.merchant.management.service.MerchantPermissionService;
import com.fzy.admin.fp.merchant.management.service.MerchantRolePermissionService;
import com.fzy.admin.fp.merchant.management.service.MerchantRoleService;
import com.fzy.admin.fp.merchant.management.service.MerchantUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author zk
 * @description 角色表控制层
 * @create 2018-07-25 15:10:47
 **/
@Slf4j
@RestController
@RequestMapping("/merchant/role")
public class MerchantRoleController extends BaseController<MerchantRole> {


    @Resource
    private MerchantRoleService merchantRoleService;
    @Resource
    private MerchantUserRoleService merchantUserRoleService;
    @Resource
    private MerchantRolePermissionService merchantRolePermissionService;
    @Resource
    private MerchantPermissionService merchantPermissionService;

    @Override
    public MerchantRoleService getService() {
        return merchantRoleService;
    }

    @Override
    public Resp list(MerchantRole entity, PageVo pageVo) {
        Page<MerchantRole> page = getService().list(entity, pageVo);
        for (MerchantRole role : page.getContent()) {
            role.setPermissions(merchantPermissionService.findByRoleId(role.getId()));
        }
        return Resp.success(page);
    }

    /**
     * @author zk
     * @date 2018-08-13 21:45
     * @Description 分配角色权限
     */
    @PostMapping(value = "/edit_role_perm")
    @SystemLog(description = "分配角色权限")
    @Transactional
    public Resp editRolePerm(String id, String[] permIds) {
        if (ParamUtil.isBlank(id)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请选择角色");
        }
        MerchantRole role = merchantRoleService.findOne(id);
        if (role == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "查无此角色");
        }
        merchantRolePermissionService.deleteByRoleId(id);
        if (!ParamUtil.isBlank(permIds)) {
            for (String permId : permIds) {
                MerchantRolePermission rolePermission = new MerchantRolePermission();
                rolePermission.setRoleId(id);
                MerchantPermission permission = merchantPermissionService.findOne(permId);
                if (permission == null) {
                    throw new BaseException("权限信息异常，请重试", Resp.Status.PARAM_ERROR.getCode());
                }
                rolePermission.setPermissionId(permId);
                merchantRolePermissionService.save(rolePermission);
            }
        }
        return Resp.success("权限分配成功");
    }

    /**
     * @author zk
     * @date 2018-08-13 21:29
     * @Description 设置或取消默认角色
     */
    @PostMapping(value = "/setDefault")
    public Resp setDefault(String id, Boolean isDefault) {
        MerchantRole role = merchantRoleService.findOne(id);
        if (role == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "角色不存在");
        }
        merchantRoleService.update(role);
        return Resp.success("设置成功");
    }

    @Override
    @SystemLog(description = "删除角色")
    @Transactional
    public Resp delete(String[] ids) {
        if (ParamUtil.isBlank(ids)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请选择删除对象");
        }
        List<MerchantRole> list = getService().findAll(ids);
        for (MerchantRole role : list) {
            if (MerchantRole.RoleType.MANAGER.getCode().equals(role.getRoleType())) {
                throw new BaseException("店长角色不能删除");
            }
            //删除相关联用户
            merchantUserRoleService.deleteByRoleId(role.getId());
            merchantRoleService.delete(role);
            //删除相关联权限
            merchantRolePermissionService.deleteByRoleId(role.getId());
        }
        return Resp.success("删除成功");
    }

    @Override
    public Resp logicalDel(String[] ids) {
        return null;
    }


    @Override
    public Resp save(MerchantRole entity) {
        if (ParamUtil.isBlank(entity.getName())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请填写角色名称");
        }
        entity.setRoleType(MerchantRole.RoleType.OTHER.getCode());
        super.save(entity);
        return Resp.success("添加成功，请分配权限");
    }


}
