package com.fzy.admin.fp.merchant.merchant.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.FootInfo;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.auth.service.FootInfoService;
import com.fzy.admin.fp.auth.service.UserService;
import com.fzy.admin.fp.auth.vo.MerchantVO;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.domain.City;
import com.fzy.admin.fp.common.domain.Province;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.repository.CityRepository;
import com.fzy.admin.fp.common.repository.ProvinceRepository;
import com.fzy.admin.fp.common.spring.base.BaseController;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.Validation;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.validation.domain.BindingResult;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.member.member.service.MemberService;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.MerchantAppletConfigVO;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.MerchantAppletConfig;
import com.fzy.admin.fp.merchant.merchant.dto.MchBlankQrCodeDTO;
import com.fzy.admin.fp.merchant.merchant.dto.MerchantRebateListDTO;
import com.fzy.admin.fp.merchant.merchant.dto.SetMerchantRebateDTO;
import com.fzy.admin.fp.merchant.merchant.dto.SetMerchantRewardDTO;
import com.fzy.admin.fp.merchant.merchant.service.MchBlankQrCodeService;
import com.fzy.admin.fp.merchant.merchant.service.MchInfoService;
import com.fzy.admin.fp.merchant.merchant.service.MerchantAppletConfigService;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.sdk.auth.feign.AuthServiceFeign;
import com.fzy.admin.fp.sdk.merchant.domain.MerchantListVo;
import com.fzy.admin.fp.wx.domain.WxRedPackDetail;
import com.fzy.admin.fp.wx.domain.WxRewardDetail;
import com.fzy.admin.fp.wx.service.WxRedPackDetailService;
import com.fzy.admin.fp.wx.service.WxRewardDetailService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Created by wtl on 2019-04-18 10:19:37
 * @description 商户控制层
 */
@Slf4j
@RestController
@RequestMapping("/merchant/merchant")
public class MerchantController extends BaseController<Merchant> {

    @Resource
    private MchInfoService mchInfoService;

    @Resource
    private MerchantService merchantService;

    @Resource
    private AuthServiceFeign authServiceFeign;

    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private MerchantAppletConfigService merchantAppletConfigService;

    @Resource
    private FootInfoService footInfoService;

    @Resource
    private CompanyService companyService;

    @Resource
    private CityRepository cityRepository;

    @Resource
    private MchBlankQrCodeService mchBlankQrCodeService;

    @Resource
    private ProvinceRepository provinceRepository;

    @Resource
    private UserService userService;

    @Resource
    private WxRedPackDetailService wxRedPackDetailService;

    @Resource
    private WxRewardDetailService wxRewardDetailService;

    @Value("${secret.header}")
    private String header;


    @Resource
    private MemberService memberService;

    @Override
    public MerchantService getService() {
        return merchantService;
    }

    @PostMapping("/save_applet_config")
    public Resp saveAppletConfig(@Valid MerchantAppletConfig entity) {
        final MerchantAppletConfig byAppId = merchantAppletConfigService.findByAppId(entity.getAppId());
        //若该APPID已存在且修改者不是该APPID数据持有人，则返回APPID已占用
        if (byAppId != null && !byAppId.getMerchantId().equals(entity.getMerchantId())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该AppId已被占用");
        }
        MerchantAppletConfig merchantAppletConfig = merchantAppletConfigService.findByMerchantId(entity.getMerchantId());
        if (merchantAppletConfig == null) {
            //如果为空，则需要进行新建数据
            merchantAppletConfig = entity;
        } else {
            merchantAppletConfig.setAppId(entity.getAppId());
            merchantAppletConfig.setAppKey(entity.getAppKey());
        }
        merchantAppletConfigService.save(merchantAppletConfig);
        return Resp.success("小程序信息配置成功");
    }

    @Override
    public Resp update(Merchant entity) {
        Merchant merchant = merchantService.findOne(entity.getId());
        if (merchant == null) {
            throw new BaseException("查无此用户", Resp.Status.PARAM_ERROR.getCode());
        }
        //获取Hutool拷贝实例
        CopyOptions copyOptions = CopyOptions.create();
        //忽略为null值得属性
        copyOptions.setIgnoreNullValue(true);
        //进行属性拷贝
        BeanUtil.copyProperties(entity, merchant, copyOptions);
        //对实体类中的@validation注解进行校验
        BindingResult bindingResult = Validation.valid(merchant);
        if (!bindingResult.isFlag()) {
            throw new BaseException(bindingResult.getMessage().get(0), Resp.Status.PARAM_ERROR.getCode());
        }
        //查询该商户对应登录用户信息
        MerchantUser merchantUser = merchantUserService.findByUsername(merchant.getPhone());
        merchantUser.setName(merchant.getContact());
        merchantUserService.save(merchantUser);
        return Resp.success("操作成功");
    }


