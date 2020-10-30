package com.fzy.admin.fp.common.repository;

import com.fzy.admin.fp.common.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 9:14 2019/4/22
 * @ Description:
 **/

public interface CityRepository extends JpaRepository<City, String>, JpaSpecificationExecutor {


    /**
     * 获取市列表
     * @param provinceId
     * @return
     */
    List<City> findAllByProvinceId(String provinceId);

    /**
     * 通过id获取集合
     * @param cityIds
     * @return
     */
    List<String> findAllByIdIn(List<String> cityIds);

    List<City> findByIdIn(List<String> cityIds);
}
