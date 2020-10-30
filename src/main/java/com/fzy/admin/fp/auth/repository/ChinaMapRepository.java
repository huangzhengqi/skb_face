package com.fzy.admin.fp.auth.repository;

import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChinaMapRepository extends BaseRepository<Company> {
    @Query(value = "select pro.province_name as name,COALESCE(a.countP,0) as value from (select count(1) countP,province p_id from lysj_auth_company where id_path like concat ('%',:cid,'%') group by province) a right join province_info pro on pro.id = a.p_id ORDER BY VALUE DESC",nativeQuery = true)
    List<Object[]> getChinaMapAgent(@Param("cid") String companyId);

    /**
     * 查询代理商下的商户
     * @param companyId
     * @return
     */
    @Query(value = "SELECT pro.province_name as name,COALESCE(a.countM,0) as value from (select COUNT(1) countM,province p_id FROM lysj_merchant_merchant WHERE company_id = (:cid) OR company_id IN (SELECT id FROM lysj_auth_company WHERE id_path like concat ('%',:cid,'%')) GROUP BY province) a right JOIN province_info pro ON pro.id = a.p_id ORDER BY VALUE DESC",nativeQuery = true)
    List<Object[]> getChinaCompanyMapMerchant(@Param("cid") String companyId);

    /**
     * 查询服务商下的商户
     * @param companyId
     * @return
     */
    @Query(value = "SELECT pro.province_name as name,COALESCE(a.countM,0) as value from (select COUNT(1) countM,province p_id FROM lysj_merchant_merchant WHERE service_provider_id = (:cid)  GROUP BY province) a right JOIN province_info pro ON pro.id = a.p_id ORDER BY VALUE DESC",nativeQuery = true)
    List<Object[]> getChinaProvidersMapMerchant(@Param("cid") String companyId);

    /**
     * 查询总管理员下的所有商户
     * @return
     */
    @Query(value = "SELECT pro.province_name as name,COALESCE(a.countM,0) as value from (select COUNT(1) countM,province p_id FROM lysj_merchant_merchant GROUP BY province) a right JOIN province_info pro ON pro.id = a.p_id ORDER BY VALUE DESC",nativeQuery = true)
    List<Object[]> getChinaAdminMapMerchant();
}