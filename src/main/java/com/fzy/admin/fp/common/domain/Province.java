package com.fzy.admin.fp.common.domain;


import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.constant.CommonConstant;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author zk
 * @description 角色表
 * @create 2018-07-25 15:10:47
 **/
@Data
@Entity
@Table(name = "province_info")
public class Province {


    @Id
    @Column(length = 128)
    @GeneratedValue(generator = "snowFlakeIdStrategy")
    @GenericGenerator(name = "snowFlakeIdStrategy", strategy = CommonConstant.SNOW_FLAKE_ID_STRATEGY)
    private String id;

    private String provinceCode;

    private String provinceName;


}