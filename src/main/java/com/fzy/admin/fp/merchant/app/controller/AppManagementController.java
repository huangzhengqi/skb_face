package com.fzy.admin.fp.merchant.app.controller;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fzy.admin.fp.advertise.domain.Advertise;
import com.fzy.admin.fp.advertise.dto.AdvertiseViewDTO;
import com.fzy.admin.fp.advertise.service.AdvertiseService;
import com.fzy.admin.fp.auth.domain.Equipment;
import com.fzy.admin.fp.auth.repository.EquipmentRepository;
import com.fzy.admin.fp.common.util.TokenUtils;
import com.fzy.admin.fp.merchant.app.dto.LoginInfoDTO;
import com.fzy.admin.fp.merchant.management.VerifyCodeApplicationRunner;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.MerchantAppletConfig;
import com.fzy.admin.fp.merchant.merchant.service.MerchantAppletConfigService;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.auth.AuthConstant;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.SiteInfo;
import com.fzy.admin.fp.auth.repository.SiteInfoRepository;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.repository.MemberRepository;
import com.fzy.admin.fp.merchant.app.dto.WeiqingCallBackDTO;
import com.fzy.admin.fp.merchant.app.vo.UuidVO;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.dto.OrderDto;
import com.fzy.admin.fp.order.order.service.OrderService;
import com.fzy.admin.fp.pay.pay.domain.AliConfig;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.domain.WxConfig;
import com.fzy.admin.fp.pay.pay.repository.AliConfigRepository;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.pay.pay.repository.WxConfigRepository;
import com.fzy.admin.fp.sdk.auth.feign.AuthServiceFeign;
import com.fzy.admin.fp.sdk.auth.feign.MessageServiceFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by wtl on 2019-04-29 17:04
 * @description app登录接口
 */
@RestController
@RequestMapping("/merchant/admin/app")
@Api(value = "AppManagementController", tags = {"app登录接口"})
public class AppManagementController extends BaseContent {

    private static final Logger log = LoggerFactory.getLogger(AppManagementController.class);

    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private SiteInfoRepository siteInfoRepository;
    @Resource
    private MemberRepository memberRepository;

    @Resource
    private MerchantService merchantService;

    @Resource
    private AuthServiceFeign authServiceFeign;

    @Resource
    private MessageServiceFeign messageServiceFeign;
    @Resource
    private HttpServletRequest request;
    @Resource
    private TopConfigRepository topConfigRepository;
    @Resource
    private MerchantAppletConfigService merchantAppletConfigService;
    @Resource
    private WxConfigRepository wxConfigRepository;
    @Resource
    private CompanyService companyService;
    @Resource
    private OrderService orderService;

    @Resource
    private AliConfigRepository aliConfigRepository;


    @Resource
    private EquipmentRepository equipmentRepository;

    @Resource
    private AdvertiseService advertiseService;

    @Value("${secret.key}")
    private String key;
    @Value("${secret.tokenExpiration}")
    private int tokenExpiration;


    Cache<String, Map<String, Object>> LOGINCACHE = null;
    Cache<String, String> CHECKLOGINCACHE = null;


