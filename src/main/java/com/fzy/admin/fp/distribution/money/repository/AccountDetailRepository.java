package com.fzy.admin.fp.distribution.money.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.money.domain.AccountDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface AccountDetailRepository extends BaseRepository<AccountDetail> {
    @Query("FROM com.fzy.admin.fp.distribution.money.domain.AccountDetail a  WHERE a.userId =:userId and a.status LIKE concat('%',:status,'%') and createTime between :startTime and :endTime")
    Page<AccountDetail> findAllByUserIdAndCreateTimeBetweenAndStatusLike(@Param("userId")String userId,@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("status")String status,Pageable pageable);

    @Query("FROM com.fzy.admin.fp.distribution.money.domain.AccountDetail a  WHERE a.userId =:userId and a.status LIKE concat('%',:status,'%')")
    Page<AccountDetail> findAllByUserIdAndStatusLike(@Param("userId")String userId,@Param("status")String status, Pageable pageable);
}
