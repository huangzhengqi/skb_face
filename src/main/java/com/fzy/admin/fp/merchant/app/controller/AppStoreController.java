package com.fzy.admin.fp.merchant.app.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.Validation;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.validation.domain.BindingResult;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.merchant.merchant.service.StoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author ：drj.
 * @Date ：Created in 22:09 2019/5/26
 * @Description: 商户APP门店接口
 **/
@RestController
@RequestMapping("/merchant/store/app")
public class AppStoreController extends BaseContent {


    @Resource
    private StoreService storeService;

    @Resource
    private MerchantUserService merchantUserService;


    /**
     * @author drj
     * @date 2019-04-26 10:00
     * @Description :获取门店列表
     */
    @GetMapping("/list")
    public Resp list(Store entity, PageVo pageVo, @UserId String userId) {
        return Resp.success(storeService.listRewrite(entity, pageVo, userId));
    }

    /**
     * @author drj
     * @date 2019-05-27 16:08
     * @Description :添加门店
     */
    @PostMapping("/save")
    public Resp save(@Valid Store entity, @UserId String userId) {

        return Resp.success(storeService.saveRewrite(entity, userId));
    }

    /**
     * @author drj
     * @date 2019-05-27 16:08
     * @Description :修改门店
     */
    @PostMapping("/update")
    public Resp update(@Valid Store entity) {

        Store store = storeService.findOne(entity.getId());
        if (store == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        //获取Hutool拷贝实例
        CopyOptions copyOptions = CopyOptions.create();
        //忽略为null值得属性
        copyOptions.setIgnoreNullValue(true);
        //进行属性拷贝
        BeanUtil.copyProperties(entity, store, copyOptions);
        //对实体类中的@validation注解进行校验
        BindingResult bindingResult = Validation.valid(store);
        if (!bindingResult.isFlag()) {
            throw new BaseException(bindingResult.getMessage().get(0), Resp.Status.PARAM_ERROR.getCode());
        }
        storeService.save(store);
        return Resp.success("修改成功");
    }

    /**
     * @author drj
     * @date 2019-04-26 10:12
     * @Description :查看门店详情
     */
    @GetMapping("/find_one")
    public Resp<Store> findOne(String id) {
        return Resp.success(storeService.findOne(id));
    }


    /**
     * @author drj
     * @date 2019-04-26 15:07
     * @Description :获取商户对应的门店下拉框
     */
    @GetMapping("/find_by_merchant_id")
    public Resp findByMerchantId(@UserId String userId, String name) {
        MerchantUser user = merchantUserService.findOne(userId);
        if (user.getUserType().equals(MerchantUser.UserType.MERCHANT.getCode())) {
            return Resp.success(storeService.getRepository().selectItemByMchId(user.getMerchantId(), name));
        } else if (user.getUserType().equals(MerchantUser.UserType.MANAGER.getCode())) {
            return Resp.success(storeService.getRepository().selectItemByStoreId(user.getStoreId(), name));
        }
        return new Resp().error(Resp.Status.NO_ACCESS, "暂无权限");
    }
}
