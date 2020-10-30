package com.fzy.admin.fp.merchant.merchant.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.alibaba.fastjson.JSON;
import com.fzy.admin.fp.advertise.device.domian.AdvertiseDevice;
import com.fzy.admin.fp.advertise.group.domain.Group;
import com.fzy.admin.fp.advertise.group.dto.GroupDTO;
import com.fzy.admin.fp.auth.service.*;
import com.fzy.admin.fp.auth.vo.CountDataVO;
import com.fzy.admin.fp.common.domain.Area;
import com.fzy.admin.fp.common.domain.City;
import com.fzy.admin.fp.common.domain.Province;
import com.fzy.admin.fp.common.repository.AreaRepository;
import com.fzy.admin.fp.common.repository.CityRepository;
import com.fzy.admin.fp.common.repository.ProvinceRepository;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.app.service.DistUserService;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.merchant.merchant.dto.MerchantRebateListDTO;
import com.fzy.admin.fp.merchant.merchant.dto.SetMerchantRebateDTO;
import com.fzy.admin.fp.merchant.merchant.dto.SetMerchantRewardDTO;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantRepository;
import com.fzy.admin.fp.merchant.thirdMchInfo.service.HsfMchInfoService;
import com.fzy.admin.fp.merchant.thirdMchInfo.vo.ThirdMchInfoVO;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.Role;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.domain.UserRole;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.jpa.ConditionUtil;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.merchant.domain.MchInfo;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.domain.Store;
import com.fzy.admin.fp.order.order.repository.OrderRepository;
import com.fzy.admin.fp.order.pc.dto.OrderCountDto;
import com.fzy.admin.fp.pay.channel.repository.PayChannelRepository;
import com.fzy.admin.fp.pay.channel.service.PayChannelService;
import com.fzy.admin.fp.sdk.auth.feign.AuthServiceFeign;
import com.fzy.admin.fp.sdk.auth.feign.MessageServiceFeign;
import com.fzy.admin.fp.sdk.merchant.domain.DataViewAppVo;
import com.fzy.admin.fp.wx.domain.WxRedPackDetail;
import com.fzy.admin.fp.wx.service.WxRedPackDetailService;
import com.fzy.admin.fp.wx.service.WxRewardDetailService;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.beans.Transient;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.fzy.admin.fp.pay.channel.domain.PayChannel;

/**
 * @author Created by wtl on 2019-04-18 10:19:37
 * @description 商户服务层
 */
@Slf4j
@Service
@Transactional
public class MerchantService implements BaseService<Merchant> {

    @Resource
    private PayChannelService payChannelService;

    @Resource
    private MerchantRepository merchantRepository;

    @Resource
    private CompanyRepository companyRepository;

    @Resource
    private StoreService storeService;

    @Resource
    private MessageServiceFeign messageServiceFeign;

    @Resource
    private AuthServiceFeign authServiceFeign;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleService roleService;

    @Resource
    private MerchantUserService merchantUserService;

    @Resource
    private MchInfoService mchInfoService;

    @Resource
    private HsfMchInfoService hsfMchInfoService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private DistUserService distUserService;

    @Autowired
    private OrderRepository orderRepository;

    @Resource
    private CompanyService companyService;

    @PersistenceContext
    private EntityManager em;

    @Resource
    private ProvinceRepository provinceRepository;

    @Resource
    private CityRepository cityRepository;

    @Resource
    private AreaRepository areaRepository;

    @Resource
    private WxRedPackDetailService wxRedPackDetailService;

    @Resource
    private WxRewardDetailService wxRewardDetailService;

    @Override
    public MerchantRepository getRepository() {
        return merchantRepository;
    }

    @Transient
    public String saveMerchant(Merchant model, String serviceProviderId, String userId) {
        DistUser distUser = distUserService.findOne(userId);
        distUser.setMerchantNum(distUser.getMerchantNum() + 1);
        model.setCompanyId(distUser.getCompanyId());
        distUserService.update(distUser);
        return saveRewrite(model, serviceProviderId);
    }

