package com.fzy.admin.fp.wx.controller;

import cn.hutool.core.text.StrFormatter;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.config.RedisConfiguration;
import com.fzy.admin.fp.distribution.order.service.DistOrderService;
import com.fzy.admin.fp.distribution.pc.domain.NoteConfig;
import com.fzy.admin.fp.distribution.pc.service.NoteConfigService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.sdk.pay.feign.WxConfigServiceFeign;
import io.swagger.annotations.*;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @Author hzq
 * @Date 2020/9/9 17:15
 * @Version 1.0
 * @description
 */
@RestController
@Slf4j
@RequestMapping("/wx/wechant/merchant")
@Api(value = "微信用户绑定商户控制层", tags = {"微信用户绑定商户控制层"})
public class WeChantMerchantController extends BaseContent {

    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    static final String product = "Dysmsapi";

    /**
     * 产品域名,开发者无需替换
     */
    static final String domain = "dysmsapi.aliyuncs.com";


    private final String ERROR = "error";

    @Resource
    private WxConfigServiceFeign wxConfigServiceFeign;
    @Resource
    private MerchantService merchantService;
    @Resource
    private DistOrderService distOrderService;
    @Resource
    private RedisConfiguration redisConfiguration;
    @Resource
    private NoteConfigService noteConfigService;

    /**
     * 获取code
     */
    @ApiOperation(value = "微信授权", notes = "微信授权")
    @PostMapping("/wechat/authorization")
    public Resp getCode() throws IOException {
        String serviceProviderId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        // 微信支付配置
        Map<String, String> configMap = wxConfigServiceFeign.getWxConfig(serviceProviderId);
        String msg = configMap.get("msg");
        if (ERROR.equals(msg)) {
            throw new BaseException("请先配置微信支付参数", Resp.Status.PARAM_ERROR.getCode());
        }
        String appId = configMap.get("appId");
        // 用户授权后腾讯重定向的地址 &转义成 %26 否则&后的参数会被微信截取掉
        //微信获取code同步回调
        final String url = getDomain() + "/wx/wechant/merchant/wx/oauth";
        // 微信授权链接 （非静默）
        String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={}&redirect_uri={}&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        // 授权链接补全参数
        redirectUrl = StrFormatter.format(redirectUrl, appId, url);
        String encode = URLEncoder.encode(redirectUrl, "UTF-8");
        //二维码
        String erweima = "https://pay.hstypay.com/pay/qrcode?uuid=" + encode;
        log.info("二维码链接 ----------  " + erweima);
        return Resp.success(redirectUrl, "获取成功");
    }


    /**
     * 回调链接
     *
     * @return
     */
    @GetMapping("/wx/oauth")
    @ApiOperation(value = "微信信息用户回调", notes = "微信信息用户回调")
    public void wxOauth() throws IOException {
        response.setCharacterEncoding("UTF-8");
        String serviceProviderId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        String code = request.getParameter("code");
        if (ParamUtil.isBlank(code)) {
            throw new BaseException("请在微信浏览器中打开!");
        }
        log.info("code参数 ------->: {} ", code);
        //通过code换取网页授权access_token openId
        Map map = distOrderService.getAccessToken(code, serviceProviderId);
        log.info("通过code换取网页授权access_token -------- " + map.toString());
        String openid = map.get("openid").toString();
        String accessToken = map.get("access_token").toString();
        //获取微信用户信息
        Map wxUserMap = distOrderService.getWxUser(openid, accessToken);
        Merchant merchant = merchantService.getRepository().findByOpenId(openid);
        log.info("重定向地址，{}", getDomain());
        // 商户为空重定向到绑定页面
        if (merchant == null) {
            String url =  getDomain() + "/web/bind/index.html#/" + "?status=" + 1 + "&openId=" + openid;
            log.info("路径1 ======= > {}" , url);
            response.sendRedirect(getDomain() + "/web/bind/index.html#/" + "?status=" + 1 + "&openId=" + openid );
        }else {
            String url2 = getDomain() + "/web/bind/index.html#/" + "?status=" + 2 + "&openId=" + openid + "&phone=" + merchant.getPhone();
            log.info("路径2 ======= > {}" , url2);
            response.sendRedirect(getDomain() + "/web/bind/index.html#/" + "?status=" + 2 + "&openId=" + openid + "&phone=" + merchant.getPhone());
        }
    }


