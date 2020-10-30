package com.fzy.admin.fp.distribution.feedback.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.feedback.domain.Feedback;
import com.fzy.admin.fp.distribution.feedback.dto.FeedbackDTO;
import com.fzy.admin.fp.distribution.feedback.service.FeedbackService;
import com.fzy.admin.fp.distribution.feedback.vo.FeedbackVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-12-6 18:00:43
 * @Desp
 **/
@RestController
@RequestMapping("/dist/feedback")
@Api(value = "FeedbackController", tags = {"分销-反馈"})
public class FeedbackController extends BaseContent {

    @Resource
    private FeedbackService feedbackService;

    @GetMapping("/query")
    @ApiOperation(value = "查询反馈", notes = "查询反馈")
    public Resp query(@TokenInfo(property="serviceProviderId")String serviceProviderId, PageVo pageVo, FeedbackDTO feedbackDTO){
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<FeedbackVO> all =null;
        if(feedbackDTO.getStartTime()!=null&&feedbackDTO.getEndTime()!=null){
            all = feedbackService.getRepository().getPage(serviceProviderId,feedbackDTO.getStatus(),feedbackDTO.getStartTime(),feedbackDTO.getEndTime(), pageable);
        }else{
            all = feedbackService.getRepository().getPage(serviceProviderId,feedbackDTO.getStatus(), pageable);
        }
        return Resp.success(all);
    }

    @PostMapping("/reply")
    @ApiOperation(value = "回复反馈", notes = "回复反馈")
    public Resp reply(@TokenInfo(property="serviceProviderId")String serviceProviderId, @Valid FeedbackDTO feedbackDTO){
        Feedback feedback = feedbackService.getRepository().findByServiceProviderIdAndId(serviceProviderId, feedbackDTO.getId());
        if(feedback==null){
            return new Resp().error(Resp.Status.PARAM_ERROR,"参数错误");
        }
        feedback.setReply(feedbackDTO.getReply());
        feedback.setStatus(1);
        feedbackService.update(feedback);
        return Resp.success("回复成功");
    }

}