    public String saveRewrite(Merchant model, String serviceProviderId) {
        if (ParamUtil.isBlank(model.getPhone())) {
            throw new BaseException("请输入手机号");
        }
        if (model.getPhone().length() != 11) {
            throw new BaseException("请输入正确的手机号码", Resp.Status.PARAM_ERROR.getCode());
        }
        Integer merchantNum = merchantRepository.countByPhoneAndDelFlag(model.getPhone(), CommonConstant.NORMAL_FLAG);
        if (merchantNum > 0) {
            throw new BaseException("该手机号已存在");
        }
        //查询用户表
        Integer userNum = merchantUserService.getRepository().countByUsernameAndDelFlag(model.getPhone(), CommonConstant.NORMAL_FLAG);
        if (userNum > 0) {
            throw new BaseException("该手机号已存在");
        }

        // 校验分佣比例
//        if (model.getPayProrata().compareTo(BigDecimal.ZERO) < 0) {
//            throw new BaseException("手续费率不能小于0", Resp.Status.PARAM_ERROR.getCode());
//        }
        model.setBind(Integer.valueOf(1));
        model.setRebateType(Integer.valueOf(1));
        model.setCumulationRebate(new BigDecimal("0"));
        model.setAppKey(ParamUtil.uuid());
        model.setStatus(Merchant.Status.GOODS.getCode());
        //保存商户信息
        merchantRepository.save(model);
        //创建商户进件
        MchInfo mchInfo = new MchInfo();
        mchInfo.setMerchantId(model.getId());
        mchInfo.setStatus(1);
        mchInfo.setRepresentativeName(model.getContact());
        mchInfo.setShortName(model.getName());
        mchInfo.setPhone(model.getPhone());
        mchInfo.setEmail(model.getEmail());
        //查询省市区
        Province province = provinceRepository.findOne(model.getProvince());
        City city = cityRepository.findOne(model.getCity());
        Area area = areaRepository.findOne(model.getArea());
        mchInfo.setStoreAddress(province.getProvinceName() + "/" + city.getCityName() + "/" + area.getAreaName());//省市区
        mchInfo.setServiceProviderId(serviceProviderId); //设置服务商id
        mchInfo.setWxSuccess(Integer.valueOf(0));
        mchInfo.setZfbSuccess(Integer.valueOf(0));
        mchInfo.setTqSxfSuccess(Integer.valueOf(0));
        mchInfo.setRegisterAddress(model.getAddress());
        mchInfoService.save(mchInfo); // 商户
        // 添加商户默认支付通道
        String payConfig = "{\"wx\":{\"scanPay\":\"12\",\"wapPay\":\"12\"},\"ali\":{\"scanPay\":\"12\",\"wapPay\":\"12\"}}";
        payChannelService.configSave(model.getId(), payConfig);
        // 给商户创建一个默认门店
        Store store = new Store();
        store.setMerchantId(model.getId());
        store.setName(model.getName()); // 店名为商户名称
        store.setPhone(model.getPhone());
        store.setStoreNo("100001");
        store.setProvince(model.getProvince());
        store.setCity(model.getCity());
        store.setAddress(model.getAddress());
        store.setStatus(Store.Status.ENABLE.getCode());
        store.setStoreFlag(Store.StoreFlag.DEFAULT.getCode());
        storeService.save(store);// 门店


        //创建用户
        MerchantUser user = new MerchantUser();
        String password = "123456"; //RandomUtil.randomNumbers(8);
        user.setName(model.getName());
        user.setUsername(model.getPhone());
        user.setPhone(model.getPhone());
        user.setPassword(BCrypt.hashpw(password));
        user.setUserType(MerchantUser.UserType.MERCHANT.getCode());
        user.setMerchantId(model.getId());
        user.setStoreId(store.getId());
        user.setSex(User.SEX.MAN.getCode());
        user.setStatus(User.Status.ENABLE.getCode());
        user.setPhotoId("");
        user.setServiceProviderId(serviceProviderId);
        merchantUserService.save(user);

        //调用短信通知用户
        boolean flag = messageServiceFeign.sendSmsInfo(model.getName(), model.getPhone(), password);
        if (!flag) {
            throw new BaseException("短信发送失败", Resp.Status.PARAM_ERROR.getCode());
        }
        return "添加成功! 默认密码123456";

    }

