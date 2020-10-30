package com.fzy.admin.fp.common.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.fzy.admin.fp.auth.domain.SiteInfo;
import com.fzy.admin.fp.auth.service.SiteInfoService;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.config.RedisConfiguration;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.app.service.DistUserService;
import com.fzy.admin.fp.distribution.order.domain.ShopOrder;
import com.fzy.admin.fp.distribution.pc.domain.NoteConfig;
import com.fzy.admin.fp.distribution.pc.service.NoteConfigService;
import com.fzy.admin.fp.goods.controller.GoodsCategoryController;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auth yy
 * @Date 2019-11-13 17:55:14
 * @Desp 短信的相关api
 */
@RequestMapping("/common")
@RestController
public class NoteController {

    @Resource
    private DistUserService distUserService;
    @Resource
    private MerchantService merchantService;

    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    static final String product = "Dysmsapi";

    /**
     * 产品域名,开发者无需替换
     */
    static final String domain = "dysmsapi.aliyuncs.com";

    private static final Logger log = LoggerFactory.getLogger(GoodsCategoryController.class);

    @Resource
    private RedisConfiguration redisConfiguration;

    @Autowired
    private SiteInfoService siteInfoService;

    @Autowired
    private NoteConfigService noteConfigService;

    private static String content = "，您的验证码，有效时间5分钟，请尽快验证";

    /**
     * 短信验证码
     *
     * @param request
     * @param phone
     * @param type    0.注册验证码  1忘记密码验证码
     * @return
     */
    @RequestMapping("/note/send")
    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    public Resp noteSend(HttpServletRequest request, String phone, Integer type) {
        try {
            boolean mobileNO = isMobileNO(phone);
            if (mobileNO == false) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "请输入有效手机号");
            }

            if (!ParamUtil.isBlank(type)) {
                if (type.equals(Integer.valueOf(0))) {
                    //查询该手机号是否注册过了
                    DistUser distUser = distUserService.getRepository().findByUserName(phone);
                    if (!ParamUtil.isBlank(distUser)) {
                        return new Resp().error(Resp.Status.PARAM_ERROR, "当前用户已经注册过了");
                    }
                } else if (type.equals(Integer.valueOf(1))) {
                    //查询该手机号是否注册过了
                    DistUser distUser = distUserService.getRepository().findByUserName(phone);
                    if (ParamUtil.isBlank(distUser)) {
                        return new Resp().error(Resp.Status.PARAM_ERROR, "该手机号尚未注册");
                    }
                } else if (type.equals(Integer.valueOf(2))) {
                    //查询该手机号是否是商户的注册手机号
                    Merchant merchant = merchantService.getRepository().findByPhone(phone);
                    if (ParamUtil.isBlank(merchant)) {
                        return new Resp().error(Resp.Status.PARAM_ERROR, "商户账号不存在");
                    }
                    if(merchant.getBind().equals(Integer.valueOf(2))){
                        return new Resp().error(Resp.Status.PARAM_ERROR, "该商户账号已绑定");
                    }
                }
            }

            String phoneNum = phone + "Num";
            Jedis resource = redisConfiguration.redisPoolFactory().getResource();

