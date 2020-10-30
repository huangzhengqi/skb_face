package com.fzy.admin.fp.distribution.feedback.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.feedback.domain.Feedback;
import com.fzy.admin.fp.distribution.feedback.vo.FeedbackVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface FeedbackRepository extends BaseRepository<Feedback> {


    Integer countByUserIdAndStatusAndType(String userId,Integer status,Integer type);

    Feedback findByServiceProviderIdAndId(String serviceProviderId,String id);

    @Query("select new com.fzy.admin.fp.distribution.feedback.vo.FeedbackVO(f.id,f.createTime,d.name,d.userName,f.content,f.reply,f.img) " +
            "from com.fzy.admin.fp.distribution.feedback.domain.Feedback f,com.fzy.admin.fp.distribution.app.domain.DistUser d where f.userId=d.id and f.serviceProviderId=:serviceProviderId and f.status  LIKE concat('%',:status,'%')")
    Page<FeedbackVO> getPage(@Param("serviceProviderId")String serviceProviderId,@Param("status") String status, Pageable pageable);

    @Query("select new com.fzy.admin.fp.distribution.feedback.vo.FeedbackVO(f.id,f.createTime,d.name,d.userName,f.content,f.reply,f.img) " +
            "from com.fzy.admin.fp.distribution.feedback.domain.Feedback f,com.fzy.admin.fp.distribution.app.domain.DistUser d where f.userId=d.id and f.serviceProviderId=:serviceProviderId and f.status  LIKE concat('%',:status,'%') and f.createTime between :startTime and :endTime")
    Page<FeedbackVO> getPage(@Param("serviceProviderId")String serviceProviderId, @Param("status") String status, @Param("startTime") Date startTime, @Param("endTime") Date endTime, Pageable pageable);

    Page<Feedback> findAllByUserId(String userId, Pageable pageable);


}
