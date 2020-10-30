package com.fzy.admin.fp.merchant.management.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.LevelAliasConfig;
import com.fzy.admin.fp.auth.domain.Permission;
import com.fzy.admin.fp.auth.repository.LevelAliasConfigRepository;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.common.annotation.SystemLog;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.management.AuthConstant;
import com.fzy.admin.fp.merchant.management.domain.MerchantPermission;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantPermissionService;
import com.fzy.admin.fp.merchant.management.service.MerchantRolePermissionService;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zk
 * @description 权限表控制层
 * @create 2018-07-25 15:32:54
 **/
@Slf4j
@RestController
@RequestMapping("/merchant/permission")
public class MerchantPermissionController extends BaseController<MerchantPermission> {

    @Resource
    private LevelAliasConfigRepository levelAliasConfigRepository;

    @Resource
    private MerchantPermissionService merchantPermissionService;

    @Override
    public MerchantPermissionService getService() {
        return merchantPermissionService;
    }

    @Resource
    private MerchantRolePermissionService merchantRolePermissionService;

    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private CompanyService companyService;

    @Value("${secret.key}")
    private String key;

    @Value("${secret.header}")
    private String header;

    @GetMapping("/findMenuList")
    public Resp findMenuList(HttpServletRequest request) {
        MerchantUser user = getUserByToken(request);
        if (ParamUtil.isBlank(user)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "token有误，请重新登录");
        }
        //该用户的所有权限 已排序去重
        List<MerchantPermission> list = new ArrayList<>();
        if (user.getUserType().equals("1")) {
            list = merchantPermissionService.findAll();
        } else {
            list = merchantPermissionService.findByUserId(user.getId());
        }


        List<MerchantPermission> menuList = new ArrayList<>();

        //筛选一级页面
//        for (Permission p : list) {
//            if (AuthConstant.PERMISSION_PAGE.equals(p.getType()) && AuthConstant.LEVEL_ONE.equals(p.getLevel())) {
//                menuList.add(p);
//            }
//        }
        menuList = list.stream()
                .filter(p -> AuthConstant.PERMISSION_PAGE.equals(p.getType()) && AuthConstant.LEVEL_ONE.equals(p.getLevel()))
                .sorted(Comparator.comparing(MerchantPermission::getSortOrder))
                .collect(Collectors.toList());

        //筛选二级页面
        List<MerchantPermission> secondMenuList = new ArrayList<>();
        for (MerchantPermission p : list) {
            if (AuthConstant.PERMISSION_PAGE.equals(p.getType()) && AuthConstant.LEVEL_TWO.equals(p.getLevel())) {
                secondMenuList.add(p);
            }
        }

        //筛选二级页面拥有的按钮权限
        List<MerchantPermission> buttonPermissions = new ArrayList<>();
        for (MerchantPermission p : list) {
            if (AuthConstant.PERMISSION_OPERATION.equals(p.getType()) && AuthConstant.LEVEL_THREE.equals(p.getLevel())) {
                buttonPermissions.add(p);
            }
        }

        //匹配二级页面拥有权限
        for (MerchantPermission p : secondMenuList) {
            List<MerchantPermission> buttonMenu = new ArrayList<>();
            for (MerchantPermission pe : buttonPermissions) {
                if (p.getId().equals(pe.getParentId())) {
                    buttonMenu.add(pe);
                }
            }
            p.setChildren(buttonMenu);
        }

        //匹配一级页面拥有二级页面
        for (MerchantPermission p : menuList) {
            List<MerchantPermission> secondMenu = new ArrayList<>();
            for (MerchantPermission pe : secondMenuList) {
                if (p.getId().equals(pe.getParentId())) {
                    secondMenu.add(pe);
                }
            }
            p.setChildren(secondMenu);
        }
        //是否配置别名
        Company oem = companyService.findOne(user.getServiceProviderId());
        LevelAliasConfig levelAliasConfig = levelAliasConfigRepository.findByIdAndStatus(oem.getId(),1);
        if (levelAliasConfig == null) {
            levelAliasConfig = levelAliasConfigRepository.findByIdAndStatus(oem.getParentId(),1);
        }
        if (levelAliasConfig != null) {
            this.useAlias(menuList,levelAliasConfig);
        }

        return Resp.success(menuList);
    }

    private void useAlias(List<MerchantPermission> list,LevelAliasConfig levelAliasConfig) {
        for (MerchantPermission permission : list) {
            String perName = permission.getName();
            if (!StringUtils.isEmpty(levelAliasConfig.getOemName())) {
                perName = perName.replace("服务商",levelAliasConfig.getOemName());
            }
            if (!StringUtils.isEmpty(levelAliasConfig.getFirstName())) {
                perName = perName.replace("一级代理商",levelAliasConfig.getFirstName());
            }
            if (!StringUtils.isEmpty(levelAliasConfig.getSecondName())) {
                perName = perName.replace("二级代理商",levelAliasConfig.getSecondName());
            }
            if (!StringUtils.isEmpty(levelAliasConfig.getThirdName())) {
                perName = perName.replace("三级代理商",levelAliasConfig.getThirdName());
            }
            permission.setName(perName);
            if (permission.getChildren() != null) {
                this.useAlias(permission.getChildren(),levelAliasConfig);
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
        List<MerchantPermission> permissions = merchantPermissionService.findByLevelOrderBySortOrderDesc(AuthConstant.LEVEL_ONE);
        for (MerchantPermission p1 : permissions) {
            List<MerchantPermission> children1 = merchantPermissionService.findByParentIdOrderBySortOrder(p1.getId());
            p1.setChildren(children1);
            for (MerchantPermission p2 : children1) {
                List<MerchantPermission> children2 = merchantPermissionService.findByParentIdOrderBySortOrder(p2.getId());
                p2.setChildren(children2);
            }
        }
        return Resp.success(permissions);
    }

    /**
     * @author Created by wtl on 2019/3/13 15:47
     * @Description 添加权限
     */
    @Override
    @PostMapping("/save")
    public Resp save(@Valid MerchantPermission entity) {
        return Resp.success(merchantPermissionService.add(entity));
    }

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
            MerchantPermission permission = merchantPermissionService.findOne(id);
            if (permission == null) {
                throw new BaseException("权限信息异常，请重试", Resp.Status.PARAM_ERROR.getCode());
            }
            merchantRolePermissionService.deleteByPermissionId(id);
            merchantPermissionService.delete(id);
        }
//        mySecurityMetadataSource.loadResourceDefine();
        return Resp.success("删除成功");
    }

    @Override
    public Resp list(MerchantPermission entity, PageVo pageVo) {
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

    private MerchantUser getUserByToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (ParamUtil.isBlank(token)) {
            return null;
        }
        JWTVerifier verifier = JWT.require(Algorithm.HMAC512(key)).build();
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }

        return merchantUserService.findOne(jwt.getSubject());
    }
}
