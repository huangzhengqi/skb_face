package com.fzy.admin.fp.member.coupon.controller;

import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.file.upload.service.UploadInfoMd5InfoRelationService;
import com.fzy.admin.fp.file.upload.vo.FileInfoVo;
import com.fzy.admin.fp.member.coupon.domain.Coupon;
import com.fzy.admin.fp.member.coupon.domain.PersonCoupon;
import com.fzy.admin.fp.member.coupon.dto.CancelAfterVerificationListDTO;
import com.fzy.admin.fp.member.coupon.dto.CouponAnalysis;
import com.fzy.admin.fp.member.coupon.service.AnalysisService;
import com.fzy.admin.fp.member.coupon.service.CheckCodeService;
import com.fzy.admin.fp.member.coupon.service.CouponService;
import com.fzy.admin.fp.member.credits.utils.CorrectionService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantDefaultStore;
import com.fzy.admin.fp.sdk.merchant.domain.StoreInfo;
import com.fzy.admin.fp.sdk.merchant.feign.StoreServiceFeign;
import com.fzy.admin.fp.sdk.pay.feign.AliConfigServiceFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author lb
 * @date 2019/5/24 9:22
 * @Description
 */

@RestController
@RequestMapping(value = "/member/coupon")
@Api(value = "CouponController", tags = {"商户后台优惠券控制层"})
@Slf4j
public class CouponController extends BaseContent {

    @Resource
    private CouponService couponService;

    @Resource
    private CorrectionService correctionService;

    @Resource
    private CheckCodeService checkCodeService;

    @Resource
    private AnalysisService analysisService;

    @Resource
    private StoreServiceFeign storeServiceFeign;

    @Resource
    private MerchantService merchantService;

    @Resource
    private UploadInfoMd5InfoRelationService uploadInfoMd5InfoRelationService;

    @Resource
    private AliConfigServiceFeign aliConfigServiceFeign;

    @Resource
    private WXCodeUtil wxCodeUtil;

    private final String SUCCESS = "SUCCESS";
    private final String FAILURE = "FAILURE";

