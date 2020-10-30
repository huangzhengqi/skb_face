package com.fzy.admin.fp.common.repository;

import com.fzy.admin.fp.common.domain.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 9:14 2019/4/22
 * @ Description:
 **/

public interface SystemConfigRepository extends JpaRepository<SystemConfig, String>, JpaSpecificationExecutor {

    SystemConfig findByConfigKey(String key);

    List<SystemConfig> findAllByType(String type);

    List<SystemConfig> findAllByCompanyId(String companyId);

    SystemConfig findByConfigKeyAndCompanyId(String key, String companyId);

}
