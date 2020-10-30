package com.fzy.admin.fp.merchant.merchant.repository;

import com.fzy.admin.fp.merchant.merchant.domain.FeiyuConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 10:33 2019/4/30
 * @ Description:
 **/

public interface FeiYuConfigRepository extends JpaRepository<FeiyuConfig, String> {
    List<FeiyuConfig> findAllByStoreId(String id);

    List<FeiyuConfig> findAllByStoreIdAndStatus(String id,Integer status);

    FeiyuConfig findByIdNotAndDeviceId(String id,String deviceId);
    FeiyuConfig findByDeviceId(String deviceId);

    FeiyuConfig findByDeviceIdAndStatus(String deviceId,Integer status);
}