            if (resource.exists(phone + "Disable")) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "近期发送验证码次数过多，请隔天再试");
            }
            //10分钟里发送的次数
            Integer num = 0;
            String redisNum = resource.get(phone + "Num");
            if (StringUtil.isNotEmpty(redisNum)) {
                num = Integer.valueOf(redisNum) + 1;
            }
            if (num >= 7) {
                resource.set(phone + "Disable", "1", "NX", "PX", 86400000);
                return new Resp().error(Resp.Status.PARAM_ERROR, "近期发送验证码次数过多，请隔天再试");
            }
            boolean keyExist = resource.exists(phoneNum);
            if (keyExist) {
                resource.del(phoneNum);
            }
            request.setCharacterEncoding("UTF-8");
            String domainName = request.getServerName();
            SiteInfo siteInfo = siteInfoService.findByDomainName(domainName);
            //2.域名未经认证，返回 401
            if (siteInfo == null) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "域名尚未经过认证");
            }

            NoteConfig noteConfig = noteConfigService.getRepository().findByServiceProviderId(siteInfo.getServiceProviderId());
            if (noteConfig == null) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "未配置短信通道");
            }
            //记录发送的次数 10分钟内累计不能超过7
            resource.set(phoneNum, num.toString(), "NX", "PX", 600000);

            Integer code = (int) ((Math.random() * 9 + 1) * 1000);
            sendSms(code, phone, noteConfig);
            keyExist = resource.exists(phone);
            if (keyExist) {
                resource.del(phone);
            }
            // NX是不存在时才set， XX是存在时才set， EX是秒，PX是毫秒
            resource.set(phone, String.valueOf(code), "NX", "PX", 300000);
            resource.close();
            return Resp.success("发送成功");
        } catch (Exception e) {
            log.error("发送短信异常" + e.getMessage());
            return new Resp().error(Resp.Status.PARAM_ERROR, "请重试");
        }
    }

    public static void sendSms(Integer code, String phone, NoteConfig noteConfig) throws IOException, ClientException {
        if (noteConfig.getType() == 0) {
            String str = URLEncoder.encode(code + content, "GB2312");
            CloseableHttpClient client = null;
            try {
                String url = "http://182.254.129.192:8888/smsGbk.aspx?action=send&userid=" + noteConfig.getUserId() + "&account=" + noteConfig.getFzyAccount() + "&password=" + noteConfig.getFzyPassword() + "&mobile=" + phone + "&content=" + str;
                HttpGet httpGet = new HttpGet(url);
                client = HttpClients.createDefault();
                CloseableHttpResponse execute = client.execute(httpGet);
            } finally {
                if (client != null) {
                    client.close();
                }
            }
        } else if (noteConfig.getType() == 1) {
            String params = "{\"code\":\"" + code + "\"}";
            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");

            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", noteConfig.getAccessKeyId(), noteConfig.getAccessKeySecret());
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phone);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(noteConfig.getSignName());
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(noteConfig.getTemplateCode());
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            request.setTemplateParam(params);

            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId("yourOutId");

            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
                //请求成功
                System.out.println("=====success====");
            } else {
                System.out.println("=====fail=======");
            }

        }
    }


    /**
     * 订单完成发送短信通知发货
     */
    public static void sendSmsOrder(ShopOrder shopOrder, String contact, String phones, NoteConfig noteConfig) throws IOException, ClientException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //支付时间
        String payTime = formatter.format(shopOrder.getPayTime());
        //收货地址
        String place = shopOrder.getPlace();
        //交易金额
        String price = shopOrder.getPrice().stripTrailingZeros().toString();
        //订单号
        String orderNumber = shopOrder.getOrderNumber();
        String message = "您有一个订单号:" + orderNumber + " 下单时间:" + payTime + " 请登录后台发货";
        log.info("短信推送消息 ------  " + message);

        //切割电话号码
        String[] split = phones.split(",");
        for (int i = 0; i < split.length; i++) {
            String phone = split[i];

            //锋之云短信通道
            if (noteConfig.getType() == 0) {

                String str = URLEncoder.encode(message, "GB2312");
                CloseableHttpClient client = null;
                try {
                    String url = "http://182.254.129.192:8888/smsGbk.aspx?action=send&userid=" + noteConfig.getUserId() + "&account=" + noteConfig.getFzyAccount() + "&password=" + noteConfig.getFzyPassword() + "&mobile=" + phone + "&content=" + str;
                    HttpGet httpGet = new HttpGet(url);
                    client = HttpClients.createDefault();
                    CloseableHttpResponse execute = client.execute(httpGet);
                } finally {
                    if (client != null) {
                        client.close();
                    }
                }
            } else if (noteConfig.getType() == 1) {

                String params = "{\"code\":\"" + message + "\"}";
                //可自助调整超时时间
                System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
                System.setProperty("sun.net.client.defaultReadTimeout", "10000");

                //初始化acsClient,暂不支持region化
                IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", noteConfig.getAccessKeyId(), noteConfig.getAccessKeySecret());
                DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
                IAcsClient acsClient = new DefaultAcsClient(profile);

                //组装请求对象-具体描述见控制台-文档部分内容
                SendSmsRequest request = new SendSmsRequest();
                //必填:待发送手机号
                request.setPhoneNumbers(phone);
                //必填:短信签名-可在短信控制台中找到
                request.setSignName(noteConfig.getSignName());
                //必填:短信模板-可在短信控制台中找到
                request.setTemplateCode(noteConfig.getTemplateCode());
                //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
                request.setTemplateParam(params);

                //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
                //request.setSmsUpExtendCode("90997");

                //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
                request.setOutId("yourOutId");

                //hint 此处可能会抛出异常，注意catch
                acsClient.getAcsResponse(request);
            }
        }
    }

    /**
     * 验证手机号是否合法
     *
     * @return
     */
    public static boolean isMobileNO(String mobile) {
        if (StringUtil.isEmpty(mobile) || mobile.length() != 11) {
            return
                    false;
        } else {
            /**
             * 移动号段正则表达式
             */
            String pat1 = "^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$";

            /**
             * 联通号段正则表达式
             */
            String pat2 = "^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$";

            /**
             * 电信号段正则表达式
             */
            String pat3 = "^((19[1,9])|(173)|(133)|(153)|(177)|(18[0,1,9])|(149))\\d{8}$";

            /**
             * 虚拟运营商正则表达式
             */
            String pat4 = "^((170))\\d{8}|(1718)|(1719)\\d{7}$";


            Pattern pattern1 = Pattern.compile(pat1);

            Matcher match1 = pattern1.matcher(mobile);

            boolean isMatch1 = match1.matches();

            if (isMatch1) {
                return true;
            }

            Pattern pattern2 = Pattern.compile(pat2);

            Matcher match2 = pattern2.matcher(mobile);

            boolean isMatch2 = match2.matches();

            if (isMatch2) {
                return true;
            }

            Pattern pattern3 = Pattern.compile(pat3);

            Matcher match3 = pattern3.matcher(mobile);

            boolean isMatch3 = match3.matches();

            if (isMatch3) {
                return true;
            }

            Pattern pattern4 = Pattern.compile(pat4);

            Matcher match4 = pattern4.matcher(mobile);

            boolean isMatch4 = match4.matches();

            if (isMatch4) {
                return true;
            }

            return
                    false;
        }
    }
}