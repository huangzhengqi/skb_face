package com.fzy.admin.fp.auth.repository;

import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.web.SelectItem;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.web.SelectItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 9:14 2019/4/22
 * @ Description:
 **/

public interface CompanyRepository extends BaseRepository<Company> {


    //查看二级代理商列表
    List<Company> findByParentIdAndDelFlag(String parentId, Integer delFlag);

    List<Company> findByParentId(String parentId);

    List<Company> findByParentIdAndType(String parentId,Integer type);

    List<Company> findByParentIdAndTypeLessThanEqual(String parentId,Integer type);


    //获取运营商或者渠道商列表
    @Query("select new com.fzy.admin.fp.common.web.SelectItem(c.id,c.name) from Company c where c.managerId=?1 AND c.type=?2 AND c.delFlag=1")
    List<SelectItem> findByManagerIdSelectItem(String managerId, Integer type);

    Integer countByManagerIdAndDelFlag(String managerId, Integer delFlag);

    List<Company> findByIdPathIsNullOrIdPath(String blankStr);

    List<Company> findByIdPathStartingWith(String serviceProviderId);
    List<Company> findByIdPathStartingWithAndNameLike(String serviceProviderId,String name);
    List<Company> findByIdPathStartingWithAndType(String serviceProviderId,Integer type);

    //服务商获取二级代理商列表
    Page<Company> findByIdPathStartsWith(String serviceProviderId, Pageable pageable);

    List<Company> findByType(Integer type);

    List<Company> findByTypeAndStatusAndDelFlag(Integer type,Integer status,Integer delFlag);

    Company findByPhoneAndDelFlag(String phone, Integer delFlag);

    //查询业务员关联的运营商获取渠道商
    List<Company> findByManagerIdAndDelFlag(String managerId, Integer delflag);

    /**
     * @param ids
     * @return
     */
    List<Company> findByParentIdIn(List<String> ids);

    List<Company> findByParentIdInAndStatus(List<String> ids,Integer status);

    Company findByIdAndType(String id,Integer type);

    List<Company> findByIdIn(List<String> paramList);

    @Query(value = "SELECT * from lysj_auth_company WHERE INSTR(id_path,?1)>0 ", nativeQuery = true)
    List<Company> findByCompanyIdPath(String paramString);
}
