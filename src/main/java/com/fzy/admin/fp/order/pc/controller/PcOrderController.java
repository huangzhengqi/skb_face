package com.fzy.admin.fp.order.pc.controller;


import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.dto.OrderDto;
import com.fzy.admin.fp.order.order.dto.OrderRefundDTO;
import com.fzy.admin.fp.order.pc.dto.OrderFlowDto;
import com.fzy.admin.fp.order.pc.service.PcOrderService;
import com.fzy.admin.fp.order.pc.util.BarcodeUtil;
import com.fzy.admin.fp.order.pc.vo.*;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantUserSelect;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Created by wtl on 2019-04-26 14:30
 * @description pc端插件下单接口
 */
@RestController
@RequestMapping("/order/management/pc")
@Api(value = "PcOrderController", tags = "pc端插件下单接口")
public class PcOrderController extends BaseContent {

    @Resource
    private PcOrderService pcOrderService;

    /**
     * @author Created by wtl on 2019/6/12 11:44
     * @Description 根据订单号生成条形码
     */
    @GetMapping("/create_barcode")
    public void createBarCode(String orderNumber) throws IOException {
        if (ParamUtil.isBlank(orderNumber)) {
            throw new BaseException("订单号不能为空", Resp.Status.PARAM_ERROR.getCode());
        }
        BarcodeUtil.generate(orderNumber, response.getOutputStream());
    }


    /**
     * pc端插件扫码支付
     * @param userId
     * @param model
     * @return
     * @throws Exception
     */
    @PostMapping("/scan_pay")
    @ApiOperation(value = "pc端插件扫码支付",notes = "pc端插件扫码支付")
    @ApiResponses(@ApiResponse(code = 200,message = "OK",response = PayResult.class))
    public Resp<PayResult> scanPay(@UserId String userId, OrderDto model) throws Exception {
        // pc端
        model.setPayClient(Order.PayClient.PC.getCode());
        model.setUserId(userId);
        PayResult payResult = pcOrderService.scanPay(model);
        if (Order.Status.SUCCESSPAY.getCode().equals(payResult.getStatus())) {
            return Resp.success(payResult);
        }
        return new Resp().error(Resp.Status.PARAM_ERROR, "支付失败");
    }

    /**
     * @author Created by wtl on 2019/4/26 14:43
     * @Description 查询订单
     */
    @GetMapping("/query_order")
    @ApiOperation(value = "查询订单", notes = "查询订单")
    public Resp<Map<String, Object>> queryOrder(@UserId String userId, Order model, PageVo pageVo) {
        return Resp.success(pcOrderService.queryOrder(userId, model, pageVo));
    }

    /**
     * @author Created by wtl on 2019/4/26 14:43
     * @Description 查询订单
     */
    @GetMapping("/query_order_chajian")
    @ApiOperation(value = "查询订单", notes = "查询订单")
    public Resp<Map<String, Object>> queryOrderChaJian(@UserId String userId, Order model) {
        return Resp.success(pcOrderService.queryOrderChaJian(userId, model));
    }

    /**
     * @author Created by wtl on 2019/5/1 21:47
     * @Description 订单详情
     */
    @GetMapping("/order_detail")
    @ApiOperation(value = "订单详情", notes = "订单详情")
    public Resp<OrderDetail> orderDetail(@RequestParam(value = "orderNumber") @ApiParam(value = "订单号") String orderNumber) {
        return Resp.success(pcOrderService.orderDetail(orderNumber));
    }

    /**
     * @author Created by wtl on 2019/5/1 22:46
     * @Description 修改订单备注
     */
    @PostMapping("/edit_remarks")
    public Resp editOrderRemarks(String orderNumber, String remarks) {
        pcOrderService.editOrderRemarks(orderNumber, remarks);
        return Resp.success("成功修改备注");
    }

    /**
     * @author Created by wtl on 2019/4/30 10:45
     * @Description 获取登录用户的员工，商户显示所有员工、店长显示门店员工、员工显示自己
     */
    @GetMapping("/merchant_user")
    @ApiOperation(value = "获取登录用户的员工", notes = "获取登录用户的员工")
    public Resp<List<MerchantUserSelect>> merchantUser(@UserId String userId) {
        return Resp.success(pcOrderService.merchantUser(userId));
    }

    /**
     * @author Created by wtl on 2019/4/29 21:34
     * @Description 退款接口
     */
    @PostMapping("/refund")
    @ApiOperation(value = "退款接口",notes = "退款接口")
    public Resp<RefundResult> refund(@UserId String userId, OrderRefundDTO model) throws Exception {
        return Resp.success(pcOrderService.refund(userId, model), "退款成功");
    }

    /**
     * @author Created by wtl on 2019/4/30 14:26
     * @Description 订单数(日 、 周 、 月)、订单金额、退款金额统计
     */
    @GetMapping("/count_order")
    @ApiOperation(value = "订单数(日 、 周 、 月)、订单金额、退款金额统计",notes = "订单数(日 、 周 、 月)、订单金额、退款金额统计")
    public Resp<OrderCountVo> countOrder(@UserId String userId) {
        return Resp.success(pcOrderService.countOrder(userId));
    }

    /**
     * @author Created by wtl on 2019/5/4 23:17
     * @Description 订单流水
     */
    @GetMapping("/order_flow")
    @ApiOperation(value = "订单流水",notes = "订单流水")
    public Resp<OrderFlow> orderFlow(@UserId String userId, OrderFlowDto orderFlowDto) {
        return Resp.success(pcOrderService.orderFlow(userId, orderFlowDto));
    }


}
