package com.fzy.admin.fp.push.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.push.domain.PushUser;

import java.util.List;

/**
 * @Author hzq
 * @Date 2020/10/7 18:25
 * @Version 1.0
 * @description
 */
public interface PushUserRepository extends BaseRepository<PushUser> {

    /**
     * 根据用户id和类型去查列表
     * @param userId
     * @param type
     * @return
     */
    List<PushUser> findAllByUserIdAndType(String userId ,Integer type);

    /**
     * 根据用户和推送id获取对象
     * @param userId
     * @param id
     * @return
     */
    PushUser findByUserIdAndPushId(String userId, String id);
}