    /**
     * 蜻蜓小程序登陆获取广告接口
     * @param deviceId
     * @param deviceType
     * @return
     * @throws Exception
     */
    @GetMapping({"/login_info_by_token"})
    public Resp<LoginInfoDTO> loginBytoken(@RequestParam(value = "deviceId", required = false) @ApiParam("设备序列号，传的话则返回设备相关信息") String deviceId,
                                           @RequestParam(value="deviceType",defaultValue = "1")Integer deviceType) throws Exception {
        String userId = TokenUtils.getUserId();
        if (ParamUtil.isBlank(userId)) {
            return (new Resp()).error(Resp.Status.TOKEN_ERROR, "token有误，请重新登录");
        }
        LoginInfoDTO loginInfoDTO = new LoginInfoDTO();
        MerchantUser user = this.merchantUserService.findOne(userId);
        Merchant merchant = this.merchantService.findOne(user.getMerchantId());
        String token = this.request.getHeader("authorized");
        loginInfoDTO.setToken(token);
        loginInfoDTO.setUserType(user.getUserType());
        loginInfoDTO.setMerchantId(user.getMerchantId());
        loginInfoDTO.setMerchantName(merchant.getName());
        loginInfoDTO.setMerchantIcon("");
        loginInfoDTO.setUserId(user.getId());
        loginInfoDTO.setStore_id(user.getStoreId());
        loginInfoDTO.setPhone(user.getUsername());
        loginInfoDTO.setName(user.getName());

        loginInfoDTO.setServicePhone(merchant.getPhone());

        String serviceId = user.getServiceProviderId();
        loginInfoDTO.setServiceId(serviceId);

        SiteInfo siteInfo = this.siteInfoRepository.findByServiceProviderId(serviceId);
        loginInfoDTO.setDomain(siteInfo.getDomainName());
        TopConfig topConfig = this.topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        MerchantAppletConfig merchantAppletConfig = this.merchantAppletConfigService.findByMerchantId(user.getMerchantId());
        WxConfig wxConfig = this.wxConfigRepository.findByMerchantIdAndDelFlag(user.getMerchantId(), CommonConstant.NORMAL_FLAG);
        AliConfig aliConfig = this.aliConfigRepository.findByMerchantIdAndDelFlag(user.getMerchantId(), CommonConstant.NORMAL_FLAG);
        if (wxConfig != null) {
            loginInfoDTO.setWx_sub_mch_id(wxConfig.getSubMchId());
        }
        if (aliConfig != null) {
            loginInfoDTO.setAli_app_id(aliConfig.getPid());
        }
        if (merchantAppletConfig != null) {
            loginInfoDTO.setWx_sub_appid(merchantAppletConfig.getAppId());
        }
        if (topConfig != null) {
            loginInfoDTO.setWx_appid(topConfig.getWxAppId());
            loginInfoDTO.setWx_mch_id(topConfig.getWxMchId());
            loginInfoDTO.setAli_app_id(topConfig.getAliAppletAppId());
            loginInfoDTO.setAli_partner_id(topConfig.getAliPartnerId());
            loginInfoDTO.setAli_private_key(topConfig.getAliApplePrivateKey());
        }

        if (StringUtil.isNotEmpty(deviceId)) {
            Equipment equipment = this.equipmentRepository.findByDeviceIdAndDelFlagAndStoreId(deviceId, CommonConstant.NORMAL_FLAG, user.getStoreId());
            if (equipment != null) {
                loginInfoDTO.setEquipmentId(equipment.getId());
                loginInfoDTO.setButtonType(equipment.getMode());
            }
        }

        try {
            AdvertiseViewDTO advertiseViewDTO = new AdvertiseViewDTO();
            advertiseViewDTO.setMerchantId(user.getMerchantId());
            advertiseViewDTO.setStatus(deviceType);
            advertiseViewDTO.setDeviceType(deviceType);
            advertiseViewDTO.setFromRange(Integer.valueOf(8));
            List<Advertise> advertisesStartUp = this.advertiseService.findAdv(advertiseViewDTO).getObj();
            //判断是否存在视频
            if(advertisesStartUp.stream().anyMatch(s -> s.getSelectSt() != null && s.getSelectSt() == 1)){
                if(advertisesStartUp.stream().anyMatch(s -> s.getType()==1)){
                    advertisesStartUp = advertisesStartUp.stream().filter(s -> s.getSelectSt() == 1&&s.getType()==1).collect(Collectors.toList());
                }else{
                    advertisesStartUp = advertisesStartUp.stream().filter(s -> s.getSelectSt() == 1).collect(Collectors.toList());
                }
                loginInfoDTO.setVideoUrl(advertisesStartUp.get(0).getImageUrl());
                loginInfoDTO.setAdvType(true);
            }
            advertiseViewDTO.setFromRange(Integer.valueOf(9));
            List<Advertise> advertisesMemberPay = this.advertiseService.findAdv(advertiseViewDTO).getObj();

            advertiseViewDTO.setFromRange(Integer.valueOf(10));
            List<Advertise> advertisesPaySuccess = this.advertiseService.findAdv(advertiseViewDTO).getObj();

            advertiseViewDTO.setFromRange(Integer.valueOf(12));
            List<Advertise> advertisesUnMemberPay = this.advertiseService.findAdv(advertiseViewDTO).getObj();

            advertiseViewDTO.setFromRange(Integer.valueOf(13));
            List<Advertise> advertisesDep = this.advertiseService.findAdv(advertiseViewDTO).getObj();

            List<Advertise> advertises = new ArrayList<Advertise>();
            advertises.addAll(advertisesStartUp);
            advertises.addAll(advertisesMemberPay);
            advertises.addAll(advertisesPaySuccess);
            advertises.addAll(advertisesUnMemberPay);
            advertises.addAll(advertisesDep);

            loginInfoDTO.setAdvertiseList(advertises);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("获取广告异常");
        }


        return Resp.success(loginInfoDTO, "登录信息");
    }

