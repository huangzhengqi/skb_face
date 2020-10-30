package com.fzy.admin.fp.order.app.vo;


import com.fzy.admin.fp.order.pc.vo.OrderPcVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Created by wtl on 2019-05-10 10:01
 * @description 商户app端订单内容
 */
@Data
public class AppOrderVO {

    private List<OrderPcVo> orders; // 订单列表
    private BigDecimal actPrice; // 收款金额，统计实付金额即可，不用减去退款金额
    private BigDecimal refundPrice; // 退款金额


}
