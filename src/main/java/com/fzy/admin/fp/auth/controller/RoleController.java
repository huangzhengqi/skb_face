package com.fzy.admin.fp.auth.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fzy.admin.fp.common.annotation.SystemLog;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.Permission;
import com.fzy.admin.fp.auth.domain.Role;
import com.fzy.admin.fp.auth.domain.RolePermission;
import com.fzy.admin.fp.auth.dto.RoleDTO;
import com.fzy.admin.fp.auth.dto.RoleListDTO;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.auth.service.PermissionService;
import com.fzy.admin.fp.auth.service.RolePermissionService;
import com.fzy.admin.fp.auth.service.RoleService;
import com.fzy.admin.fp.auth.vo.ChildVO;
import com.fzy.admin.fp.common.annotation.SystemLog;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zk
 * @description 角色表控制层
 * @create 2018-07-25 15:10:47
 **/
@Slf4j
@RestController
@RequestMapping("/auth/role")
public class RoleController extends BaseController<Role> {

    @Resource
    private RoleService roleService;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private CompanyService companyService;

    @Override
    public RoleService getService() {
        return roleService;
    }

    @Override
    @SystemLog(description = "查看角色")
    public Resp list(Role entity, PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification specification = ConditionUtil.createSpecification(entity);
        Page<Role> page = roleService.findAll(specification, pageable);
        for (Role role : page.getContent()) {
            role.setPermissions(permissionService.findByRoleId(role.getId()));
        }
        return Resp.success(page);
    }

    @SystemLog(description = "查看角色")
    @RequestMapping(value = "/re_list", method = RequestMethod.GET)
    public Resp re_list(Role entity, PageVo pageVo, @TokenInfo(property = "companyId") String companyId) {
        Company company = companyService.findOne(companyId);
        if (company.getType().equals(Company.Type.ADMIN.getCode())) {
            entity.setCompanyId("0");
        } else {
            entity.setCompanyId(company.getId());
        }

        Pageable pageable = PageUtil.initPage(pageVo);
        Specification specification = ConditionUtil.createSpecification(entity);
        Page<Role> page = roleService.findAll(specification, pageable);
        for (Role role : page.getContent()) {
            role.setPermissions(permissionService.findByRoleId(role.getId()));
        }
        return Resp.success(page);
    }

    @ApiOperation(value = "获取下级列表", notes = "获取下级列表")
    @RequestMapping(value = "/company", method = RequestMethod.GET)
    public Resp<List<ChildVO>> re_list(@TokenInfo(property = "companyId") String companyId,
                                       @ApiParam(value = "id") @RequestParam(value = "id") String id,
                                       @ApiParam(value = "type") @RequestParam(value = "type", required = false) Integer type) {
        List<Company> companies;
        if (type == null) {
            companies = companyService.findByParentId(companyId);
        } else {
            companies = companyService.findByParentIdAndTypeLessThanEqual(companyId, type);
        }
        List<ChildVO> childVOS = new ArrayList<>();
        for (Company company : companies) {
            ChildVO childVO = new ChildVO();
            childVO.setId(company.getId());
            childVO.setName(company.getName());
            Role role = roleService.findBySourceRoleIdAndCompanyIdAndDelFlag(id, company.getId(), 1);
            if (role != null) {
                childVO.setStatus(1);
            } else {
                childVO.setStatus(0);
            }
            childVOS.add(childVO);
        }
        return Resp.success(childVOS);
    }

