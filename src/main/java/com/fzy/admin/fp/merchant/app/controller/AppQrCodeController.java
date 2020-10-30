package com.fzy.admin.fp.merchant.app.controller;

import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.domain.BlankQrCode;
import com.fzy.admin.fp.merchant.merchant.domain.MchBlankQrCode;
import com.fzy.admin.fp.merchant.merchant.domain.MchQrCode;
import com.fzy.admin.fp.merchant.merchant.service.BlankQrCodeService;
import com.fzy.admin.fp.merchant.merchant.service.MchBlankQrCodeService;
import com.fzy.admin.fp.merchant.merchant.service.MchQrCodeService;
import com.fzy.admin.fp.merchant.merchant.service.StoreService;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 22:10 2019/5/26
 * @ Description: 商户APP二维码接口
 **/
@Slf4j
@RestController
@RequestMapping("/merchant/qrcode/app")
public class AppQrCodeController extends BaseContent {


    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private MchQrCodeService mchQrCodeService;

    @Resource
    private StoreService storeService;

    @Resource
    private BlankQrCodeService blankQrCodeService;

    @Resource
    private MchBlankQrCodeService mchBlankQrCodeService;

    /*
     * @author drj
     * @date 2019-05-27 16:42
     * @Description 根据用户id查询对应的用户列表)(不包含商户)
     */
    @GetMapping("/user/select_item/find_by_user_id")
    public Resp findByMerchantId(@UserId String userId) {
        return Resp.success(merchantUserService.selectItem(userId));
    }

    /*
     * @author drj
     * @date 2019-05-27 16:43
     * @Description :获取二维码列表
     */
    @GetMapping("/list")
    public Resp listRewrite(MchQrCode entity, PageVo pageVo, @UserId String userId) {
        return Resp.success(mchQrCodeService.listRewrite(entity, pageVo, userId));
    }

    /*
     * @author drj
     * @date 2019-05-27 16:43
     * @Description :添加二维码
     */
    @PostMapping("/save")
    @Transactional
    public Resp saveRewrite(MchQrCode entity, @UserId String userId, Integer flag, String blankQrCodeId) {
        entity = mchQrCodeService.saveRewrite(entity, userId);
        if (1 == flag) {
            //查看空码是否被激活
            BlankQrCode blankQrCode = blankQrCodeService.findOne(blankQrCodeId);
            if (!ParamUtil.isBlank(blankQrCode.getMchQrCodeId())) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "该二维码已经被激活");
            }
            blankQrCode.setMchQrCodeId(entity.getId());
            blankQrCodeService.save(blankQrCode);
        }
        return Resp.success(mchQrCodeService.saveRewrite(entity, userId));
    }

    /*
     * @author drj
     * @date 2019-05-27 16:43
     * @Description :修改二维码
     */
    @PostMapping("/update")
    public Resp updateRewrite(MchQrCode entity, @UserId String userId) {
        return Resp.success(mchQrCodeService.updateRewrite(entity, userId));
    }

    /*
     * @author drj
     * @date 2019-05-27 17:11
     * @Description :查看详情
     */
    @GetMapping("/detail")
    public Resp detail(String id) {
        MchQrCode mchQrCode = mchQrCodeService.findOne(id);
        if (ParamUtil.isBlank(mchQrCode)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        mchQrCode.setStoreName(storeService.findOne(mchQrCode.getStoreId()).getName());
        mchQrCode.setUserName(merchantUserService.findOne(mchQrCode.getUserId()).getName());
        return Resp.success(mchQrCode);
    }

    /*
     * @author drj
     * @date 2019-05-27 17:17
     * @Description :扫描空的二维码
     */
    @GetMapping("/scan_blank_qrcode")
    public Resp scanBlankCode(String blankQrCodeId) {
        BlankQrCode blankQrCode = blankQrCodeService.findOne(blankQrCodeId);
        if (ParamUtil.isBlank(blankQrCode)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        if (ParamUtil.isBlank(blankQrCode.getMchQrCodeId())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该二维码尚未激活");
        }
        MchQrCode mchQrCode = mchQrCodeService.findOne(blankQrCode.getMchQrCodeId());
        if (MchQrCode.Status.DISABLE.getCode().equals(mchQrCode.getStatus())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该二维码不存在");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("totalPrice", mchQrCode.getMoney());
        map.put("userId", mchQrCode.getUserId());
        map.put("storeId", mchQrCode.getStoreId());
        return Resp.success(map);
    }


    @GetMapping("/judge")
    public String judge(String id) throws IOException {
        MchQrCode mchQrCode = mchQrCodeService.findOne(id);
        if (ParamUtil.isBlank(mchQrCode)) {
            return "参数错误";
        }
        if (MchQrCode.Status.DISABLE.getCode().equals(mchQrCode.getStatus())) {
            return "该二维码不存在";
        }
        String gatherUrl = getDomain() + "/web/pay/index.html#/" + "?totalPrice=" + mchQrCode.getMoney() + "&userId=" + mchQrCode.getUserId();
        log.info("gatherUrl  ---- "   + gatherUrl);
        response.sendRedirect(gatherUrl);
        return "";
    }


    @ApiOperation(value = "扫描二维码" ,notes = "扫描二维码")
    @GetMapping("/get_qrcode")
    public String getQrCode(String id) throws IOException {
        MchBlankQrCode mchBlankQrCode=mchBlankQrCodeService.getRepository().findByQrCodeId(id);
        if (ParamUtil.isBlank(mchBlankQrCode)) {
            String gatherUrl = getDomain() + "/web/pay2/index.html#/" + "?userAgent=" + 412 ;
            response.sendRedirect(gatherUrl);
            return "";
        }
        if (MchQrCode.Status.DISABLE.getCode().equals(mchBlankQrCode.getStatus())) {
            String gatherUrl = getDomain() + "/web/pay2/index.html#/" + "?userAgent=" + 412 ;
            response.sendRedirect(gatherUrl);
            return "";
        }

        String userAgentType = null;
        String userAgent = request.getHeader("user-agent");
        if (userAgent != null && userAgent.contains("MicroMessenger")){
            userAgentType = "0";
        }else if (userAgent !=null && userAgent.contains("AlipayClient")){
            userAgentType = "2";
        }
        log.info("userAgent ----------------  " + userAgent);
        String gatherUrl = getDomain() + "/web/pay2/index.html#/" + "?userAgent=" + userAgentType + "&userId=" + mchBlankQrCode.getUserId();
        log.info("gatherUrl  ---- "   + gatherUrl);
        response.sendRedirect(gatherUrl);
        return userAgent;
    }
}
