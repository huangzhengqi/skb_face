package com.fzy.admin.fp.advertise.device.repository;

import com.fzy.admin.fp.advertise.device.domian.AdvertiseDevice;
import com.fzy.admin.fp.advertise.device.vo.AdvertiseDevicePageVO;
import com.fzy.admin.fp.advertise.device.vo.AdvertiseDeviceViewListVO;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface AdvertiseDeviceRepository extends BaseRepository<AdvertiseDevice> {

    /**
     * 曝光/点击列表
     *
     * @param id
     * @param status
     * @param pageable
     * @return
     */
    @Query("select new com.fzy.admin.fp.advertise.device.vo.AdvertiseDeviceViewListVO(c.name,a.createTime) from AdvertiseDeviceViewLog a, " +
            "AdvertiseDevice b,Merchant c  where b.id=?1 and a.advertiseDeviceId = b.id and a.status=?2 and a.merchantId = c.id order by b.createTime desc")
    Page<AdvertiseDeviceViewListVO> viewList(@Param("id") String id, @Param("status") Integer status, Pageable pageable);

    /**
     * 曝光/点击列表
     *
     * @param id
     * @param beginTime
     * @param endTime
     * @param status
     * @param pageable
     * @return
     */
    @Query("select new com.fzy.admin.fp.advertise.device.vo.AdvertiseDeviceViewListVO(c.name,a.createTime) from AdvertiseDeviceViewLog a, " +
            "AdvertiseDevice b,Merchant c where b.id=?1 and a.advertiseDeviceId = b.id and a.createTime >=?2 and a.createTime<=?3 and a.status=?4 and a.merchantId = c.id order by b.createTime desc" )
    Page<AdvertiseDeviceViewListVO> viewList(@Param("id") String id, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("status") Integer status, Pageable pageable);

    AdvertiseDevice findByIdAndStatus(String id, Integer status);

    /**
     * 删除广告
     * @param ids 广告ID
     * @return
     */
    @Transactional
    @Modifying
    @Query("UPDATE AdvertiseDevice m set m.delFlag='0' WHERE  m.id in (?1)")
    int updateDelFlag(List<String> ids);
}
