package com.fzy.admin.fp.merchant.pc.service;

import cn.hutool.crypto.digest.BCrypt;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.merchant.merchant.service.StoreService;
import com.fzy.admin.fp.merchant.pc.vo.AccountInfo;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.web.SelectItem;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.merchant.merchant.service.StoreService;
import com.fzy.admin.fp.merchant.pc.vo.AccountInfo;
import com.fzy.admin.fp.merchant.pc.vo.StoreSelect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by wtl on 2019-05-01 23:16
 * @description
 */
@Service
@Slf4j
public class PcMerchantService {

    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private MerchantService merchantService;

    @Resource
    private StoreService storeService;

    /**
     * @author Created by wtl on 2019/5/1 23:16
     * @Description 修改密码
     */
    public void editPassword(String id, String password, String newPassword) {
        if (ParamUtil.isBlank(password)) {
            throw new BaseException("请填写旧密码", Resp.Status.PARAM_ERROR.getCode());
        }
        if (ParamUtil.isBlank(newPassword)) {
            throw new BaseException("请填写新密码", Resp.Status.PARAM_ERROR.getCode());
        }
        MerchantUser user = merchantUserService.findOne(id);
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
        merchantUserService.save(user);
    }

    /**
     * @author Created by wtl on 2019/5/2 0:02
     * @Description 账户信息
     */
    public AccountInfo findAccountInfo(String userId) {
        log.info("账户信息接口，userId,{}", userId);
        // 用户信息
        MerchantUser user = merchantUserService.findOne(userId);
        // 用户对应的商户信息
        Merchant merchant = merchantService.findOne(user.getMerchantId());
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setMerchantName(merchant.getName());
        accountInfo.setUsername(user.getUsername());
        // 门店列表
        List<StoreSelect> storeSelects = new ArrayList<>();
        String storeId = "";
        String storeName = "";
        // 商户
        if (MerchantUser.UserType.MERCHANT.getCode().equals(user.getUserType())) {
            storeSelects.add(new StoreSelect(null, "全部门店"));
            accountInfo.setName(user.getName() + "(商户)");
            // 获取门店下拉列表
            List<SelectItem> selectItems = storeService.getRepository().selectItem(user.getMerchantId());
            for (SelectItem selectItem : selectItems) {
                StoreSelect storeSelect = new StoreSelect();
                storeSelect.setStoreId(selectItem.getValue() == null ? "" : selectItem.getValue());
                storeSelect.setStoreName(selectItem.getName() == null ? "" : selectItem.getName());
                storeSelects.add(storeSelect);
            }
        } else {
            Store store = storeService.getRepository().findOne(user.getStoreId());
            if (ParamUtil.isBlank(store)) {
                throw new BaseException("获取门店失败", Resp.Status.PARAM_ERROR.getCode());
            }
            storeId = store.getId();
            storeName = store.getName();
        }
        // 店长
        if (MerchantUser.UserType.MANAGER.getCode().equals(user.getUserType())) {
            accountInfo.setName(user.getName() + "(店长)");
        }
        // 员工
        if (MerchantUser.UserType.EMPLOYEES.getCode().equals(user.getUserType())) {
            accountInfo.setName(user.getName() + "(员工)");
        }
        accountInfo.setUserType(user.getUserType());
        accountInfo.setStoreId(storeId);
        accountInfo.setStoreName(storeName);
        accountInfo.setStoreSelectItem(storeSelects);
        accountInfo.setRebateType(merchant.getRebateType());
        accountInfo.setBind(merchant.getBind());
        return accountInfo;
    }


}
