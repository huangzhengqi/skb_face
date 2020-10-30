package com.fzy.admin.fp.common.spring.base;

import com.fzy.admin.fp.distribution.pc.domain.AfterSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author zk
 * @description 基础Dao类
 * @create 2018-07-15 15:38
 **/
@NoRepositoryBean//告知JPA该接口无需实现
public interface BaseRepository<E extends BaseEntity> extends JpaRepository<E, String>, JpaSpecificationExecutor {
}
