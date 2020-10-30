package com.fzy.admin.fp.member.rule.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.member.app.vo.AppStoreRecordVO;
import com.fzy.admin.fp.member.rule.domain.StoredRecored;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 16:41 2019/5/14
 * @ Description:
 **/

public interface StoredRecoredRepository extends BaseRepository<StoredRecored> {

    //根据会员id查询对应的储值记录列表
    Page<StoredRecored> findByMemberIdAndTradeTypeIn(String memberId, Integer[] tradeTypes, Pageable pageable);

    //根据卡券Id查询对应的消费记录
    List<StoredRecored> findByCouponId(String couponId);

    //查找会员消费记录条数
    Integer countByMemberId(String memberId);

    //根据时间区间查找指定会员消费记录
    @Query(nativeQuery = true, value = "SELECT a.`id` FROM lysj_member_stored_recored a WHERE a.`member_id`=?1 AND a.`status`=2 AND a.`update_time` BETWEEN ''+?2+'' AND ''+?3+'';")
    List<StoredRecored> getBytimes(String memberId, String date, String date2);

    //用于会员活跃分析
    List<StoredRecored> findByMerchantIdAndCreateTimeBetweenAndMemberIdInAndTradeType(String merchantId, Date begin, Date end,
                                                                                      List<String> memberIds, Integer tradeType);

    //根据订单号查询消费记录(支付回调使用)
    StoredRecored findByOrderNumber(String orderNumber);


    //商户APP查询会员消费记录
    @Query(value = "SELECT NEW com.fzy.admin.fp.member.app.vo.AppStoreRecordVO(s.id,s.orderNumber,s.tradingMoney,s.createTime,s.payStatus,s.payWay) FROM StoredRecored s WHERE s.merchantId LIKE CONCAT('%',:merchantId,'%') and s.storeId LIKE CONCAT('%',:storeId,'%') AND s.payStatus LIKE CONCAT('%',:payStatus,'%') AND s.payWay LIKE CONCAT('%',:payWay,'%') AND s.memberId LIKE CONCAT('%',:memberId,'%') AND s.createTime>=:startTime AND s.createTime<=:endTime ORDER BY s.createTime DESC")
    Page<AppStoreRecordVO> findByAppStoreRecordDto(Pageable pageable, @Param("merchantId") String merchantId, @Param("storeId") String storeId,
                                                   @Param("payStatus") String payStatus, @Param("payWay") String payWay, @Param("memberId") String memberId, @Param("startTime") Date startTime,
                                                   @Param("endTime") Date endTime);

}