    @GetMapping("/re_list")
    public Resp list(Merchant entity, PageVo pageVo, @TokenInfo(property = "companyId") String companyId) {
        Page<Merchant> page = null;
        //获取服务商id
        final String serviceProviderId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        entity.setServiceProviderId(serviceProviderId);
        //判断当前登录用户对应角色信息  如果是服务商就查询全部商户 （包括分销）
        Company company = companyService.findOne(companyId);
        //服务商查询
        if (company.getType().equals(Company.Type.PROVIDERS.getCode())) {
            page = getService().getPageService(entity, pageVo, companyId);

        } else {
            page = getService().getPage(entity, pageVo, companyId);
        }

        Map<String, String> userNameMap = authServiceFeign.getUserName();
        Map<String, String> distUserName = authServiceFeign.getDistUserName(); //分销业务员
        Map<String, String> companyMap = authServiceFeign.getCompanyName();
        if (page != null) {
            for (Merchant merchant : page) {
                final MerchantAppletConfig config = merchantAppletConfigService.findByMerchantId(merchant.getId());
                if (!ParamUtil.isBlank(config)) {
                    merchant.setMerchantAppletConfigVO(new MerchantAppletConfigVO(config.getAppId(), config.getAppKey()));
                } else {
                    merchant.setMerchantAppletConfigVO(new MerchantAppletConfigVO());
                }

                //分销
                if (merchant.getType().equals(Merchant.Type.DIST.getCode())) {
                    merchant.setManagerName(distUserName.get(merchant.getManagerId())); //分销业务员
                } else {
                    merchant.setManagerName(userNameMap.get(merchant.getManagerId()));
                }
                if (merchant.getCompanyId() == null) {
                    merchant.setCompanyName("分销");
                } else {
                    merchant.setCompanyName(companyMap.get(merchant.getCompanyId()));
                }
            }
        }

        return Resp.success(page);
    }

    @GetMapping({"/merchant_detail_list"})
    public Resp merchantDetailList(PageVo pageVo, String companyId) {
        return Resp.success(this.merchantService.getDirectMerchantPage(pageVo, companyId));
    }

    @PostMapping("/save_rewrite")
    public Resp saveRewrite(@Valid Merchant model, @RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId) {
//        if (model.getPayProrata().compareTo(BigDecimal.ZERO) <= 0) {
//            throw new BaseException("利率要大于0", Resp.Status.PARAM_ERROR.getCode());
//        }
        model.setStatus(2);
        model.setType(0);
        model.setServiceProviderId((String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID)); //getAttribute：返回指定属性的属性值
        return Resp.success(merchantService.saveRewrite(model, serviceProviderId));
    }


