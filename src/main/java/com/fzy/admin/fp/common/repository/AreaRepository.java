package com.fzy.admin.fp.common.repository;

import com.fzy.admin.fp.common.domain.Area;
import com.fzy.admin.fp.common.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 9:14 2019/4/22
 * @ Description:
 **/

public interface AreaRepository extends JpaRepository<Area, String>, JpaSpecificationExecutor {


    /**
     * 地区列表
     * @param cityId
     * @return
     */
    List<Area> findAllByCityId(String cityId);
}
