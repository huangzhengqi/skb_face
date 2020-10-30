package com.fzy.admin.fp.member.member.service;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.file.upload.service.UploadInfoMd5InfoRelationService;
import com.fzy.admin.fp.file.upload.vo.FileInfoVo;
import com.fzy.admin.fp.member.coupon.controller.WXUtil;
import com.fzy.admin.fp.member.member.domain.MemberCard;
import com.fzy.admin.fp.member.member.dto.MemberCardDTO;
import com.fzy.admin.fp.member.member.repository.MemberCardRepository;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.MerchantAppletConfig;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantAppletConfigRepository;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.pay.pay.domain.WxConfig;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantVO;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantBusinessService;
import com.fzy.admin.fp.sdk.pay.feign.AliConfigServiceFeign;
import com.fzy.admin.fp.sdk.pay.feign.WxConfigServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 14:27 2019/5/31
 * @ Description:
 **/
@Slf4j
@Service
@Transactional
public class MemberCardService implements BaseService<MemberCard> {

    @Resource
    private WxConfigServiceFeign wxConfigServiceFeign;

    @Resource
    private AliConfigServiceFeign aliConfigServiceFeign;

    private final String CARD_STATUS_NOT_VERIFY = "CARD_STATUS_NOT_VERIFY";
    private final String CARD_STATUS_VERIFY_FAIL = "CARD_STATUS_VERIFY_FAIL";
    private final String CARD_STATUS_VERIFY_OK = "CARD_STATUS_VERIFY_OK";
    private final String CARD_STATUS_DELETE = "CARD_STATUS_DELETE";
    private final String CARD_STATUS_DISPATCH = "CARD_STATUS_DISPATCH";
    private final String STATUS = "status";
    private final String ERRCODE = "errcode";
    private final String ERROR = "error";
    private final String ERRMSG = "errmsg";
    private final String ZERO = "0";

    @Value("${file.upload.path}")
    public String prefixPath;

    @Resource
    private MemberCardRepository memberCardRepository;

    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private MerchantService merchantService;

    @Resource
    private MerchantAppletConfigRepository merchantAppletConfigRepository;

    @Resource
    private UploadInfoMd5InfoRelationService uploadInfoMd5InfoRelationService;

    @Override
    public MemberCardRepository getRepository() {
        return memberCardRepository;
    }


