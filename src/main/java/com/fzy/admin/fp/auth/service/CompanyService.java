package com.fzy.admin.fp.auth.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.auth.repository.UserRepository;
import com.fzy.admin.fp.auth.utils.DateUtils;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.domain.City;
import com.fzy.admin.fp.common.domain.Province;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.repository.CityRepository;
import com.fzy.admin.fp.common.repository.ProvinceRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.vo.IndexCommissionVO;
import com.fzy.admin.fp.distribution.article.domain.Article;
import com.fzy.admin.fp.distribution.money.domain.RateStandards;
import com.fzy.admin.fp.distribution.money.service.RateStandardsService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.repository.MerchantRepository;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.order.order.domain.Commission;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.repository.CommissionRepository;
import com.fzy.admin.fp.order.order.repository.OrderRepository;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.Role;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.domain.UserRole;
import com.fzy.admin.fp.auth.dto.DataTransactionDTO;
import com.fzy.admin.fp.auth.utils.SmsService;
import com.fzy.admin.fp.auth.vo.CompanyVO;
import com.fzy.admin.fp.order.order.service.CommissionService;
import com.fzy.admin.fp.order.pc.dto.OrderActPayPriceDto;
import com.fzy.admin.fp.order.pc.dto.OrderCommissionDto;
import com.fzy.admin.fp.sdk.auth.feign.AuthServiceFeign;
import com.fzy.admin.fp.sdk.merchant.domain.*;
import com.fzy.assist.wraps.DateWrap;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
//import org.junit.Test;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zk
 * @description 用户表服务层
 * @create 2018-07-25 15:02:19
 **/
@Slf4j
@Service
@Transactional
public class CompanyService implements BaseService<Company> {
    @Resource
    private CompanyRepository companyRepository;
    @Resource
    private MerchantRepository merchantRepository;
    @Resource
    private OrderRepository orderRepository;

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UserRepository userRepository;
    @Resource
    private SmsService smsService;
    @Resource
    private HttpServletRequest request;

    @Resource
    private AuthServiceFeign authServiceFeign;

    @Resource
    private RateStandardsService rateStandardsService;

    @Resource
    private MerchantService merchantService;

    @Resource
    private CityRepository cityRepository;

    @Resource
    private ProvinceRepository provinceRepository;
    @Resource
    private CommissionRepository commissionRepository;
    @Resource
    private CommissionService commissionService;

    @Override
    public CompanyRepository getRepository() {
        return companyRepository;
    }

    /**
     * @author Created by zk on 2019/4/29 17:31
     * @Description 通过公司Id找到下属所有二级代理商列表
     */
    public List<String> findChildrenById(String id, int level, String userId) {
        return companyRepository.findByParentId(id).stream()
                //如果级别为高级则通过或者创建该公司的业务员是当前业务员
                .filter(company -> Role.LEVEL.TOP.getCode().equals(level) || company.getManagerId().equals(userId))
                .map(Company::getId).collect(Collectors.toList());
    }

    /**
     * @author Created by zk on 2019/7/1 21:56
     * @Description 填充idPath
     */
    public Company fillIdPath(Company company) {
        if (ParamUtil.isBlank(company.getIdPath())) {
            Company father = findOne(company.getParentId());
            if (father.getType().equals(Company.Type.PROVIDERS.getCode())) {
                company.setIdPath("|" + company.getParentId() + "|");
            } else {
                company.setIdPath(father.getIdPath() + father.getId() + "|");
            }
        }
        return company;
    }

    public List<Company> findByIdPathIsBlank() {
        return getRepository().findByIdPathIsNullOrIdPath("");
    }

    /**
     * @author Created by zk on 2019/7/4 18:10
     * @Description 添加服务商
     */
    public String saveRewrite(Company entity, String userId, Integer roleType) {
        validPhone(entity);
        entity.setManagerId(userId);//业务员id
        entity.setType(Company.Type.PROVIDERS.getCode()); //公司类型
        entity = fillIdPath(entity);
        companyRepository.save(entity);


        //创建登录用户
        User user = new User();
        String password = "123456";
        user.setUsername(entity.getPhone());
        user.setName(entity.getContact()); //用户名称改成联系人
        user.setEmail("/"); //邮箱改成/
        user.setSex(User.SEX.MAN.getCode());
        user.setStatus(User.Status.ENABLE.getCode());
        user.setPassword(BCrypt.hashpw(password));
        user.setCompanyId(entity.getId());
        user.setPhone(entity.getPhone());
        user.setServiceProviderId(entity.getId());
        userService.save(user);

        Role role = roleService.getRepository().findByTypeAndKindAndLevelAndCompanyId(roleType, Role.Kind.MANAGER.getCode(), Role.LEVEL.TOP.getCode(), "0");
        UserRole ur = new UserRole();
        ur.setRoleId(role.getId());
        ur.setUserId(user.getId());
        userRoleService.save(ur);
        //调用发短信feign通知用户登录信息
        boolean flag = smsService.sendSmsInfo(entity.getName(), entity.getPhone(), password);
        if (!flag) {
            throw new BaseException("短信发送失败", Resp.Status.PARAM_ERROR.getCode());
        }
        return "操作成功";
    }

    /**
     * 新建代理商
     *
     * @param entity
     * @param userId
     * @param companyType
     * @return
     */
    public String saveAgent(Company entity, String userId, Integer companyType) {
        validPhone(entity);
        entity.setManagerId(userId);//业务员id
        User user = userService.findOne(userId);
        entity.setParentId(user.getCompanyId());//设置父id
        entity.setType(companyType); //公司类型
        entity = fillIdPath(entity);
        companyRepository.save(entity);
        return "操作成功";
    }

    private void validPhone(Company entity) {
        if (entity.getPhone().length() != 11) {
            throw new BaseException("请输入正确的手机号码", Resp.Status.PARAM_ERROR.getCode());
        }
        //查询手机号是否存在
        Company company = getRepository().findByPhoneAndDelFlag(entity.getPhone(), CommonConstant.NORMAL_FLAG);
        if (!ParamUtil.isBlank(company)) {
            throw new BaseException("该手机号已存在", Resp.Status.PARAM_ERROR.getCode());
        }
        User userValid = userRepository.findByPhoneAndDelFlag(entity.getPhone(), CommonConstant.NORMAL_FLAG);
        if (!ParamUtil.isBlank(userValid)) {
            throw new BaseException("手机号已存在", Resp.Status.PARAM_ERROR.getCode());
        }
    }


