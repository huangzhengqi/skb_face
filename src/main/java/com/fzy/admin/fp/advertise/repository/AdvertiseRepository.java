package com.fzy.admin.fp.advertise.repository;

import com.fzy.admin.fp.advertise.domain.Advertise;
import com.fzy.admin.fp.advertise.vo.AdvertisePageVO;
import com.fzy.admin.fp.advertise.vo.AdvertiseViewListVO;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * @author lb
 * @date 2019/7/1 16:07
 * @Description
 */
public interface AdvertiseRepository extends BaseRepository<Advertise> {


    /**
     * 点击/曝光分页记录
     *
     * @param beginTime
     * @param endTime
     * @param status
     * @param pageable
     * @return
     */
    @Query("select new com.fzy.admin.fp.advertise.vo.AdvertiseViewListVO(c.name,a.createTime) from AdvertiseViewLog a, " +
            "Advertise b,Merchant c where b.id=?1 and a.advertiseId = b.id and a.createTime >=?2 and a.createTime<=?3 and a.status=?4 and a.merchantId = c.id order by b.createTime desc")
    Page<AdvertiseViewListVO> viewList(@Param("id") String id,@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("status") Integer status, Pageable pageable);

    /**
     * 点击/曝光分页记录
     *
     * @param status
     * @param pageable
     * @return
     */
    @Query("select new com.fzy.admin.fp.advertise.vo.AdvertiseViewListVO(c.name,a.createTime) from AdvertiseViewLog a, " +
            "Advertise b,Merchant c  where b.id=?1 and a.advertiseId = b.id and a.status=?2 and a.merchantId = c.id order by b.createTime desc")
    Page<AdvertiseViewListVO> viewList(@Param("id") String id, @Param("status") Integer status, Pageable pageable);


    /**
     * 广告列表
     * @param title
     * @param serviceId
     * @param status
     * @param pageable
     * @return
     */
    @Query("select new com.fzy.admin.fp.advertise.vo.AdvertisePageVO(a.id,a.title,a.targetType,a.targetRange,a.status,a.beginTime,a.endTime,a.imageUrl,a.selectSt,a.type,a.deviceType) " +
            "from Advertise a where a.status=:status and a.title like concat('%',:title,'%') and a.serviceProviderId=:serviceId order by a.createTime desc")
    Page<AdvertisePageVO> getPage(@Param("title") String title, @Param("serviceId") String serviceId,@Param("status") Integer status, Pageable pageable);

    /**
     * 广告列表
     * @param title
     * @param serviceId
     * @param pageable
     * @return
     */
    @Query("select new com.fzy.admin.fp.advertise.vo.AdvertisePageVO(a.id,a.title,a.targetType,a.targetRange,a.status,a.beginTime,a.endTime,a.imageUrl,a.selectSt,a.type,a.deviceType) " +
            "from Advertise a where a.title like concat('%',:title,'%') and a.serviceProviderId=:serviceId order by a.createTime desc")
    Page<AdvertisePageVO> getPage(@Param("title") String title,@Param("serviceId") String serviceId, Pageable pageable);

    /**
     * 广告列表
     * @param ids
     * @param date
     * @param date2
     * @param status
     * @param targetRange
     * @param serviceId
     * @return
     */
    List<Advertise> findTop4ByIdInAndBeginTimeLessThanAndEndTimeGreaterThanAndStatusAndTargetRangeInAndServiceProviderIdOrderByBeginTimeDesc(List<String> ids,Date date,Date date2,Integer status,List<Integer> targetRange,String serviceId);

    List<Advertise> findTop4ByIdInAndBeginTimeLessThanAndEndTimeGreaterThanAndStatusAndTypeAndServiceProviderIdOrderByBeginTimeDesc(List<String> paramList, Date paramDate1, Date paramDate2, Integer paramInteger1, Integer paramInteger2, String paramString);

    List<Advertise> findTop4ByIdInAndStatusAndTypeAndServiceProviderIdOrderByBeginTimeDesc(List<String> paramList, Integer paramInteger1, Integer paramInteger2, String paramString);

    List<Advertise> findTop4ByBeginTimeLessThanAndEndTimeGreaterThanAndStatusAndTypeAndServiceProviderIdAndTargetRangeOrderByBeginTimeDesc(Date paramDate1, Date paramDate2, Integer paramInteger1, Integer paramInteger2, String paramString, Integer paramInteger3);

    List<Advertise> findTop4ByBeginTimeLessThanAndEndTimeGreaterThanAndStatusAndTypeAndTargetRangeOrderByBeginTimeDesc(Date paramDate1, Date paramDate2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);

    List<Advertise> findTop4ByBeginTimeLessThanAndEndTimeGreaterThanAndStatusAndTypeAndTargetRangeAndServiceProviderIdAndDeviceTypeInOrderByBeginTimeDesc(Date paramDate1, Date paramDate2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString,List<Integer> deviceType);

    List<Advertise> findTop4ByBeginTimeLessThanAndEndTimeGreaterThanAndStatusAndTargetRangeAndServiceProviderIdOrderByBeginTimeDesc(Date paramDate1, Date paramDate2, Integer paramInteger1, Integer paramInteger3, String paramString);


    List<Advertise> findTop4ByStatusAndTypeAndTargetRangeInAndServiceProviderIdOrderByBeginTimeDesc(Integer paramInteger1, Integer paramInteger2, List<Integer> paramList, String paramString);
}
