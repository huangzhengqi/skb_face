package com.fzy.admin.fp.push.controller;

import com.fzy.admin.fp.auth.service.UserService;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.push.domain.Push;
import com.fzy.admin.fp.push.service.PushServiceImpl;
import com.fzy.admin.fp.push.service.PushUserServiceImpl;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author hzq
 * @Date 2020/9/30 9:36
 * @Version 1.0
 * @description 个推推送控制层
 */
@RestController
@Slf4j
@RequestMapping("/push/push")
@Api(value = "个推推送控制层", tags = {"个推推送控制层"})
public class PushController extends BaseContent {
    /**
     * 商户app 的appId，appKey，masterSecret
     */
//    private final String appId = "ox2YXTifv06NkP5fM6JpW";
//    private final String appKey = "rkdjx2rrvm9RQCCpcdQhz3";
//    private final String masterSecret = "PKAqpgf4Pl7PWTSISqNU07";

    /**
     * 校刷宝
     */
    private final String appId = "7z1ls8ZkZ09V1P41BRB5N3";
    private final String appKey = "tPe1q425i99gUHmfaG3W52";
    private final String masterSecret = "l8FjISLdiw6oKZe7GmqDC2";
    @Resource
    private PushServiceImpl pushServiceImpl;

    /**
     * 对指定应用的所有（或符合筛选条件的）用户群发推送消息。有定时、定速功能。
     */
    @ApiOperation(value = "群发推送", notes = "群发推送")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "title", dataType = "String", value = "标题"),
            @ApiImplicitParam(paramType = "query", name = "titleText", dataType = "String", value = "内容"),
            @ApiImplicitParam(paramType = "query", name = "pushType", dataType = "Integer", value = "推送app 1刷客宝 2刷客宝代理版 3刷客宝分销版"),
            @ApiImplicitParam(paramType = "query", name = "platForm", dataType = "Integer", value = "平台 1:android 2:ios 3全选")})
    @PostMapping("/message/toapp")
    public Resp pushMessageToApp(@TokenInfo(property = "serviceProviderId") String serviceProviderId, @UserId String userId, String title, String titleText, Integer pushType, Integer platForm) {
        if (ParamUtil.isBlank(serviceProviderId)) {
            throw new BaseException("serviceProviderId不能为空,toekn有误", Resp.Status.TOKEN_ERROR.getCode());
        }
        if (ParamUtil.isBlank(userId)) {
            throw new BaseException("userId,toekn有误", Resp.Status.TOKEN_ERROR.getCode());
        }
        Map<String, String> msg = new HashMap<>();
        msg.put("title", title);
        msg.put("titleText", titleText);
        //穿透内容
        msg.put("transText", "");
        return Resp.success(pushServiceImpl.pushMessageToApp(serviceProviderId,userId, msg, pushType, platForm), "推送成功");
    }

    @ApiOperation(value = "推送列表", notes = "推送列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "title", dataType = "String", value = "标题")})
    @ApiResponse(code = 200, message = "OK", response = Push.class)
    @GetMapping("/list")
    public Resp list(@TokenInfo(property = "serviceProviderId") String serviceProviderId,@UserId String userId, String title, PageVo pageVo) {
        if (ParamUtil.isBlank(serviceProviderId)) {
            throw new BaseException("serviceProviderId不能为空,toekn有误", Resp.Status.TOKEN_ERROR.getCode());
        }
        if (ParamUtil.isBlank(userId)) {
            throw new BaseException("userId,toekn有误", Resp.Status.TOKEN_ERROR.getCode());
        }
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification<Push> specification = new Specification<Push>() {
            @Override
            public Predicate toPredicate(Root<Push> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("serviceProviderId"), serviceProviderId));
                predicates.add(cb.equal(root.get("userId"), userId));
                if (StringUtils.isNotBlank(title)) {
                    predicates.add(cb.like(root.get("title"), "%" + title + "%"));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                Predicate Pre_And = cb.and(predicates.toArray(pre));
                return query.where(Pre_And).getRestriction();
            }
        };
        return Resp.success(pushServiceImpl.findAll(specification, pageable));
    }

}
