package com.fzy.admin.fp.auth.repository;


import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.web.SelectItem;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.web.SelectItem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author zk
 * @description 用户表数据处理层
 * @create 2018-07-25 15:02:19
 **/
public interface UserRepository extends BaseRepository<User> {

    User findByUsernameAndDelFlag(String username, Integer delFlag);

    User findByPhoneAndDelFlag(String phone, Integer delFlag);

    User findByUsernameAndServiceProviderIdAndDelFlag(String username, String serviceProviderId, Integer delFlag);

  //  DistUser findByPhoneAndServiceProviderIdAndDelFlag(String phone, String serviceProviderId, Integer delFlag);

    //根据公司id查询业务员列表
    List<User> findByCompanyId(String companyId);


    @Query("select new com.fzy.admin.fp.common.web.SelectItem(u.id,u.name) from User u where u.companyId=?1 AND u.delFlag=1")
    List<SelectItem> findByCompanyIdSelectItem(String companyId);


}