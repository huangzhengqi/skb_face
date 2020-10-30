package com.fzy.admin.fp.goods.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GoodsCount {
    @ApiModelProperty("全部")
    private Integer all;
    @ApiModelProperty("上架")
    private Integer isShelf;
    @ApiModelProperty("下架")
    private Integer noShelf;
}
