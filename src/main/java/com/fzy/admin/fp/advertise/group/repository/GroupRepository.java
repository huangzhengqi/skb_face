package com.fzy.admin.fp.advertise.group.repository;

import com.fzy.admin.fp.advertise.group.domain.Group;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupRepository extends BaseRepository<Group> {

    //    Page<Group> findAllByServiceProviderIdAndCompanyIdAndGroupNameLikeOrderByCreateTimeDesc(String serviceProviderId, String companyId,String groupName, Pageable pageable);
    Page<Group> findAllByServiceProviderIdAndDelFlagAndGroupNameLikeOrderByCreateTimeDesc(String serviceProviderId, Integer delFlag, String groupName, Pageable pageable);

    List<Group> findAllByServiceProviderIdAndCompanyIdAndDelFlagOrderByCreateTimeDesc(String serviceProviderId, String companyId, Integer delflag);

    int countAllByGroupNameAndDelFlag(String groupName, Integer delFlag);

    Page<Group> findAllByServiceProviderIdAndDelFlagOrderByCreateTimeDesc(String serviceId, Integer delFlag, Pageable pageable);

    /**
     * 删除群组
     *
     * @param ids 群组ID
     * @return
     */
    @Transactional
    @Modifying
    @Query("UPDATE Group m set m.delFlag='0' WHERE  m.id in (?1)")
    int updateDelFlag(List<String> ids);
}
