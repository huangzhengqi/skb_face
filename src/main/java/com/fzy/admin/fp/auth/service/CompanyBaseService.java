package com.fzy.admin.fp.auth.service;

import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 16:03 2019/7/1
 * @ Description:
 **/

public interface CompanyBaseService<E extends CompanyBaseEntity> extends BaseService {

    default List<E> findByServiceProviderId(String serviceProviderId) {
        return findByServiceProviderId(serviceProviderId, null);
    }

    default List<E> findByServiceProviderId(String serviceProviderId, Integer delFlag) {
        final List<E> list = getRepository().findAll();
        return list.parallelStream()
                .filter(e -> e.getServiceProviderId().equals(serviceProviderId) && ParamUtil.isBlank(delFlag) || e.getDelFlag().equals(delFlag))
                .collect(toList());
    }
}