    //审核或拒绝审核
    public String audit(String id, Integer status, Integer roleType) {
        Company company = companyRepository.findOne(id);
        company.setStatus(status);
        companyRepository.save(company);

        //如果选择的是审核
        if (Company.Status.SIGNED.getCode().equals(status)) {
            final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
            User byUsername = userService.findByUsername(company.getPhone(), serviceId);
            if (!ParamUtil.isBlank(byUsername)) {
                throw new BaseException("该手机号已存在", Resp.Status.PARAM_ERROR.getCode());
            }
            //创建登录用户
            User user = new User();
            String password = "123456";//RandomUtil.randomNumbers(8);
            user.setUsername(company.getPhone());
            user.setName(company.getContact()); //用户名称改成联系人
            user.setEmail("/"); //邮箱改成/
            user.setSex(User.SEX.MAN.getCode());
            user.setStatus(User.Status.ENABLE.getCode());
            user.setPassword(BCrypt.hashpw(password));
            user.setCompanyId(company.getId());
            user.setPhone(company.getPhone());
            user.setServiceProviderId(serviceId);
            userService.save(user);

            Role role = roleService.getRepository().findByTypeAndKindAndLevelAndCompanyId(roleType, Role.Kind.MANAGER.getCode(), Role.LEVEL.TOP.getCode(), "0");
            UserRole ur = new UserRole();
            ur.setRoleId(role.getId());
            ur.setUserId(user.getId());
            userRoleService.save(ur);
            //调用发短信feign通知用户登录信息
            boolean flag = smsService.sendSmsInfo(company.getName(), company.getPhone(), password);
            if (!flag) {
                throw new BaseException("短信发送失败", Resp.Status.PARAM_ERROR.getCode());
            }
        }
        return "操作成功";
    }


    //查看一级代理商详情
    public CompanyVO detail(String id) {
        if (ParamUtil.isBlank(id)) {
            throw new BaseException("参数错误", Resp.Status.PARAM_ERROR.getCode());
        }
        Company company = companyRepository.findOne(id);
        //获取业务员信息
        User user = userService.findOne(company.getManagerId());
        company.setSaleName(user.getName());
        company.setEndCooperaTime(new Date(company.getCreateTime().getTime() + 31622400000L));//合作到期
        CompanyVO companyVO = new CompanyVO();
        BeanUtil.copyProperties(company, companyVO);
        if (StringUtils.isNumeric(company.getProvince())) {
            Province province = provinceRepository.findOne(company.getProvince());
            companyVO.setProvinceName(province.getProvinceName());
        }
        if (StringUtils.isNumeric(company.getCity())) {
            City city = cityRepository.findOne(company.getCity());
            companyVO.setCityName(city.getCityName());
        }

        return companyVO;
    }


    public List<Company> findChildrenByServiceProviderId(String companyId) {
        return getRepository().findByIdPathStartingWith("|" + companyId + "|");
    }

    public List<Company> findByType(Integer type) {
        return getRepository().findByType(type);
    }