    public Resp detail(String merchantId) {
        MemberCard memberCard = memberCardRepository.findByMerchantId(merchantId);
        //若会员卡信息为空,则无法查询会员卡
        if (ParamUtil.isBlank(memberCard)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "会员卡不存在，请创建会员卡");
        }
        //查询微信官方会员卡详情
        Merchant merchant = merchantService.findOne(merchantId);
        String appId = aliConfigServiceFeign.getAppId(merchant.getId());
        String appSecret = aliConfigServiceFeign.getAppSecret(merchant.getId());
        String accessToken = WXUtil.getAccessToken(appId, appSecret);
        JSONObject jsonObject = WXUtil.detailMemberCard(accessToken, memberCard.getCardId());
        if (!jsonObject.get(ERRCODE).toString().equals(ZERO)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请联系客服人员");
        }
        if (jsonObject.get(STATUS).toString().equals(CARD_STATUS_NOT_VERIFY)) {
            memberCard.setStatus(Integer.valueOf(1));
        } else if (jsonObject.get(STATUS).toString().equals(CARD_STATUS_VERIFY_FAIL)) {
            memberCard.setStatus(Integer.valueOf(2));
        } else if (jsonObject.get(STATUS).toString().equals(CARD_STATUS_VERIFY_OK)) {
            memberCard.setStatus(Integer.valueOf(3));
        } else if (jsonObject.get(STATUS).toString().equals(CARD_STATUS_DELETE)) {
            memberCard.setStatus(Integer.valueOf(4));
        } else if (jsonObject.get(STATUS).toString().equals(CARD_STATUS_DISPATCH)) {
            memberCard.setStatus(Integer.valueOf(5));
        }
        return Resp.success(memberCardRepository.saveAndFlush(memberCard), "查询成功");
    }


    /**
     * 获取accessToken
     *
     * @param appId
     * @param appSecret
     * @return
     */
    public String getAccessToken(String appId, String appSecret) {
        // 请求获取accessToken
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={}&secret={}";
        String result = HttpUtil.get(StrFormatter.format(url, appId, appSecret));
        System.err.println(result);
        Map<String, String> accessTokenMap = JacksonUtil.toStringMap(result);
        if (accessTokenMap.get(ERRCODE) != null) {
            throw new BaseException(accessTokenMap.get("errmsg"), Resp.Status.PARAM_ERROR.getCode());
        }
        return accessTokenMap.get("access_token");
    }

    /**
     * 获取cardId
     *
     * @param access_token
     * @param merchantName
     * @param appSourceId  原始id
     * @return
     */
    public String cardId(String access_token, String merchantName, String appSourceId) {
        String prefixUrl = "/pages/index/index";
        String appUserName = appSourceId + "@app";
        Map<String, Object> map = new TreeMap<String, Object>();
        Map<String, Object> cardMap = new TreeMap<String, Object>();
        cardMap.put("card_type", "MEMBER_CARD");
        Map<String, Object> memberCardMap = new TreeMap<String, Object>();
        Map<String, Object> baseInfoMap = new TreeMap<String, Object>();
        baseInfoMap.put("logo_url", "http://pay-adm.h5h5h5.cn/fms/upload/resource/1161544666510172160");
        baseInfoMap.put("brand_name", merchantName);
        baseInfoMap.put("code_type", "CODE_TYPE_QRCODE");
        baseInfoMap.put("title", "VIP会员");
        baseInfoMap.put("color", "Color010");
        baseInfoMap.put("notice", "请出示刷客宝2020二维码");
        baseInfoMap.put("description", "不可与其他优惠共享");
        Map<String, Object> skuMap = new TreeMap<String, Object>();
        skuMap.put("quantity", Integer.valueOf(50000000));
        baseInfoMap.put("sku", skuMap);
        Map<String, Object> dateInfoMap = new HashMap<String, Object>();
        dateInfoMap.put("type", "DATE_TYPE_PERMANENT");
        baseInfoMap.put("date_info", dateInfoMap);
        baseInfoMap.put("get_limit", Integer.valueOf(1));
        baseInfoMap.put("use_custom_code", Boolean.valueOf(false));
        baseInfoMap.put("can_give_friend", Boolean.valueOf(true));
        baseInfoMap.put("custom_url_name", "会员中心");
        baseInfoMap.put("custom_url", prefixUrl);
        baseInfoMap.put("custom_app_brand_user_name", appUserName);
        baseInfoMap.put("custom_app_brand_pass", prefixUrl);
        baseInfoMap.put("custom_url_sub_title", "");
        baseInfoMap.put("need_push_on_view", Boolean.valueOf(true));
        memberCardMap.put("base_info", baseInfoMap);
        memberCardMap.put("supply_bonus", Boolean.valueOf(true));
        memberCardMap.put("bonus_url", prefixUrl);
        memberCardMap.put("bonus_app_brand_user_name", appUserName);
        memberCardMap.put("bonus_app_brand_pass", prefixUrl);
        memberCardMap.put("supply_balance", Boolean.valueOf(false));
        memberCardMap.put("balance_rules", "储值说明");
        memberCardMap.put("balance_url", prefixUrl);
        memberCardMap.put("prerogative", "会员");
        memberCardMap.put("auto_activate", Boolean.valueOf(true));
        Map<String, Object> customField1Map = new TreeMap<String, Object>();
        customField1Map.put("url", prefixUrl);
        customField1Map.put("name", "余额");
        customField1Map.put("app_brand_user_name", appUserName);
        customField1Map.put("app_brand_pass", prefixUrl);
        memberCardMap.put("custom_fieldl", customField1Map);
        Map<String, Object> customField2Map = new TreeMap<String, Object>();
        customField2Map.put("url", prefixUrl);
        customField2Map.put("name", "优惠券");
        customField2Map.put("app_brand_user_name", appUserName);
        customField2Map.put("app_brand_pass", prefixUrl);
        memberCardMap.put("custom_field2", customField2Map);

        cardMap.put("member_card", memberCardMap);
        map.put("card", cardMap);
        String json = JacksonUtil.toJson(map);
        String result = HttpUtil.post("https://api.weixin.qq.com/card/create?access_token=" + access_token, json);
        Map<String, Object> resultMap = JacksonUtil.toObjectMap(result);
        if (resultMap.get(ERRCODE).equals(Integer.valueOf(0)) && resultMap.get(ERRMSG).equals("ok")) {
            log.info("card_id{} " + resultMap.get("card_id").toString());
            return resultMap.get("card_id").toString();
        }
        log.info("生成会员卡异常信息{} " + result);
        throw new BaseException((String) resultMap.get("errmsg"), Resp.Status.PARAM_ERROR.getCode());
    }


    //memberCard初始化
    public MemberCard memberCardInit(String companyId, String merchantId, String photoId) {
        MemberCard memberCard = new MemberCard();
        memberCard.setMerchantId(merchantId);
        memberCard.setMerchantPhotoId(photoId);
        memberCard.setCardNumber(RandomUtil.randomNumbers(12));

        //---------------创建同步到微信卡包url-----------------------------
        // 获取商户的appId和appSecret，AppID和AppSecret可在“微信公众平台-开发-基本配置”页中获得（需要已经成为开发者，且帐号没有异常状态）

        Map<String, String> wxConfigMap = wxConfigServiceFeign.getWxConfig(companyId);
        if ("error".equals(wxConfigMap.get("msg"))) {
            throw new BaseException("请先配置微信支付参数", Resp.Status.PARAM_ERROR.getCode());
        }
        memberCardRepository.save(memberCard);
        return memberCard;
    }

    public String apiTicket(String accessToken) {
        Map<String, Object> map = new TreeMap<>();
        map.put("access_token", accessToken);
        map.put("type", "wx_card");
        String result = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket", map);
        Map<String, Object> retMapper = new HashMap<>();
        retMapper = JacksonUtil.toObjectMap(result);
        if (retMapper.get("errcode").equals(0) && retMapper.get("errmsg").equals("ok")) {
            return retMapper.get("ticket").toString();
        } else {
            log.info("获取apiTicket请求参数异常{} " + retMapper.get("errmsg"));
            throw new BaseException((String) retMapper.get("errmsg"), Resp.Status.PARAM_ERROR.getCode());
        }
    }

    @Transactional
    public String cardDelete(String accessToken, String cardId) {

        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("card_id", cardId);
        String json = JacksonUtil.toJson(map);
        String result = HttpUtil.post("https://api.weixin.qq.com/card/delete?access_token=" + accessToken, json);
        Map<String, Object> resultMap = JacksonUtil.toObjectMap(result);
        log.info("resultMap -----------  " + resultMap);
        if (resultMap.get("errcode").equals(Integer.valueOf(0)) && resultMap.get("errmsg").equals("ok")) {
            log.info("删除成功");
            return resultMap.get("errmsg").toString();
        }
        log.info("删除失败" + result);
        throw new BaseException((String) resultMap.get("errmsg"), Resp.Status.PARAM_ERROR.getCode());
    }

    /**
     * 创建会员卡
     *
     * @param userId
     */
    public Resp createCard(String userId, MemberCardDTO memberCardDTO) {
        MerchantUser merchantUser = merchantUserService.findOne(userId);
        Merchant merchant = merchantService.findOne(merchantUser.getMerchantId());
        String appId = aliConfigServiceFeign.getAppId(merchant.getId());
        String appSecret = aliConfigServiceFeign.getAppSecret(merchant.getId());
        String accessToken = WXUtil.getAccessToken(appId, appSecret);
        MerchantAppletConfig merchantAppletConfig = this.merchantAppletConfigRepository.findByMerchantId(merchant.getId());
        if (ParamUtil.isBlank(merchantAppletConfig)) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "当前商户未配置小程序");
        }
        FileInfoVo photoIdVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(merchant.getPhotoId());
        if (photoIdVo == null) {
            throw new BaseException("请配置商户logo", Resp.Status.PARAM_ERROR.getCode());
        }
        String photoIdPath = prefixPath + photoIdVo.getPath();
        FileInfoVo backgroundPicUrlVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(memberCardDTO.getBackgroundPicUrl());
        if (backgroundPicUrlVo == null) {
            throw new BaseException("请配置会员卡背景图logo", Resp.Status.PARAM_ERROR.getCode());
        }
        String backgroundPicUrlPath = prefixPath + backgroundPicUrlVo.getPath();
        //创建会员卡参数
        String cardId = WXUtil.memberCardCreate(accessToken, merchant.getName(), merchantAppletConfig.getUserName(), WXUtil.upLoadImage(photoIdPath), WXUtil.upLoadImage(backgroundPicUrlPath), memberCardDTO);
        if (cardId.equals(ERROR)) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "会员卡参数错误,生成失败");
        }
        MemberCard memberCard = new MemberCard();
        memberCard.setAcceptWay(MemberCard.AcceptWay.FREE.getCode());
        memberCard.setBackgroundPictureId(memberCardDTO.getBackgroundPicUrl());
        memberCard.setName(memberCardDTO.getTitle());
        memberCard.setPhone(merchant.getPhone());
        memberCard.setCardNumber(cardId);
        memberCard.setCardId(cardId);
        memberCard.setMerchantId(merchant.getId());
        memberCard.setMerchantPhotoId(merchant.getPhotoId());
        memberCard.setCouponId("123");
        memberCard.setPresentScores(10);
        memberCard.setPrivilegeExplain(memberCardDTO.getPrerogative());
        memberCard.setDescription(memberCardDTO.getDescription());
        memberCard.setType(Integer.valueOf(1));
        return Resp.success(memberCardRepository.save(memberCard));
    }


    public Resp activateuserform(String userId, String cardId) {

        MerchantUser merchantUser = merchantUserService.findOne(userId);
        Merchant merchant = merchantService.findOne(merchantUser.getMerchantId());
        //获取accessToken
        Map<String, String> wxConfigMap = wxConfigServiceFeign.getWxConfig(merchant.getCompanyId());
        if ("error".equals(wxConfigMap.get("msg"))) {
            throw new BaseException("请先配置微信支付参数", Resp.Status.PARAM_ERROR.getCode());
        }
        String accessToken = getAccessToken(wxConfigMap.get("appId"), wxConfigMap.get("appSecret"));
        Map<String, Object> map = new TreeMap<String, Object>();

        map.put("card_id", "p0PbBw_B7RG5fP8zyu1sy3FLmJzE");
        Map<String, Object> serviceStatementMap = new TreeMap<String, Object>();
        serviceStatementMap.put("name", "会员守则");
        serviceStatementMap.put("url", "https://www.qq.com");


        Map<String, Object> requiredFormMap = new TreeMap<String, Object>();
        List list = new ArrayList();
        list.add("USER_FORM_INFO_FLAG_MOBILE");
        requiredFormMap.put("common_field_id_list", list);

        Map<String, Object> map3 = new TreeMap<String, Object>();
        map3.put("type", "FORM_FIELD_RADIO");
        map3.put("name", "手机");
        map3.put("values", "15706682319");
        List list2 = new ArrayList();
        list2.add(map3);

//        requiredFormMap.put("rich_field_list",richFieldList);


        map.put("service_statement", serviceStatementMap);
        map.put("required_form", requiredFormMap);

        String json = JacksonUtil.toJson(map);
        String result = HttpUtil.post("https://api.weixin.qq.com/card/membercard/activateuserform/set?access_token=" + accessToken, json);
        Map<String, Object> resultMap = JacksonUtil.toObjectMap(result);
        return Resp.success(resultMap);

    }

    public Resp cardQrcodeCreate(String merchantId) {
        MemberCard memberCard = memberCardRepository.findByMerchantId(merchantId);
        //若会员卡信息为空,则无法查询会员卡
        if (ParamUtil.isBlank(memberCard)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "会员卡不存在，请创建会员卡");
        }
        //查询微信官方会员卡详情
        Merchant merchant = merchantService.findOne(merchantId);
        String appId = aliConfigServiceFeign.getAppId(merchant.getId());
        String appSecret = aliConfigServiceFeign.getAppSecret(merchant.getId());
        String accessToken = WXUtil.getAccessToken(appId, appSecret);
        return Resp.success(WXUtil.cardQrcodeCreate(accessToken, memberCard.getCardId()));
    }

    public Resp testwhitelistSet(String merchantId, String openId) {
        //查询微信官方会员卡详情
        Merchant merchant = merchantService.findOne(merchantId);
        String appId = aliConfigServiceFeign.getAppId(merchant.getId());
        String appSecret = aliConfigServiceFeign.getAppSecret(merchant.getId());
        String accessToken = WXUtil.getAccessToken(appId, appSecret);
        return Resp.success(WXUtil.testwhitelistSet(accessToken,openId));
    }
}
