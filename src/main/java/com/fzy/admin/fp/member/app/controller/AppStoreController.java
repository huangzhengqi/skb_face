package com.fzy.admin.fp.member.app.controller;


import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.app.dto.AppStoreDTO;
import com.fzy.admin.fp.member.app.service.AppStoreService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Created by wtl on 2019-06-05 11:12
 * @description app会员储值接口
 */
@RestController
@RequestMapping("/member/store/app")
@Component("MemberAppStoreController")
public class AppStoreController extends BaseContent {

    @Resource
    private AppStoreService appStoreService;

    /**
     * @param appStoreDTO APP会员储值参数
     * @param userId      收银员id ，从token中解析
     * @author Created by wtl on 2019/6/5 10:14+
     * @Description App会员储值，扫码枪
     * 充值流程：
     * 1.生成订单并调用扫码支付接口付款
     * 2.生成会员储值记录
     * 3.判断储值额外赠送（积分、会员卡金额、卡券）并添加到对应的表
     * 4.积分有变动的话，生成积分明细记录
     */
    @PostMapping("/store_money")
    public Resp storeMoney(@UserId String userId, @Valid AppStoreDTO appStoreDTO) {
        return Resp.success(appStoreService.storeMoney(userId, appStoreDTO), "储值成功");
    }

}
