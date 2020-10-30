package com.fzy.admin.fp.auth.controller;

import com.fzy.admin.fp.auth.repository.LevelAliasConfigRepository;
import com.fzy.admin.fp.common.annotation.SystemLog;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.AuthConstant;
import com.fzy.admin.fp.auth.domain.*;
import com.fzy.admin.fp.auth.repository.LevelAliasConfigRepository;
import com.fzy.admin.fp.auth.service.*;
import com.fzy.admin.fp.common.annotation.SystemLog;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zk
 * @description 权限表控制层
 * @create 2018-07-25 15:32:54
 **/
@Slf4j
@RestController
@RequestMapping("/auth/permission")
public class PermissionController extends BaseController<Permission> {

    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;
    @Resource
    private CompanyService companyService;

    @Resource
    private LevelAliasConfigRepository levelAliasConfigRepository;

    @Override
    public PermissionService getService() {
        return permissionService;
    }

    @Resource
    private RolePermissionService rolePermissionService;

    @Resource
    private UserService userService;

    @Value("${secret.header}")
    private String header;

    @GetMapping("/findMenuList")
    public Resp findMenuList(@UserId String userId) {
        if (ParamUtil.isBlank(userId)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "token有误，请重新登录");
        }
        User user = userService.findOne(userId);
        if (ParamUtil.isBlank(user)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "token有误，请重新登录");
        }
        List<Permission> list;
        //该用户的所有权限 已排序去重
        //list = permissionService.findByUserId(user.getId());
        list = permissionService.findByUserIdNew(user);
        List<Permission> menuList = new ArrayList<>();

        //筛选一级页面
        for (Permission p : list) {
            if (AuthConstant.PERMISSION_PAGE.equals(p.getType()) && AuthConstant.LEVEL_ONE.equals(p.getLevel())) {
                menuList.add(p);
            }
        }

        //筛选二级页面
        List<Permission> secondMenuList = new ArrayList<>();
        for (Permission p : list) {
            if (AuthConstant.PERMISSION_PAGE.equals(p.getType()) && AuthConstant.LEVEL_TWO.equals(p.getLevel())) {
                secondMenuList.add(p);
            }
        }

        //筛选二级页面拥有的按钮权限
        List<Permission> buttonPermissions = new ArrayList<>();
        for (Permission p : list) {
            if (AuthConstant.PERMISSION_OPERATION.equals(p.getType()) && AuthConstant.LEVEL_THREE.equals(p.getLevel())) {
                buttonPermissions.add(p);
            }
        }

        //匹配二级页面拥有权限
        for (Permission p : secondMenuList) {
            List<Permission> buttonMenu = new ArrayList<>();
            for (Permission pe : buttonPermissions) {
                if (p.getId().equals(pe.getParentId())) {
                    buttonMenu.add(pe);
                }
            }
            p.setChildren(buttonMenu);
        }

        //匹配一级页面拥有二级页面
        for (Permission p : menuList) {
            List<Permission> secondMenu = new ArrayList<>();
            for (Permission pe : secondMenuList) {
                if (p.getId().equals(pe.getParentId())) {
                    secondMenu.add(pe);
                }
            }
            p.setChildren(secondMenu);
        }

        //是否配置别名
        Company oem = companyService.findOne(user.getServiceProviderId());
        LevelAliasConfig levelAliasConfig = levelAliasConfigRepository.findByIdAndStatus(oem.getId(), 1);
        if (levelAliasConfig == null) {
            levelAliasConfig = levelAliasConfigRepository.findByIdAndStatus(oem.getParentId(), 1);
        }
        if (levelAliasConfig != null) {
            this.useAlias(menuList, levelAliasConfig);
        }

        return Resp.success(menuList);
    }

    private void useAlias(List<Permission> list, LevelAliasConfig levelAliasConfig) {
        for (Permission permission : list) {
            String perName = permission.getName();
            if (!StringUtils.isEmpty(levelAliasConfig.getOemName())) {
                perName = perName.replace("服务商", levelAliasConfig.getOemName());
            }
            if (!StringUtils.isEmpty(levelAliasConfig.getFirstName())) {
                perName = perName.replace("一级代理商", levelAliasConfig.getFirstName());
            }
            if (!StringUtils.isEmpty(levelAliasConfig.getSecondName())) {
                perName = perName.replace("二级代理商", levelAliasConfig.getSecondName());
            }
            if (!StringUtils.isEmpty(levelAliasConfig.getThirdName())) {
                perName = perName.replace("三级代理商", levelAliasConfig.getThirdName());
            }
            permission.setName(perName);
            if (permission.getChildren() != null) {
                this.useAlias(permission.getChildren(), levelAliasConfig);
            }
        }
    }

    /**
     * @author zk
     * @date 2018-08-13 22:04
     * @Description 获取权限菜单树
     */
    @GetMapping("/find_all_list")
    public Resp findAllList() {
        List<Permission> permissions = permissionService.findByLevelOrderBySortOrderDesc(AuthConstant.LEVEL_ONE);
        for (Permission p1 : permissions) {
            List<Permission> children1 = permissionService.findByParentIdOrderBySortOrder(p1.getId());
            p1.setChildren(children1);
            for (Permission p2 : children1) {
                List<Permission> children2 = permissionService.findByParentIdOrderBySortOrder(p2.getId());
                p2.setChildren(children2);
            }
        }
        return Resp.success(permissions);
    }

    /**
     * @author zk
     * @date 2018-08-13 22:04
     * @Description 获取权限菜单树
     */
    @GetMapping("/find_all_list_new")
    public Resp findAllListNew(@ApiParam(value = "角色id") @RequestParam(value = "roleId",required = false) String roleId) {
        List<Permission> permissions = new ArrayList<>();
        if (!StringUtils.isEmpty(roleId)){
            Role role = roleService.findOne(roleId);
            //如果是上级授权的角色，查找出授权的菜单，如果未授权则显示所有菜单
            if (!StringUtils.isEmpty(role.getSourceRoleId())) {
                List<RolePermission> rolePermissions = rolePermissionService.findByRoleId(role.getSourceRoleId());
                if (rolePermissions.size() > 0) {
                    List<String> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
                    permissions = permissionService.findByLevelAndIdInOrderBySortOrder(AuthConstant.LEVEL_ONE, permissionIds);
                    for (Permission p1 : permissions) {
                        List<Permission> children1 = permissionService.findByParentIdAndIdInOrderBySortOrder(p1.getId(),permissionIds);
                        p1.setChildren(children1);
                        for (Permission p2 : children1) {
                            List<Permission> children2 = permissionService.findByParentIdAndIdInOrderBySortOrder(p2.getId(),permissionIds);
                            p2.setChildren(children2);
                        }
                    }
                    return Resp.success(permissions);
                }
            }
        }
        return findAllList();


    }


