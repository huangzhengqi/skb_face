package com.fzy.admin.fp.common.domain;


import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.auth.domain.Permission;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * @author zk
 * @description 角色表
 * @create 2018-07-25 15:10:47
 **/
@Data
@Entity
@Table(name = "city_info")
public class City {


    @Id
    @Column(length = 128)
    @GeneratedValue(generator = "snowFlakeIdStrategy")
    @GenericGenerator(name = "snowFlakeIdStrategy", strategy = CommonConstant.SNOW_FLAKE_ID_STRATEGY)
    private String id;//唯一标识

    private String cityCode;

    private String cityName;

    private String provinceId;


}