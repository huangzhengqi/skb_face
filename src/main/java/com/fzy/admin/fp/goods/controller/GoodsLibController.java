package com.fzy.admin.fp.goods.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.goods.domain.GoodsLib;
import com.fzy.admin.fp.goods.service.GoodsLibService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/merchant/goods_lib")
@Api(value = "GoodsLibController", tags = {"商品库管理"})
public class GoodsLibController extends BaseController<GoodsLib> {

    @Value("${goodslib.aliappcode}")
    private String appcode;


    @Value("${goodslib.url}")
    private String url;


    @Value("${goodslib.libType}")
    private String libType;


    @Autowired
    private GoodsLibService goodsLibService;



    public BaseService<GoodsLib> getService() { return this.goodsLibService; }


    @ApiOperation(value = "根据条形码获取商品", notes = "根据条形码获取商品")
    @GetMapping({""})
    public Resp<GoodsLib> getDetail(String code) {
        GoodsLib goodsLib = this.goodsLibService.findByCode(code);
        if (goodsLib == null) {
            HttpRequest request = HttpUtil.createGet(this.url);
            request.timeout(5000);
            Map<String, Object> querys = new HashMap<String, Object>();
            querys.put("code", code);
            if ("ali".equals(this.libType)) {
                request.header("Authorization", "APPCODE " + this.appcode);
                HttpResponse response = request.execute();
                if (response.getStatus() == 200) {
                    String resultBoyd = response.body();
                    JSONObject resultJson = JSONObject.parseObject(resultBoyd);
                    if ("200".equals(resultJson.get("status"))) {
                        goodsLib = (GoodsLib)JSONObject.parseObject(JSON.toJSONString(resultJson.get("result")), GoodsLib.class);
                    } else {
                        return (new Resp()).error(Resp.Status.PARAM_ERROR, "查询异常," + resultJson.get("msg"));
                    }
                } else {
                    return (new Resp()).error(Resp.Status.PARAM_ERROR, "查询异常!");
                }
            } else {
                HttpResponse response = request.execute();
                if (response.getStatus() == 200) {
                    String resultBoyd = response.body();
                    JSONObject resultJson = JSONObject.parseObject(resultBoyd);
                    if ("200".equals(resultJson.get("status"))) {
                        goodsLib = (GoodsLib)JSONObject.parseObject(JSON.toJSONString(resultJson.get("obj")), GoodsLib.class);
                    } else {
                        return (new Resp()).error(Resp.Status.PARAM_ERROR, "查询异常," + resultJson.get("msg"));
                    }
                } else {
                    return (new Resp()).error(Resp.Status.PARAM_ERROR, "查询异常!!");
                }
            }
            goodsLib.setId(null);
            this.goodsLibService.save(goodsLib);
        }
        return Resp.success(goodsLib);
    }
}
