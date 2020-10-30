package com.fzy.admin.fp.merchant.management.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.crypto.digest.BCrypt;
import com.fzy.admin.fp.merchant.management.repository.MerchantUserRepository;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.Validation;
import com.fzy.admin.fp.common.validation.domain.BindingResult;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.web.SelectItem;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.domain.MerchantUserRole;
import com.fzy.admin.fp.merchant.management.repository.MerchantUserRepository;
import com.fzy.admin.fp.merchant.management.vo.UserVo;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.service.StoreService;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserSelect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zk
 * @description 用户表服务层
 * @create 2018-07-25 15:02:19
 **/
@Slf4j
@Service
@Transactional
public class MerchantUserService implements BaseService<MerchantUser> {

    @Resource
    private MerchantUserRepository merchantUserRepository;

    @Resource
    private MerchantRoleService merchantRoleService;

    @Resource
    private MerchantUserRoleService merchantUserRoleService;

    @Resource
    private StoreService storeService;


    @Override
    public MerchantUserRepository getRepository() {
        return merchantUserRepository;
    }

    public MerchantUser findByUsername(String username) {
        return merchantUserRepository.findByUsernameAndDelFlag(username, CommonConstant.NORMAL_FLAG);
    }


    /*
     * @author drj
     * @date 2019-05-27 16:19
     * @Description :获取用户列表
     */
    public Page<UserVo> listRewrite(MerchantUser entity, PageVo pageVo, String userId) {
        MerchantUser userQuery = merchantUserRepository.findOne(userId);
        String storeId = "";
        String id = "";
        //如果当前登录用户为店长
        if (MerchantUser.UserType.MANAGER.getCode().equals(userQuery.getUserType())) {
            storeId = userQuery.getStoreId();
        }
        if (MerchantUser.UserType.EMPLOYEES.getCode().equals(userQuery.getUserType())) {
            id = userId;
        }
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<UserVo> page = merchantUserRepository.findByMerchantId(userQuery.getMerchantId(), storeId, entity.getName(), entity.getPhone(), id, pageable);
        for (UserVo user : page.getContent()) {
            Store store = storeService.findOne(user.getStoreId());
            user.setStoreName(store.getName());
        }
        return page;
    }


    public String updateUser(MerchantUser entity) {

        MerchantUser user = merchantUserRepository.findOne(entity.getId());
        if (user == null) {
            throw new BaseException("查无此用户", Resp.Status.PARAM_ERROR.getCode());
        }

        //获取Hutool拷贝实例
        CopyOptions copyOptions = CopyOptions.create();
        //忽略为null值得属性
        copyOptions.setIgnoreNullValue(true);
        //进行属性拷贝
        BeanUtil.copyProperties(entity, user, copyOptions);
        Set<String> ignoreProperties = new HashSet<>();
        ignoreProperties.add("username");
        ignoreProperties.add("password");
        BindingResult bindingResult = Validation.valid(user, ignoreProperties);
        if (!bindingResult.isFlag()) {
            throw new BaseException(bindingResult.getMessage().get(0), Resp.Status.PARAM_ERROR.getCode());
        }
        // 先删除原来的所有权限
        merchantUserRoleService.deleteByUserId(user.getId());
        if (!merchantRoleService.countRoleId(String.valueOf(entity.getUserType()))) {
            throw new BaseException("角色信息有误", Resp.Status.PARAM_ERROR.getCode());
        }
        MerchantUserRole ur = new MerchantUserRole();
        ur.setRoleId(String.valueOf(entity.getUserType()));
        ur.setUserId(entity.getId());
        merchantUserRoleService.save(ur);
        return "修改成功";
    }


    /**
     * @author Created by wtl on 2019/3/14 15:54
     * @Description 添加员工
     */
    public String add(MerchantUser entity, String userId) {

        MerchantUser user = merchantUserRepository.findByUsernameAndDelFlag(entity.getUsername(), CommonConstant.NORMAL_FLAG);
        if (!ParamUtil.isBlank(user)) {
            throw new BaseException("用户名已存在", Resp.Status.PARAM_ERROR.getCode());
        }
        //查询当前员工
        MerchantUser currentUser = merchantUserRepository.findOne(userId);
        entity.setMerchantId(currentUser.getMerchantId());
        entity.setPassword(BCrypt.hashpw(entity.getPassword()));
        //若为店长
        if (MerchantUser.UserType.MANAGER.getCode().equals(currentUser.getUserType())) {
            entity.setStoreId(currentUser.getStoreId());
            entity.setUserType(MerchantUser.UserType.EMPLOYEES.getCode());
            merchantUserRepository.save(entity);
            if (!merchantRoleService.countRoleId("3")) {
                throw new RuntimeException("角色信息有误");
            }
            MerchantUserRole ur = new MerchantUserRole();
            ur.setRoleId("3");  //roleId=1为商户,roleId=2为店长,3为店员
            ur.setUserId(entity.getId());
            merchantUserRoleService.save(ur);
            return "添加成功";
        }
        //若为商户
        if (ParamUtil.isBlank(entity.getStoreId())) {
            throw new BaseException("请选择门店", Resp.Status.PARAM_ERROR.getCode());
        }
        if (ParamUtil.isBlank(entity.getUserType())) {
            throw new BaseException("请选择角色", Resp.Status.PARAM_ERROR.getCode());
        }
        merchantUserRepository.save(entity);
        if (!merchantRoleService.countRoleId(String.valueOf(entity.getUserType()))) {
            throw new RuntimeException("角色信息有误");
        }
        MerchantUserRole ur = new MerchantUserRole();
        ur.setRoleId(String.valueOf(entity.getUserType()));  //roleId=1为商户,roleId=2为店长,3为店员
        ur.setUserId(entity.getId());
        merchantUserRoleService.save(ur);
        return "添加成功";
    }