    @GetMapping("/find_one")
    public Resp<MerchantVO> findOne(String merchantId) {
        if (ParamUtil.isBlank(merchantId)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        Merchant merchant = merchantService.findOne(merchantId);
        if (!ParamUtil.isBlank(merchant)) {
            //获取业务员名称
            Map<String, String> map;
            map = authServiceFeign.getUserName();
            merchant.setManagerName(map.get(merchant.getManagerId()) == null ? "" : map.get(merchant.getManagerId()));
            merchant.setEndCooperaTime(new Date(merchant.getCreateTime().getTime() + 31622400000L));//合作到期

        }
        MerchantVO merchantVO = new MerchantVO();
        BeanUtil.copyProperties(merchant, merchantVO);
        try {
            Integer.parseInt(merchant.getProvince());
            Province province = provinceRepository.findOne(merchant.getProvince());
            merchantVO.setProvinceName(province.getProvinceName());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            Integer.parseInt(merchant.getCity());
            City city = cityRepository.findOne(merchant.getCity());
            merchantVO.setCityName(city.getCityName());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        MchInfo mchInfo = mchInfoService.findByMerchantId(merchantId);
        merchantVO.setIsOpenElectronicInvoice(mchInfo.getIsOpenElectronicInvoice());
        return Resp.success(merchantVO);
    }

    /*
     * @author drj
     * @date 2019-04-24 17:28
     * @Description:根据一级代理商id或者二级代理商id查询商户列表
     */
    @GetMapping("/find_by_company_id")
    public Resp findByCompanyId(Merchant model, PageVo pageVo, String userType, Integer channel, @UserId String userId) {
        return Resp.success(merchantService.findByCompanyId(model, pageVo, userType, channel, userId));
    }

    @GetMapping("/find_foot_info")
    public Resp findFootInfo(@UserId String userId) {
        MerchantUser merchantUser = merchantUserService.findOne(userId);
        FootInfo footInfo = footInfoService.getRepository().findByServiceProviderId(merchantUser.getServiceProviderId());
        return Resp.success(footInfo);
    }

    @GetMapping("/find_applet_config")
    public Resp findAppletConfig(String merchantId) {
        if (ParamUtil.isBlank(merchantId)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        return Resp.success(merchantAppletConfigService.findByMerchantId(merchantId));
    }


    @PostMapping("/cancle")
    public Resp disable(String merchantId, @RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId) {
        merchantService.cancel(merchantId, serviceProviderId);
        return Resp.success("操作成功");
    }

    /*
     * @author drj
     * @date 2019-04-28 17:22
     * @Description :获取对应公司的商户下拉框
     */
    @GetMapping("/select_item_find_by_company_id")
    public Resp selectItemFindByCompanyId(String companyId, String name, PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        return Resp.success(merchantService.getRepository().selectItem(companyId, name, pageable));
    }

    /*
     * @author drj
     * @date 2019-05-31 10:18
     * @Description :修改商户头像
     */
    @PostMapping("/update_photo")
    public Resp updatePhoto(@TokenInfo(property = "merchantId") String merchantId, String photoId) {
        Merchant merchant = merchantService.findOne(merchantId);
        merchant.setPhotoId(photoId);
        merchantService.save(merchant);
        return Resp.success("操作成功");
    }

    /**
     * 商户头像回显
     *
     * @param merchantId
     * @return
     */
    @GetMapping("/show_photo")
    public Resp showPhoto(@TokenInfo(property = "merchantId") String merchantId) {
        Merchant merchant = merchantService.findOne(merchantId);
        if (ParamUtil.isBlank(merchant.getPhotoId())) {
            return Resp.success("", "");
        }
        return Resp.success(merchant.getPhotoId());
    }


    @GetMapping("/mch/select_item")
    public Resp mchSelectItem() {
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        return Resp.success(merchantService.getRepository().mchSelectItem(serviceId));
    }


    //初始化商户对应登录用户密码
    @PostMapping("/default/reset_password")
    public Resp defaultResetPassword(String username) {
        merchantService.defaultResetPassword(username);
        return Resp.success("新密码重置为123456");
    }


    //一级代理商或二级代理商,获取商户慧闪付进件列表信息
    @GetMapping("/hsf_mch_info/find_by_company_id")
    public Resp findHsfMchInfoByCompanyId(@TokenInfo(property = "companyId") String companyId, Merchant model, PageVo pageVo) {
        model.setCompanyId(companyId);
        return Resp.success(merchantService.findByHsfMchInfo(model, pageVo));
    }

    //获服务商取商户慧闪付进件列表信息
    @GetMapping("/hsf_mch_info/find_by_fuwushang")
    public Resp findHsfMchInfoByFuwushang(Merchant model, PageVo pageVo, @RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId) {
        model.setServiceProviderId(serviceProviderId); //设置服务商id
        return Resp.success(merchantService.findByHsfMchInfo(model, pageVo));
    }

    /**
     * 根据登录用户获取商户名称
     */
    @GetMapping("/getMerchantName")
    public Resp findMerchantName(String userId) {
        MerchantUser merchantUser = merchantUserService.findOne(userId);
        if (merchantUser == null) {
            throw new BaseException("参数错误,未找到用户", Resp.Status.PARAM_ERROR.getCode());
        } else {
            Merchant merchant = merchantService.findOne(merchantUser.getMerchantId());
            if (merchant == null) {
                throw new BaseException("参数错误，未找到商户", Resp.Status.PARAM_ERROR.getCode());
            }
            return Resp.success(merchant.getName(), merchant.getName());
        }

    }

    /**
     * 保存电子发票设置
     */
    @PostMapping("/saveElectronicInvoiceSettings")
    public Resp saveElectronicInvoiceSettings(String merchantId, BigDecimal min, BigDecimal max) {
        Merchant merchant = merchantService.findOne(merchantId);
        if (merchant == null) {
            throw new BaseException("参数错误，未找到商户", Resp.Status.PARAM_ERROR.getCode());
        }
        merchant.setMinElectronicInvoiceOrderPrice(min);
        merchant.setMaxElectronicInvoiceOrderPrice(max);
        return super.update(merchant);
    }

    /**
     * 通过服务商id查自己的商户类型
     *
     * @return
     */
    @GetMapping("/findBusinessThree")
    public Resp findBusinessThree(@TokenInfo(property = "companyId") String companyId) {
        return Resp.success(merchantService.getMerchantType(companyId));
    }

    /**
     * 获取我的商户
     */
    @GetMapping("/ger_merchant_list")
    public Resp getMerchantList(String companyId, Integer payPriceType, Integer commissionType) {

        Map<String, Object> map = new HashMap<>();

        Company company = companyService.findOne(companyId);
        if (company == null || company.equals("")) {
            return Resp.success("当前部门不存在");
        }

        List<MerchantListVo> listVoList;

        listVoList = companyService.get_merchant_list(company);

        if (listVoList.size() <= 0) {
            return Resp.success("当前无数据");
        }

        //销售总额顺序
        if (payPriceType.equals(1)) {
            listVoList.sort(Comparator.comparing(MerchantListVo::getCountPayPrice));
        } else {
            listVoList.sort(Comparator.comparing(MerchantListVo::getCountPayPrice).reversed());
        }

        //佣金总额逆序
        if (commissionType.equals(1)) {
            listVoList.sort(Comparator.comparing(MerchantListVo::getCommissionMoney));
        }

        map.put("merchantNum", listVoList.size());//商户总数
        map.put("listVoList", listVoList);

        return Resp.success(map, "操作成功");
    }

    /**
     * 代理发展商户
     */
    @GetMapping("/get_proxy_merchant_list")
    public Resp getProxyMerchantList(String companyId, Integer type, Integer payPriceType, Integer merchantNumType) {

        Map<String, Object> map = new HashMap<>();

        Company company = companyService.findOne(companyId);
        if (company == null || company.equals("")) {
            return Resp.success("当前部门不存在");
        }

        List<MerchantListVo> listVoList = new ArrayList<>();

        listVoList = companyService.getProxyMerchantList(company, type);

        if (listVoList.size() <= 0) {
            return Resp.success("当前无数据");
        }

        int sum = listVoList.stream().mapToInt(MerchantListVo -> MerchantListVo.getMerchantNum()).sum();


//        //销售金额（顺序）
        if (payPriceType.equals(1)) {
            listVoList.sort(Comparator.comparing(MerchantListVo::getCountPayPrice));
        } else {
            listVoList.sort(Comparator.comparing(MerchantListVo::getCountPayPrice).reversed());
        }

        //商户数
        if (merchantNumType.equals(1)) {
            listVoList.sort(Comparator.comparing(MerchantListVo::getMerchantNum));
        } else {
            listVoList.sort(Comparator.comparing(MerchantListVo::getMerchantNum).reversed());
        }

        map.put("merchantNum", sum);
        map.put("listVoList", listVoList);
        return Resp.success(map, "返回成功");
    }


    @ApiOperation(value = "商户绑定二维码", notes = "商户绑定二维码")
    @PostMapping("/save_merchant_qrcode")
    public Resp saveMerchantQrcode(MchBlankQrCodeDTO mchBlankQrCodeDTO) {
        return Resp.success(mchBlankQrCodeService.saveMerchantQrcode(mchBlankQrCodeDTO));
    }

    @ApiOperation(value = "商户回显二维码id", notes = "商户回显二维码id")
    @GetMapping("/query_mer_qrcode")
    public Resp queryMerQrcode(String merchantId) {
        return Resp.success(mchBlankQrCodeService.queryMerQrcode(merchantId));
    }

    @ApiOperation(value = "编辑二维码", notes = "编辑二维码")
    @PostMapping("/update_merchant_qrcode")
    public Resp updateMerchantQrcode(MchBlankQrCodeDTO mchBlankQrCodeDTO) {
        return Resp.success(mchBlankQrCodeService.updateMerchantQrcode(mchBlankQrCodeDTO));
    }

    @ApiOperation(value = "商户返佣列表", notes = "商户返佣列表")
    @ApiResponse(code = 200,message = "OK",response = Merchant.class)
    @GetMapping("/rebate/list")
    public Resp rebateList(@UserId String userId, MerchantRebateListDTO merchantRebateListDTO, PageVo pageVo) {
        if (ParamUtil.isBlank(userId)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, Resp.Status.TOKEN_ERROR.getMsg());
        }
        User user = userService.findOne(userId);
        if (ParamUtil.isBlank(user)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "当前用户不存在");
        }
        Company company = companyService.findOne(user.getCompanyId());
        return merchantService.rebateList(company, merchantRebateListDTO, pageVo);
    }

    @ApiOperation(value = "批量设置返佣/单独设置返佣", notes = "批量设置返佣/单独设置返佣")
    @ApiResponse(code = 200,message = "OK",response = Merchant.class)
    @PostMapping("/set")
    public Resp set(String merchantIds, SetMerchantRebateDTO setMerchantRebateDTO) {
        if(setMerchantRebateDTO.getSiteRate().compareTo(new BigDecimal("0.0039")) != -1){
            throw new BaseException("费率不能超过0.38", Resp.Status.PARAM_ERROR.getCode());
        }
        if(setMerchantRebateDTO.getSiteRate().compareTo(new BigDecimal("0.0001")) == -1){
            throw new BaseException("费率不能低于0.01", Resp.Status.PARAM_ERROR.getCode());
        }
        String[] split = merchantIds.split(",");
        List<String> list = new ArrayList<String >();
        for (int i = 0; i < split.length; i++) {
            list.add(split[i]);
        }
       return merchantService.set(list,setMerchantRebateDTO);
    }

    @ApiOperation(value = "批量设置奖励/单独设置奖励", notes = "批量设置奖励/单独设置奖励")
    @ApiResponse(code = 200,message = "OK",response = Merchant.class)
    @PostMapping("/set/reward")
    public Resp setReward(String merchantIds, SetMerchantRewardDTO setMerchantRewardDTO) {

        String[] split = merchantIds.split(",");
        List<String> list = new ArrayList<String >();
        for (int i = 0; i < split.length; i++) {
            list.add(split[i]);
        }
        return merchantService.setReward(list,setMerchantRewardDTO);
    }

    @ApiOperation(value = "返佣记录",notes = "返佣记录")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",name = "merchantId",dataType = "String",value = "商户id")})
    @ApiResponse(code = 200,message = "OK",response = WxRedPackDetail.class)
    @GetMapping("/rebate/record")
    public Resp<List<WxRedPackDetail>> rebateRecord(String merchantId){
        if(ParamUtil.isBlank(merchantId)){
            return new Resp().error(Resp.Status.PARAM_ERROR,"商户id不能为空");
        }
        return Resp.success(wxRedPackDetailService.getRepository().findByMerchantIdOrderByCreateTimeDesc(merchantId),"查询成功");
    }


    @ApiOperation(value = "奖励记录",notes = "奖励记录")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",name = "merchantId",dataType = "String",value = "商户id")})
    @ApiResponse(code = 200,message = "OK",response = WxRedPackDetail.class)
    @GetMapping("/reward/record")
    public Resp<List<WxRewardDetail>> rewardRecord(String merchantId){
        if(ParamUtil.isBlank(merchantId)){
            return new Resp().error(Resp.Status.PARAM_ERROR,"商户id不能为空");
        }
        return Resp.success(wxRewardDetailService.getRepository().findByMerchantIdOrderByCreateTimeDesc(merchantId),"查询成功");
    }

    @ApiOperation(value = "返佣排行",nickname = "返佣排行")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",name = "type",dataType = "Integer",value = "1:已返金额  2:实际领取")})
    @GetMapping("/rebate/ranking")
    public Resp rebateRanking(@UserId String userId, Integer type){
        if (ParamUtil.isBlank(userId)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, Resp.Status.TOKEN_ERROR.getMsg());
        }
        if (ParamUtil.isBlank(type)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, Resp.Status.PARAM_ERROR.getMsg());
        }
        User user = userService.findOne(userId);
        if (ParamUtil.isBlank(user)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "当前用户不存在");
        }
        Company company = companyService.findOne(user.getCompanyId());
        return merchantService.rebateRanking(company,type);
    }
}