    //根据一级代理商id或者二级代理商id查询商户列表
    public Page<Merchant> findByCompanyId(Merchant model, PageVo pageVo, String userType, Integer channel, String userId) {
        Page<Merchant> merchantPage = null;
        final String serviceProviderId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        //若是渠道商查询商户列表
        if ("3".equals(userType)) {
            UserRole userRole = userRoleService.getRepository().findByUserId(userId);
            Role role = roleService.findOne(userRole.getRoleId());
            if (role != null && role.getLevel().equals(Role.LEVEL.COMMON.getCode())) {
                model.setManagerId(userId);
            }
            merchantPage = findByCompanyIdDirectly(model, pageVo);
        }
        //查询一级代理直接开通的商户和下面二级代理开通的商户
        if (channel == null) {
            List<String> stringList = authServiceFeign.findByOperaId(model.getCompanyId());
            stringList.add(model.getCompanyId());
            Pageable pageable = PageUtil.initPage(pageVo);
            if (model.getName() == null) {
                model.setName("");
            }
            if (model.getContact() == null) {
                model.setContact("");
            }
            if (model.getStatus() != null) {
                merchantPage = merchantRepository.findByCompanyIds(model.getStatus(), model.getName(), model.getContact(), stringList, pageable);
            } else {
                merchantPage = merchantRepository.findByCompanyIds(model.getName(), model.getContact(), stringList, pageable);
            }

            Map<String, String> userNameMap = authServiceFeign.getUserName();
            Map<String, String> companyMap = authServiceFeign.getCompanyName();
            for (Merchant merchant : merchantPage) {
                merchant.setManagerName(userNameMap.get(merchant.getManagerId()));
                merchant.setCompanyName(companyMap.get(merchant.getCompanyId()));
            }
        } else if (1 == channel) {
            merchantPage = findByCompanyIdDirectly(model, pageVo);
        } else if (2 == channel) {
            //若一级代理商查询二级代理商下的商户
            List<String> stringList = authServiceFeign.findByOperaId(model.getCompanyId());
            Pageable pageable = PageUtil.initPage(pageVo);
            if (model.getStatus() != null) {
                merchantPage = merchantRepository.findByCompanyIds(model.getStatus(), model.getName(), model.getContact(), stringList, pageable);
            } else {
                merchantPage = merchantRepository.findByCompanyIds(model.getName(), model.getContact(), stringList, pageable);
            }
            Map<String, String> userNameMap = authServiceFeign.getUserName();
            Map<String, String> companyMap = authServiceFeign.getCompanyName();
            for (Merchant merchant : merchantPage) {
                merchant.setManagerName(userNameMap.get(merchant.getManagerId()));
                merchant.setCompanyName(companyMap.get(merchant.getCompanyId()));
            }
        }

        return merchantPage;
    }


    //根据一级代理商id或者二级代理商id查询商户列表(直联)
    public Page<Merchant> findByCompanyIdDirectly(Merchant model, PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification specification = ConditionUtil.createSpecification(model);
        Page<Merchant> page = findAll(specification, pageable);
        Map<String, String> userNameMap = authServiceFeign.getUserName();
        Map<String, String> companyMap = authServiceFeign.getCompanyName();
        for (Merchant merchant : page) {
            merchant.setManagerName(userNameMap.get(merchant.getManagerId()));
            merchant.setCompanyName(companyMap.get(merchant.getCompanyId()));
        }
        return page;
    }


    //注销商户
    public void cancel(String merchantId, String serviceProviderId) {
        Merchant merchant = merchantRepository.findOne(merchantId);
        merchant.setDelFlag(CommonConstant.DEL_FLAG);
        //注销登录商户的用户信息
        MerchantUser merchantUser = merchantUserService.findByUsername(merchant.getPhone());
        merchantUser.setDelFlag(CommonConstant.DEL_FLAG);
        merchantUserService.getRepository().save(merchantUser);
        merchantRepository.save(merchant);
    }


    //重置商户密码
    public void defaultResetPassword(String username) {
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        MerchantUser user = merchantUserService.findByUsername(username);
        if (ParamUtil.isBlank(user)) {
            throw new BaseException("参数错误", Resp.Status.PARAM_ERROR.getCode());
        }
        user.setPassword(BCrypt.hashpw("123456"));
        merchantUserService.save(user);
    }


    public Map<String, Object> findByHsfMchInfo(Merchant model, PageVo pageVo) {
        Map<String, Object> map = new HashMap<>();
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification specification = ConditionUtil.createSpecification(model);
        Page<Merchant> page = merchantRepository.findAll(specification, pageable);
        //将Merchant转成ThirdMchInfoVO
        List<Merchant> merchantList = page.getContent();
        List<ThirdMchInfoVO> cartDTOList = merchantList.stream()
                .map(e -> new ThirdMchInfoVO(e.getId(), e.getName(), e.getPhone()))
                .collect(Collectors.toList());
        //获取慧闪付进件列表
        Map<String, Object> hsfMap = hsfMchInfoService.hsfMap();
        //遍历集合,初始化进件签约状态
        for (ThirdMchInfoVO thirdMchInfoVO : cartDTOList) {
            if (hsfMap.containsKey(thirdMchInfoVO.getMerchantId())) {
                thirdMchInfoVO.setSignStatus((Integer) hsfMap.get(thirdMchInfoVO.getMerchantId()));
            } else {
                thirdMchInfoVO.setSignStatus(1);//未签约
            }
        }
        map.put("content", cartDTOList);
        map.put("totalElements", page.getTotalElements());
        map.put("totalPages", page.getTotalPages());
        return map;
    }