    @GetMapping({"/login"})
    public Resp loginAli(@RequestParam("username") @ApiParam("登录名") String username, @RequestParam("password") @ApiParam("密码") String password, @RequestParam(value = "deviceId", required = false) @ApiParam("设备编号") String deviceId, @RequestParam(value = "deviceType", required = false) @ApiParam("设备类型") Integer deviceType) {
        return login(username, password, deviceId, deviceType,0);
    }

    /**
     * 青蛙/蜻蜓设备登录接口
     * @param username
     * @param password
     * @param deviceId
     * @param deviceType
     * @param status
     * @return
     */
    @PostMapping("/login")
    public Resp login(String username, String password, String deviceId, Integer deviceType,@RequestParam(defaultValue = "0") Integer status) {
        MerchantUser user = merchantUserService.findByUsername(username);
        if (ParamUtil.isBlank(user)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该用户名不存在");
        }
        if (user.getStatus().equals(MerchantUser.Status.DISABLE.getCode())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该用户已被禁用");
        }
        if (!BCrypt.checkpw(password, user.getPassword())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "密码错误");
        }
        Merchant merchant = merchantService.findOne(user.getMerchantId());
        if (merchant.getStatus().equals(Merchant.Status.GIFT.getCode())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该商户未签约");
        }

        SiteInfo siteInfo = this.siteInfoRepository.findByServiceProviderId(user.getServiceProviderId());
        String token = JWT.create().withIssuer(com.fzy.admin.fp.merchant.management.AuthConstant.AUTH_NAME).withSubject(user.getId())
                .withClaim("merchantId", user.getMerchantId())
                .withClaim(CommonConstant.SERVICE_PROVIDERID, user.getServiceProviderId())
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpiration * 60 * 1000))
                .sign(Algorithm.HMAC512(key));
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("domain", siteInfo.getDomainName());
        map.put("userType", user.getUserType());
        map.put("merchantId", user.getMerchantId());
        map.put("userId", user.getId());
        map.put("store_id", user.getStoreId());
        map.put("name", user.getName());
        map.put("phone", merchant.getPhone());
        map.put("merchantName", merchant.getName());
        map.put("merchantIcon", "");
        map.put("servicePhone", merchant.getPhone());
        map.put("serviceId", user.getServiceProviderId());
        if (deviceType != null && deviceType.equals(Integer.valueOf(1))) {
            final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
            TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
            MerchantAppletConfig merchantAppletConfig = merchantAppletConfigService.findByMerchantId(user.getMerchantId());
            WxConfig wxConfig = wxConfigRepository.findByMerchantIdAndDelFlag(user.getMerchantId(), CommonConstant.NORMAL_FLAG);
            AliConfig aliConfig = this.aliConfigRepository.findByMerchantIdAndDelFlag(user.getMerchantId(), CommonConstant.NORMAL_FLAG);
            if (wxConfig == null) {
                map.put("wx_sub_mch_id", null);
            } else {
                map.put("wx_sub_mch_id", wxConfig.getSubMchId());
            }

            if (aliConfig == null) {
                map.put("ali_pid", null);
            } else {
                map.put("ali_pid", aliConfig.getPid());
            }

            if (merchantAppletConfig == null) {
                map.put("wx_sub_appid", null);
            } else {
                map.put("wx_sub_appid", merchantAppletConfig.getAppId());
            }
            if (topConfig == null) {
                map.put("wx_appid", null);
                map.put("wx_mch_id", null);
                map.put("ali_app_id", null);
                map.put("ali_partner_id", null);
                map.put("ali_private_key", null);
            } else {
                map.put("wx_appid", topConfig.getWxAppId());
                map.put("wx_mch_id", topConfig.getWxMchId());
                map.put("ali_app_id", topConfig.getAliAppletAppId());
                map.put("ali_partner_id", topConfig.getAliPartnerId());
                map.put("ali_private_key", topConfig.getAliApplePrivateKey());
            }

        }

        try {
            AdvertiseViewDTO advertiseViewDTO = new AdvertiseViewDTO();
            advertiseViewDTO.setMerchantId(user.getMerchantId());
            advertiseViewDTO.setDeviceType(deviceType);
            advertiseViewDTO.setFromRange(Integer.valueOf(8));
            List<Advertise> advertisesStartUp = (List)this.advertiseService.findAdv(advertiseViewDTO).getObj();

            advertiseViewDTO.setFromRange(Integer.valueOf(9));
            List<Advertise> advertisesMemberPay = (List)this.advertiseService.findAdv(advertiseViewDTO).getObj();

            advertiseViewDTO.setFromRange(Integer.valueOf(10));
            List<Advertise> advertisesPaySuccess = (List)this.advertiseService.findAdv(advertiseViewDTO).getObj();

            advertiseViewDTO.setFromRange(Integer.valueOf(12));
            List<Advertise> advertisesUnMemberPay = (List)this.advertiseService.findAdv(advertiseViewDTO).getObj();

            advertiseViewDTO.setFromRange(Integer.valueOf(13));
            List<Advertise> advertisesDep = (List)this.advertiseService.findAdv(advertiseViewDTO).getObj();

            List<Advertise> advertises = new ArrayList<Advertise>();
            advertises.addAll(advertisesStartUp);
            advertises.addAll(advertisesMemberPay);
            advertises.addAll(advertisesPaySuccess);
            advertises.addAll(advertisesUnMemberPay);
            advertises.addAll(advertisesDep);

            map.put("advertiseList", advertises);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("获取广告异常"+e.getMessage());
        }


        if (StringUtils.isNoneEmpty(new CharSequence[] { deviceId })) {
            Equipment equipment = this.equipmentRepository.findByDeviceIdAndDelFlagAndStoreId(deviceId, CommonConstant.NORMAL_FLAG, user.getStoreId());
            if (equipment == null) {
                Equipment equipment1 = new Equipment();
                equipment1.setDeviceId(deviceId);
                equipment1.setDeviceType(deviceType);
                equipment1.setDelFlag(CommonConstant.NORMAL_FLAG);
                equipment1.setMerchantId(user.getMerchantId());
                equipment1.setServiceProviderId(user.getServiceProviderId());
                equipment1.setStoreId(user.getStoreId());
                equipment1.setMode(Integer.valueOf(1));
                equipment1.setStatus(status);
                this.equipmentRepository.save(equipment1);
                map.put("equipmentId", equipment1.getId());
                map.put("buttonType", Integer.valueOf(1));
            } else {
                map.put("equipmentId", equipment.getId());
                map.put("buttonType", equipment.getMode());
            }
        }

        return Resp.success(map, "登录成功");
    }

    @GetMapping("/login/qr")
    @ApiOperation(value = "检测是否授权成功", notes = "检测是否授权成功")
    public Resp loginWithRqCode(String uuid) {

        if (CHECKLOGINCACHE == null) {
            return new Resp().error(Resp.Status.NO_LOGIN, "二维码已过期");
        }
        String checkUuid = CHECKLOGINCACHE.get(uuid);
        if (checkUuid == null) {
            return new Resp().error(Resp.Status.NO_LOGIN, "二维码已过期");
        }
        Map<String, Object> map = LOGINCACHE.get(uuid);
        if (map != null) {
            return Resp.success(map, "授权成功");
        } else {
            return new Resp().error(Resp.Status.NO_LOGIN, "还未扫码成功！");
        }
    }

    @GetMapping("/login/get_qr_code")
    @ApiOperation(value = "获取uuid，并设置扫码有效期", notes = "获取uuid，并设置扫码有效期")
    public Resp<UuidVO> getUuid() {

        UuidVO uuidVO = new UuidVO();
        Integer timeout = 120;
        String uuid = UUID.randomUUID().toString();
        LOGINCACHE = CacheUtil.newLFUCache(2000, timeout * 1000);
        CHECKLOGINCACHE = CacheUtil.newLFUCache(2000, timeout * 1000);
        CHECKLOGINCACHE.put(uuid, uuid);
        uuidVO.setTimeout(timeout);
        uuidVO.setUuid(uuid);
        return Resp.success(uuidVO, "获取成功！");

    }

    @GetMapping("/login/scan")
    @ApiOperation(value = "app/设备扫码登陆", notes = "app/设备扫码登陆")
    public Resp loginScan(@UserId String userId, String uuid) {
//        if (ParamUtil.isBlank(userId)) {
//            return new Resp().error(Resp.Status.TOKEN_ERROR, "token有误，请重新登录");
//        }
//        if (CHECKLOGINCACHE == null) {
//            return new Resp().error(Resp.Status.INNER_ERROR, "二维码已过期");
//        }
//        String checkUuid = CHECKLOGINCACHE.get(uuid);
//        if (checkUuid == null) {
//            return new Resp().error(Resp.Status.INNER_ERROR, "二维码已过期");
//        }
//        MerchantUser user = merchantUserService.findOne(userId);
//        String token = request.getHeader("authorized");
//        Map<String, Object> map = new HashMap<>();
//        map.put("token", token);
//        map.put("userType", user.getUserType());
//        map.put("merchantId", user.getMerchantId());
//        map.put("userId", user.getId());
//        map.put("store_id", user.getStoreId());
//        map.put("phone", user.getUsername());
//        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
//        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
//        MerchantAppletConfig merchantAppletConfig = merchantAppletConfigService.findByMerchantId(user.getMerchantId());
//        WxConfig wxConfig = wxConfigRepository.findByMerchantIdAndDelFlag(user.getMerchantId(), CommonConstant.NORMAL_FLAG);
//        if (wxConfig == null) {
//            map.put("wx_sub_mch_id", null);
//        } else {
//            map.put("wx_sub_mch_id", wxConfig.getSubMchId());
//        }
//        if (merchantAppletConfig == null) {
//            map.put("wx_sub_appid", null);
//        } else {
//            map.put("wx_sub_appid", merchantAppletConfig.getAppId());
//        }
//        if (topConfig == null) {
//            map.put("wx_appid", null);
//            map.put("wx_mch_id", null);
//        } else {
//            map.put("wx_appid", topConfig.getWxAppId());
//            map.put("wx_mch_id", topConfig.getWxMchId());
//        }
//        LOGINCACHE.put(uuid, map);
//
//        return Resp.success("授权成功");

        if (ParamUtil.isBlank(userId)) {
            return (new Resp()).error(Resp.Status.TOKEN_ERROR, "token有误，请重新登录");
        }
        if (this.CHECKLOGINCACHE == null) {
            return (new Resp()).error(Resp.Status.INNER_ERROR, "二维码已过期");
        }
        String checkUuid = (String)this.CHECKLOGINCACHE.get(uuid);
        if (checkUuid == null) {
            return (new Resp()).error(Resp.Status.INNER_ERROR, "二维码已过期");
        }
        MerchantUser user = (MerchantUser)this.merchantUserService.findOne(userId);
        Merchant merchant = (Merchant)this.merchantService.findOne(user.getMerchantId());
        String token = this.request.getHeader("authorized");
        SiteInfo siteInfo = this.siteInfoRepository.findByServiceProviderId(user.getServiceProviderId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("token", token);
        map.put("domain", siteInfo.getDomainName());
        map.put("userType", user.getUserType());
        map.put("merchantId", user.getMerchantId());
        map.put("merchantName", merchant.getName());
        map.put("merchantIcon", "");
        map.put("userId", user.getId());
        map.put("store_id", user.getStoreId());
        map.put("phone", user.getUsername());
        String serviceId = (String)this.request.getAttribute("serviceProviderId");
        TopConfig topConfig = this.topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        MerchantAppletConfig merchantAppletConfig = this.merchantAppletConfigService.findByMerchantId(user.getMerchantId());
        WxConfig wxConfig = this.wxConfigRepository.findByMerchantIdAndDelFlag(user.getMerchantId(), CommonConstant.NORMAL_FLAG);
        AliConfig aliConfig = this.aliConfigRepository.findByMerchantIdAndDelFlag(user.getMerchantId(), CommonConstant.NORMAL_FLAG);
        if (wxConfig == null) {
            map.put("wx_sub_mch_id", null);
        } else {
            map.put("wx_sub_mch_id", wxConfig.getSubMchId());
        }

        if (aliConfig == null) {
            map.put("ali_pid", null);
        } else {
            map.put("ali_pid", aliConfig.getPid());
        }
        if (merchantAppletConfig == null) {
            map.put("wx_sub_appid", null);
        } else {
            map.put("wx_sub_appid", merchantAppletConfig.getAppId());
        }
        if (topConfig == null) {
            map.put("wx_appid", null);
            map.put("wx_mch_id", null);
            map.put("ali_app_id", null);
            map.put("ali_partner_id", null);
            map.put("ali_private_key", null);
        } else {
            map.put("wx_appid", topConfig.getWxAppId());
            map.put("wx_mch_id", topConfig.getWxMchId());
            map.put("ali_app_id", topConfig.getAliAppletAppId());
            map.put("ali_partner_id", topConfig.getAliPartnerId());
            map.put("ali_private_key", topConfig.getAliApplePrivateKey());
        }


        AdvertiseViewDTO advertiseViewDTO = new AdvertiseViewDTO();
        advertiseViewDTO.setMerchantId(user.getMerchantId());
        advertiseViewDTO.setFromRange(Integer.valueOf(8));
        List<Advertise> advertisesStartUp = (List)this.advertiseService.findAdv(advertiseViewDTO).getObj();

        advertiseViewDTO.setFromRange(Integer.valueOf(9));
        List<Advertise> advertisesMemberPay = (List)this.advertiseService.findAdv(advertiseViewDTO).getObj();

        advertiseViewDTO.setFromRange(Integer.valueOf(10));
        List<Advertise> advertisesPaySuccess = (List)this.advertiseService.findAdv(advertiseViewDTO).getObj();

        advertiseViewDTO.setFromRange(Integer.valueOf(12));
        List<Advertise> advertisesUnMemberPay = (List)this.advertiseService.findAdv(advertiseViewDTO).getObj();

        advertiseViewDTO.setFromRange(Integer.valueOf(13));
        List<Advertise> advertisesDep = (List)this.advertiseService.findAdv(advertiseViewDTO).getObj();

        List<Advertise> advertises = new ArrayList<Advertise>();
        advertises.addAll(advertisesStartUp);
        advertises.addAll(advertisesMemberPay);
        advertises.addAll(advertisesPaySuccess);
        advertises.addAll(advertisesUnMemberPay);
        advertises.addAll(advertisesDep);

        map.put("advertiseList", advertises);
        this.LOGINCACHE.put(uuid, map);

        return Resp.success("登陆成功");
    }

    @GetMapping("/login/config")
    @ApiOperation(value = "获取配置", notes = "获取配置")
    public Resp<Map> getConfig(@UserId String userId){
        Map<String, Object> map = new HashMap<>();
        MerchantUser user = merchantUserService.findOne(userId);
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        MerchantAppletConfig merchantAppletConfig = merchantAppletConfigService.findByMerchantId(user.getMerchantId());
        WxConfig wxConfig = wxConfigRepository.findByMerchantIdAndDelFlag(user.getMerchantId(), CommonConstant.NORMAL_FLAG);
        if (wxConfig == null) {
            map.put("wx_sub_mch_id", null);
        } else {
            map.put("wx_sub_mch_id", wxConfig.getSubMchId());
        }
        if (merchantAppletConfig == null) {
            map.put("wx_sub_appid", null);
        } else {
            map.put("wx_sub_appid", merchantAppletConfig.getAppId());
        }
        if (topConfig == null) {
            map.put("wx_appid", null);
            map.put("wx_mch_id", null);
        } else {
            map.put("wx_appid", topConfig.getWxAppId());
            map.put("wx_mch_id", topConfig.getWxMchId());
        }
        return Resp.success(map,"");
    }

    /**
     * 微擎登陆token
     *
     * @param userId
     * @return
     */
    @GetMapping("/weiqing_url")
    public Resp<String> weiqing(@UserId String userId) {
        if (ParamUtil.isBlank(userId)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "token有误，请重新登录");
        }
        MerchantUser user = merchantUserService.findOne(userId);
        Merchant merchant = merchantService.findOne(user.getMerchantId());
        MerchantAppletConfig merchantAppletConfig = merchantAppletConfigService.findByMerchantId(user.getMerchantId());
        WxConfig wxConfig = wxConfigRepository.findByMerchantIdAndDelFlag(user.getMerchantId(), CommonConstant.NORMAL_FLAG);
        if (wxConfig == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "当前商户未配置微信支付相关参数");
        }
        if (merchantAppletConfig == null) {
            return new Resp().error(Resp.Status.SUCCESS, "当前商户未配置小程序相关参数");
        }
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        Company oemCompany = companyService.findOne(serviceId);
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag(serviceId, CommonConstant.NORMAL_FLAG);
        SiteInfo siteInfo = siteInfoRepository.findByServiceProviderId(serviceId);
        if (siteInfo == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "服务商不存在");

        }

        String token = "";
        String firstItem = serviceId.substring(10, 11).equals("0") ? "1" : serviceId.substring(10, 11);
        String newServiceId = firstItem + serviceId.substring(11, 19);
        try {
            Map map = new HashMap();
            map.put("name", merchant.getName());
            map.put("account", user.getId() + "_" + user.getUsername());
            map.put("password", "123456");
            map.put("phone", user.getPhone());
            map.put("mch_id", wxConfig.getSubMchId());
            map.put("sub_appid", merchantAppletConfig.getAppId());
            map.put("oem_id", newServiceId);
            map.put("oem_mch_id", topConfig.getWxMchId());
            map.put("oem_appid", topConfig.getWxAppId());
            map.put("oem_key", topConfig.getWxAppKey());
            map.put("oem_name", oemCompany.getName());
            map.put("oem_secret", topConfig.getWxAppSecret());
            map.put("call_back_url", "http://" + siteInfo.getDomainName() + "/merchant/admin/app/weiqing_call_back");
            String jsonStr = JSONUtil.toJsonStr(map);
            token = "123123." + Base64.encode(jsonStr) + ".123123";

        } catch (Exception e) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "当前商户未配置小程序/微信支付相关参数");
        }
        String url = "http://" + siteInfo.getDomainName() + "/app/fp_smdc.php?i=" + newServiceId + "&c=entry&do=slzfLogin&m=dy_more&token=" + token;
        return Resp.success(url, "");
    }

    @PostMapping("/weiqing_call_back")
    @ApiOperation(value = "微擎扫码订餐回调地址")
    public void weiqingCallBack(@RequestBody WeiqingCallBackDTO weiqingCallBackDTO) {
        MerchantUser user = merchantUserService.findByUsername(weiqingCallBackDTO.getAccount());
        OrderDto orderDto = new OrderDto();
        Member member = memberRepository.findByOpenIdAndDelFlag(weiqingCallBackDTO.getOpenid(), CommonConstant.NORMAL_FLAG);
        if (member != null) {
            orderDto.setMemberId(member.getId());
        }
        //生成订单信息
        BeanUtil.copyProperties(weiqingCallBackDTO, orderDto);
        orderDto.setStatus(Order.Status.SUCCESSPAY.getCode());
        orderDto.setUserId(user.getId());
        orderDto.setStoreId(user.getStoreId());
        orderDto.setUserName(user.getUsername());
        orderDto.setStoreName(user.getStoreName());
        orderDto.setPayClient(Order.PayClient.APP.getCode());
        orderDto.setPayWay(Order.PayWay.WXPAY.getCode());
        orderDto.setMerchantId(user.getMerchantId());
        orderDto.setOutTradeNo(weiqingCallBackDTO.getTransactionId());
        orderDto.setTotalPrice(weiqingCallBackDTO.getTotalPrice());
        orderDto.setRemarks(weiqingCallBackDTO.getRemarks());
        orderDto.setOpenId(weiqingCallBackDTO.getOpenid());
        orderDto.setPayTime(weiqingCallBackDTO.getPayTime());
        orderService.createOrder(orderDto);
    }


    /**
     * @author drj
     * @date 2019-05-08 17:34
     * @Description :运营概况
     */
    @GetMapping("/business_info")
    public Resp businessInfo(String userType, String companyId) {
        Map<String, Object> map = new HashMap<>();
        //查询直联商户
        Integer merchantNum = merchantService.getRepository().countByCompanyIdAndDelFlag(companyId, CommonConstant.NORMAL_FLAG);
        map.put("merchantNum", merchantNum);
        //如果当前登录为一级代理商
        if ("2".equals(userType)) {
            //获取二级代理商数量
            List<String> stringList = authServiceFeign.findByOperaId(companyId);
            map.put("distributeNum", stringList.size());
            if (stringList.size() > 0) {
                //获取二级代理商关联商户
                List<Merchant> merchantList = merchantService.getRepository().findByCompanyIdInAndDelFlag(stringList.toArray(new String[0]), CommonConstant.NORMAL_FLAG);
                map.put("merchantNum", merchantNum + merchantList.size());
            }
            return Resp.success(map);
        }
        return Resp.success(map);
    }


    @GetMapping("/test")
    public Resp test() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        Optional<Integer> maxInteger = list.stream().max(Integer::compareTo);
        System.out.println(maxInteger);
        return Resp.success(maxInteger.get());

    }


    /*
     * @author drj
     * @date 2019-05-07 20:40
     * @Description :根据用户名查询手机号码
     */
    @GetMapping("/phone_find_by_username")
    public Resp phoneFindByUsername(String username) {
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        MerchantUser user = merchantUserService.getRepository().findByUsernameAndDelFlag(username, CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(user)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "用户名输入有误");
        }
        return Resp.success(user.getPhone(), "");
    }


    /*
     * @author drj
     * @date 2019-05-07 21:04
     * @Description :发送手机验证码
     */
    @PostMapping("/send_sms")
    public Resp sendSms(String phone) {
        String code = RandomUtil.randomNumbers(6);
        boolean flag = messageServiceFeign.sendSms(phone, code);
        if (!flag) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "短信发送失败,请重试");
        }
        //将手机号码跟验证码存入缓存中
        VerifyCodeApplicationRunner.verifyCodeCache.put(phone, code);
        return Resp.success("短信发送成功");
    }


    /*
     * @author drj
     * @date 2019-05-07 20:58
     * @Description :校验手机验证码
     */
    @PostMapping("/verify_code")
    public Resp verifyCode(String phone, String code) {
        //清理过期缓存
        VerifyCodeApplicationRunner.verifyCodeCache.prune();
        //从缓存中获取该手机的校验码
        String phoneCode = VerifyCodeApplicationRunner.verifyCodeCache.get(phone, false);
        if (ParamUtil.isBlank(phoneCode)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "验证码已过期,请重试");
        }
        if (!phoneCode.equals(code)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "验证码输入有误");
        }
        return Resp.success("验证码校验成功");
    }


    /*
     * @author drj
     * @date 2019-05-07 20:59
     * @Description :设置新密码
     */
    @PostMapping("reset_new_pwd")
    public Resp resetNewPwd(String username, String password) {
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        MerchantUser user = merchantUserService.getRepository().findByUsernameAndDelFlag(username, CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(user)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        if (ParamUtil.isBlank(password)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请输入密码");
        }
        if (!password.matches("^[a-zA-Z0-9_-]{6,20}$")) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "密码应为英文、数字、下划线组成的6-20位字符");
        }
        user.setPassword(BCrypt.hashpw(password));
        merchantUserService.save(user);
        return Resp.success("设置成功");
    }

    @GetMapping("/dynamic_password")
    @ApiOperation(value = "生成动态密码")
    public Resp<Integer> dynamicPassword(String device, String version,String password) {
        String str = device+version+password;
        String md5Str = ParamUtil.md5(str).toUpperCase().substring(0,6);
        Integer result =  Integer.parseInt(md5Str,16);
        return Resp.success(result);
    }


}
