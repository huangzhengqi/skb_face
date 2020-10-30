package com.fzy.admin.fp.distribution.guide.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.feedback.domain.Feedback;
import com.fzy.admin.fp.distribution.feedback.repository.FeedbackRepository;
import com.fzy.admin.fp.distribution.guide.domain.Guide;
import com.fzy.admin.fp.distribution.guide.repository.GuideRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2020-1-11 13:48:48
 * @Desp
 **/
@Service
public class GuideService implements BaseService<Guide> {

    @Resource
    private GuideRepository guideRepository;


    public GuideRepository getRepository() {
        return guideRepository;
    }
}
