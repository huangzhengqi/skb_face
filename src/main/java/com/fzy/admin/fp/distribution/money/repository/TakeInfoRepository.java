package com.fzy.admin.fp.distribution.money.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.money.domain.TakeInfo;
import com.fzy.admin.fp.distribution.money.vo.TakeInfoDetailVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;

public interface TakeInfoRepository extends BaseRepository<TakeInfo> {

   @Query("select new com.fzy.admin.fp.distribution.money.vo.TakeInfoDetailVO(dt.id,dt.remark,dt.updateTime,dw.balance,dt.createTime,du.name,du.userName,du.grade,dt.sum,dt.takeType,dt.name ,dt.account,dt.status) from TakeInfo dt , com.fzy.admin.fp.distribution.app.domain.DistUser du,com.fzy.admin.fp.distribution.money.domain.Wallet dw where dt.userId=du.id and dw.userId=du.id and dt.serviceProviderId=:serviceProviderId and (du.name like concat('%',:param,'%') or du.userName like concat('%',:param,'%')) and dt.status like concat('%',:status,'%')")
   Page<TakeInfoDetailVO> getDetails(@Param("serviceProviderId") String serviceProviderId, @Param("param") String param,@Param("status")String status, Pageable paramPageable);

   @Query("select new com.fzy.admin.fp.distribution.money.vo.TakeInfoDetailVO(dt.id,dt.remark,dt.updateTime,dw.balance,dt.createTime,du.name,du.userName,du.grade,dt.sum,dt.takeType,dt.name ,dt.account,dt.status) from TakeInfo dt , com.fzy.admin.fp.distribution.app.domain.DistUser du,com.fzy.admin.fp.distribution.money.domain.Wallet dw where dt.userId=du.id and dw.userId=du.id and dt.serviceProviderId=:serviceProviderId and (du.name like concat('%',:param,'%') or du.userName like concat('%',:param,'%')) and dt.createTime between :begin and :endTime and dt.status like concat('%',:status,'%')")
   Page<TakeInfoDetailVO> getDetails(@Param("serviceProviderId") String serviceProviderId, @Param("begin") Date paramDate1, @Param("endTime") Date paramDate2, @Param(("status"))String status,@Param("param") String param,  Pageable paramPageable);

   TakeInfo findByServiceProviderIdAndId(String serviceProviderId,String id);

   @Query("select new com.fzy.admin.fp.distribution.money.vo.TakeInfoDetailVO(sum(dt.sum)) from TakeInfo dt,com.fzy.admin.fp.distribution.app.domain.DistUser du,com.fzy.admin.fp.distribution.money.domain.Wallet dw where dt.userId=du.id and dw.userId=du.id and dt.serviceProviderId=:serviceProviderId and (du.name like concat('%',:param,'%') or du.userName like concat('%',:param,'%')) and dt.createTime between :begin and :endTime and dt.status like concat('%',:status,'%')")
   TakeInfoDetailVO getSum(@Param("serviceProviderId") String serviceProviderId, @Param("begin") Date paramDate1, @Param("endTime") Date paramDate2, @Param(("status"))String status,@Param("param") String param);

   @Query("select new com.fzy.admin.fp.distribution.money.vo.TakeInfoDetailVO(sum(dt.sum)) from TakeInfo dt,com.fzy.admin.fp.distribution.app.domain.DistUser du,com.fzy.admin.fp.distribution.money.domain.Wallet dw where dt.userId=du.id and dw.userId=du.id and dt.serviceProviderId=:serviceProviderId and (du.name like concat('%',:param,'%') or du.userName like concat('%',:param,'%')) and dt.status like concat('%',:status,'%')")
   TakeInfoDetailVO getSum(@Param("serviceProviderId") String serviceProviderId, @Param("param") String param,@Param("status")String status);
}
