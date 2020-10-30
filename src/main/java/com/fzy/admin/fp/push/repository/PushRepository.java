package com.fzy.admin.fp.push.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.push.domain.Push;

import java.util.List;

/**
 * @Author hzq
 * @Date 2020/10/8 10:00
 * @Version 1.0
 * @description
 */
public interface PushRepository extends BaseRepository<Push> {

    /**
     * 根据服务商获取推送列表
     * @param serviceProviderId
     * @return
     */
    List<Push> findByServiceProviderId(String serviceProviderId);
}
