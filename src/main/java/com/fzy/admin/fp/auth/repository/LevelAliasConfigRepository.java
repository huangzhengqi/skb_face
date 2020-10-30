package com.fzy.admin.fp.auth.repository;


import com.fzy.admin.fp.auth.domain.LevelAliasConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zk
 * @description 角色表数据处理层
 * @create 2018-07-25 15:10:47
 **/
public interface LevelAliasConfigRepository extends JpaRepository<LevelAliasConfig, String> {
    LevelAliasConfig findByIdAndStatus(String id,Integer status);
}