    public DataViewVO dataview(String companyId) {
        Date date = new Date();
        String nowDate = DateWrap.format(date, "yyyy-MM");

        Company company = this.findOne(companyId);

        //定义1返回对象
        DataViewVO dataViewVO = new DataViewVO();

        //通过idpath获取下级个数，一二三级代理/商户数
        String idPath;
        if (company.getType().equals(Company.Type.PROVIDERS.getCode())) {
            idPath = "|" + companyId;
        } else {
            idPath = company.getIdPath() + companyId + "|";
        }
        //全部下级
        List<Company> companies;
        if (company.getType().equals(Company.Type.ADMIN.getCode())) {
            companies = companyRepository.findAll();
        } else {
            companies = companyRepository.findByIdPathStartingWith(idPath);
        }

        //贴牌商
        List<Company> oemCompany = companies.stream().filter(item -> item.getType().equals(Company.Type.PROVIDERS.getCode())).collect(Collectors.toList());
        //一级代理商
        List<Company> firstCompany = companies.stream().filter(item -> item.getType().equals(Company.Type.OPERATOR.getCode())).collect(Collectors.toList());
        dataViewVO.setMonthFirstAgentNum(firstCompany.stream().filter(month -> nowDate.equals(DateWrap.format(month.getCreateTime(), "yyyy-MM"))).collect(Collectors.toList()).size());


        //二级代理商
        List<Company> secondCompany = companies.stream().filter(item -> item.getType().equals(Company.Type.DISTRIBUTUTORS.getCode())).collect(Collectors.toList());
        dataViewVO.setMonthSecondAgentNum(secondCompany.stream().filter(month -> nowDate.equals(DateWrap.format(month.getCreateTime(), "yyyy-MM"))).collect(Collectors.toList()).size());


        //三级代理商
        List<Company> thirdCompany = companies.stream().filter(item -> item.getType().equals(Company.Type.THIRDAGENT.getCode())).collect(Collectors.toList());
        dataViewVO.setMonthThirdAgentNum(thirdCompany.stream().filter(month -> nowDate.equals(DateWrap.format(month.getCreateTime(), "yyyy-MM"))).collect(Collectors.toList()).size());

        dataViewVO.setOemNum(oemCompany.size());
        dataViewVO.setFirstAgentNum(firstCompany.size());
        dataViewVO.setSecondAgentNum(secondCompany.size());
        dataViewVO.setThirdAgentNum(thirdCompany.size());

        //获取当前后台下所有的商户
        List<String> merchantIds = findAllmerchantIds(company.getId());
        if (merchantIds.size() > 0) {
            dataViewVO.setMerchantNum(merchantIds.size());
            //获取店铺所有交易订单 / 过滤会员支付订单
            List<Integer> statusList = new ArrayList<>();
            statusList.add(Order.Status.SUCCESSPAY.getCode());
            statusList.add(Order.Status.REFUNDPART.getCode());
            statusList.add(Order.Status.REFUNDTOTAL.getCode());
            List<Order> orders = orderRepository.findByMerchantIdInAndStatusInAndPayWayNot(merchantIds, statusList, Order.PayWay.MEMBERCARD.getCode());
            //过滤无效订单
            List<Order> newOrders = orders.stream().filter(order -> {
                if (order.getStatus().equals(2) || order.getStatus().equals(5) || order.getStatus().equals(6)) {
                    return true;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            if (newOrders.size() > 0) {
                //交易数
                dataViewVO.setTransactiontNum(newOrders.size());
                BigDecimal transactiontMoney = new BigDecimal(0);
                String payDate;
                for (Order order : newOrders) {
                    payDate = DateWrap.format(order.getPayTime(), "yyyy-MM");
                    //统计当月数据
                    if (payDate.equals(nowDate)) {
                        dataViewVO.setMonthMoney(dataViewVO.getMonthMoney().add(order.getActPayPrice()));
                    }
                    transactiontMoney = transactiontMoney.add(order.getActPayPrice());
                }
                // 交易额度
                dataViewVO.setTransactionMoney(transactiontMoney);
                //佣金
                BigDecimal commissionMoney = getLeftCommission(company, null, null);
                dataViewVO.setCommissionMoney(commissionMoney);
            }
        }
        return dataViewVO;


    }

    /**
     * 获取企业佣金
     *
     * @param userId
     * @param begin
     * @param end
     * @return
     */
    public Map<String, BigDecimal> getCommission(String userId, Date begin, Date end) {
        Map map = new HashMap();
        List<Commission> commissionList = null;
        Specification<Commission> specification = new Specification<Commission>() {
            @Override
            public Predicate toPredicate(Root<Commission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> directId = new ArrayList<Predicate>();

                directId.add(cb.equal(root.get("directId"), userId));
                directId.add(cb.greaterThanOrEqualTo(root.get("createTime"), begin));
                directId.add(cb.lessThanOrEqualTo(root.get("createTime"), end));
                directId.add(cb.equal(root.get("type"), 1));
                directId.add(cb.equal(root.get("orderStatus"), 2));

                Predicate[] pre = new Predicate[directId.size()];
                Predicate Pre_And = cb.and(directId.toArray(pre));
                Predicate oneLevelId = cb.or(Pre_And, cb.equal(root.get("oneLevelId"), userId));
                Predicate twoLevelId = cb.or(oneLevelId, cb.equal(root.get("twoLevelId"), userId));
                Predicate threeLevelId = cb.or(twoLevelId, cb.equal(root.get("threeLevelId"), userId));

//                List<Predicate> oneLevelId = new ArrayList<>();
//                oneLevelId.add(cb.equal(root.get("oneLevelId"), userId));
//                Predicate[] oneLevelIdPre = new Predicate[oneLevelId.size()];
//                Predicate oneLevelIdPermission = cb.or(oneLevelId.toArray(oneLevelIdPre));

//                List<Predicate> twoLevelId = new ArrayList<>();
//                twoLevelId.add(cb.equal(root.get("twoLevelId"), userId));
//                Predicate[] twoLevelIdPre = new Predicate[twoLevelId.size()];
//                Predicate twoLevelIdPermission = cb.or(twoLevelId.toArray(twoLevelIdPre));
//
//                List<Predicate> threeLevelId = new ArrayList<>();
//                threeLevelId.add(cb.equal(root.get("threeLevelId"), userId));
//                Predicate[] threeLevelIdPre = new Predicate[threeLevelId.size()];
//                Predicate threeLevelIdPermission = cb.or(threeLevelId.toArray(threeLevelIdPre));
//                return query.where(Pre_And, oneLevelIdPermission,twoLevelIdPermission,threeLevelIdPermission).getRestriction();
                return query.where(threeLevelId).getRestriction();

            }
        };
        commissionList = this.commissionRepository.findAll(specification);

        BigDecimal orderPrice = new BigDecimal(0);
        BigDecimal comissionMoney = new BigDecimal(0);

        for (Commission commission : commissionList) {
//            ComissionMoney = ComissionMoney.add(commission.getFirstCommission());
            if (null != commission.getDirectCommission()) {
                comissionMoney = comissionMoney.add(commission.getDirectCommission());
            }
            if (null != commission.getOrderPrice()) {
                //订单金额
                orderPrice = orderPrice.add(commission.getOrderPrice());
            }

        }
        map.put("orderPrice", orderPrice);
        map.put("comissionMoney", comissionMoney);
        return map;
    }

    public IndexCommissionVO dataTransactionDist(String userId, DataTransactionDTO dataTransactionDTO) {
        List<Merchant> merchantList = merchantService.getRepository().findByManagerIdAndTypeAndDelFlag(userId, 1, CommonConstant.NORMAL_FLAG);
        List<String> merchantIds = merchantList.stream().map(Merchant::getId).collect(Collectors.toList());
        //获取所有正常订单
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Order.Status.SUCCESSPAY.getCode());
        statusList.add(Order.Status.REFUNDPART.getCode());
        statusList.add(Order.Status.REFUNDTOTAL.getCode());
        //查询出所有有效订单
        List<Order> orders;
        if (dataTransactionDTO.getStartTime() == null) {
            orders = orderRepository.findByStatusInAndMerchantIdIn(statusList, merchantIds);
        } else {
            orders = orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, dataTransactionDTO.getStartTime(), dataTransactionDTO.getEndTime(), Order.PayWay.MEMBERCARD.getCode());
        }
        IndexCommissionVO indexCommissionVO = new IndexCommissionVO();
        Integer transactionCount = orders.size(); //交易笔数

        //获取佣金和交易金额
        Map<String, BigDecimal> map = getCommission(userId, dataTransactionDTO.getStartTime(), dataTransactionDTO.getEndTime());

        indexCommissionVO.setDealNum(transactionCount);
        indexCommissionVO.setTotalMoney(map.get("orderPrice"));//交易总额
        indexCommissionVO.setCommission(map.get("comissionMoney"));
        return indexCommissionVO;
    }

    public DataTransactionVO dataTransaction(String userId, Integer level, DataTransactionDTO dataTransactionDTO) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = formatter.format(dataTransactionDTO.getStartTime());
        String endTime = formatter.format(dataTransactionDTO.getEndTime());
        //当前企业
        Company company = this.findOne(dataTransactionDTO.getCompanyId());
        List<String> parentIds = new ArrayList<>();
        parentIds.add(company.getId());
        //获取当前企业下所有的商户
        List<String> merchantIds = this.findAllmerchantIds(company.getId());
        //获取所有正常订单
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Order.Status.SUCCESSPAY.getCode());
        statusList.add(Order.Status.REFUNDPART.getCode());
        statusList.add(Order.Status.REFUNDTOTAL.getCode());

