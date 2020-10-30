package com.fzy.admin.fp.common.repository;

import com.fzy.admin.fp.common.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 9:14 2019/4/22
 * @ Description:
 **/

public interface ProvinceRepository extends JpaRepository<Province, String>, JpaSpecificationExecutor {

    List<Province> findAllByOrderByProvinceCodeAsc();
}
