package com.fzy.admin.fp.advertise.repository;

import com.fzy.admin.fp.advertise.domain.CostMoney;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

import java.util.Date;
import java.util.List;

/**
 * @author lb
 * @date 2019/7/4 9:56
 * @Description
 */
public interface CostMoneyRepository extends BaseRepository<CostMoney> {

    List<CostMoney> findByOnIdAndCreateTimeBetween(String onId, Date time1, Date time2);

    CostMoney findByOnIdAndDay(String onId, String day);

}
