package com.fzy.admin.fp.order.order.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.commission.vo.CommissionTotalVO;
import com.fzy.admin.fp.order.order.domain.CommissionDay;
import com.fzy.admin.fp.order.order.vo.CommissionDayVO;
import com.fzy.admin.fp.order.order.vo.SummaryVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Created by wtl on 2019-04-22 19:09:33
 * @description 订单dao
 */
public interface CommissionDayRepository extends BaseRepository<CommissionDay> {
    List<CommissionDay> findAllByCompanyIdAndCreateTimeAfter(String companyId, Date date);

    List<CommissionDay> findAllByCompanyIdIn(List<String> companyIds);

    List<CommissionDay> findAllByCreateTime(Date time);
    List<CommissionDay> findAllByCreateTimeAndCompanyIdIn(Date time,List<String> companyIds);

    Page<CommissionDay> findAllByCompanyIdInAndCreateTimeBetweenAndCompanyNameLike(Pageable pageable, List<String> companyIds, Date begin, Date end, String companyName);

    Page<CommissionDay> findAllByCompanyIdInAndCreateTimeBetweenAndCompanyNameLikeAndStatus(Pageable pageable, List<String> companyIds, Date begin, Date end, String companyName, Integer status);

    List<CommissionDay> findByIdInOrderByCreateTimeDesc(List<String> idList);

    @Query("select new com.fzy.admin.fp.order.order.vo.CommissionDayVO(c.createTime,d.name,d.userName,c.orderTotal,c.commissionTotal) " +
            "from com.fzy.admin.fp.order.order.domain.CommissionDay c,com.fzy.admin.fp.distribution.app.domain.DistUser d where c.companyId=d.id and c.updateTime=:createTime")
    Page<CommissionDayVO> getPage(@Param("createTime") Date startTime, Pageable pageable);

    /**
     * 通过时间查询
     * @param createTime
     * @return
     */
    @Query("select new com.fzy.admin.fp.distribution.commission.vo.CommissionTotalVO(orderTotal,sum(commissionTotal)) from com.fzy.admin.fp.order.order.domain.CommissionDay where updateTime=:createTime and type=1 GROUP BY orderTotal")
    CommissionTotalVO getCommissionTotal(@Param("createTime") Date createTime);

    @Query("select new com.fzy.admin.fp.distribution.commission.vo.CommissionTotalVO(sum(orderTotal),sum(commissionTotal)) from com.fzy.admin.fp.order.order.domain.CommissionDay where companyId=:companyId and type=1")
    CommissionTotalVO getCommissionTotalByCompanyId(@Param("companyId") String userId);

    Page<CommissionDay> findAllByCreateTimeBetweenAndTypeAndCompanyId(Pageable pageable,Date begin, Date end,Integer type,String userId);

    Page<CommissionDay> findAllByTypeAndCompanyId(Pageable pageable,Integer type,String userId);

    List<CommissionDay> findAllByCreateTimeBetweenAndType(Date begin, Date end,Integer type);

    List<CommissionDay> findAllByCompanyIdAndCreateTimeBetweenAndType(String companyIds, Date begin, Date end,Integer type);

    List<CommissionDay> findAllByCompanyIdAndType(String companyIds,Integer type);

    CommissionDay findAllByCompanyIdAndCreateTimeAndType(String companyIds, Date begin,Integer type);
}
