package com.fzy.admin.fp.goods.vo;

import com.fzy.admin.fp.goods.domain.Goods;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel(value = "菜单信息")
@Data
public class GoodsCategoryNameVO {

    private String categoryName;

    private List<Goods> goods;
}
