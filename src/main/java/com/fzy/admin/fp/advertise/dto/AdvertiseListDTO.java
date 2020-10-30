package com.fzy.admin.fp.advertise.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author huxiangqiang
 * @date 2019/7/15 19:51
 * @Description 添加广告 入参
 */
@Data
public class AdvertiseListDTO extends PageVo {

    @ApiModelProperty(value = "广告标题")
    private String title;

    @ApiModelProperty(value = "状态 1启用 0禁用")
    private Integer status;




}