//    @Override
//    @PostMapping("/save")
//    public Resp save(@Valid Permission entity) {
//        permissionService.update(entity);
//        //重新加载权限表
////        mySecurityMetadataSource.loadResourceDefine();
//        return Resp.success("添加成功");
//    }

//    @Override
//    @PostMapping("/update")
//    public Resp update(Permission entity) {
//        if (ParamUtil.isBlank(entity.getId())) {
//            return Resp.error(Resp.Status.PARAM_ERROR, "参数错误，请重试");
//        }
//        Permission permission = getService().findOne(entity.getId());
//        if (permission == null) {
//            return Resp.error(Resp.Status.PARAM_ERROR, "查无此数据，请刷新重试");
//        }
//        //获取Hutool拷贝实例
//        CopyOptions copyOptions = CopyOptions.create();
//        //忽略为null值得属性
//        copyOptions.setIgnoreNullValue(true);
//        //进行属性拷贝
//        BeanUtil.copyProperties(entity, permission, copyOptions);
////        //对实体类中的@validation注解进行校验
////        Medusa medusa = Message.getMsg(permission);
////        if (!medusa.isFlag()) {
////            return new Resp(Resp.PARAM_ERROR, medusa.getMessage());
////        }
//        BindingResult bindingResult = Validation.valid(permission);
//        if(!bindingResult.isFlag()){
//            return Resp.error(Resp.Status.PARAM_ERROR,bindingResult.getMessage().get(0));
//        }
//        permissionService.update(permission);
//        //重新加载权限表
////        mySecurityMetadataSource.loadResourceDefine();
//        return Resp.success("修改成功");
//    }

    @Override
    @SystemLog(description = "删除权限")
    @Transactional
    public Resp delete(String[] ids) {
        if (ParamUtil.isBlank(ids)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请选择删除对象");
        }
        for (String id : ids) {
            Permission permission = permissionService.findOne(id);
            if (permission == null) {
                throw new BaseException("参数有误，请重新操作", Resp.Status.PARAM_ERROR.getCode());
            }
            rolePermissionService.deleteByPermissionId(id);
            permissionService.delete(id);
        }
//        mySecurityMetadataSource.loadResourceDefine();
        return Resp.success("删除成功");
    }

    @Override
    public Resp list(Permission entity, PageVo pageVo) {
        return null;
    }

    @Override
    public Resp logicalDel(String[] ids) {
        return null;
    }

    @Override
    public Resp selectItem() {
        return null;
    }

}
