package com.fzy.admin.fp.advertise.device.repository;

import com.fzy.admin.fp.advertise.device.domian.AdvertiseDeviceHome;
import com.fzy.admin.fp.advertise.device.vo.AdvertiseDeviceVO;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface AdvertiseDeviceHomeRepository  extends BaseRepository<AdvertiseDeviceHome> {

    List<AdvertiseDeviceHome> findAllByMerchantId(String merchantId);

    @Query(value = "select new com.fzy.admin.fp.advertise.device.vo.AdvertiseDeviceVO(a.id,a.name,a.companyId) from AdvertiseDeviceHome a,com.fzy.admin.fp.advertise.device.domian.AdvertiseDevice h where a.advertiseDeviceId=h.id and a.merchantId=:merchantId AND h.beginTime>=:beginTime and h.endTime<=:endTime")
    List<AdvertiseDeviceVO> getAdvertiseDeviceHome(@Param("merchantId") String merchantId,@Param("beginTime") Date beginTime, @Param("endTime")Date endTime);


    @Modifying
    @Transactional
    @Query(value="delete from AdvertiseDeviceHome where advertiseDeviceId  = (:id) ")
    int deleteByIds(@Param("id")String id);
}
