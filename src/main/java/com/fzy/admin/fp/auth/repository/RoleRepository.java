package com.fzy.admin.fp.auth.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.web.SelectItem;
import com.fzy.admin.fp.auth.domain.Role;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.web.SelectItem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author zk
 * @description 角色表数据处理层
 * @create 2018-07-25 15:10:47
 **/
public interface RoleRepository extends BaseRepository<Role> {
    int countByIdAndDelFlag(String id, Integer delFlag);

    List<Role> findByDefaultRoleAndDelFlag(boolean isDefault, Integer delFlag);

    List<Role> findBySourceRoleIdIn(List<String> sourceIds);


    @Query("select new com.fzy.admin.fp.common.web.SelectItem(r.id,r.name) from Role r where r.type=?1 AND r.kind=?2  AND r.delFlag=1 ")
    List<SelectItem> selectItemByTypeAndKind(Integer type, Integer kind);


    //通过类型、种类、等级查询对应角色
    Role findByTypeAndKindAndLevel(Integer type, Integer kind, Integer level);
    Role findByTypeAndKindAndLevelAndCompanyId(Integer type, Integer kind, Integer level,String companyId);

    Role findBySourceRoleId(String sourceRoleId);

    Role findByCompanyIdAndLevelAndTypeAndKindAndDelFlag(String companyId,Integer level,Integer type,Integer kind,Integer delFalg);

    List<Role> findByCompanyIdInAndSourceRoleIdAndAuthCompanyId(List<String> ids,String sourceRoleId,String authCompanyId);

    Role findBySourceRoleIdAndCompanyIdAndDelFlag(String sourceRoleId,String companyId,Integer flag);

}