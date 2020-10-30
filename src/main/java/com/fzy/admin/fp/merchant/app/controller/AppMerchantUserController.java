package com.fzy.admin.fp.merchant.app.controller;

import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.management.vo.UserVo;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.service.StoreService;
import com.fzy.admin.fp.common.annotation.SystemLog;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author ：drj.
 * @Date  ：Created in 22:10 2019/5/26
 * @Description:商户APP员工接口
 **/
@RestController
@RequestMapping("/merchant/user/app")
public class AppMerchantUserController extends BaseContent {


    @Resource
    private StoreService storeService;

    @Resource
    private MerchantUserService merchantUserService;

    /**
     * @author drj
     * @date 2019-05-27 16:23
     * @Description :获取用户列表
     */
    @GetMapping("/list")
    public Resp listApp(String userType, String status, String name, PageVo pageVo, @UserId String userId, @TokenInfo(property = "merchantId") String merchantId, String storeId) {
        MerchantUser userQuery = merchantUserService.findOne(userId);
        if (ParamUtil.isBlank(storeId)) {
            //如果当前登录用户为店长
            if (MerchantUser.UserType.MANAGER.getCode().equals(userQuery.getUserType())) {
                storeId = userQuery.getStoreId();
            }
        }
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<UserVo> page = merchantUserService.getRepository().findByMerchantIdApp(userQuery.getMerchantId(), storeId, userType, status, name, pageable);
        for (UserVo user : page.getContent()) {
            Store store = storeService.findOne(user.getStoreId());
            user.setStoreName(store.getName());
        }
        return Resp.success(page);
    }

    /**
     * @author drj
     * @date 2019-05-27 16:29
     * @Description :添加员工
     */
    @PostMapping("/save")
    @SystemLog(description = "添加员工")
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
    @PostMapping("/update")
    @SystemLog(description = "修改员工")
    public Resp update(MerchantUser entity) {
        return Resp.success(merchantUserService.updateUser(entity));
    }


    /**
     * @author drj
     * @date 2019-05-27 16:31
     * @Description :获取员工详情
     */
    @GetMapping("/detail")
    public Resp detail(String id) {
        return Resp.success(merchantUserService.detail(id));
    }
}