    /**
     * 获取商户所有上级id 一级/二级/贴牌
     *
     * @param merchant
     * @return
     */
    public List<String> findSuperiorIds(Merchant merchant) {
        List<String> superiorIds = new ArrayList<>();
        //商户上级 一级/或者二级代理商
        if (null != merchant.getCompanyId()) {
            Company company = companyRepository.findOne(merchant.getCompanyId());
            if (company == null) {
                return superiorIds;
            }
            superiorIds.add(company.getId());
            //二级代理商
            if (company.getType().equals(3)) {
                //一级代理
                Company firstCompany = companyRepository.findOne(company.getParentId());
                superiorIds.add(firstCompany.getId());
                superiorIds.add(firstCompany.getParentId());
            } else {
                superiorIds.add(company.getParentId());

            }
        }
        return superiorIds;
    }


    public Page<Merchant> getPage(Merchant entity, PageVo pageVo, String companyId) {
        if (StringUtils.isEmpty(entity.getName())) {
            entity.setName("");
        }
        if (StringUtils.isEmpty(entity.getContact())) {
            entity.setContact("");
        }
        if (StringUtils.isEmpty(entity.getCompanyName())) {
            entity.setCompanyName("");
        }
        Company company = companyRepository.findOne(companyId);
        String idPath;
        List<Company> childCompanies;
        //如果是服务商
        if (companyId.equals(entity.getServiceProviderId())) {
            childCompanies = companyRepository.findByIdPathStartingWithAndNameLike("|" + companyId, "%" + entity.getCompanyName() + "%");
        } else {
            idPath = company.getIdPath() + company.getId();
            childCompanies = companyRepository.findByIdPathStartingWithAndNameLike(idPath, "%" + entity.getCompanyName() + "%");
        }

        List<String> companyIds = new ArrayList<>();
        companyIds.add(companyId);
        if (childCompanies.size() > 0) {
            companyIds.addAll(childCompanies.stream().map(Company::getId).collect(Collectors.toList()));
        }

        Pageable pageable = PageUtil.initPage(pageVo);
        Page<Merchant> page;
        if (entity.getStatus() == null) {
            page = merchantRepository.findByCompanyIds(entity.getName(), entity.getContact(), companyIds, pageable);
        } else {
            page = merchantRepository.findByCompanyIds(entity.getStatus(), entity.getName(), entity.getContact(), companyIds, pageable);
        }
        return page;
    }


    public Page<Merchant> getPageService(Merchant entity, PageVo pageVo, String companyId) {
        if (StringUtils.isEmpty(entity.getName())) {
            entity.setName("");
        }
        if (StringUtils.isEmpty(entity.getContact())) {
            entity.setContact("");
        }
        if (StringUtils.isEmpty(entity.getCompanyName())) {
            entity.setCompanyName("");
        }
        List<Company> childCompanies = null;
        //如果是服务商
        if (companyId.equals(entity.getServiceProviderId())) {
            childCompanies = companyRepository.findByIdPathStartingWithAndNameLike("|" + companyId, "%" + entity.getCompanyName() + "%");
        }

        List<String> companyIds = new ArrayList<>();
        companyIds.add(companyId);
        if (childCompanies.size() > 0) {
            companyIds.addAll(childCompanies.stream().map(Company::getId).collect(Collectors.toList()));
        }

        Pageable pageable = PageUtil.initPage(pageVo);
        Page<Merchant> page;
        if (entity.getStatus() == null) {
            page = merchantRepository.findByServiceProviderId(entity.getName(), entity.getContact(), companyIds, pageable);
        } else {
            page = merchantRepository.findByServiceProviderId(entity.getStatus(), entity.getName(), entity.getContact(), companyIds, pageable);
        }
        return page;
    }

    public List<String> getMerchantType(String companyId) {
        List<Company> childCompanies;
        childCompanies = companyRepository.findByIdPathStartingWith("|" + companyId);
        List<String> companyIds = new ArrayList<>();
        companyIds.add(companyId);
        if (childCompanies.size() > 0) {
            companyIds.addAll(childCompanies.stream().map(Company::getId).collect(Collectors.toList()));
        }

        return merchantRepository.findMerchantType(companyIds);

    }