    @PostMapping("")
    @ApiOperation(value = "授权/取消授权", notes = "授权/取消授权")
    public Resp<String> authRole(@RequestBody RoleDTO roleDTO, @TokenInfo(property = "companyId") String companyId) {
        List<String> authIds = new ArrayList<>();
        List<String> unAuthIds = new ArrayList<>();

        for (RoleListDTO item : roleDTO.getList()) {
            if (item.getStatus() == 1) {
                authIds.add(item.getId());
            } else {
                unAuthIds.add(item.getId());
            }
        }

        Role entity = roleService.findOne(roleDTO.getId());

        if (authIds.size() > 0) {
            //授权
            List<Role> authRole = roleService.findByCompanyIdInAndSourceRoleIdAndAuthCompanyId(authIds, roleDTO.getId(), companyId);
            List<String> authIdsNew = new ArrayList<>(authIds);
            for (String id : authIds) {
                for (Role role : authRole) {
                    if (id.equals(role.getCompanyId())) {
                        role.setDelFlag(1);
                        roleService.save(role);
                        authIdsNew.remove(id);
                    }
                }
            }
            for (String id : authIdsNew) {
                Role role = new Role();
                BeanUtil.copyProperties(entity, role);
                role.setId(null);
                role.setAuthCompanyId(companyId);
                role.setCompanyId(id);
                role.setSourceRoleId(roleDTO.getId());
                roleService.save(role);
            }
        }

        if (unAuthIds.size() > 0) {
            //取消授权
            List<Role> cancelRole = roleService.findByCompanyIdInAndSourceRoleIdAndAuthCompanyId(unAuthIds, roleDTO.getId(), companyId);
            for (Role role : cancelRole) {
                role.setDelFlag(-1);
                roleService.save(role);
                rolePermissionService.deleteByRoleId(role.getId());
            }
            //同时取消所有下级的角色
            List<Role> canceChildlRole = childRole(cancelRole);
            for (Role role : canceChildlRole) {
                role.setDelFlag(-1);
                roleService.save(role);
                rolePermissionService.deleteByRoleId(role.getId());
            }

        }
        return Resp.success("", "设置成功");
    }

    //获取所有下级角色
    private List<Role> childRole(List<Role> parentRole) {
        if (parentRole.size()==0) {
            return new ArrayList<>();
        }
        List<String> parentRoleId = parentRole.stream().map(Role::getId).collect(Collectors.toList());
        if (parentRoleId.size() > 0) {
            List<Role> childRole = roleService.findBySourceRoleIdIn(parentRoleId);
            if (childRole.size() > 0) {
                List<Role> childRole1 = childRole(childRole);
                if (childRole1.size()>0) {
                    childRole.addAll(childRole1);
                }
            }
            return childRole;
        }
        return new ArrayList<>();
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
        Role role = roleService.findOne(id);
        if (role == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "查无此角色");
        }
        rolePermissionService.deleteByRoleId(id);
        if (!ParamUtil.isBlank(permIds)) {
            for (String permId : permIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(id);
                Permission permission = permissionService.findOne(permId);
                if (permission == null) {
                    throw new BaseException("参数有误，请重新操作", Resp.Status.PARAM_ERROR.getCode());
                }
                rolePermission.setPermissionId(permId);
                rolePermissionService.save(rolePermission);
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
        Role role = roleService.findOne(id);
        if (role == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "角色不存在");
        }
        roleService.update(role);
        return Resp.success("设置成功");
    }

    @Override
    @SystemLog(description = "删除角色")
    @Transactional
    public Resp delete(String[] ids) {
        if (ParamUtil.isBlank(ids)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请选择删除对象");
        }
        List<Role> list = getService().findAll(ids);
        for (Role role : list) {
            //删除相关联用户
            roleService.delete(role);
            //删除相关联权限
            rolePermissionService.deleteByRoleId(role.getId());
        }
        return Resp.success("删除成功");
    }

    /*
     * @author drj
     * @date 2019-04-29 19:44
     * @Description :通过公司类型和种类查询角色
     */
    @GetMapping("/find_by_type_and_kind/select_item")
    public Resp selectItemByTypeAndKind(String companyId, Integer kind) {
        return Resp.success(roleService.selectItemByTypeAndKind(companyId, kind));
    }


    @Override
    public Resp logicalDel(String[] ids) {
        return null;
    }
}
