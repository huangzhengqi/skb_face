package com.fzy.admin.fp.merchant.thirdMchInfo.controller;


import cn.hutool.http.HttpUtil;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.thirdMchInfo.domain.HsfMchInfo;
import com.fzy.admin.fp.merchant.thirdMchInfo.domain.HsfMchPhoto;
import com.fzy.admin.fp.merchant.thirdMchInfo.service.HsfMchInfoService;
import com.fzy.admin.fp.merchant.thirdMchInfo.service.HsfMchPhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 10:58 2019/6/27
 * @ Description:
 **/
@Slf4j
@RestController
@RequestMapping("/merchant/hsf_mch_photo")
public class HsfMchPhotoController extends BaseController<HsfMchPhoto> {


    @Resource
    private HsfMchPhotoService hsfMchPhotoService;

    @Resource
    private HsfMchInfoService hsfMchInfoService;

    @Override
    public HsfMchPhotoService getService() {
        return hsfMchPhotoService;
    }

    /*
     * @author drj
     * @date 2019-06-27 11:05
     * @Description :点击慧闪付进件图片
     */
    @GetMapping("/entry")
    public Resp entry(String merchantId) {
        if (ParamUtil.isBlank(merchantId)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        HsfMchPhoto hsfMchPhoto = hsfMchPhotoService.getRepository().findByMerchantId(merchantId);
        //如果慧闪付进件为空,创建一个
        if (ParamUtil.isBlank(hsfMchPhoto)) {
            hsfMchPhoto = new HsfMchPhoto();
            hsfMchPhoto.setMerchantId(merchantId);
            hsfMchPhotoService.save(hsfMchPhoto);
        }
        return Resp.success(hsfMchPhoto);
    }

    /*
     * @author drj
     * @date 2019-06-27 11:12
     * @Description :将第三方进件图片提交给慧闪付
     */
    @PostMapping("/internal/registernewmerchant")
    public Resp registernewmerchant(String merchantId) {
        if (ParamUtil.isBlank(merchantId)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        HsfMchInfo hsfMchInfo = hsfMchInfoService.getRepository().findByMerchantId(merchantId);
        if (ParamUtil.isBlank(hsfMchInfo)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "进件图片资料,请先完善");
        }
        if (ParamUtil.isBlank(hsfMchInfo.getShopId())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "shop_id为空,请先提交进件资料");
        }
        HsfMchPhoto hsfMchPhoto = hsfMchPhotoService.getRepository().findByMerchantId(merchantId);

        Map<String, Object> map = new HashMap<>();
        map.put("shop_id", hsfMchInfo.getShopId());
        //图片路径转文件
        File merchantHeadFile = new File(photoJudgeInfo(hsfMchPhoto.getMerchantHead()));
        File merchantCheckFile = new File(photoJudgeInfo(hsfMchPhoto.getMerchantCheck()));
        File otherPhoto3File = new File(photoJudgeInfo(hsfMchPhoto.getOtherPhoto3()));
        File identityFaceFile = new File(photoJudgeInfo(hsfMchPhoto.getIdentityFace()));
        File identityBackFile = new File(photoJudgeInfo(hsfMchPhoto.getIdentityBack()));
        File bussinessCardFile = new File(photoJudgeInfo(hsfMchPhoto.getBussinessCard()));
        File bussinessFile = new File(photoJudgeInfo(hsfMchPhoto.getBussiness()));
        File identityFaceCopyFile = new File(photoJudgeInfo(hsfMchPhoto.getIdentityFaceCopy()));
        File identityBackCopyFile = new File(photoJudgeInfo(hsfMchPhoto.getIdentityBackCopy()));
        File identityBodyFile = new File(photoJudgeInfo(hsfMchPhoto.getIdentityBody()));
        File otherPhoto4File = new File(photoJudgeInfo(hsfMchPhoto.getOtherPhoto4()));
        File otherPhoto2File = new File(photoJudgeInfo(hsfMchPhoto.getOtherPhoto2()));
        File otherPhotoFile = new File(photoJudgeInfo(hsfMchPhoto.getOtherPhoto()));
        File cardFaceFile = new File(photoJudgeInfo(hsfMchPhoto.getCardFace()));
        //初始化参数
        map.put("merchantHead", merchantHeadFile);
        map.put("identityBack ", merchantCheckFile);
        map.put("otherPhoto3", otherPhoto3File);
        map.put("identityFace", identityFaceFile);
        map.put("identityBack", identityBackFile);
        map.put("bussinessCard", bussinessCardFile);
        map.put("bussiness", bussinessFile);
        map.put("identityFaceCopy", identityFaceCopyFile);
        map.put("identityBackCopy", identityBackCopyFile);
        map.put("identityBody", identityBodyFile);
        map.put("otherPhoto4", otherPhoto4File);
        map.put("otherPhoto2", otherPhoto2File);
        map.put("otherPhoto", otherPhotoFile);
        map.put("cardFace", cardFaceFile);
        String result = HttpUtil.post("http://internal.congmingpay.com/internal/uploadmerchantimg.do"
                , map);
        Map<String, Object> resultMap = JacksonUtil.toObjectMap(result);
        if ("success".equals(resultMap.get("result_code"))) {
            log.info("进件成功");
            return Resp.success("操作成功");
        } else {
            hsfMchInfo.setStatus(HsfMchInfo.Status.SIGNFAIL.getCode());
            hsfMchInfoService.save(hsfMchInfo);
            return new Resp().error(Resp.Status.PARAM_ERROR, "图片审核失败");
        }
    }


    private String photoJudgeInfo(String photoInfo) {
        if (ParamUtil.isBlank(photoInfo)) {
            return "";
        }
        return photoInfo;
    }

}