        //查询出所有有效订单
        List<Order> orders;
        if (dataTransactionDTO.getStartTime() == null) {
            orders = orderRepository.findByStatusInAndMerchantIdIn(statusList, merchantIds);
        } else {
            orders = orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, dataTransactionDTO.getStartTime(), dataTransactionDTO.getEndTime(), Order.PayWay.MEMBERCARD.getCode());
        }
        DataTransactionVO dataTransactionVO = new DataTransactionVO();

        BigDecimal transactionMoney = BigDecimal.ZERO; //交易总额
        Integer transactionCount = orders.size(); //交易笔数
        BigDecimal refundMoney = BigDecimal.ZERO; //退款总额
        Integer refundCount = 0; //退款笔数
        BigDecimal commissionMoney = getLeftCommission(company, startTime, endTime);
        Integer zfbPayTimes = 0;
        Integer wxPayTimes = 0;
        Integer ohterPayTimes = 0;

        Date lastStartTime = DateUtils.getLastMonth(dataTransactionDTO.getStartTime());//上个月开始时间
        Date lastEndTime = DateUtils.getLastMonth(dataTransactionDTO.getEndTime());//下个月结束时间
        String lastStartTimeString = formatter.format(lastStartTime);
        String lastEndTimeString = formatter.format(lastEndTime);

        for (Order order : orders) {
            transactionMoney = transactionMoney.add(order.getActPayPrice());
            //退款 区分全额退款和部分退款
            if (order.getStatus().equals(Order.Status.REFUNDTOTAL.getCode())) {
                refundCount++;
                refundMoney = refundMoney.add(order.getActPayPrice());
            }
            if (order.getStatus().equals(Order.Status.REFUNDPART.getCode())) {
                refundCount++;
                refundMoney = refundMoney.add(order.getRefundPayPrice());
            }
            if (order.getPayWay().equals(Order.PayWay.ALIPAY.getCode())) {
                zfbPayTimes++;
            } else if (order.getPayWay().equals(Order.PayWay.WXPAY.getCode())) {
                wxPayTimes++;
            } else {
                ohterPayTimes++;
            }
        }
        BigDecimal actualMoney = transactionMoney.subtract(refundMoney); //实际营收

        //查询出上个月所有有效订单
        List<Order> lastOrders;
        if (dataTransactionDTO.getStartTime() == null) {
            lastOrders = orderRepository.findByStatusInAndMerchantIdIn(statusList, merchantIds);
        } else {
            lastOrders = orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, lastStartTime, lastEndTime, Order.PayWay.MEMBERCARD.getCode());
        }

        BigDecimal lastTransactionMoney = BigDecimal.ZERO; //上个月交易总额
        Integer lastTransactionCount = lastOrders.size();//上个月交易笔数
        BigDecimal lastRefundMoney = BigDecimal.ZERO;//上个月退款总额
        Integer lastRefundCount = 0; //上个月退款笔数
        BigDecimal lastCommissionMoney = getLeftCommission(company, lastStartTimeString, lastEndTimeString); //上个月佣金

        for (Order order : lastOrders) {
            lastTransactionMoney = lastTransactionMoney.add(order.getActPayPrice());
            //退款 区分全额退款和部分退款
            if (order.getStatus().equals(Order.Status.REFUNDTOTAL.getCode())) {
                lastRefundCount++;
                lastRefundMoney = lastRefundMoney.add(order.getActPayPrice());
            }
            if (order.getStatus().equals(Order.Status.REFUNDPART.getCode())) {
                lastRefundCount++;
                lastRefundMoney = lastRefundMoney.add(order.getRefundPayPrice());
            }
        }

        BigDecimal lastActualMoney = lastTransactionMoney.subtract(lastRefundMoney);//上个月实际营收


        dataTransactionVO.setLastTransactionMoney(lastTransactionMoney);
        dataTransactionVO.setLastTransactionCount(lastTransactionCount);
        dataTransactionVO.setLastRefundMoney(lastRefundMoney);
        dataTransactionVO.setLastRefundCount(lastRefundCount);
        dataTransactionVO.setLastActualMoney(lastActualMoney);
        dataTransactionVO.setLastCommissionMoney(lastCommissionMoney);

        dataTransactionVO.setTransactionMoney(transactionMoney);
        dataTransactionVO.setTransactionCount(transactionCount);
        dataTransactionVO.setRefundCount(refundCount);
        dataTransactionVO.setRefundMoney(refundMoney);
        dataTransactionVO.setActualMoney(actualMoney);
        dataTransactionVO.setZfbPayTimes(zfbPayTimes);
        dataTransactionVO.setWxPayTimes(wxPayTimes);
        dataTransactionVO.setOhterPayTimes(ohterPayTimes);
        dataTransactionVO.setCommissionMoney(commissionMoney);
        return dataTransactionVO;
    }

    /**
     * 通过上级id集合 获取所有下级id集合
     *
     * @param parentId
     * @return
     */
    private List<String> childIds(List<String> parentId) {
        List<Company> companies = companyRepository.findByParentIdInAndStatus(parentId, Company.Status.SIGNED.getCode());
        return companies.stream().map(Company::getId).collect(Collectors.toList());
    }

    public Company findByIdAndType(String id, Integer type) {
        return companyRepository.findByIdAndType(id, type);
    }

    /**
     * 获取当前企业下面所有的商户 直属商户+非直属商户
     *
     * @param companyId
     * @return
     */
    public List<String> findAllmerchantIds(String companyId) {
        Company company = this.findOne(companyId);
        //定义上级id集合
        List<String> parentIds = new ArrayList<>();
        String idPaht;
        //服务商
        if (company.getType().equals(Company.Type.PROVIDERS.getCode())) {
            idPaht = "|" + companyId;
        } else {
            idPaht = company.getIdPath() + companyId + "|";
        }
        List<Company> childCompany;
        //管理员
        if (company.getType().equals(Company.Type.ADMIN.getCode())) {
            childCompany = companyRepository.findAll();
        } else {
            childCompany = companyRepository.findByIdPathStartingWith(idPaht);
        }
        List<String> companyIds = childCompany.stream().map(Company::getId).collect(Collectors.toList());
        parentIds.add(companyId);
        parentIds.addAll(companyIds);

        List<Merchant> merchants = new ArrayList<>();
        if(company.getType().equals(Company.Type.PROVIDERS.getCode())){
             merchants = merchantRepository.findByServiceProviderId(company.getId());
        }else {
            //获取当前后台下所有的商户
             merchants = merchantRepository.findByCompanyIdIn(parentIds);
        }
        return merchants.stream().map(Merchant::getId).collect(Collectors.toList());
    }


    /**
     * 获取当前企业下面的代理
     *
     * @param companyId
     * @return
     */
    public List<String> findAllCompanyIds(String companyId) {
        Company company = this.findOne(companyId);
        //定义上级id集合
        List<String> parentIds = new ArrayList<>();
        String idPaht;
        if (company.getType().equals(Company.Type.PROVIDERS.getCode())) {
            idPaht = "|" + companyId;
        } else {
            idPaht = company.getIdPath() + companyId + "|";
        }
        List<Company> childCompany;
        if (company.getType().equals(Company.Type.ADMIN.getCode())) {
            childCompany = companyRepository.findAll();
        } else {
            childCompany = companyRepository.findByIdPathStartingWith(idPaht);
        }
        List<String> companyIds = childCompany.stream().map(Company::getId).collect(Collectors.toList());
        parentIds.add(companyId);
        parentIds.addAll(companyIds);
        return parentIds;
    }

    /**
     * 获取企业佣金
     *
     * @param company
     * @param begin
     * @param end
     * @return
     */
    public BigDecimal getLeftCommission(Company company, String begin, String end) {
        if(begin == null){
            return commissionService.getLeftCommission(company);
        }else {
            return commissionService.getLeftCommissionCreateTime(begin, end,company);
        }
//        List<Commission> commissionList = null;
//        if (company.getType().equals(Company.Type.PROVIDERS.getCode())) {
//            if (begin == null) {
//
//                commissionList = commissionRepository.findAllByOemIdAndOrderStatus(company.getId(), 2);
//            } else {
//                commissionList = commissionRepository.findAllByOemIdAndCreateTimeBetweenAndOrderStatus(company.getId(), begin, end, 2);
//            }
//            BigDecimal ComissionMoney = new BigDecimal(0);
//            for (Commission commission : commissionList) {
//                ComissionMoney = ComissionMoney.add(commission.getOemCommission() == null ? new BigDecimal("0") : commission.getOemCommission());
//            }
//            return ComissionMoney;
//        } else if (company.getType().equals(Company.Type.OPERATOR.getCode())) {
//            if (begin == null) {
//                commissionList = commissionRepository.findAllByFirstIdAndOrderStatus(company.getId(), 2);
//            } else {
//                commissionList = commissionRepository.findAllByFirstIdAndCreateTimeBetweenAndOrderStatus(company.getId(), begin, end, 2);
//            }
//            BigDecimal ComissionMoney = new BigDecimal(0);
//            for (Commission commission : commissionList) {
//                ComissionMoney = ComissionMoney.add(commission.getFirstCommission());
//            }
//            return ComissionMoney;
//        } else if (company.getType().equals(Company.Type.DISTRIBUTUTORS.getCode())) {
//            if (begin == null) {
//                commissionList = commissionRepository.findAllBySecondIdAndOrderStatus(company.getId(), 2);
//            } else {
//                commissionList = commissionRepository.findAllBySecondIdAndCreateTimeBetweenAndOrderStatus(company.getId(), begin, end, 2);
//            }
//            BigDecimal ComissionMoney = new BigDecimal(0);
//            for (Commission commission : commissionList) {
//                ComissionMoney = ComissionMoney.add(commission.getSecondCommission());
//            }
//            return ComissionMoney;
//        } else if (company.getType().equals(Company.Type.THIRDAGENT.getCode())) {
//            if (begin == null) {
//                commissionList = commissionRepository.findAllByThirdIdAndOrderStatus(company.getId(), 2);
//            } else {
//                commissionList = commissionRepository.findAllByThirdIdAndCreateTimeBetweenAndOrderStatus(company.getId(), begin, end, 2);
//            }
//            BigDecimal ComissionMoney = new BigDecimal(0);
//            for (Commission commission : commissionList) {
//                ComissionMoney = ComissionMoney.add(commission.getSecondCommission());
//            }
//            return ComissionMoney;
//        }

    }

  /*  public BigDecimal getCommission(String userId, Date begin, Date end) {
        List<Commission> commissionList = null;
        if (company.getType().equals(Company.Type.PROVIDERS.getCode())) {
            if (begin == null) {
                commissionList = commissionRepository.findAllByOemIdAndOrderStatus(company.getId(), 2);
            } else {
                commissionList = commissionRepository.findAllByOemIdAndCreateTimeBetweenAndOrderStatus(company.getId(), begin, end, 2);
            }
            BigDecimal ComissionMoney = new BigDecimal(0);
            for (Commission commission : commissionList) {
                ComissionMoney = ComissionMoney.add(commission.getOemCommission());
            }
            return ComissionMoney;
        } else if (company.getType().equals(Company.Type.OPERATOR.getCode())) {
            if (begin == null) {
                commissionList = commissionRepository.findAllByFirstIdAndOrderStatus(company.getId(), 2);
            } else {
                commissionList = commissionRepository.findAllByFirstIdAndCreateTimeBetweenAndOrderStatus(company.getId(), begin, end, 2);
            }
            BigDecimal ComissionMoney = new BigDecimal(0);
            for (Commission commission : commissionList) {
                ComissionMoney = ComissionMoney.add(commission.getFirstCommission());
            }
            return ComissionMoney;
        } else if (company.getType().equals(Company.Type.DISTRIBUTUTORS.getCode())) {
            if (begin == null) {
                commissionList = commissionRepository.findAllBySecondIdAndOrderStatus(company.getId(), 2);
            } else {
                commissionList = commissionRepository.findAllBySecondIdAndCreateTimeBetweenAndOrderStatus(company.getId(), begin, end, 2);
            }
            BigDecimal ComissionMoney = new BigDecimal(0);
            for (Commission commission : commissionList) {
                ComissionMoney = ComissionMoney.add(commission.getSecondCommission());
            }
            return ComissionMoney;
        } else if (company.getType().equals(Company.Type.THIRDAGENT.getCode())) {
            if (begin == null) {
                commissionList = commissionRepository.findAllByThirdIdAndOrderStatus(company.getId(), 2);
            } else {
                commissionList = commissionRepository.findAllByThirdIdAndCreateTimeBetweenAndOrderStatus(company.getId(), begin, end, 2);
            }
            BigDecimal ComissionMoney = new BigDecimal(0);
            for (Commission commission : commissionList) {
                ComissionMoney = ComissionMoney.add(commission.getSecondCommission());
            }
            return ComissionMoney;
        }
        return new BigDecimal(0);
    }
*/

    /**
     * 获取企业单个商户佣金
     *
     * @param company
     * @param begin
     * @param end
     * @return
     */
    public BigDecimal getMerchantLeftCommission(Company company, String merchantId, Date begin, Date end) {
        List<Commission> commissionList = null;
        if (company.getType().equals(Company.Type.PROVIDERS.getCode())) {
            if (begin == null) {
                commissionList = commissionRepository.findAllByOemIdAndMerchantIdAndOrderStatus(company.getId(), merchantId, 2);
            } else {
                commissionList = commissionRepository.findAllByOemIdAndMerchantIdAndCreateTimeBetweenAndOrderStatus(company.getId(), merchantId, begin, end, 2);
            }
            BigDecimal ComissionMoney = new BigDecimal(0);
            for (Commission commission : commissionList) {
                ComissionMoney = ComissionMoney.add(commission.getOemCommission());
            }
            return ComissionMoney;
        } else if (company.getType().equals(Company.Type.OPERATOR.getCode())) {
            if (begin == null) {
                commissionList = commissionRepository.findAllByFirstIdAndMerchantIdAndOrderStatus(company.getId(), merchantId, 2);
            } else {
                commissionList = commissionRepository.findAllByFirstIdAndMerchantIdAndCreateTimeBetweenAndOrderStatus(company.getId(), merchantId, begin, end, 2);
            }
            BigDecimal ComissionMoney = new BigDecimal(0);
            for (Commission commission : commissionList) {
                ComissionMoney = ComissionMoney.add(commission.getFirstCommission());
            }
            return ComissionMoney;
        } else if (company.getType().equals(Company.Type.DISTRIBUTUTORS.getCode())) {
            if (begin == null) {
                commissionList = commissionRepository.findAllBySecondIdAndAndMerchantIdAndOrderStatus(company.getId(), merchantId, 2);
            } else {
                commissionList = commissionRepository.findAllBySecondIdAndMerchantIdAndCreateTimeBetweenAndOrderStatus(company.getId(), merchantId, begin, end, 2);
            }
            BigDecimal ComissionMoney = new BigDecimal(0);
            for (Commission commission : commissionList) {
                ComissionMoney = ComissionMoney.add(commission.getSecondCommission());
            }
            return ComissionMoney;
        } else if (company.getType().equals(Company.Type.THIRDAGENT.getCode())) {
            if (begin == null) {
                commissionList = commissionRepository.findAllByThirdIdAndMerchantIdAndOrderStatus(company.getId(), merchantId, 2);
            } else {
                commissionList = commissionRepository.findAllByThirdIdAndMerchantIdAndCreateTimeBetweenAndOrderStatus(company.getId(), merchantId, begin, end, 2);
            }
            BigDecimal ComissionMoney = new BigDecimal(0);
            for (Commission commission : commissionList) {
                ComissionMoney = ComissionMoney.add(commission.getSecondCommission());
            }
            return ComissionMoney;
        }
        return new BigDecimal(0);


    }

    /**
     * 获取二级代理商的佣金
     *
     * @param company
     * @return
     */
    public CommissionDataVO getSecondCommission(Company company, Date begin, Date end) {
        CommissionDataVO commissionDataVO = new CommissionDataVO();
        List<Order> orders;
        List<String> compayIds = new ArrayList<>();
        compayIds.add(company.getId());
        List<Merchant> merchants = merchantRepository.findByCompanyIdIn(compayIds);
        if (merchants.size() == 0) {
            return commissionDataVO;
        }
        List<String> merchantIds = merchants.stream().map(Merchant::getId).collect(Collectors.toList());
        //获取所有正常订单
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Order.Status.SUCCESSPAY.getCode());
        statusList.add(Order.Status.REFUNDPART.getCode());
        statusList.add(Order.Status.REFUNDTOTAL.getCode());
        if (begin == null) {
            orders = orderRepository.findByMerchantIdInAndStatusInAndPayWayNot(merchantIds, statusList, Order.PayWay.MEMBERCARD.getCode());
        } else {
            orders = orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, begin, end, Order.PayWay.MEMBERCARD.getCode());
        }
        if (orders.size() == 0) {
            return commissionDataVO;
        }
        //二级代理所有流水
        BigDecimal allMoney = new BigDecimal(0);
        //支付通道收取的费用
        BigDecimal payFee = new BigDecimal(0);
        for (Order order : orders) {
            allMoney = allMoney.add(order.getActPayPrice());
            payFee = payFee.add(order.getActPayPrice().multiply(order.getInterestRate()));
        }
        //获取一级代理
        Company firstCompany = companyRepository.findOne(company.getParentId());
        //获取服务商
        Company oemCompany = companyRepository.findOne(firstCompany.getParentId());
        //当前企业流水（扣除支付通道利率）
        BigDecimal allCommisson = allMoney.subtract(payFee);
        //佣金 = （流水-支付通道费用）*服务商抽成*一级抽成*二级抽成
        BigDecimal selfCommission = allCommisson.multiply(oemCompany.getPayProrata()).multiply(firstCompany.getPayProrata()).multiply(company.getPayProrata());
        commissionDataVO.setAllMoney(allCommisson);
        commissionDataVO.setCommission(selfCommission);
        return commissionDataVO;
    }

    /**
     * 获取一级代理商的佣金
     *
     * @param company
     * @return
     */
    public CommissionDataVO getFirstCommission(Company company, Date begin, Date end) {

        CommissionDataVO commissionDataVO = new CommissionDataVO();
        //参与分配的佣金
        BigDecimal allCommission = BigDecimal.ZERO;
        //可获取的佣金
        BigDecimal selfCommission = BigDecimal.ZERO;

        /**
         * 获取直接开通的商户中获取的抽成
         */
        List<Order> orders;
        List<String> compayIds = new ArrayList<>();
        compayIds.add(company.getId());
        List<Merchant> firstMerchants = merchantRepository.findByCompanyIdIn(compayIds);
        List<String> merchantIds = firstMerchants.stream().map(Merchant::getId).collect(Collectors.toList());
        //获取所有正常订单
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Order.Status.SUCCESSPAY.getCode());
        statusList.add(Order.Status.REFUNDPART.getCode());
        statusList.add(Order.Status.REFUNDTOTAL.getCode());
        if (begin == null) {
            orders = orderRepository.findByMerchantIdInAndStatusInAndPayWayNot(merchantIds, statusList, Order.PayWay.MEMBERCARD.getCode());
        } else {
            orders = orderRepository.findByStatusInAndMerchantIdInAndPayTimeBetweenAndPayWayNot(statusList, merchantIds, begin, end, Order.PayWay.MEMBERCARD.getCode());
        }
        //查询出所有流水
        BigDecimal allMoney = new BigDecimal(0);
        //支付通道收取的费用
        BigDecimal payFee = new BigDecimal(0);
        for (Order order : orders) {
            allMoney = allMoney.add(order.getActPayPrice());
            payFee = payFee.add(order.getActPayPrice().multiply(order.getInterestRate()));
        }
        //查询数服务商
        Company oemCompany = companyRepository.findOne(company.getParentId());
        //直接开通的商户需要收取的佣金 （流水-支付通道费用）*服务商抽成*一级抽成

        allCommission = allCommission.add(allMoney.subtract(payFee));
        selfCommission = selfCommission.add(allMoney.subtract(payFee).multiply(oemCompany.getPayProrata()).multiply(company.getPayProrata()));

        /**
         * 获取二级代理商中获取的抽成
         */

        //获取开通的二级代理商
        List<Company> secondCompany = companyRepository.findByParentId(company.getId());
        for (Company item : secondCompany) {
            CommissionDataVO itemCommissionDataVO = this.getSecondCommission(item, begin, end);
            allCommission = allCommission.add(itemCommissionDataVO.getAllMoney());
            selfCommission = selfCommission.add(itemCommissionDataVO.getAllMoney().multiply(oemCompany.getPayProrata()).multiply(company.getPayProrata()).subtract(itemCommissionDataVO.getCommission()));
        }
        commissionDataVO.setAllMoney(allCommission);
        commissionDataVO.setCommission(selfCommission);
        return commissionDataVO;
    }

    /**
     * 获取服务商的佣金
     *
     * @param company
     * @return
     */
    //@Test
    public CommissionDataVO getOemCommission(Company company, Date begin, Date end) {
        CommissionDataVO commissionDataVO = new CommissionDataVO();
        //参与分配的佣金
        BigDecimal allCommission = BigDecimal.ZERO;
        //可获取的佣金
        BigDecimal selfCommission = BigDecimal.ZERO;
        //获取开通的一级代理商
        List<Company> firstCompany = companyRepository.findByParentId(company.getId());
        for (Company item : firstCompany) {
            CommissionDataVO itemCommission = this.getFirstCommission(item, begin, end);
            allCommission = allCommission.add(itemCommission.getAllMoney());
            selfCommission = selfCommission.add(itemCommission.getAllMoney().multiply(company.getPayProrata()).subtract(itemCommission.getCommission()));
        }
        commissionDataVO.setAllMoney(allCommission);
        commissionDataVO.setCommission(selfCommission);
        return commissionDataVO;
    }

    public List<Company> findByParentId(String id) {
        return companyRepository.findByParentId(id);

    }


    public List<Company> findByParentIdAndTypeLessThanEqual(String id, Integer type) {
        return companyRepository.findByParentIdAndTypeLessThanEqual(id, type);
    }

    public void getCompanyByParentId(Company company, List<String> companyIdList) {
        List<String> resultCompanyIdList = companyIdList;
        if (resultCompanyIdList == null) {
            resultCompanyIdList = new ArrayList<String>();
        }
        //如果是三级
        if (company.getType().equals(Company.Type.THIRDAGENT.getCode())) {
            resultCompanyIdList.add(company.getId());
        } else {
            resultCompanyIdList.add(company.getId());
            List<Company> companyList = this.companyRepository.findByCompanyIdPath(company.getId());
            for (Company comp : companyList) {
                resultCompanyIdList.add(comp.getId());
            }
        }
    }

    /**
     *
     * @param companyId
     * @return
     */
    public DataViewAppVo appdataview(String companyId) {
        Company company = this.findOne(companyId);
        //定义1返回对象
        DataViewAppVo dataViewAppVo = new DataViewAppVo();

        //如果当前用户为三级代理商
        if (company.getType().equals(Company.Type.THIRDAGENT.getCode())) {
            //查询业务员
            List<User> salesmanNum = userRepository.findByCompanyId(companyId);
            dataViewAppVo.setSalesmanNum(salesmanNum.size());
        } else {
            //通过idpath获取下级个数，一二三级代理/商户数
            String idPath;
            if (company.getType().equals(Company.Type.PROVIDERS.getCode())) {
                idPath = "|" + companyId;
            } else {
                idPath = company.getIdPath() + companyId + "|";
            }
            //全部下级
            List<Company> companies;
            if (company.getType().equals(Company.Type.ADMIN.getCode())) {
                companies = companyRepository.findAll();
            } else {
                companies = companyRepository.findByIdPathStartingWith(idPath);
            }

            //一级代理商
            List<Company> firstCompany = companies.stream().filter(item -> item.getType().equals(Company.Type.OPERATOR.getCode())).collect(Collectors.toList());
            int firstAgentNum = firstCompany.size();
            dataViewAppVo.setFirstAgentNum(firstAgentNum);

            //二级代理商
            List<Company> secondCompany = companies.stream().filter(item -> item.getType().equals(Company.Type.DISTRIBUTUTORS.getCode())).collect(Collectors.toList());
            int secondCompanyNum = secondCompany.size();
            dataViewAppVo.setSecondAgentNum(secondCompanyNum);

            //三级代理商
            List<Company> thirdCompany = companies.stream().filter(item -> item.getType().equals(Company.Type.THIRDAGENT.getCode())).collect(Collectors.toList());
            int thirdAgentNum = thirdCompany.size();
            dataViewAppVo.setThirdAgentNum(thirdAgentNum);

            //代理总数
            dataViewAppVo.setCountAgentNum(firstAgentNum + secondCompanyNum + thirdAgentNum);
        }
        //查询出所有有效订单
        List<Order> orders = null;
        List<String> merchantIds = null;
        //如果是服务商角色
        if(company.getType().equals(Company.Type.PROVIDERS.getCode())){
            //获取当前企业下所有商户
            List<Merchant> merchantList = merchantRepository.findByServiceProviderIdAndType(company.getId(), Merchant.Type.NORMAL.getCode());
            merchantIds =  merchantList.stream().map(Merchant::getId).collect(Collectors.toList());
            //首页数据
            merchantService.getTransactionAmount(company.getId(),dataViewAppVo);

        }else if(!company.getType().equals(Company.Type.PROVIDERS.getCode()) || !company.getType().equals(Company.Type.ADMIN.getCode())){
            //获取当前企业下所有的商户
            merchantIds = this.findAllmerchantIds(company.getId());
            //获取所有正常订单
            List<Integer> statusList = new ArrayList<>();
            statusList.add(Order.Status.SUCCESSPAY.getCode());
            statusList.add(Order.Status.REFUNDPART.getCode());
            statusList.add(Order.Status.REFUNDTOTAL.getCode());
            orders = orderRepository.findByStatusInAndMerchantIdIn(statusList, merchantIds);

            //交易总额
            BigDecimal transactionMoney = BigDecimal.ZERO;
            //交易笔数
            Integer transactionCount = orders.size();
            //退款总额
            BigDecimal refundMoney = BigDecimal.ZERO;
            //退款笔数
            Integer refundCount = 0;

            Integer zfbPayTimes = 0;
            Integer wxPayTimes = 0;
            Integer ohterPayTimes = 0;

            for (Order order : orders) {
                transactionMoney = transactionMoney.add(order.getActPayPrice());
                //退款 区分全额退款和部分退款
                if (order.getStatus().equals(Order.Status.REFUNDTOTAL.getCode())) {
                    refundCount++;
                    refundMoney = refundMoney.add(order.getActPayPrice());
                }
                if (order.getStatus().equals(Order.Status.REFUNDPART.getCode())) {
                    refundCount++;
                    refundMoney = refundMoney.add(order.getRefundPayPrice());
                }
                if (order.getPayWay().equals(Order.PayWay.ALIPAY.getCode())) {
                    zfbPayTimes++;
                } else if (order.getPayWay().equals(Order.PayWay.WXPAY.getCode())) {
                    wxPayTimes++;
                } else {
                    ohterPayTimes++;
                }
            }
            //实际营收
            BigDecimal actualMoney = transactionMoney.subtract(refundMoney);
            //销售总额
            dataViewAppVo.setTransactionMoney(actualMoney);
            dataViewAppVo.setRefundCount(refundCount);
            dataViewAppVo.setRefundMoney(refundMoney);
            //交易笔数
            dataViewAppVo.setTransactionCount(transactionCount);

        }
        //代理类型
        dataViewAppVo.setType(company.getType());
        //佣金
        BigDecimal commissionMoney = getLeftCommission(company, null, null);
        dataViewAppVo.setCommissionMoney(commissionMoney);
        //商户总数
        dataViewAppVo.setMerchantNum(merchantIds.size());
        return dataViewAppVo;
    }

    /**
     * 商家营收排行榜
     *
     * @return
     */
    public List<MerchantDataVo> getMerchantData(Company company, Date startTime, Date endTime) {

        ArrayList list = new ArrayList();
        List<String> companyIds = new ArrayList<>();
        OrderActPayPriceDto orderActPayPriceDto;
        List<Merchant> merchantList;

        this.getCompanyByParentId(company, companyIds);

        //获取当前企业下所有的商户
        List<String> merchantIds = this.findAllmerchantIds(company.getId());

        if (merchantIds.size() > 0) {

            merchantList = merchantRepository.findByCompanyIdIn(companyIds);

            //获取所有正常订单
            List<Integer> statusList = new ArrayList<>();
            statusList.add(Order.Status.SUCCESSPAY.getCode());
            statusList.add(Order.Status.REFUNDPART.getCode());
            statusList.add(Order.Status.REFUNDTOTAL.getCode());

            Map<String, String> userNameMap = authServiceFeign.getUserName();
            Map<String, String> companyMap = authServiceFeign.getCompanyName();

            //查询每个商户
            for (Merchant merchant : merchantList) {

                MerchantDataVo merchantDataVo = new MerchantDataVo();
                //统计每一个商户的总金额（按时间查询）
                if (startTime == null) {
                    orderActPayPriceDto = orderRepository.findactPpayPrice(merchant.getId());
                } else {
                    orderActPayPriceDto = orderRepository.findactPpayPriceOrder(merchant.getId(), startTime, endTime);
                }

                if (orderActPayPriceDto.getTotalPrice() == null) {
                    merchantDataVo.setCountPayPrice(BigDecimal.ZERO);
                } else {
                    BigDecimal actualMoney = orderActPayPriceDto.getTotalPrice().subtract(orderActPayPriceDto.getRefundPrice()); //实际营收
                    merchantDataVo.setCountPayPrice(actualMoney);
                }

                merchantDataVo.setStoreName(merchant.getName());

                Company company1 = companyRepository.findOne(merchant.getCompanyId());
                merchantDataVo.setCompanyType(company1.getType());
                merchantDataVo.setCompanyName(userNameMap.get(merchant.getManagerId())); //业务员名称

                list.add(merchantDataVo);
            }
        } else {
            return list;
        }

        return list;
    }


    /**
     * 我发展的商户
     *
     * @param company
     * @return
     */
    public List<MerchantListVo> get_merchant_list(Company company) {

        List<MerchantListVo> listVoList = new ArrayList<>();

        Map<String, String> userNameMap = authServiceFeign.getUserName();
        //查询我的商户数
        List<Merchant> merchantList = merchantRepository.findByCompanyId(company.getId());

        //查询每个商户总销售金额
        for (Merchant merchant : merchantList) {

            MerchantListVo merchantListVo = new MerchantListVo();
            //计算每个商户的总金额
            OrderActPayPriceDto orderActPayPriceDto = orderRepository.findactPpayPrice(merchant.getId());
            if (orderActPayPriceDto.getTotalPrice() == null) {
                merchantListVo.setCountPayPrice(BigDecimal.ZERO);
            } else {
                BigDecimal actualMoney = orderActPayPriceDto.getTotalPrice().subtract(orderActPayPriceDto.getRefundPrice()); //实际营收
                merchantListVo.setCountPayPrice(actualMoney);
            }
            merchantListVo.setMerchant_id(merchant.getId()); //商户id
            merchantListVo.setStoreName(merchant.getName());//商户名称
            //结算每个商户的佣金额度
            OrderCommissionDto orderCommissionDto = commissionRepository.getCountCommission(company.getId(), merchant.getId());
            if (orderCommissionDto.getCommission() == null) {
                merchantListVo.setCommissionMoney(BigDecimal.ZERO);
            } else {
                merchantListVo.setCommissionMoney(orderCommissionDto.getCommission()); //总佣金额度
            }
            merchantListVo.setCompanyType(company.getType()); //代理类型
            merchantListVo.setCompanyId(company.getId()); //公司id
            merchantListVo.setManagerName(userNameMap.get(merchant.getManagerId()));//业务员名称
            listVoList.add(merchantListVo);
        }
        return listVoList;
    }

    /**
     * 代理发展的商户
     * @param company
     * @param type
     * @return
     */
    public List<MerchantListVo> getProxyMerchantList(Company company, Integer type) {
        List<MerchantListVo> listVoList = new ArrayList<>();
        //通过idpath获取下级个数，一二三级代理/商户数
        String idPath;
        if (company.getType().equals(Company.Type.PROVIDERS.getCode())) {
            idPath = "|" + company.getId();
        } else {
            idPath = company.getIdPath() + company.getId() + "|";
        }
        //全部下级
        List<Company> companies;
        if (company.getType().equals(Company.Type.ADMIN.getCode())) {
            companies = companyRepository.findAll();
        } else {
            companies = companyRepository.findByIdPathStartingWith(idPath);
        }
        //查询每个公司下的商户数
        for (Company newCompany : companies) {
            //根据公司类型查询
            if (type.equals(newCompany.getType())) {
                MerchantListVo merchantListVo = new MerchantListVo();
                List<String> merchantIds = new ArrayList<>();
                //查询出每个公司下有多少个商户
                List<Merchant> merchantList = merchantRepository.findByCompanyId(newCompany.getId());
                merchantListVo.setMerchantNum(merchantList.size());//商户数
                if (merchantList.size() > 0) {
                    merchantIds.addAll(merchantList.stream().map(Merchant::getId).collect(Collectors.toList()));
                }
                //判断当前公司是否有商户
                if (merchantIds != null && merchantIds.size() != 0) {
                    OrderActPayPriceDto orderActPayPriceDto = orderRepository.findByPayPriceNum(merchantIds);
                    if (orderActPayPriceDto.getTotalPrice() == null) {
                        merchantListVo.setCountPayPrice(BigDecimal.ZERO);//销售总额
                    } else {
                        merchantListVo.setCountPayPrice(orderActPayPriceDto.getTotalPrice());//销售总额
                    }
                } else {
                    merchantListVo.setCountPayPrice(BigDecimal.ZERO);
                }
                merchantListVo.setCompanyId(newCompany.getId());//公司id
                merchantListVo.setCompanyName(newCompany.getName());//公司名称
                merchantListVo.setCompanyType(newCompany.getType());//公司类型
                listVoList.add(merchantListVo);
            } else if (type.equals(5)) {
                MerchantListVo merchantListVo = new MerchantListVo();
                List<String> merchantIds = new ArrayList<>();
                //查询出每个公司下有多少个商户
                List<Merchant> merchantList = merchantRepository.findByCompanyId(newCompany.getId());
                merchantListVo.setMerchantNum(merchantList.size());//商户数
                if (merchantList.size() > 0) {
                    merchantIds.addAll(merchantList.stream().map(Merchant::getId).collect(Collectors.toList()));
                }
                //判断当前公司是否有商户
                if (merchantIds != null && merchantIds.size() != 0) {
                    OrderActPayPriceDto orderActPayPriceDto = orderRepository.findByPayPriceNum(merchantIds);
                    if (orderActPayPriceDto.getTotalPrice() == null) {
                        merchantListVo.setCountPayPrice(BigDecimal.ZERO);//销售总额
                    } else {
                        merchantListVo.setCountPayPrice(orderActPayPriceDto.getTotalPrice());//销售总额
                    }
                } else {
                    merchantListVo.setCountPayPrice(BigDecimal.ZERO);
                }
                merchantListVo.setCompanyId(newCompany.getId());//公司id
                merchantListVo.setCompanyName(newCompany.getName());//公司名称
                merchantListVo.setCompanyType(newCompany.getType());//公司类型
                listVoList.add(merchantListVo);
            }
        }
        return listVoList;

    }

    public List<Company> listCompany(Company company) {
        return companyRepository.findByTypeAndStatusAndDelFlag(company.getType(), company.getStatus(), company.getDelFlag());
    }
}