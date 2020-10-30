package com.fzy.admin.fp.common.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.List;

/**
 * @author: huxiangqiang
 * @since: 2019/7/18
 */
@Data
public class ProvinceTree {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "名字")
    private String name;
    @ApiModelProperty(value = "下级列表")
    private List<ProvinceTree> children;
}
