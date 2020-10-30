package com.fzy.admin.fp.distribution.app.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.app.vo.DistUserVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.annotation.Resource;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author yy
 * @Date 2019-11-15 14:39:34
 */
public interface DistUserRepository extends BaseRepository<DistUser> {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    DistUser findById(String userId);

    DistUser findByUserName(String username);

    @Query("select new com.fzy.admin.fp.distribution.app.vo.DistUserVO(du.name,du.headImg) from DistUser du where du.inviteNum=:inviteNum")
    DistUserVO findByInviteNum1(@Param("inviteNum") String inviteNum);

    DistUser findByInviteNum(String inviteNum);

    DistUser findByServiceProviderIdAndId(String serviceProviderId, String id);

    DistUser findByGradeAndId(Integer grade, String id);

    Integer countByParentIdLike(String inviteNum);

    Integer countByParentIdLikeAndGradeGreaterThan(String inviteNum, Integer grade);

    Integer countByInviteNumLikeAndServiceProviderId(String inviteNum, String serviceProviderIdAndId);

    Integer countByParentIdLikeAndServiceProviderId(String inviteNum, String serviceProviderId);

    DistUser findByInviteNumAndServiceProviderId(String inviteNum, String serviceProviderId);

    List<DistUser> findAllByParentIdLikeAndLevel(String inviteNum, Integer Level);

    List<DistUser> findAllByParentIdLike(String inviteNum);

    Integer countByOneLevelId(String userId);

    Integer countByOneLevelIdAndGrade(String userId, Integer grade);

    Integer countByOneLevelIdAndGradeGreaterThan(String userId, Integer grade);

    Integer countByTwoLevelId(String userId);

    //List<DistUser> findByParentIdLikeAndServiceProviderIdAndCreateTimeIsGreaterThanEqual(String inviteNum, String serviceProviderId);

    Integer countByParentIdLikeAndServiceProviderIdAndCreateTimeIsGreaterThanEqual(String inviteNum, String serviceProviderId, Date month);

    //每月新增团队人数
    Integer countByParentIdLikeAndServiceProviderIdAndCreateTimeIsGreaterThanEqualAndCreateTimeIsLessThan(String inviteNum, String serviceProviderId, Date month, Date lastMonth);

    Integer countByParentIdLikeAndServiceProviderIdAndCreateTimeGreaterThanEqualAndGradeGreaterThanEqual(String inviteNum, String serviceProviderId, Date month, Integer grade);

    //每月新增代理人数
    Integer countByParentIdLikeAndServiceProviderIdAndCreateTimeGreaterThanEqualAndCreateTimeLessThanAndGradeGreaterThanEqual(String inviteNum, String serviceProviderId, Date month, Date lastMonth, Integer grade);

    Integer countByParentIdLikeAndGradeGreaterThanAndServiceProviderId(String parentId, Integer grade, String serviceProviderId);

    List<DistUser> findAllByParentIdLikeAndServiceProviderId(String inviteNum, String serviceProviderId);

    List<DistUser> findAllByServiceProviderIdAndPayTimeBetweenAndGradeGreaterThanAndGradeLessThan(String serviceProviderId, Date begin, Date end, Integer greater, Integer less);

    Integer countByServiceProviderIdAndCreateTimeBetween(String serviceProviderId, Date begin, Date end);

    Integer countByServiceProviderIdAndCreateTimeBetweenAndGradeGreaterThanAndGradeLessThan(String serviceProviderId, Date begin, Date end, Integer greater, Integer less);

    Integer countByServiceProviderIdAndGradeGreaterThanAndGradeLessThan(String serviceProviderId, Integer greater, Integer less);

    Page<DistUser> findAllByParentIdLikeAndServiceProviderId(String inviteNum, String serviceProviderId, Pageable paramPageable);

    Page<DistUser> findAllByParentIdLikeAndServiceProviderIdAndCreateTimeBetween(String inviteNum, String serviceProviderId, Date begin, Date end, Pageable paramPageable);

    List<DistUser> findAllByParentIdLikeAndServiceProviderIdAndCreateTimeBetween(String inviteNum, String serviceProviderId, Date begin, Date end);

    List<DistUser> findAllByParentIdLikeAndServiceProviderIdAndPayTimeBetweenOrderByCreateTimeAsc(String inviteNum, String serviceProviderId, Date begin, Date end);