    @ApiOperation(value = "绑定手机号", tags = "绑定手机号")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "openId", dataType = "String", value = "openId"),
            @ApiImplicitParam(paramType = "query", name = "phone", dataType = "String", value = "电话号码"),
            @ApiImplicitParam(paramType = "query", name = "code", dataType = "String", value = "验证码")})
    @PostMapping("/bind/phone")
    public Resp bindPhone(String openId, String phone, String code) {
        if (ParamUtil.isBlank(openId)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "openId不能为空");
        }
        if (ParamUtil.isBlank(phone)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "电话号码不能为空");
        }
        if (StringUtil.isEmpty(code) || !code.equals(redisConfiguration.redisPoolFactory().getResource().get(phone))) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "验证码不正确");
        }
        //通过手机号判断商户
        Merchant merchant = merchantService.getRepository().findByPhone(phone);
        if (merchant == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "商户不存在");
        }
        merchant.setOpenId(openId);
        merchant.setBind(Integer.valueOf(2));
        merchantService.update(merchant);
        log.info("绑定成功");
//
//        try {
//            NoteConfig noteConfig = noteConfigService.getRepository().findByServiceProviderId(merchant.getServiceProviderId());
//            String params = "尊敬的刷客宝商户，您好。您已完成绑定。客服电话：4007071990。感谢您的使用。如有朋友装机，转介绍有红包赠送50元/户。";
//            //初始化acsClient,暂不支持region化
//            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", noteConfig.getAccessKeyId(), noteConfig.getAccessKeySecret());
//            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
//            IAcsClient acsClient = new DefaultAcsClient(profile);
//
//            //组装请求对象-具体描述见控制台-文档部分内容
//            SendSmsRequest request = new SendSmsRequest();
//            //必填:待发送手机号
//            request.setPhoneNumbers(phone);
//            //必填:短信签名-可在短信控制台中找到
//            request.setSignName("刷客宝");
//            //必填:短信模板-可在短信控制台中找到
//            request.setTemplateCode(noteConfig.getTemplateCode());
//            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//            //request.setTemplateParam(params);
//            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//            //request.setOutId("yourOutId");
//
//            //hint 此处可能会抛出异常，注意catch
//            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
//            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
//                //请求成功
//                System.out.println("=====success====");
//            } else {
//                System.out.println("=====fail=======");
//            }
//        }catch (Exception e){
//            log.error("发送短信异常" + e.getMessage());
//            return new Resp().error(Resp.Status.PARAM_ERROR, "请重试");
//        }
        return Resp.success("绑定成功");
    }

    @ApiOperation(value = "根据手机号和openId获取商户信息")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "openId", dataType = "String", value = "openId"),
            @ApiImplicitParam(paramType = "query", name = "phone", dataType = "String", value = "电话号码")})
    @ApiResponse(code = 200,message = "OK",response = Merchant.class)
    @GetMapping("/get/merchant")
    public Merchant getMerchant(String openId ,String phone){
        return merchantService.getRepository().findByOpenIdAndPhone(openId,phone);
    }


    @ApiOperation(value = "解除绑定",notes = "解除绑定")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",name = "phone",dataType = "String",value = "电话")})
    @PostMapping("/remove/bind")
    public Resp removeBind(String phone){
        Merchant merchant = merchantService.getRepository().findByPhone(phone);
        merchant.setBind(Integer.valueOf(1));
        merchant.setOpenId("");
        return Resp.success(merchantService.update(merchant),"解除绑定成功");
    }



}
