package com.fzy.admin.fp.distribution.order.vo;

import com.fzy.admin.fp.distribution.shop.vo.DistGoodsVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author yy
 * @Date 2019-12-4 17:53:53
 * @Desp
 **/
@Data
public class ShopOrderVO {
    private Date createTime;

    private Integer status;

    private String orderNumber;

    private BigDecimal price;

    private List<DistGoodsVO> distGoodsVOList=new ArrayList<>();

}
