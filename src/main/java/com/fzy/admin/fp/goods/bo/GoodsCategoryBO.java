package com.fzy.admin.fp.goods.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GoodsCategoryBO {
    private String id;

    @ApiModelProperty("商户名称")
    private String cagegoryName;

    @ApiModelProperty("级别")
    private Integer level;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("商品数量")
    private Integer goodsNum;

    @ApiModelProperty("父级id")
    private String parentId;

    @ApiModelProperty("操作员id")
    private String merchantUserId;

    public GoodsCategoryBO(String id, String cagegoryName, Integer level, Integer sort, String parentId) {
        this.id = id;
        this.cagegoryName = cagegoryName;
        this.level = level;
        this.sort = sort;
        this.parentId = parentId;
    }
}