    @GetMapping(value = "/cancel_after_verification_list")
    @ApiOperation(value = "卡券核销", notes = "卡券核销")
    public Resp cancelAfterVerificationList(@TokenInfo(property = "merchantId") String merchantId, CancelAfterVerificationListDTO req, PageVo pageVo) {
        if (merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        List<Coupon> coupons = couponService.findByMerchantId(merchantId, 1);
        correctionService.correctionCoupon(coupons);
        req.setMerchantId(merchantId);
        List<PersonCoupon> orderList = couponService.cancelAfterVerificationList(req);
        Map<String, Object> content = new HashMap<>();
        Integer pageSize = pageVo.getPageSize();
        Integer pageNum = pageVo.getPageNumber();
        content.put("totalPage", orderList.size() % pageSize == 0 ? orderList.size() / pageSize : orderList.size() / pageSize + 1);
        content.put("totalElement", orderList.size());
        content.put("listData", orderList.stream()
                .skip((pageNum - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList()));
        return Resp.success(content);
    }


    @GetMapping(value = "/list_re")
    public Resp findByPage(@TokenInfo(property = "merchantId") String merchantId, Coupon coupon, PageVo pageVo) {
        if (merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        coupon.setMerchantId(merchantId);
        //查询卡券活动的
        coupon.setCardType(1);
        //未删除的
        coupon.setDelFlag(CommonConstant.NORMAL_FLAG);
        List<Coupon> coupons = couponService.findByMerchantId(merchantId, 1, coupon.getType());
        if (!coupons.isEmpty()) {
            correctionService.correctionCoupon(coupons);
        }
        Page<Coupon> page = couponService.list(coupon, pageVo);
        return Resp.success(page);
    }

    //根据绑卡赠券绑定唯一优惠券查，只能有一条数据
    @GetMapping(value = "/find_type_coupon")
    public Resp findType(@TokenInfo(property = "merchantId") String merchantId, Integer type) {
        List<Coupon> coupons = couponService.findByType(merchantId, type);
        if (coupons.isEmpty()) {
            return Resp.success("无卡券");
        } else {
            return Resp.success(coupons.get(0));
        }
    }

    @PostMapping(value = "/save_type_coupon")
    @ApiOperation(value = "根据绑卡赠券绑定唯一优惠券增改，只能有一条数据,储值规则同理", notes = "根据绑卡赠券绑定唯一优惠券增改，只能有一条数据,储值规则同理")
    public Resp saveTypeCoupon(@TokenInfo(property = "merchantId") String merchantId, Coupon coupon, String merchantName) {
        if (merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "token带参");
        }
        coupon.setMerchantId(merchantId);
        Integer cardType = coupon.getCardType();
        if (cardType != 2 && cardType != 3) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "接口不是给你的");
        }
        //新增前查唯一，有则逻辑删除无则新增
        List<Coupon> coupon1 = couponService.findByType(merchantId, cardType);
        if (!coupon1.isEmpty()) {
            for (Coupon coupon2 : coupon1) {
                coupon2.setDelFlag(CommonConstant.DEL_FLAG);
                couponService.update(coupon2);
            }
        }
        if (null == coupon.getInterrupt()) {
            coupon.setInterrupt(0);
        }
        if (null == coupon.getActStatus()) {
            if (null != coupon.getActTimeStart() && null != coupon.getActTimeEnd()) {
                long a = System.currentTimeMillis();
                long b = coupon.getActTimeStart().getTime();
                long c = coupon.getActTimeEnd().getTime();

                if (a < b) {
                    coupon.setActStatus(1);
                } else if (a > c) {
                    coupon.setActStatus(3);
                } else if (a > b && a < c) {
                    coupon.setActStatus(2);
                }
            } else {
                coupon.setActStatus(0);
            }
        }
        coupon.setChangeInventory(0);
        if (null == coupon.getClaimUpperLimit()) {
            coupon.setClaimUpperLimit(1);
        }

        if (null == coupon.getCouponSourceType()) {
            coupon.setCouponSourceType(2);
        }
        if (null == coupon.getRemindType()) {
            coupon.setRemindType(2);
        }

        if (null == coupon.getTotalInventory()) {
            coupon.setTotalInventory(99999);
        }

        if (null == coupon.getUseTimeDay() || ("-").equals(coupon.getUseTimeDay())) {
            coupon.setUseTimeDay("00:00-23:59");
        }

        if (null == coupon.getUseTimeWeek()) {
            coupon.setUseTimeWeek("1,2,3,4,5,6,7");
        }

        if (null == coupon.getStoreIds()) {
            MerchantDefaultStore merchantDefaultStore = storeServiceFeign.findDefaultByMchid(merchantId);
            if (null != merchantDefaultStore) {
                coupon.setStoreIds(merchantDefaultStore.getStoreId());
            }
        }

        if (null == coupon.getClaimedTime()) {
            coupon.setClaimedTime(0);
        }

        if (coupon.getType() == 1) {
            return verifyThenSaveCoupon(merchantId, coupon, merchantName);
        } else if (coupon.getType() == 2) {
            coupon = couponService.save(coupon);
            return Resp.success(coupon.getId(), "添加成功!");
        }
        return Resp.success(null, "");
    }

    private Resp verifyThenSaveCoupon(String merchantId, Coupon coupon, String merchantName) {
        checkInfo(coupon, merchantName, merchantId);
        //二维码
        String image;
        try {
            image = wxCodeUtil.getImageBuffer(coupon.getId(), merchantId);
            if (image == null) {
                throw new BaseException("推广图片生成失败", Resp.Status.PARAM_ERROR.getCode());
            }
        } catch (BaseException e) {
            throw new BaseException(e.getMessage(), Resp.Status.PARAM_ERROR.getCode());
        }
        coupon.setImage(image);
        couponService.save(coupon);
        return Resp.success(coupon.getId(), "添加成功!");
    }


    @GetMapping(value = "/look")
    public FileInfoVo getFile(String id) {
        FileInfoVo fileInfoVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(id);
        return fileInfoVo;
    }


    public Coupon checkInfo(Coupon coupon, String merchantName, String merchantId) {
        //判断是否关联微信
        if (coupon.getCouponSourceType() == 1) {
            String appId = aliConfigServiceFeign.getAppId(merchantId);
            String appSecret = aliConfigServiceFeign.getAppSecret(merchantId);

            Merchant merchant = merchantService.findOne(merchantId);
            WXUtil.getAccessToken(appId, appSecret);
            if (merchantName == null) {
                merchantName = "刷客宝";
            }
            String id = coupon.getPhotoId();
            FileInfoVo fileInfoVo = uploadInfoMd5InfoRelationService.getRepository().queryFileInfoVo(id);
            if (fileInfoVo == null) {
                log.info("没有拿到本地路径");
                throw new BaseException("没有拿到本地路径", Resp.Status.PARAM_ERROR.getCode());
            } else {
                String path = fileInfoVo.getPath();
                Map<String, String> cards = WXUtil.createCoupon(merchant, coupon, merchantName, WXUtil.upLoadImage(path));
                String cardId = cards.get("card_id");
                if (cardId == null) {
                    throw new BaseException("没有获取到cardId", Resp.Status.PARAM_ERROR.getCode());
                }
                coupon.setCardId(cardId);
            }

        }
        return coupon;
    }

