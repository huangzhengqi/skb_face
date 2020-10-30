package com.fzy.admin.fp.auth.app;

import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.domain.UserRole;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.auth.service.RoleService;
import com.fzy.admin.fp.auth.service.UserRoleService;
import com.fzy.admin.fp.auth.service.UserService;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.domain.UserRole;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.auth.service.RoleService;
import com.fzy.admin.fp.auth.service.UserRoleService;
import com.fzy.admin.fp.auth.service.UserService;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 11:36 2019/6/1
 * @ Description: APP业务员
 **/
@Slf4j
@RestController
@RequestMapping("/auth/user/app")
public class AppUserController extends BaseContent {

    @Resource
    private UserService userService;
    @Resource
    private CompanyRepository companyRepository;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleService roleService;

    @PostMapping("/list")
    public Resp list(@TokenInfo(property = "companyId") String companyId, User entity, PageVo pageVo) {
        entity.setCompanyId(companyId);
        Page<User> page = userService.list(entity, pageVo);
        for (User user : page.getContent()) {
            UserRole userRole = userRoleService.getRepository().findByUserId(user.getId());
            user.setLevel(roleService.findOne(userRole.getRoleId()).getName());
            Company company = companyRepository.findOne(user.getCompanyId());
            user.setCompanyName(company.getName());
        }
        return Resp.success(page);
    }


    /*
     * @author drj
     * @date 2019-06-01 14:47
     * @Description :获取等级下拉框
     */
    @GetMapping("/find_by_type_and_kind/select_item")
    public Resp selectItemByTypeAndKind(@TokenInfo(property = "companyId") String companyId, Integer kind) {
        return Resp.success(roleService.selectItemByTypeAndKind(companyId, kind));
    }

    /**
     * @author zk
     * @date 2018-08-13 20:20
     * @Description 添加用户
     */
    @PostMapping("/save")
    public Resp save(@TokenInfo(property = "companyId") String companyId, User entity, String roleId) {
        entity.setCompanyId(companyId);
        return Resp.success(userService.saveUser(entity, roleId));
    }

    /**
     * @author zk
     * @date 2018-08-13 21:08
     * @Description 修改用户
     */
    @PostMapping("/update")
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
}
