package com.fzy.admin.fp.order.third.controller;


import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.order.third.dto.ThirdPayDTO;
import com.fzy.admin.fp.order.third.dto.ThirdQueryDTO;
import com.fzy.admin.fp.order.third.dto.ThirdRefundDTO;
import com.fzy.admin.fp.order.third.dto.ThirdSnDTO;
import com.fzy.admin.fp.order.third.service.ThirdPayService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Created by wtl on 2019-06-03 17:10
 * @description 第三方开放接口
 */
@RestController
@RequestMapping("/order/ly")
public class ThirdPayController extends BaseContent {

    @Resource
    private ThirdPayService thirdPayService;

    /**
     * @author Created by wtl on 2019/6/12 15:40
     * @Description 根据sn号获取支付配置
     */
    @PostMapping("/download_config")
    public Resp downloadConfig(@RequestBody ThirdSnDTO thirdSnDTO) {
        return Resp.success(thirdPayService.downloadConfig(thirdSnDTO.getSn()));
    }


    /**
     * @author Created by wtl on 2019/6/3 17:11
     * @Description 被扫支付
     */
    @PostMapping("/micropay")
    public Resp microPay(@RequestBody ThirdPayDTO model) throws Exception {
        return Resp.success(thirdPayService.microPay(model));
    }

    /**
     * @author Created by wtl on 2019/6/4 1:07
     * @Description 查询订单
     */
    @PostMapping("/order_query")
    public Resp orderQuery(@RequestBody ThirdQueryDTO model) {
        return Resp.success(thirdPayService.orderQuery(model));
    }

    /**
     * @author Created by wtl on 2019/6/10 11:48
     * @Description 订单退款
     */
    @PostMapping("/refund")
    public Resp refund(@RequestBody ThirdRefundDTO model) throws Exception {
        return Resp.success(thirdPayService.refund(model));
    }


}
