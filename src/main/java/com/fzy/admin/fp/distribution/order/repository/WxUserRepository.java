package com.fzy.admin.fp.distribution.order.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.distribution.pc.domain.WxUser;

import java.util.List;

public interface WxUserRepository  extends BaseRepository<WxUser> {

    WxUser findAllByOpenid(String Openid);

    List<WxUser> findAllByServiceProviderId(String serviceProviderId);
}