    @GetMapping(value = "/find_one")
    public Resp findOne(String id) {
        if (id == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错哦");
        }
        Coupon coupon = couponService.findOne(id);
        return Resp.success(coupon);
    }

    @PostMapping(value = "/save_re")
    @ApiOperation(value = "创建新卡券", notes = "创建新卡券")
    public Resp saveCoupon(@TokenInfo(property = "merchantId") String merchantId, Coupon coupon
            , @RequestParam(value = "merchantName", required = false) String merchantName) {
        coupon.setMerchantId(merchantId);
        coupon.setCardType(Integer.valueOf(1));
        if (null == coupon.getInterrupt()) {
            coupon.setInterrupt(0);
        }
        if (null == coupon.getActStatus()) {
            if (null != coupon.getActTimeStart() && null != coupon.getActTimeEnd()) {
                long a = System.currentTimeMillis();
                long b = coupon.getActTimeStart().getTime();
                long c = coupon.getActTimeEnd().getTime();

                if (a < b) {
                    coupon.setActStatus(1);
                } else if (a > c) {
                    coupon.setActStatus(3);
                } else if (a > b && a < c) {
                    coupon.setActStatus(2);
                }
            } else {
                coupon.setActStatus(0);
            }
        }
        coupon.setChangeInventory(0);
        if (null == coupon.getClaimUpperLimit()) {
            coupon.setClaimUpperLimit(1);
        }

        if (null == coupon.getCouponSourceType()) {
            coupon.setCouponSourceType(2);
        }
        if (null == coupon.getRemindType()) {
            coupon.setRemindType(2);
        }

        if (null == coupon.getTotalInventory()) {
            coupon.setTotalInventory(99999999);
        }

        if (null == coupon.getUseTimeDay() || ("-").equals(coupon.getUseTimeDay())) {
            coupon.setUseTimeDay("00:00-23:59");
        }

        if (null == coupon.getUseTimeWeek()) {
            coupon.setUseTimeWeek("1,2,3,4,5,6,7");
        }

        if (null == coupon.getStoreIds()) {
            MerchantDefaultStore merchantDefaultStore = storeServiceFeign.findDefaultByMchid(merchantId);
            if (null != merchantDefaultStore) {
                coupon.setStoreIds(merchantDefaultStore.getStoreId());
            }
        }

        if (null == coupon.getClaimedTime()) {
            coupon.setClaimedTime(0);
        }

        if (coupon.getType() == 1) {
            return verifyThenSaveCoupon(merchantId, coupon, merchantName);
        } else if (coupon.getType() == 2) {
            coupon = couponService.save(coupon);
            return Resp.success(coupon.getId(), "添加成功");
        }
        return Resp.success(null, "");
    }

