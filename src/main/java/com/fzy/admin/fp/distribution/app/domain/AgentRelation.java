package com.fzy.admin.fp.distribution.app.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author yy
 * @Date 2019-12-25 17:18:15
 * @Desp
 **/

@Entity
@Data
@Table(name = "lysj_dist_agent_relation")
public class AgentRelation extends BaseEntity {
    private String userId;

    @Column(columnDefinition = "int(1)")
    @ApiModelProperty("1合伙人 2团长")
    private Integer type;

    private String childId;
}
