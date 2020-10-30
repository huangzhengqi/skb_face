package com.fzy.admin.fp.auth.controller;

import cn.hutool.crypto.digest.BCrypt;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.common.annotation.SystemLog;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.JwtUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.web.SelectItem;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.Role;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.domain.UserRole;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.auth.service.RoleService;
import com.fzy.admin.fp.auth.service.UserRoleService;
import com.fzy.admin.fp.auth.service.UserService;
import com.fzy.admin.fp.common.annotation.SystemLog;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.JwtUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.web.SelectItem;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantSelectItem;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zk
 * @description 用户表控制层
 * @create 2018-07-25 15:02:19
 **/
@Slf4j
@RestController
@RequestMapping("/auth/user")
public class UserController extends BaseController<User> {

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleService roleService;

    @Resource
    private CompanyRepository companyRepository;

    @Value("${secret.header}")
    private String header;


    @Resource
    private MerchantBusinessService merchantBusinessService;


    @Override
    public UserService getService() {
        return userService;
    }


    @Override
    public Resp list(User entity, PageVo pageVo) {
        //获取当前登录用户id
        String header1 = request.getHeader(header);
        String currentUserId = JwtUtil.getPayloadProperty(header1, "sub");
        //判断该用户是否为普通用户,若是普通用户查询自己
        UserRole userRoleQuery = userRoleService.getRepository().findByUserId(currentUserId);
        Role role = roleService.findOne(userRoleQuery.getRoleId());
        if (Role.LEVEL.COMMON.getCode().equals(role.getLevel())) {
            entity.setId(currentUserId);
        }
        Page<User> page = getService().list(entity, pageVo);
        for (User user : page.getContent()) {
            UserRole userRole = userRoleService.getRepository().findByUserId(user.getId());
            user.setLevel(roleService.findOne(userRole.getRoleId()).getName());
            Company company = companyRepository.findOne(user.getCompanyId());
            user.setCompanyName(company.getName());
        }
        return Resp.success(page);
    }


    /**
     * @author zk
     * @date 2018-08-13 20:20
     * @Description 添加用户
     */
    @PostMapping("/save_user")
    @SystemLog(description = "添加用户")
    public Resp save(User entity, String roleId, @UserId String userId) {
        User user = userService.findOne(userId);
        List<SelectItem> itemList =
                roleService.selectItemByTypeAndKind(user.getCompanyId(), 1).stream().filter(selectItem -> selectItem.getName().endsWith("业务员")).collect(Collectors.toList());
        return Resp.success(userService.saveUser(entity, itemList.get(0).getValue()));
    }

    /**
     * @author zk
     * @date 2018-08-13 21:08
     * @Description 修改用户
     */
    @PostMapping("/update_user")
    @SystemLog(description = "修改用户")
    @Transactional
    public Resp updateUser(User entity) {
        return Resp.success(userService.updateUser(entity));
    }


    /**
     * @author zk
     * @date 2018-08-13 21:00
     * @Description 禁用或启用
     */
    @PostMapping("/disable_or_enable")
    public Resp disableOrEnable(String id, Integer status) {

        return Resp.success(userService.disableOrEnable(id, status));
    }


    /*
     * @author drj
     * @date 2019-05-06 14:35
     * @Description :查询业务员详情
     */
    @GetMapping("/detail")
    public Resp detail(String id) {
        return Resp.success(userService.detail(id));
    }


