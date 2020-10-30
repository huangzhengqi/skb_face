package com.fzy.admin.fp.push.domain;

import com.fzy.admin.fp.auth.domain.CompanyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author hzq
 * @Date 2020/10/8 9:58
 * @Version 1.0
 * @description 推送表
 */
@Data
@Entity
@Table(name = "lysj_push")
public class Push extends CompanyBaseEntity {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String titleText;

    @ApiModelProperty("任务id")
    private String taskId;

    @ApiModelProperty("推送app 1刷客宝 2刷客宝代理版 3刷客宝分销版")
    private Integer pushType;

    @ApiModelProperty("平台 1:android 2:ios 3全选 ")
    private Integer platForm;

}
