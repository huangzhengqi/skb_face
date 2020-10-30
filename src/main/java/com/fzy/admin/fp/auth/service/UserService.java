package com.fzy.admin.fp.auth.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.crypto.digest.BCrypt;
import com.fzy.admin.fp.auth.repository.UserRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.domain.UserRole;
import com.fzy.admin.fp.auth.repository.UserRepository;
import com.fzy.admin.fp.auth.utils.SmsService;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

/**
 * @author zk
 * @description 用户表服务层
 * @create 2018-07-25 15:02:19
 **/
@Slf4j
@Service
@Transactional
public class UserService implements BaseService<User> {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private SmsService smsService;

    @Resource
    private RoleService roleService;

    @Resource
    private HttpServletRequest request;


    @Override
    public UserRepository getRepository() {
        return userRepository;
    }

    public User findByUsername(String username, String serviceProviderId) {
        //return userRepository.findByUsernameAndServiceProviderIdAndDelFlag(username, serviceProviderId, CommonConstant.NORMAL_FLAG);
        return userRepository.findByUsernameAndDelFlag(username, CommonConstant.NORMAL_FLAG);
    }


    public String saveUser(User entity, String roleId) {
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        User user = userRepository.findByPhoneAndDelFlag(entity.getPhone(), CommonConstant.NORMAL_FLAG);
        if (!ParamUtil.isBlank(user)) {
            throw new BaseException("手机号已存在", Resp.Status.PARAM_ERROR.getCode());
        }
        if (ParamUtil.isBlank(roleId)) {
            throw new BaseException("请选择等级", Resp.Status.PARAM_ERROR.getCode());
        }
        String password = "123456";//RandomUtil.randomNumbers(8);
        entity.setUsername(entity.getPhone());
        entity.setPassword(BCrypt.hashpw(password));
        entity.setServiceProviderId(serviceId);
        //调用短信通知用户
        boolean flag = smsService.sendSmsInfo(entity.getName(), entity.getPhone(), password);
        if (!flag) {
            throw new BaseException("短信发送失败", Resp.Status.PARAM_ERROR.getCode());
        }
        userRepository.save(entity);
        UserRole ur = new UserRole();
        ur.setRoleId(roleId);
        ur.setUserId(entity.getId());
        userRoleService.save(ur);
        return "添加成功! 默认密码123456";
    }


    public String updateUser(User entity) {
        User user = userRepository.findOne(entity.getId());
        if (user == null) {
            throw new BaseException("查无此用户", Resp.Status.PARAM_ERROR.getCode());
        }
        //获取Hutool拷贝实例
        CopyOptions copyOptions = CopyOptions.create();
        //忽略为null值得属性
        copyOptions.setIgnoreNullValue(true);
        //进行属性拷贝
        BeanUtil.copyProperties(entity, user, copyOptions);
        userRepository.save(user);
        return "修改成功";
    }

    public String disableOrEnable(String id, Integer status) {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new BaseException("查无此用户", Resp.Status.PARAM_ERROR.getCode());
        }
        user.setStatus(status);
        userRepository.save(user);
        return "操作成功";
    }

    public User detail(String id) {
        User user = userRepository.findOne(id);
        UserRole userRole = userRoleService.getRepository().findByUserId(user.getId());
        user.setLevel(roleService.findOne(userRole.getRoleId()).getName());
        return user;
    }
}