    @Query("select new com.fzy.admin.fp.distribution.app.vo.DistUserVO(du.status,du.id,du.name,du.userName,du.city,du.updateTime,du.grade,dw.bonus,dw.balance,dw.take, du.activate, du.buyNum ,du.teamActivate) from DistUser du,com.fzy.admin.fp.distribution.money.domain.Wallet dw where du.serviceProviderId=:serviceProviderId and (du.id=dw.userId and (du.name like concat('%',:param,'%') or du.userName like concat('%',:param,'%') ) ) and du.grade like concat('%',:grade,'%') and du.status like concat('%',:status,'%') and du.grade < 4")
    Page<DistUserVO> getDetailsList(@Param("serviceProviderId") String serviceProviderId, @Param("param") String param, @Param("status") String status, @Param("grade") String grade, Pageable paramPageable);

    @Query("select new com.fzy.admin.fp.distribution.app.vo.DistUserVO(du.status,du.id,du.name,du.userName,du.city,du.updateTime,du.grade,dw.bonus,dw.balance,dw.take, du.activate, du.buyNum,du.teamActivate) from DistUser du,com.fzy.admin.fp.distribution.money.domain.Wallet dw where du.serviceProviderId=:serviceProviderId and du.id=dw.userId and (du.name like concat('%',:param,'%') or du.userName like concat('%',:param,'%') ) and du.grade like concat('%',:grade,'%') and du.status like concat('%',:status,'%') and du.grade < 4 and du.createTime between :begin and :endTime")
    Page<DistUserVO> getDetailsList(@Param("serviceProviderId") String serviceProviderId, @Param("begin") Date paramDate1, @Param("endTime") Date paramDate2, @Param("param") String param, @Param("status") String status, @Param("grade") String grade, Pageable paramPageable);

    @Query("select new com.fzy.admin.fp.distribution.app.vo.DistUserVO(du.id,du.wxNum,du.aliNum,du.bankNum,du.bankName,du.aliName,du.inviteNum,du.sex,du.parentId,du.name,du.userName,du.city,du.updateTime,du.grade,du.headImg,du.createTime, dw.bonus, dw.balance, dw.take, du.activate, du.buyNum, du.teamActivate) from DistUser du,com.fzy.admin.fp.distribution.money.domain.Wallet dw where du.serviceProviderId=:serviceProviderId and du.id=dw.userId and dw.userId=:userId")
    DistUserVO getDetails(@Param("serviceProviderId") String serviceProviderId, @Param("userId") String userId);

    @Query("update DistUser set status= :status,activate = :activate,teamActivate = :teamActivate where id= :id and teamActivate = :oldTeamActivate")
    @Modifying
    int updateActivate(@Param("status") Integer status, @Param("activate") Integer activate, @Param("teamActivate") Integer teamActivate, @Param("id") String id, @Param("oldTeamActivate") Integer oldTeamActivate);

    List<DistUser> findAllByOneLevelId(String userId);

    List<DistUser> findAllByTwoLevelId(String userId);

    List<DistUser> findAllByThreeLevelId(String userId);

    List<DistUser> findAllByZeroLevelId(String userId);

    Integer countByCreateTimeBetweenAndServiceProviderId(Date begin, Date end, String serviceProviderId);

    Integer countByBecomeTimeBetweenAndGradeGreaterThanAndServiceProviderId(Date begin, Date end, Integer grade, String serviceProviderId);

    Integer countByGradeGreaterThanAndBecomeTimeLessThanEqualAndServiceProviderId(Integer grade, Date end, String serviceProviderId);

    List<DistUser> findAllByCreateTimeBetweenAndServiceProviderId(Date begin, Date end, String serviceProviderId);

    List<DistUser> findAllByBecomeTimeBetweenAndGradeGreaterThanAndServiceProviderId(Date begin, Date end, Integer grade, String serviceProviderId);

    Page<DistUser> findAllByServiceProviderIdAndGrade(String serviceProviderId, Integer grade, Pageable paramPageable);

    @Query(nativeQuery = true, value = "select * from lysj_dist_user where service_provider_id=:serviceProviderId ORDER BY activate desc LIMIT 20")
    List<DistUser> rankList(@Param("serviceProviderId") String serviceProviderId);

}
