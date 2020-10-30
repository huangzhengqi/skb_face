package com.fzy.admin.fp.advertise.group.repository;

import com.fzy.admin.fp.advertise.group.domain.GroupMerchantCompany;
import com.fzy.admin.fp.common.spring.base.BaseRepository;

import java.util.List;

public interface GroupMerchantRepository extends BaseRepository<GroupMerchantCompany> {

    List<GroupMerchantCompany> findAllByGroupId(String groupId);
}
