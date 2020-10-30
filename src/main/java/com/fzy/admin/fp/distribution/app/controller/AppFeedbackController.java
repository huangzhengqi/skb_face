package com.fzy.admin.fp.distribution.app.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.feedback.domain.Feedback;
import com.fzy.admin.fp.distribution.feedback.dto.AppFeedbackDTO;
import com.fzy.admin.fp.distribution.feedback.service.FeedbackService;
import com.fzy.admin.fp.distribution.money.domain.Costs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-12-6 18:02:27
 * @Desp 反馈
 **/
@RestController
@RequestMapping("/dist/app/feedback")
@Api(value = "FeedbackController", tags = {"分销-app反馈"})
public class AppFeedbackController extends BaseContent {

    @Resource
    private FeedbackService feedbackService;

    @PostMapping("/insert")
    @ApiOperation(value = "添加反馈", notes = "添加反馈")
    public Resp insert(@TokenInfo(property="serviceProviderId")String serviceProviderId, @UserId String userId,@Valid AppFeedbackDTO appFeedbackDTO){
        Feedback feedback=new Feedback();
        feedback.setContent(appFeedbackDTO.getContent());
        feedback.setImg(appFeedbackDTO.getImg());
        feedback.setUserId(userId);
        feedback.setType(0);
        feedback.setStatus(0);
        feedback.setServiceProviderId(serviceProviderId);
        feedbackService.save(feedback);
        return Resp.success("添加成功");
    }

    @GetMapping("/query")
    @ApiOperation(value = "查询反馈", notes = "查询反馈")
    public Resp<Page<Feedback>> query(@UserId String userId, PageVo pageVo){
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<Feedback> all = feedbackService.getRepository().findAllByUserId(userId,pageable);
        for(Feedback data:all.getContent()){
            if(data.getStatus()==1&&data.getType()==0){
                data.setType(1);
                feedbackService.save(data);
            }
        }
        return Resp.success(all);
    }

}
