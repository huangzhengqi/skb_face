package com.fzy.admin.fp.member.member.controller;


import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.helper.WxMapServiceHelper;
import com.fzy.admin.fp.member.member.domain.Member;
import com.fzy.admin.fp.member.member.dto.OpenIdAndKeyDTO;
import com.fzy.admin.fp.member.member.dto.WxMaSessionDTO;
import com.fzy.admin.fp.member.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import static com.fzy.admin.fp.member.MemberConstant.MEMBER_CODE_KEY_CACHE;

/**
 * @author Administrator
 * @description fzy小程序会员相关接口
 */
@RestController
@Slf4j
@RequestMapping("/applet/member/admin/")
@Api(value = "AppletMemberController", tags = {"小程序会员相关接口"})
public class AppletMemberController {

    @Resource
    private MemberService memberService;
    @Resource
    private WxOpenService wxOpenService;

    @Autowired
    private WxMapServiceHelper mapServiceHelper;

    /**
     * 检查用户是否注册
     *
     * @param openid
     * @return
     */
    @GetMapping("/check_user")
    @ApiOperation(value = "检查用户是否注册", notes = "检查用户是否注册")
    public Resp<Boolean> checkUserIsRegister(String openid) {
        Member member = memberService.findByOpenId(openid);
        if (member == null) {
            return Resp.success(false);
        } else {
            return Resp.success(true);
        }

    }

    /**
     * 获取微信小程序会话信息
     *
     * @param appId
     * @param code
     * @return
     */
    @GetMapping("/wx_ma_session")
    @ApiOperation(value = "获取微信小程序会话信息", notes = "获取微信小程序会话信息")
    public Resp<WxMaSessionDTO> getWxMaSessionInfo(@NotNull String appId, @NotNull String code, Integer type) {
        try {
            if (StringUtils.isEmpty(type) || type == 1) {
                //sessionKey
                WxMaJscode2SessionResult wxMaJscode2SessionResult = mapServiceHelper.getWxMaService(appId).jsCode2SessionInfo(code);
                WxMaSessionDTO wxMaSessionDTO = new WxMaSessionDTO();
                wxMaSessionDTO.setOpenId(wxMaJscode2SessionResult.getOpenid())
                        .setUnionId(wxMaJscode2SessionResult.getUnionid());
                MEMBER_CODE_KEY_CACHE.put(wxMaJscode2SessionResult.getOpenid(),
                        new OpenIdAndKeyDTO(wxMaJscode2SessionResult.getOpenid(), wxMaJscode2SessionResult.getSessionKey()));
                return Resp.success(wxMaSessionDTO);
            } else if (type == 2) {
                WxMaSessionDTO wxMaSessionDTO = new WxMaSessionDTO();
                try {
                    WxMaJscode2SessionResult wxMaJscode2SessionResult = wxOpenService.getWxOpenComponentService().miniappJscode2Session(appId, code);
                    wxMaSessionDTO.setOpenId(wxMaJscode2SessionResult.getOpenid());
                    wxMaSessionDTO.setUnionId(wxMaJscode2SessionResult.getUnionid());
                    MEMBER_CODE_KEY_CACHE.put(wxMaJscode2SessionResult.getOpenid(),
                            new OpenIdAndKeyDTO(wxMaJscode2SessionResult.getOpenid(), wxMaJscode2SessionResult.getSessionKey()));
                } catch (WxErrorException e) {
                    int errorCode = e.getError().getErrorCode();
                    String msg = "【微信错误码】:" + errorCode + ",【微信错误说明】:" + e.getError().getErrorMsg();
                    return new Resp().error(Resp.Status.PARAM_ERROR, msg);
                }

                return Resp.success(wxMaSessionDTO);

            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        Resp<WxMaJscode2SessionResult> resp = new Resp<WxMaJscode2SessionResult>();
        return resp.error(Resp.Status.INNER_ERROR, null);
    }

    /**
     * 获取用户信息
     *
     * @param appId
     * @param encryptedData
     * @param ivStr
     * @param openId
     * @return
     */
    @GetMapping("/user_info")
    @ApiOperation(value = "获取微信用户信息", notes = "获取用户信息")
    public Resp<WxMaUserInfo> getWxMaUserInfo(String appId,
                                              String encryptedData,
                                              String ivStr,
                                              String openId) {

        OpenIdAndKeyDTO openIdAndKeyDTO = MEMBER_CODE_KEY_CACHE.get(openId);

        if (ParamUtil.isBlank(openIdAndKeyDTO)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "系统错误，请重试");
        }

        WxMaUserInfo wxMaUserInfo = mapServiceHelper.getWxMaService(appId)
                .getUserService()
                .getUserInfo(openIdAndKeyDTO.getSessionKey(), encryptedData, ivStr);
        return Resp.success(wxMaUserInfo);
    }

    /**
     * 获取微信小程序用户手机号
     *
     * @param encryptData
     * @param ivStr
     * @param appId
     * @param openId
     * @return
     */
    @RequestMapping("/get_mobile_phone")
    @ApiOperation(value = "获取微信小程序用户手机号", notes = "获取微信小程序用户手机号")
    public Resp<String> getWxMaPhoneNumber(String encryptData,
                                           String ivStr,
                                           String appId,
                                           String openId) {
//        AppletConfigVO appletConfigVO = MemberConstant.MERCHANT_APPKEY_CACHE.get(appId);
        OpenIdAndKeyDTO openIdAndKeyDTO = MEMBER_CODE_KEY_CACHE.get(openId);
        if (ParamUtil.isBlank(openIdAndKeyDTO)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "系统错误，请重试");
        }

        try {
            WxMaPhoneNumberInfo wxMaPhoneNumberInfo = mapServiceHelper.getWxMaService(appId)
                    .getUserService()
                    .getPhoneNoInfo(openIdAndKeyDTO.getSessionKey(), encryptData, ivStr);
            return Resp.success(wxMaPhoneNumberInfo.getPhoneNumber(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Resp<WxMaJscode2SessionResult> resp = new Resp<WxMaJscode2SessionResult>();
        return resp.error(Resp.Status.INNER_ERROR, "解密数据失败");
    }

}
