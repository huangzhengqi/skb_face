package com.fzy.admin.fp.merchant.management.controller;

import cn.hutool.crypto.digest.BCrypt;
import com.fzy.admin.fp.common.annotation.SystemLog;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.utils.EasyPoiUtil;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.management.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zk
 * @description 用户表控制层
 * @create 2018-07-25 15:02:19
 **/
@Slf4j
@RestController
@RequestMapping("/merchant/user")
public class MerchantUserController extends BaseController<MerchantUser> {

    @Resource
    private MerchantUserService merchantUserService;

    @Override
    public MerchantUserService getService() {
        return merchantUserService;
    }


    /*
     * @author drj
     * @date 2019-04-30 15:09
     * @Description :获取用户列表
     */
    @GetMapping("/list_rewrite")
    public Resp listRewrite(MerchantUser entity, PageVo pageVo, @UserId String userId) {

        return Resp.success(merchantUserService.listRewrite(entity, pageVo, userId));
    }

    @GetMapping("/list_rewrite_excel")
    public Resp listRewriteExcel(MerchantUser entity, PageVo pageVo, @UserId String userId, String[] ids){
        List<UserVo> list = merchantUserService.listRewriteExcel(entity, pageVo, userId,ids);

        EasyPoiUtil.exportExcel(list,"商户列表","商户",UserVo.class,"商户列表.xls",response);
        return Resp.success("导出成功");
    }

    /*
     * @author drj
     * @date 2019-05-07 16:35
     * @Description :app端查询用户列表
     */


    /**
     * @author Created by wtl on 2019/3/14 15:56
     * @Description 添加员工
     */
    @PostMapping("/save_user")
    @SystemLog(description = "店长添加员工")
    public Resp add(@Valid MerchantUser entity, @UserId String userId) {
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        entity.setServiceProviderId(serviceId);
        return Resp.success(merchantUserService.add(entity, userId));
    }

    /**
     * @author zk
     * @date 2018-08-13 21:08
     * @Description 修改用户
     */
    @PostMapping("/update_user")
    @SystemLog(description = "修改用户")
    public Resp update(MerchantUser entity) {
        return Resp.success(merchantUserService.updateUser(entity));
    }

    /**
     * @author Created by wtl on 2019/3/13 16:56
     * @Description 商户下拉框，添加店长时需要
     */
    @Override
    public Resp selectItem() {
        return Resp.success(merchantUserService.selectItems());
    }

    /**
     * @author zk
     * @date 2018-08-13 20:41
     * @Description 修改密码
     */
    @PostMapping("/modify_password")
    @SystemLog(description = "修改密码")
    public Resp modifyPassword(@UserId String id, String password, String newPassword) {
        return Resp.success(merchantUserService.modifyPassword(id, password, newPassword));
    }

    /**
     * @author zk
     * @date 2018-08-13 21:00
     * @Description 禁用用户
     */
    @PostMapping("/disable")
    public Resp disable(String id) {
        MerchantUser user = merchantUserService.findOne(id);
        if (user == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "查无此用户");
        }
        if (user.getDelFlag().equals(CommonConstant.DEL_FLAG)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该用户已被禁用");
        }
        user.setDelFlag(CommonConstant.DEL_FLAG);
        merchantUserService.save(user);
        return Resp.success("禁用成功");
    }

    /**
     * @author zk
     * @date 2018-08-13 21:00
     * @Description 启用用户
     */
    @PostMapping("/enable")
    public Resp enable(String id) {
        MerchantUser user = merchantUserService.findOne(id);
        if (user == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "查无此用户");
        }
        if (user.getDelFlag().equals(CommonConstant.NORMAL_FLAG)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该用户已启用");
        }
        user.setDelFlag(CommonConstant.NORMAL_FLAG);
        merchantUserService.save(user);
        return Resp.success("启用成功");
    }

    @PostMapping("/reset_password")
    @SystemLog(description = "重置密码")
    public Resp resetPassword(String id, String password) {
        MerchantUser user = merchantUserService.findOne(id);
        if (user == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "查无此用户");
        }
        user.setPassword(BCrypt.hashpw(password));
        merchantUserService.save(user);
        return Resp.success("密码重置成功");
    }

    /*
     * @author drj
     * @date 2019-05-27 16:31
     * @Description :获取员工详情
     */
    @GetMapping("/detail")
    public Resp detail(String id) {
        return Resp.success(merchantUserService.detail(id));
    }

    /*
     * @author drj
     * @date 2019-05-09 16:29
     * @Description :根据用户id查询对应的用户列表)(不包含商户)
     */
    @GetMapping("/select_item/by_user_id")
    public Resp selectItem(@UserId String userId) {
        return Resp.success(merchantUserService.selectItem(userId));
    }

    /*
     * @author drj
     * @date 2019-05-24 16:23
     * @Description :根据门店id查询用户列表
     */
    @GetMapping("/select_item/by_store_id")
    public Resp selectItemByStoreId(String storeId) {
        return Resp.success(merchantUserService.getRepository().selectItemByStoreId(storeId));
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
