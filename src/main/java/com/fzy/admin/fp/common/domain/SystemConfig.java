package com.fzy.admin.fp.common.domain;


import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author zk
 * @description 配置表
 * @create 2018-07-25 15:10:47
 **/
@Data
@Entity
@Table(name = "system_config")
public class SystemConfig extends BaseEntity {

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", name = "type")
    private String type;
    /**
     * 键
     */
    @ApiModelProperty(value = "键", name = "config_key")
    private String configKey;
    /**
     * 值
     */
    @ApiModelProperty(value = "值", name = "config_value")
    @Column(columnDefinition = "text")
    private String configValue;
    /**
     * 分组数据
     */
    @ApiModelProperty(value = "分组数据", name = "config_group")
    private String configGroup;
    /**
     * 企业id
     */
    @ApiModelProperty(value = "企业id", name = "company_id")
    private String companyId;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", name = "remarks")
    private String remarks;


}