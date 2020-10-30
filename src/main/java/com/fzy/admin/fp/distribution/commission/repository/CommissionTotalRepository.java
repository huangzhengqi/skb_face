package com.fzy.admin.fp.distribution.commission.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.commission.domain.CommissionTotal;
import com.fzy.admin.fp.distribution.commission.vo.CommissionTotalVO;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author yy
 * @Date 2019-12-26 16:11:40
 * @Desp
 **/
public interface CommissionTotalRepository extends BaseRepository<CommissionTotal> {

    @Query("select new com.fzy.admin.fp.distribution.commission.vo.CommissionTotalVO(sum(orderTotal),sum(commissionTotal)) from CommissionTotal ")
    CommissionTotalVO getCommissionTotal();
}
