package com.fzy.admin.fp.advertise.device.repository;

import com.fzy.admin.fp.advertise.device.domian.AdvertiseDeviceMid;
import com.fzy.admin.fp.advertise.device.vo.AdvertiseDeviceVO;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface AdvertiseDeviceMidRepository extends BaseRepository<AdvertiseDeviceMid> {

    List<AdvertiseDeviceMid> findAllByMerchantId(String merchantId);

//    @Query(value="SELECT h.id AS id ,h.name AS name, h.company_id AS companyId, h.merchant_id AS merchantId, a.begin_time AS  beginTime, a.end_time AS endTime FROM lysj_advertise_device_mid h LEFT JOIN lysj_advertise_device a ON h.advertise_device_id = a.id WHERE 1=1 AND h.merchant_id = ?1 AND a.begin_time BETWEEN ?2 AND ?3", nativeQuery=true)
//    List<AdvertiseDeviceVO> getAdvertiseDeviceMid(String merchantId, Date beginTime, Date endTime);

    @Query(value = "select new com.fzy.admin.fp.advertise.device.vo.AdvertiseDeviceVO(a.id,a.name,a.companyId) from AdvertiseDeviceMid a,com.fzy.admin.fp.advertise.device.domian.AdvertiseDevice h where a.advertiseDeviceId=h.id and a.merchantId=:merchantId AND h.beginTime>=:beginTime and h.endTime<=:endTime")
    List<AdvertiseDeviceVO> getAdvertiseDeviceMid(@Param("merchantId") String merchantId,@Param("beginTime") Date beginTime, @Param("endTime")Date endTime);

    @Modifying
    @Transactional
    @Query(value="delete from AdvertiseDeviceMid where advertiseDeviceId  = (:id) ")
    int deleteByIds(@Param("id")String id);
}