    @GetMapping(value = "/store_select")
    public Resp findStoreSelect(String id, @RequestParam(value = "name", required = false) String name) {
        if (id == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错哦");
        }
        Coupon coupon = couponService.findOne(id);
        //获取门店id
        String storeIds = coupon.getStoreIds();
        String[] stores = storeIds.split(",");
        System.out.println(stores.length + "" + stores[0]);
        List<StoreInfo> storeInfos = storeServiceFeign.findStoreInfoList(stores);
        if (storeInfos.get(0).getName().equals("不存在")) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "门店一个都不存在！");
        }
        if (name != null) {
            List<StoreInfo> storeInfoList = new ArrayList<>();
            Pattern pattern = Pattern.compile(name);
            for (int i = 0; i < storeInfos.size(); i++) {
                Matcher matcher = pattern.matcher(storeInfos.get(i).getName());
                if (matcher.find()) {
                    storeInfoList.add(storeInfos.get(i));
                }
            }
            return Resp.success(storeInfoList);
        }
        return Resp.success(storeInfos);
    }

    /**
     * 卡券二维码
     *
     * @param couponId
     * @return
     */
    @GetMapping(value = "/get_image")
    @ApiOperation(value = "卡券二维码", notes = "卡券二维码")
    public Resp getImage(String couponId) {
        Coupon coupon = couponService.findOne(couponId);
        String image = coupon.getImage();
        return Resp.success(image, "二维码拿着");
    }

    //-----------------------------卡券营销分析------------------------------

    @GetMapping(value = "/analysis")
    public Resp getAnalysis(@TokenInfo(property = "merchantId") String merchantId, String name) {
        if (name == null || merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数无法查询");
        }
        List<Coupon> coupons = couponService.findByMerchantIdAndName(merchantId, name);
        if (coupons.isEmpty()) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "找不到该卡券无法分析");
        }
        if (coupons.size() > 1) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "卡券名称定位不准确无法分析");
        }
        //营销逻辑处理
        CouponAnalysis couponAnalysis = analysisService.getAnalysis(merchantId, coupons);

        return Resp.success(couponAnalysis, "返回的分析数据！");

    }

    @GetMapping(value = "/check_name")
    public Resp checkCouponName(@TokenInfo(property = "merchantId") String merchantId, String couponName) {
        if (merchantId == null || couponName == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请输入鉴定卡券名称");
        }
        List<Coupon> couponList = couponService.findByMerchantIdAndName(merchantId, couponName);
        if (couponList.isEmpty()) {
            return Resp.success("可以使用");
        } else {
            return Resp.success("卡券名称已使用");
        }
    }

    @GetMapping(value = "/list_re_all")
    public Resp getCouponList(@TokenInfo(property = "merchantId") String merchantId, Coupon coupon, PageVo pageVo) {
        if (merchantId == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        System.out.println(pageVo.getPageNumber() + "-----" + pageVo.getPageSize());
        coupon.setMerchantId(merchantId);
        //查询卡券活动的
        coupon.setCardType(1);
        //未删除的
        coupon.setDelFlag(CommonConstant.NORMAL_FLAG);
        List<Coupon> coupons = couponService.findByMerchantId(merchantId, 1);
        correctionService.correctionCoupon(coupons);

        Page<Coupon> page = couponService.fingByActIn(merchantId, pageVo);
        return Resp.success(page);
    }

    @GetMapping({"/my_list_re"})
    @ApiOperation(value = "设备会员支付显示的获取优惠券接口", notes = "设备会员支付显示的获取优惠券接口")
    public Resp findMyCouponList(@TokenInfo(property = "merchantId") String merchantId, Coupon coupon, PageVo pageVo) {
        if (merchantId == null) {
            return (new Resp()).error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        String storeId = coupon.getStoreIds();
        coupon.setStoreIds(null);
        coupon.setMerchantId(merchantId);
        coupon.setCardType(Integer.valueOf(1));
        coupon.setDelFlag(CommonConstant.NORMAL_FLAG);
        List<Coupon> coupons = this.couponService.findByMerchantId(merchantId, coupon.getCardType(), coupon.getType());
        if (!coupons.isEmpty()) {
            this.correctionService.correctionCoupon(coupons);
        }

        Page<Coupon> page = this.couponService.list(coupon, pageVo);
        List<Coupon> collect = (List) page.getContent().stream().filter(coupon1 -> (coupon1.getStoreIds().contains(storeId) && !this.checkCodeService.validCoupons(coupon1.getUseTimeWeek(), coupon1.getUseTimeDay()))).collect(Collectors.toList());
        return Resp.success(collect);
    }

    @ApiOperation(value = "修改库存接口", notes = "修改库存接口")
    @PostMapping("/update")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", dataType = "String", value = "卡券id"),
            @ApiImplicitParam(paramType = "query", name = "type", dataType = "Integer", value = "1 减少 2增加"),
            @ApiImplicitParam(paramType = "query", name = "number", dataType = "Integer", value = "数量")})
    public Resp update(@UserId String userId, String id, Integer type, Integer number) {
        if (ParamUtil.isBlank(userId)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "TOKEN错误");
        }
        Coupon coupon = couponService.findOne(id);
        Integer totalInventory = coupon.getTotalInventory();
        //增加
        if (type.equals(Integer.valueOf(2))) {
            totalInventory = totalInventory + number;
        } else {
            totalInventory = totalInventory - number;
        }
        coupon.setTotalInventory(totalInventory);
        String status = couponService.updateCardModifystock(type, userId, number, coupon.getCardId());
        if (status.equals(SUCCESS)) {
            return Resp.success(couponService.update(coupon), "修改库存成功");

        }
        return new Resp().error(Resp.Status.PARAM_ERROR, "修改库存失败");
    }

}