    /*
     * @author drj
     * @date 2019-04-24 11:08
     * @Description ：重置密码
     */
    @PostMapping("/reset_password")
    @SystemLog(description = "重置密码")
    public Resp resetPassword(String id, String password) {
        if (ParamUtil.isBlank(password)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请输入密码");
        }
        User user = userService.findOne(id);
        if (user == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "查无此用户");
        }
        if (!password.matches("^[a-zA-Z0-9_-]{6,20}$")) {
            throw new BaseException("密码应为英文、数字、下划线组成的6-20位字符", Resp.Status.PARAM_ERROR.getCode());
        }
        user.setPassword(BCrypt.hashpw(password));
        userService.save(user);
        return Resp.success("密码重置成功");
    }

    /*
     * @author drj
     * @date 2019-04-28 15:32
     * @Description :禁用用户
     */
    @PostMapping("/cancle")
    public Resp cancle(String id) {
        User user = userService.findOne(id);
        if (ParamUtil.isBlank(user)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        user.setStatus(User.Status.DISABLE.getCode());
        userService.save(user);
        //查询该业务员底下是否有运营商或者渠道商
        Integer companyNum = companyRepository.countByManagerIdAndDelFlag(id, CommonConstant.NORMAL_FLAG);
        if (companyNum > 0) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "禁用失败,该业务员存在运营商或渠道商");
        }
        return Resp.success("操作成功");
    }

    /*
     * @author drj
     * @date 2019-05-06 11:41
     * @Description :修改密码
     */
    @PostMapping("/modify_password")
    public Resp modifyPassword(@UserId String id, String password, String newPassword) {
        if (ParamUtil.isBlank(password)) {
            throw new BaseException("请填写旧密码", Resp.Status.PARAM_ERROR.getCode());
        }
        if (ParamUtil.isBlank(newPassword)) {
            throw new BaseException("请填写新密码", Resp.Status.PARAM_ERROR.getCode());
        }
        User user = userService.findOne(id);
        if (user == null) {
            throw new BaseException("查无此用户", Resp.Status.PARAM_ERROR.getCode());
        }
        if (user.getDelFlag().equals(CommonConstant.DEL_FLAG)) {
            throw new BaseException("该用户已被禁用", Resp.Status.PARAM_ERROR.getCode());
        }
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new BaseException("原密码输入错误", Resp.Status.PARAM_ERROR.getCode());
        }
        if (!newPassword.matches("^[a-zA-Z0-9_-]{6,20}$")) {
            throw new BaseException("密码应为英文、数字、下划线组成的6-20位字符", Resp.Status.PARAM_ERROR.getCode());
        }
        user.setPassword(BCrypt.hashpw(newPassword));
        userService.save(user);
        return Resp.success("修改成功");
    }


    /*
     * @author drj
     * @date 2019-05-05 14:34
     * @Description :查看业务员关联的商户、二级代理商、一级代理商,同级业务员
     */
    @GetMapping("/manager_info")
    public Resp managerInfo(String userId) {
        Map<String, Object> map = new HashMap<>();
        //获取同级业务员
        User user = userService.findOne(userId);
        List<SelectItem> managerSelectItem = userService.getRepository().findByCompanyIdSelectItem(user.getCompanyId());
        //过滤自己
        managerSelectItem = managerSelectItem.stream().filter(SelectItem -> (!SelectItem.getValue().equals(userId))).collect(Collectors.toList());
        map.put("managerSelectItem", managerSelectItem);
        //查询用户对应的公司
        Company company = companyRepository.findOne(user.getCompanyId());
        //如果当前登录者为服务商,获取一级代理商列表
        if (Company.Type.PROVIDERS.getCode().equals(company.getType())) {
            List<SelectItem> operaSelectItem = companyRepository.findByManagerIdSelectItem(userId, Company.Type.OPERATOR.getCode());
            map.put("operaSelectItem", operaSelectItem);
            return Resp.success(map);
        }
        //如果当前登录者为一级代理商,获取对应的商户列表
        List<MerchantSelectItem> merchantSelectItems = merchantBusinessService.findByManagerIdSelectItem(userId);
        map.put("merchantSelectItems", merchantSelectItems);

        //若为一级代理商获取二级代理商列表
        if (Company.Type.OPERATOR.getCode().equals(company.getType())) {
            List<SelectItem> distributeSelectItem = companyRepository.findByManagerIdSelectItem(userId, Company.Type.DISTRIBUTUTORS.getCode());
            map.put("distributeSelectItem", distributeSelectItem);
            return Resp.success(map);
        }
        return Resp.success(map);
    }

    /*
     * @author drj
     * @date 2019-05-05 15:53
     * @Description :修改业务员关联信息
     */
    @PostMapping("/update_manager_relation")
    public Resp updateManagerRelation(String[] merchantIds, String[] distributeIds, String[] operaIds, String managerId, @UserId String userId) {
        //获取同级业务员
        User user = userService.findOne(userId);
        //查询用户对应的公司
        Company company = companyRepository.findOne(user.getCompanyId());
        //如果当前登录者为服务商
        if (Company.Type.PROVIDERS.getCode().equals(company.getType())) {
            //更改一级代理商对应的业务员id
            List<Company> companyList = companyRepository.findAll(Arrays.asList(operaIds));
            for (Company company1 : companyList) {
                company1.setManagerId(managerId);
            }
            companyRepository.save(companyList);
            return Resp.success("操作成功");
        }
        //更改商户对应的业务员id
        merchantBusinessService.changeManagerId(merchantIds, managerId);
        //若为一级代理商
        if (Company.Type.OPERATOR.getCode().equals(company.getType())) {
            //更改一级代理商对应的业务员id
            List<Company> companyList = companyRepository.findAll(Arrays.asList(distributeIds));
            for (Company company1 : companyList) {
                company1.setManagerId(managerId);
            }
            companyRepository.save(companyList);
        }
        return Resp.success("操作成功");
    }

    //管理员重置服务商密码接口
    @PostMapping("/admin/reset_password")
    public Resp adminResetPassword(String username, String companyId) {
        if (ParamUtil.isBlank(username) || ParamUtil.isBlank(companyId)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        User user = userService.findByUsername(username, companyId);
        if (ParamUtil.isBlank(user)) {
            throw new BaseException("参数错误", Resp.Status.PARAM_ERROR.getCode());
        }
        user.setPassword(BCrypt.hashpw("123456"));
        userService.save(user);
        return Resp.success("新密码重置为123456");
    }

    //初始化商户对应登录用户密码
    @PostMapping("/default/reset_password")
    public Resp defaultResetPassword(String username) {
        User user = userService.findByUsername(username, (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID));
        if (ParamUtil.isBlank(user)) {
            throw new BaseException("参数错误", Resp.Status.PARAM_ERROR.getCode());
        }
        user.setPassword(BCrypt.hashpw("123456"));
        userService.save(user);
        return Resp.success("新密码重置为123456");
    }


    @Override

    public Resp delete(String[] ids) {
        return null;
    }

    @Override
    public Resp logicalDel(String[] ids) {
        return null;
    }
}