    /*
     * @author drj
     * @date 2019-05-27 16:37
     * @Description :获取用户详情
     */
    public MerchantUser detail(String id) {
        if (ParamUtil.isBlank(id)) {
            throw new BaseException("参数错误", Resp.Status.PARAM_ERROR.getCode());
        }
        MerchantUser user = merchantUserRepository.findOne(id);
        Store store = storeService.findOne(user.getStoreId());
        user.setStoreName(store.getName());
        return user;
    }


    /*
     * @author drj
     * @date 2019-05-09 16:29
     * @Description :根据用户id查询对应的用户列表)(不包含商户)
     */
    public List<SelectItem> selectItem(String userId) {
        List<SelectItem> selectItems = null;
        MerchantUser user = merchantUserRepository.findOne(userId);
        //若当前登录用户为店长
        if (MerchantUser.UserType.MANAGER.getCode().equals(user.getUserType())) {
            selectItems = merchantUserRepository.selectItemByStoreId(user.getStoreId());
        }
        //如果当前登录为商户
        if (MerchantUser.UserType.MERCHANT.getCode().equals(user.getUserType())) {
            selectItems = merchantUserRepository.selectItemByMchId(user.getMerchantId());
        }
        return selectItems;
    }


    /**
     * @author Created by wtl on 2019/3/13 17:05
     * @Description 商户下拉框
     */
    public List<SelectItem> selectItems() {
        return merchantUserRepository.selectItem();
    }

    /**
     * @author Created by wtl on 2019/4/12 15:08
     * @Description 修改密码
     */
    public String modifyPassword(String id, String password, String newPassword) {
        if (ParamUtil.isBlank(password)) {
            throw new BaseException("请填写旧密码", Resp.Status.PARAM_ERROR.getCode());
        }
        if (ParamUtil.isBlank(newPassword)) {
            throw new BaseException("请填写新密码", Resp.Status.PARAM_ERROR.getCode());
        }
        MerchantUser user = merchantUserRepository.findOne(id);
        if (user == null) {
            throw new BaseException("查无此用户", Resp.Status.PARAM_ERROR.getCode());
        }
        if (user.getDelFlag().equals(CommonConstant.DEL_FLAG)) {
            throw new BaseException("该用户已被禁用", Resp.Status.PARAM_ERROR.getCode());
        }
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new BaseException("密码错误", Resp.Status.PARAM_ERROR.getCode());
        }
        if (!newPassword.matches("^[a-zA-Z0-9_-]{6,20}$")) {
            throw new BaseException("密码应为英文、数字、下划线组成的6-20位字符", Resp.Status.PARAM_ERROR.getCode());
        }
        user.setPassword(BCrypt.hashpw(newPassword));
        merchantUserRepository.save(user);
        return "修改成功";
    }


    /**
     * @author Created by wtl on 2019/4/30 11:44
     * @Description 根据用户id获取员工列表
     */
    public List<MerchantUserSelect> findMerchantUser(String userId) {
        MerchantUser user = findOne(userId);
        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                // 用户类型为商户
                if (MerchantUser.UserType.MERCHANT.getCode().equals(user.getUserType())) {
                    predicates.add(cb.equal(root.get("merchantId"), user.getMerchantId()));
                }
                // 用户类型为店长1
                if (MerchantUser.UserType.MANAGER.getCode().equals(user.getUserType())) {
                    predicates.add(cb.equal(root.get("storeId"), user.getStoreId()));
                }
                // 用户类型为员工
                if (MerchantUser.UserType.EMPLOYEES.getCode().equals(user.getUserType())) {
                    predicates.add(cb.equal(root.get("userId"), user.getId()));
                }
                predicates.add(cb.equal(root.get("status"), User.Status.ENABLE.getCode()));
                predicates.add(cb.equal(root.get("delFlag"), CommonConstant.NORMAL_FLAG));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        List<MerchantUser> users = findAll(specification);
        List<MerchantUserSelect> merchantUsers = users.stream()
                .map(e -> new MerchantUserSelect(e.getId(), e.getUsername()))
                .collect(Collectors.toList());
        return merchantUsers;
    }

    //统计用户数量
    public Integer countByMerchantId(String id){
        return merchantUserRepository.countByMerchantId(id);
    }

    public List<UserVo> listRewriteExcel(MerchantUser entity, PageVo pageVo, String userId, String[] ids) {

        List<UserVo> list = null;

        if( ids == null || ids.length <= 0 ){
            MerchantUser userQuery = merchantUserRepository.findOne(userId);
            String storeId = "";
            String id = "";
            //如果当前登录用户为店长
            if (MerchantUser.UserType.MANAGER.getCode().equals(userQuery.getUserType())) {
                storeId = userQuery.getStoreId();
            }
            if (MerchantUser.UserType.EMPLOYEES.getCode().equals(userQuery.getUserType())) {
                id = userId;
            }
            Pageable pageable = PageUtil.initPage(pageVo);
            Page<UserVo> page = merchantUserRepository.findByMerchantId(userQuery.getMerchantId(), storeId, entity.getName(), entity.getPhone(), id, pageable);
            for (UserVo user : page.getContent()) {
                Store store = storeService.findOne(user.getStoreId());
                user.setStoreName(store.getName());
            }
            //获取分页里面的集合数据
            list = page.getContent().stream().collect(Collectors.toList());
        }else {
            List<String> asList = Arrays.asList(ids);
            list = merchantUserRepository.findByIdsInOrderByCreateTime(asList);
            for (UserVo user:list) {
                Store store = storeService.findOne(user.getStoreId());
                user.setStoreName(store.getName());
            }
        }
        return list;
    }
}