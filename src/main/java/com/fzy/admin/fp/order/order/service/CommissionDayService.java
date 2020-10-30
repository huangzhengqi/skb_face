package com.fzy.admin.fp.order.order.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.order.order.repository.CommissionDayRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.order.order.domain.CommissionDay;
import com.fzy.admin.fp.order.order.repository.CommissionDayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * @author Created by wtl on 2019-04-22 19:09:33
 * @description 订单服务层
 */
@Slf4j
@Service
@Transactional
public class CommissionDayService implements BaseService<CommissionDay> {


    @Resource
    private CommissionDayRepository commissionDayRepository;


    @Override
    public CommissionDayRepository getRepository() {
        return commissionDayRepository;
    }


}