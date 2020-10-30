package com.fzy.admin.fp.auth.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.auth.domain.WxOpenConfig;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

/**
 * @author zk
 * @description 微信开发平台服务商配置表数据处理层
 * @create 2018-07-25 15:02:19
 **/
public interface WxOpenConfigRepository extends BaseRepository<WxOpenConfig> {

    WxOpenConfig findByServiceProviderId(String id);

    WxOpenConfig findByFilename(String filename);
}