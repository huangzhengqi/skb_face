package com.fzy.admin.fp.advertise.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huxiangqiang
 * @date 2019/7/15 19:51
 * @Description 添加广告 入参
 */
@Data
public class AdvertiseIsableDTO {


    @ApiModelProperty(value = "广告id")
    private String[] ids;

    @ApiModelProperty(value = "状态 1恢复 5暂停")
    private Integer status;

}
