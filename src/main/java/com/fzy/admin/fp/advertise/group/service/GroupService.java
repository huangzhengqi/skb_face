package com.fzy.admin.fp.advertise.group.service;

import com.fzy.admin.fp.advertise.group.domain.Group;
import com.fzy.admin.fp.advertise.group.repository.GroupRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GroupService implements BaseService<Group> {

    @Resource
    private GroupRepository groupRepository;

    @Override
    public GroupRepository getRepository() {
        return groupRepository;
    }

}
