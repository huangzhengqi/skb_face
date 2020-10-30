package com.fzy.admin.fp.distribution.commission.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.distribution.commission.domain.CommissionTotal;
import com.fzy.admin.fp.distribution.commission.repository.CommissionTotalRepository;
import com.fzy.admin.fp.distribution.feedback.domain.Feedback;
import com.fzy.admin.fp.distribution.feedback.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author yy
 * @Date 2019-12-26 16:14:57
 * @Desp
 **/
@Service
public class CommissionTotalService implements BaseService<CommissionTotal> {

    @Resource
    private CommissionTotalRepository commissionTotalRepository;

    @Override
    public CommissionTotalRepository getRepository() {
        return commissionTotalRepository;
    }
}
