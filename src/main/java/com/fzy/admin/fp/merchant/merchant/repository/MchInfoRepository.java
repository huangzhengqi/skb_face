package com.fzy.admin.fp.merchant.merchant.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.vo.DisMchInfoVO;
import com.fzy.admin.fp.merchant.merchant.vo.MchInfoVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 10:33 2019/4/30
 * @ Description:
 **/

public interface MchInfoRepository extends BaseRepository<MchInfo> {



    Page<MchInfo> findByMerchantIdInAndStatus(String[] merchantIds,Integer status, Pageable pageable);
    Page<MchInfo> findByMerchantIdIn(String[] merchantIds, Pageable pageable);

    Page<MchInfo> findByServiceProviderIdAndOrganizationCodeNotNull(String serviceProviderId, Pageable pageable);

    MchInfo findByMerchantId(String merchantId);


    @Query("select new com.fzy.admin.fp.merchant.merchant.vo.MchInfoVO(a.id,m.name,a.phone,a.status,b.name,m.companyId) " +
            "from com.fzy.admin.fp.merchant.merchant.domain.Merchant m,MchInfo a,com.fzy.admin.fp.auth.domain.Company b " +
            "where m.id=a.merchantId and m.companyId=b.id and m.id in (:merchantIds) " +
            "and b.name like concat('%',:companyName,'%')and m.name like concat('%',:name,'%') and a.status=:status " +
            "order by m.createTime desc")
    Page<MchInfoVO> fuwushangPage(@Param("merchantIds") List<String> merchantIds, @Param("companyName") String companyName, @Param("name") String name, @Param("status") Integer status, Pageable pageable);

    @Query("select new com.fzy.admin.fp.merchant.merchant.vo.MchInfoVO(a.id,m.name,a.phone,a.status,b.name,m.companyId) " +
            "from com.fzy.admin.fp.merchant.merchant.domain.Merchant m,MchInfo a,com.fzy.admin.fp.auth.domain.Company b " +
            "where m.id=a.merchantId and m.companyId=b.id and m.id in (:merchantIds) " +
            "and b.name like concat('%',:companyName,'%')and m.name like concat('%',:name,'%') " +
            "order by m.createTime desc")
    Page<MchInfoVO> fuwushangPage(@Param("merchantIds") List<String> merchantIds, @Param("companyName") String companyName, @Param("name") String name, Pageable pageable);

    @Query("select new com.fzy.admin.fp.merchant.merchant.vo.MchInfoVO(a.id,m.name,a.phone,a.status, m.id) " +
            "from com.fzy.admin.fp.merchant.merchant.domain.Merchant m,MchInfo a " +
            "where m.id=a.merchantId and m.id in (:merchantIds) " +
            "order by m.createTime desc")
    Page<MchInfoVO> fuwushangPage(@Param("merchantIds") List<String> merchantIds,Pageable pageable);


    @Query("select new com.fzy.admin.fp.merchant.merchant.vo.MchInfoVO(a.id,m.name,a.phone,a.status, m.id) " +
            "from com.fzy.admin.fp.merchant.merchant.domain.Merchant m,MchInfo a " +
            "where m.id=a.merchantId and m.id in (:merchantIds) and a.status=:status" +
            " order by m.createTime desc")
    Page<MchInfoVO> fuwushangPage(@Param("merchantIds") List<String> merchantIds, @Param("status") Integer status, Pageable pageable);


    /**
     * 查分销商进件
     * @param serviceProviderId
     * @param status
     * @param pageable
     * @returnm
     */
    @Query("select new com.fzy.admin.fp.merchant.merchant.vo.DisMchInfoVO(a.id,m.name,m.phone,a.status,m.id,u.name,a.tqSxfSuccess,a.zfbSuccess,a.wxSuccess) " +
            "from com.fzy.admin.fp.merchant.merchant.domain.Merchant m,MchInfo a,com.fzy.admin.fp.distribution.app.domain.DistUser u" +
            " where m.id=a.merchantId  and m.managerId=u.id and m.type='1' and m.serviceProviderId =:serviceProviderId" +
            "  and a.status=:status " +
            "order by m.createTime desc")
    Page<DisMchInfoVO> fenxiaoshangPage(@Param("serviceProviderId") String serviceProviderId,  @Param("status") Integer status, Pageable pageable);
    /**
     * 查分销商进件
     * @param serviceProviderId
     * @param name
     * @param pageable
     * @return
     */
    @Query("select new com.fzy.admin.fp.merchant.merchant.vo.DisMchInfoVO(a.id,m.name,m.phone,a.status,m.id,u.name,a.tqSxfSuccess,a.zfbSuccess,a.wxSuccess) " +
            "from com.fzy.admin.fp.merchant.merchant.domain.Merchant m,MchInfo a,com.fzy.admin.fp.distribution.app.domain.DistUser  u " +
            " where m.id=a.merchantId  and m.managerId=u.id and m.type='1' and m.serviceProviderId=:serviceProviderId " +
            " and m.name like concat('%',:name,'%')  and u.name like concat('%',:managerName,'%')" +
            " order by m.createTime desc")
    Page<DisMchInfoVO> fenxiaoshangPage(@Param("serviceProviderId") String serviceProviderId,
                                        @Param("name") String name,   @Param("managerName") String managerName,Pageable pageable);
}