    public Page<Merchant> getDirectMerchantPage(PageVo pageVo, String companyId) {
        List<String> companyIds = new ArrayList<String>();
        companyIds.add(companyId);
        Pageable pageable = PageUtil.initPage(pageVo);
        return this.merchantRepository.findByCompanyIdsAndManagerId("", "", "", companyIds, pageable);
    }

    @Override
    public Merchant findOne(String id) {
        return merchantRepository.findOne(id);
    }

    public List<Map> getMerchant(String serviceId, GroupDTO groupDTO, Group advertiseGroup) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //笔数所有时间
        if (groupDTO.getCountDateType() != null) {
            //查询服务商下的所有商户
            List<Merchant> merchantList = merchantRepository.findByServiceProviderId(serviceId);
            for (Merchant merchant : merchantList) {
                OrderCountDto orderCountDto = orderRepository.countMerchantOrder2(merchant.getId());
                merchant.setCountNumber(Integer.valueOf(orderCountDto.getCount().intValue()));
                merchantRepository.saveAndFlush(merchant);
            }
        }
        //流水所有时间
        if (groupDTO.getSumDateType() != null) {
            //查询服务商下的所有商户
            List<Merchant> merchantList = merchantRepository.findByServiceProviderId(serviceId);
            for (Merchant merchant : merchantList) {
                OrderCountDto orderCountDto = orderRepository.countMerchantOrder2(merchant.getId());
                if (orderCountDto.getTotalPrice() != null) {
                    merchant.setMoney(orderCountDto.getTotalPrice());
                } else {
                    merchant.setMoney(new BigDecimal(0));
                }
                merchantRepository.saveAndFlush(merchant);
            }
        }
        //笔数时间范围
        if (groupDTO.getCountStartTime() != null) {
            //查询服务商下的所有商户
            List<Merchant> merchantList = merchantRepository.findByServiceProviderId(serviceId);
            for (Merchant merchant : merchantList) {
                OrderCountDto orderCountDto = orderRepository.countMerchantOrder1(merchant.getId(), groupDTO.getCountStartTime(), groupDTO.getCountEndTime());
                merchant.setCountNumber(Integer.valueOf(orderCountDto.getCount().intValue()));
                merchantRepository.saveAndFlush(merchant);
            }
        }
        //流水时间范围
        if (groupDTO.getSumStartTime() != null) {
            //查询服务商下的所有商户
            List<Merchant> merchantList = merchantRepository.findByServiceProviderId(serviceId);
            for (Merchant merchant : merchantList) {
                OrderCountDto orderCountDto = orderRepository.countMerchantOrder1(merchant.getId(), groupDTO.getCountStartTime(), groupDTO.getCountEndTime());
                if (orderCountDto.getTotalPrice() != null) {
                    merchant.setMoney(orderCountDto.getTotalPrice());
                } else {
                    merchant.setMoney(new BigDecimal(0));
                }
                merchantRepository.saveAndFlush(merchant);
            }
        }
        //按区域
        List<String> cityList = new ArrayList<>();
        List<String> cityName = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("select id,name,create_time,business_lev_one,business_lev_two,business_lev_three,city,count_number,money from lysj_merchant_merchant WHERE 1=1");
        sb.append(" AND service_provider_id = :serviceId");
        if (groupDTO.getCityIds() != null) {  //地域范围
            String[] split = groupDTO.getCityIds().split(",");
            cityList = Arrays.asList(split);
            sb.append(" AND city in( :city )");

            List<City> cities = cityRepository.findByIdIn(cityList);
            for (City city : cities) {
                cityName.add(city.getCityName());
            }
            advertiseGroup.setRegionName(cityName.toString());
        }
        //按代理下的商户
        if (groupDTO.getProxyType() != null) {
            //服务商商户
            if (groupDTO.getProxyType().equals(Integer.valueOf(1))) {
                sb.append(" AND company_id = :companyId ");
                advertiseGroup.setProxyName("服务商商户");
                //分销商户
            } else if (groupDTO.getProxyType().equals(Integer.valueOf(5))) {
                sb.append(" and type = :type");
                advertiseGroup.setProxyName("分销商户");
                //全部商户
            } else if (groupDTO.getProxyType().equals(Integer.valueOf(0))) {
                sb.append(" AND service_provider_id = :serviceId ");
                advertiseGroup.setProxyName("全部商户");
                //一级/二级/三级服务商商户
            } else {
                advertiseGroup.setProxyName("代理商户");
                sb.append(" AND company_id in( :companyId )");
            }
        }
        //按行业区分
        if (groupDTO.getBusinessLevOne() != null) {
            advertiseGroup.setIndustryName("行业：" + groupDTO.getBusinessLevThree());
            sb.append(" and business_lev_one = :businessLevOne and business_lev_two = :businessLevTwo and business_lev_three = :businessLevThree");
        }
        //按交易笔数
        if (null != groupDTO.getCountDateType()) {
            if (groupDTO.getStartCount() != null || groupDTO.getEndCount() != null) {
                advertiseGroup.setNumberName(formatter.format(groupDTO.getCountStartTime()) + "-" + formatter.format(groupDTO.getCountEndTime()) + "交易笔数(笔):" + groupDTO.getStartCount() + "-" + groupDTO.getEndCount());
                if (!groupDTO.getEndCount().equals(-1)) {
                    sb.append(" and ( count_number > :startCount and count_number < :endCount )");
                }
            }
        }
        //按流水金额
        if (null != groupDTO.getSumDateType()) {
            if (groupDTO.getStartTurnover() != null || groupDTO.getEndTurnover() != null) {
                advertiseGroup.setMoneyName(formatter.format(groupDTO.getSumStartTime()) + "-" + formatter.format(groupDTO.getSumEndTime()) + "交易流水(元):" + groupDTO.getStartTurnover() + "-" + groupDTO.getEndTurnover());
                if (!groupDTO.getEndTurnover().equals(-1)) {
                    //判断条件类型
                    if (groupDTO.getConditionType().equals(2)) {
                        sb.append(" or ( money > :startTurnover and money < :endTurnover ) ");
                    } else {
                        sb.append(" and ( money > :startTurnover and money < :endTurnover ) ");
                    }
                }
            }
        }
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("serviceId", serviceId);
        if (groupDTO.getCityIds() != null) {  //地域范围
            query.setParameter("city", cityList);
        }
        //postman的传值检查token的值是否正确
        //按代理下的商户
        if (groupDTO.getProxyType() != null) {
            //服务商商户
            if (groupDTO.getProxyType().equals(Integer.valueOf(1))) {
                query.setParameter("companyId", serviceId);
                //分销商户
            } else if (groupDTO.getProxyType().equals(Integer.valueOf(5))) {
                query.setParameter("type", 1);
                //全部商户
            } else if (groupDTO.getProxyType().equals(Integer.valueOf(0))) {
                query.setParameter("serviceId", serviceId);
                //一级/二级/三级代理商商户
            } else {
                //查询当前角色是否是服务商/代理商
                List<String> allmerchantIds = companyService.findAllCompanyIds(groupDTO.getCompanyId());
                query.setParameter("companyId", allmerchantIds);
            }
        }
        //按行业区分
        if (groupDTO.getBusinessLevOne() != null) {
            query.setParameter("businessLevOne", groupDTO.getBusinessLevOne());
            query.setParameter("businessLevTwo", groupDTO.getBusinessLevTwo());
            query.setParameter("businessLevThree", groupDTO.getBusinessLevThree());
        }
        //按交易笔数
        if (groupDTO.getStartCount() != null || groupDTO.getEndCount() != null) {
            if (!groupDTO.getEndCount().equals(-1)) {
                query.setParameter("startCount", groupDTO.getStartCount());
                query.setParameter("endCount", groupDTO.getEndCount());
            }

        }
        //按流水金额
        if (groupDTO.getStartTurnover() != null || groupDTO.getEndTurnover() != null) {
            if (!groupDTO.getEndTurnover().equals(new BigDecimal(-1))) {
                query.setParameter("startTurnover", groupDTO.getStartTurnover());
                query.setParameter("endTurnover", groupDTO.getEndTurnover());
            }
        }
        List<Map> list = query.getResultList();
        return list;
    }

    /**
     * 商户返佣列表
     *
     * @param company
     * @param merchantRebateListDTO
     * @param pageVo
     * @return
     */
    public Resp rebateList(Company company, MerchantRebateListDTO merchantRebateListDTO, PageVo pageVo) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Page<Merchant> page;
        Specification<Merchant> specification = new Specification<Merchant>() {
            @Override
            public Predicate toPredicate(Root<Merchant> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (merchantRebateListDTO.getBind() != null) {
                    predicates.add(cb.equal(root.get("bind"), merchantRebateListDTO.getBind()));
                }
                if (merchantRebateListDTO.getRebateType() != null) {
                    predicates.add(cb.equal(root.get("rebateType"), merchantRebateListDTO.getRebateType()));
                }
                if (merchantRebateListDTO.getRewardType() != null) {
                    predicates.add(cb.equal(root.get("rewardType"), merchantRebateListDTO.getRewardType()));
                }
                predicates.add(cb.equal(root.get("delFlag"), "1"));
                //区分服务商和代理商
                if (company.getType().equals(Company.Type.PROVIDERS.getCode())) {
                    predicates.add(cb.equal(root.get("serviceProviderId"), company.getId()));
                } else {
                    predicates.add(cb.equal(root.get("companyId"), company.getId()));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                Predicate Pre_And = cb.and(predicates.toArray(pre));

                List<Predicate> listOr = new ArrayList<Predicate>();
                if (StringUtil.isNotEmpty(merchantRebateListDTO.getNamePhone())) {
                    listOr.add(cb.like(root.get("name"), "%" + merchantRebateListDTO.getNamePhone() + "%"));
                    listOr.add(cb.like(root.get("phone"), "%" + merchantRebateListDTO.getNamePhone() + "%"));

                    Predicate[] arrayOr = new Predicate[listOr.size()];
                    Predicate Pre_Or = cb.or(listOr.toArray(arrayOr));
                    return query.where(Pre_And, Pre_Or).getRestriction();
                }
                return query.where(Pre_And).getRestriction();
            }
        };
        page = this.merchantRepository.findAll(specification, pageable);
        for (Merchant merchant : page.getContent()) {
            Map<String, String> map = new HashMap<>();
            if (merchantRebateListDTO.getType().equals(Integer.valueOf(1))) {
                map = wxRedPackDetailService.findAmountReturnedAndActualCollection(merchant.getId());
                merchant.setAmountReturned(new BigDecimal(map.get("totalAmount")));
                merchant.setActualCollection(new BigDecimal(map.get("totalAmount2")));
            } else {
                if (merchant.getStartRewardTime() != null) {
                    map = wxRewardDetailService.findAmountReturnedAndActualCollection(merchant.getId());
                    merchant.setActualCollection(new BigDecimal(map.get("totalAmount2")));
                    List<DateTime> dateTimeList = DateUtil.rangeToList(merchant.getStartRewardTime(), merchant.getEndRewardTime(), DateField.MONTH);
                    merchant.setRewardMonth(dateTimeList.size());
                }
            }
        }
        Map<String,Object> hashMap = new HashMap<>();
        if (merchantRebateListDTO.getType().equals(Integer.valueOf(1))) {
            wxRedPackDetailService.findTotal(hashMap,company.getId());
        }
        hashMap.put("page",page);
        return Resp.success(hashMap);
    }

    /**
     * 批量设置返佣/单独设置返佣
     *
     * @param list
     * @param setMerchantRebateDTO
     */
    public Resp set(List<String> list, SetMerchantRebateDTO setMerchantRebateDTO) {
        List<Merchant> merchants = merchantRepository.findByIdIn(list);
        for (Merchant merchant : merchants) {
            merchant.setRebateType(setMerchantRebateDTO.getRebateType());
            merchant.setStartRebateTime(setMerchantRebateDTO.getStartRebateTime());
            merchant.setEndRebateTime(setMerchantRebateDTO.getEndRebateTime());
            merchant.setSiteRate(setMerchantRebateDTO.getSiteRate());
            if (setMerchantRebateDTO.getRebateNum() != null) {
                merchant.setRebateNum(setMerchantRebateDTO.getRebateNum());
            }
            if (setMerchantRebateDTO.getLimitRebate() != null) {
                merchant.setLimitRebate(setMerchantRebateDTO.getLimitRebate());
            }
            merchantRepository.save(merchant);
        }
        return Resp.success("设置成功");
    }

    public void getTransactionAmount(String companyId, DataViewAppVo dataViewAppVo) {
        //销售总额
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(1) AS transactionCount,sum(o.act_pay_price) AS transactionMoney from lysj_order_order o left join lysj_merchant_merchant m on o.merchant_id = m.id  WHERE m.service_provider_id = :serviceProviderId and m.type = 0 and (o.`status` = 2 )");
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("serviceProviderId", companyId);
        List<Map> list = query.getResultList();
        for (Map map : list) {
            dataViewAppVo.setTransactionCount(map.get("transactionCount").hashCode());
            dataViewAppVo.setTransactionMoney(new BigDecimal(map.get("transactionMoney").toString()));
        }
        StringBuilder sb2 = new StringBuilder();
        //退款笔数/退款金额
        sb2.append("SELECT count(1) AS refundCount ,sum(o.refund_pay_price) AS refundMoney from lysj_order_order o left join lysj_merchant_merchant m on o.merchant_id = m.id  WHERE m.service_provider_id = :serviceProviderId and m.type = 0 and (o.`status` = 5 or o.`status` = 6 )");
        Query query2 = em.createNativeQuery(sb2.toString());
        query2.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query2.setParameter("serviceProviderId", companyId);
        List<Map> list2 = query2.getResultList();
        for (Map map : list2) {
            dataViewAppVo.setRefundCount(map.get("refundCount").hashCode());
            dataViewAppVo.setRefundMoney(new BigDecimal(map.get("refundMoney").toString()));
        }
    }

    /**
     * 每天的佣金记录
     *
     * @param companyId
     * @param countDataVO
     * @param startTime
     * @param endTime
     */
    public void getTransactionAmountNewDay(String companyId, CountDataVO countDataVO, String startTime, String endTime) {

        //销售总额
        StringBuilder sb3 = new StringBuilder();
        sb3.append("SELECT count(1) AS transactionCount,IFNULL(sum(o.act_pay_price),0)  AS transactionMoney from lysj_order_order o left join lysj_merchant_merchant m on o.merchant_id = m.id  WHERE m.service_provider_id = :serviceProviderId and m.type = 0 and (o.`status` = 2 ) ");
        sb3.append(" and pay_time >= ?1 ");
        sb3.append(" and pay_time <= ?2 ");
        Query query3 = em.createNativeQuery(sb3.toString());
        query3.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query3.setParameter("serviceProviderId", companyId);
        query3.setParameter(1, startTime);
        query3.setParameter(2, endTime);
        List<Map> list = query3.getResultList();
        for (Map map : list) {
            countDataVO.setOrderNum(map.get("transactionCount").hashCode());
            countDataVO.setOrderAmount(new BigDecimal(map.get("transactionMoney").toString()));
        }
        StringBuilder sb2 = new StringBuilder();
        //退款笔数/退款金额
        sb2.append("SELECT count(1) AS refundCount , IFNULL(sum(o.refund_pay_price),0) AS refundMoney from lysj_order_order o left join lysj_merchant_merchant m on o.merchant_id = m.id  WHERE m.service_provider_id = :serviceProviderId and m.type = 0 and (o.`status` = 5 or o.`status` = 6 ) ");
        sb2.append(" and pay_time >=:startTime");
        sb2.append(" and pay_time <=:endTime");
        Query query2 = em.createNativeQuery(sb2.toString());
        query2.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query2.setParameter("serviceProviderId", companyId);
        query2.setParameter("startTime", startTime);
        query2.setParameter("endTime", endTime);
        List<Map> list2 = query2.getResultList();
        for (Map map : list2) {
            countDataVO.setRefundNum(map.get("refundCount").hashCode());
            countDataVO.setRefundAmount(new BigDecimal(map.get("refundMoney").toString()));
        }
    }

    public Resp setReward(List<String> list, SetMerchantRewardDTO setMerchantRewardDTO) {
        List<Merchant> merchants = merchantRepository.findByIdIn(list);
        for (Merchant merchant : merchants) {
            merchant.setRewardType(setMerchantRewardDTO.getRewardType());
            merchant.setStartRewardTime(setMerchantRewardDTO.getStartRewardTime());
            merchant.setEndRewardTime(setMerchantRewardDTO.getEndRewardTime());
            merchant.setRewardPrice(setMerchantRewardDTO.getRewardPrice());
            merchant.setRewardNum(setMerchantRewardDTO.getRewardNum());
            merchantRepository.save(merchant);
        }
        return Resp.success("设置成功");
    }

    public Resp rebateRanking(Company company, Integer type) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.id, a.name, IFNULL( SUM(distinct b.total_amount ), 0 ) AS totalAmount1, IFNULL(SUM(distinct c.total_amount), 0) AS totalAmount2 FROM lysj_merchant_merchant a\n" +
                "\tLEFT JOIN wx_red_pack_detail b ON a.id = b.merchant_id and b.return_type != 0 \n" +
                "\tLEFT JOIN wx_red_pack_detail c ON a.id = c.merchant_id and c.`status` is not null\n" +
                "WHERE\n" +
                "\t1 = 1 and a.service_provider_id = :serviceProviderId GROUP BY a.id ORDER BY ");
        if (type.equals(Integer.valueOf(1))) {
            sb.append(" totalAmount1 ");
        } else {
            sb.append(" totalAmount2 ");
        }
        sb.append(" desc ");
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("serviceProviderId", company.getId());
        List<Map> list = query.getResultList();
        return Resp.success(list, "查询成功");
    }
}