package com.fzy.admin.fp.goods.bo;

import com.fzy.admin.fp.goods.domain.GoodsCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class GoodsCategoryTree extends GoodsCategory {

    @ApiModelProperty("二级分类")
    private List<GoodsCategoryTree> children;
}
