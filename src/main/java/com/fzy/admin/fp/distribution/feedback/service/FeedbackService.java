package com.fzy.admin.fp.distribution.feedback.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.feedback.domain.Feedback;
import com.fzy.admin.fp.distribution.feedback.repository.FeedbackRepository;
import com.fzy.admin.fp.distribution.money.domain.Costs;
import com.fzy.admin.fp.distribution.money.repository.AccountDetailRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-12-6 17:59:40
 * @Desp
 **/
@Service
public class FeedbackService implements BaseService<Feedback> {

    @Resource
    private FeedbackRepository feedbackRepository;


    public FeedbackRepository getRepository() {
        return feedbackRepository;
    }
}
