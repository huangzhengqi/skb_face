package com.fzy.admin.fp.wx.controller;

import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.wx.service.WeChatRedPacketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author hzq
 * @Date 2020/9/9 11:20
 * @Version 1.0
 * @description 微信红包返佣
 */
@RestController
@Slf4j
@RequestMapping("/wx/wechant/red/packet")
@Api(value = "微信红包返佣控制层", tags = {"微信红包返佣控制层"})
public class WeChatRedPacketController extends BaseContent {

    @Resource
    private WeChatRedPacketService weChatRedPacketService;

    @ApiOperation(value = "发放普通红包", notes = "发放普通红包")
    @PostMapping("/mmpaymkttransfers/sendredpack")
    public Resp mmpaymkttransfersSendredpack() {
        boolean b = weChatRedPacketService.bindSendredpack();
        if (b) {
            return Resp.success("发放普通红包成功");
        }
        return Resp.success("无商户发放");
    }

    @ApiOperation(value = "发放奖励红包", notes = "发放奖励红包")
    @PostMapping("/reward")
    public Resp reward() {
        boolean b = weChatRedPacketService.reward();
        if (b) {
            return Resp.success("发放奖励红包成功");
        }
        return Resp.success("无商户发放");
    }

    @ApiOperation(value = "查询返佣红包", notes = "查询返佣红包")
    @GetMapping("/find/sendredpack")
    public Resp findSendredpack() {
        weChatRedPacketService.findBindSendredpac();
        return Resp.success("查询返佣红包");
    }

    @ApiOperation(value = "查询奖励红包", notes = "查询奖励红包")
    @GetMapping("/find/reward")
    public Resp findReward() {
        weChatRedPacketService.findReward();
        return Resp.success("查询奖励红包");
    }

    /**
     * 查询单个红包
     *
     * @return
     */
    @GetMapping("/find")
    public Resp find(String mchBillno, Integer type) {
        return Resp.success(weChatRedPacketService.find(mchBillno, type));
    }
}
