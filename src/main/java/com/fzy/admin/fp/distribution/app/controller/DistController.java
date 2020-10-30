package com.fzy.admin.fp.distribution.app.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.crypto.digest.BCrypt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fzy.admin.fp.auth.AuthConstant;
import com.fzy.admin.fp.auth.domain.Equipment;
import com.fzy.admin.fp.auth.domain.SiteInfo;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.dto.DataTransactionDTO;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.auth.service.EquipmentService;
import com.fzy.admin.fp.auth.service.SiteInfoService;
import com.fzy.admin.fp.auth.utils.DateUtils;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.validation.annotation.Valid;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.config.RedisConfiguration;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.app.dto.TeamFirstDTO;
import com.fzy.admin.fp.distribution.app.vo.*;
import com.fzy.admin.fp.distribution.feedback.service.FeedbackService;
import com.fzy.admin.fp.distribution.money.domain.Costs;
import com.fzy.admin.fp.distribution.money.domain.Wallet;
import com.fzy.admin.fp.distribution.app.dto.DistUserDTO;
import com.fzy.admin.fp.distribution.app.service.DistUserService;
import com.fzy.admin.fp.distribution.money.service.CostsService;
import com.fzy.admin.fp.distribution.money.service.WalletService;
import com.fzy.admin.fp.distribution.order.domain.DistOrder;
import com.fzy.admin.fp.distribution.order.service.DistOrderService;
import com.fzy.admin.fp.distribution.order.service.ShopOrderService;
import com.fzy.admin.fp.distribution.pc.domain.SystemSetup;
import com.fzy.admin.fp.distribution.pc.service.AppDownService;
import com.fzy.admin.fp.distribution.pc.service.SystemSetupService;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.order.order.domain.CommissionDay;
import com.fzy.admin.fp.order.order.service.CommissionDayService;
import com.fzy.admin.fp.order.order.service.CommissionService;
import com.fzy.assist.wraps.BeanWrap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yy
 * @Date 2019-11-13 13:39:15
 * 分销app用户接口
 */
@RestController
@RequestMapping("/dist/app/user")
@Api(value = "UserController", tags = {"分销-用户管理"})
public class DistController extends BaseContent {
    @Value("${secret.tokenExpiration}")
    private int tokenExpiration;

    @Resource
    private CommissionDayService commissionDayService;

    @Resource
    private DistUserService distUserService;

    @Resource
    private RedisConfiguration redisConfiguration;

    @Resource
    private WalletService walletService;

    @Autowired
    private SiteInfoService siteInfoService;

    @Resource
    private CostsService costsService;

    @Resource
    private DistOrderService distOrderService;

    @Resource
    private AppDownService appDownService;

    @Resource
    private ShopOrderService shopOrderService;

    @Resource
    private FeedbackService feedbackService;

    @Resource
    private MerchantService merchantService;

    @Resource
    private EquipmentService equipmentService;

    @Resource
    private CompanyService companyService;

    @Resource
    private CommissionService commissionService;

    @Resource
    private SystemSetupService systemSetupService;

    /**
     * 商户app登陆
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "登陆", notes = "登陆")
    public Resp login(String username, String password) {

        DistUser distUser = distUserService.getRepository().findByUserName(username);
        if (ParamUtil.isBlank(distUser)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该手机号尚未注册");
        }
        if (distUser.getGrade() == 4) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "业务员不能登录");
        }
        if (distUser.getStatus().equals(User.Status.DISABLE.getCode())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "用户已被禁用");
        }
        if (!BCrypt.checkpw(password, distUser.getPassword())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "密码错误");
        }
        String token = JWT.create().withIssuer(AuthConstant.AUTH_NAME).withSubject(distUser.getId())
                .withClaim("parentId", distUser.getParentId())
                .withClaim("serviceProviderId", distUser.getServiceProviderId())
                .withClaim("grade", distUser.getGrade())
                .withClaim("loginType", 1)
                .withClaim("userName", distUser.getUserName())
                .withExpiresAt(new Date(new Date().getTime() + tokenExpiration * 60 * 1000))
                .sign(Algorithm.HMAC512(distUser.getUserName()));
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("userId", distUser.getId());
        map.put("inviteNum", distUser.getInviteNum());
        map.put("grade", distUser.getGrade());
        map.put("url", appDownService.getRepository().findByServiceProviderId(distUser.getServiceProviderId()));
        return Resp.success(map, "登录成功");
    }

    /**
     * 创建账号
     *
     * @param phone
     * @param code
     * @param password
     * @param inviteNum
     * @return
     */
    @PostMapping("/create")
    @ApiOperation(value = "创建账号", notes = "创建账号")
    public Resp create(HttpServletRequest request, String phone, String code, String password, String inviteNum) {
        String domainName = request.getServerName();
        if (StringUtil.isEmpty(phone) || phone.length() != 11) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请输入有效手机号");
        }
        if (StringUtil.isEmpty(password)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "密码不能为空");
        }
        if (!password.matches("^[a-zA-Z0-9_-]{6,20}$")) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "密码应为英文、数字、下划线组成的6-20位字符");
        }
        if (StringUtil.isEmpty(inviteNum)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "邀请码不能为空");
        }
        if (StringUtil.isEmpty(code) || !code.equals(redisConfiguration.redisPoolFactory().getResource().get(phone))) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "验证码不正确");
        }
        //通过请求的域名得到id
        SiteInfo siteInfo = siteInfoService.findByDomainName(domainName);

        DistUser parent = distUserService.getRepository().findByInviteNumAndServiceProviderId(inviteNum, siteInfo.getServiceProviderId());
        if (parent == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "邀请码不存在");
        }
        if (parent.getStatus() == 1) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该邀请码已失效");
        }
        DistUser distUser = distUserService.getRepository().findByUserName(phone);
        if (distUser != null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "账号已存在");
        }
        //parentId=邀请者的父级邀请码+邀请者的邀请码
        String stair = parent.getParentId() + inviteNum + "/";
        distUser = new DistUser();
        String[] split = stair.split("/");
        int length = split.length;

        //---------  0410进行了修改调整  ---------------
        //查询运营中心
        String operationsInviteNum = split[0];
        DistUser operations = distUserService.getRepository().findByInviteNum(operationsInviteNum);
        if (length == 1 && parent.getGrade().equals(Integer.valueOf(2))){
            distUser.setOneLevelId(operations.getId());
        }else {
            distUser.setZeroLevelId(operations.getId());
        }
        //---------  0410进行了修改调整  ---------------

        if (length > 1) {
            distUser.setOneLevelId(parent.getId());
        }
        if (length > 2) {
            distUser.setTwoLevelId(parent.getOneLevelId());
        }
        if (length > 3) {
            distUser.setThreeLevelId(parent.getTwoLevelId());
        }
        distUser.setUserName(phone);
        distUser.setServiceProviderId(parent.getServiceProviderId());
        distUser.setLevel(length);
        distUser.setParentId(stair);
        distUser.setGrade(0);
        distUser.setCity("未知");
        distUser.setHeadImg(CommonConstant.HEAD_IMG);
        distUser.setPassword(BCrypt.hashpw(password));
        distUser.setSex(0);
        distUser.setStatus(0);
        distUser.setName("skb" + phone.substring(phone.length() - 4, phone.length()));
        distUserService.saveUser(distUser);

        parent.setDirectNum(parent.getDirectNum() + 1);
        distUserService.update(parent);
        return Resp.success("注册成功");
    }

    @GetMapping("/findByCode")
    @ApiOperation(value = "确认邀请人", notes = "确认邀请人")
    public Resp<DistUserVO> findByCode(String code) {
        if (StringUtil.isEmpty(code)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请填写邀请码");
        }
        return Resp.success(distUserService.getRepository().findByInviteNum1(code));
    }

    /**
     * 忘记密码
     *
     * @param phone
     * @param code
     * @param password
     * @return
     */
    @PostMapping("/forget")
    @ApiOperation(value = "忘记密码", notes = "忘记密码")
    public Resp forget(String phone, String code, String password) {
        if (StringUtil.isEmpty(phone) || phone.length() != 11) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "请输入有效手机号");
        }
        DistUser distUser = distUserService.getRepository().findByUserName(phone);
        if (distUser == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "账号不存在");
        }
        if (StringUtil.isEmpty(password)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "密码不能为空");
        }
        if (!password.matches("^[a-zA-Z0-9_-]{6,20}$")) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "密码应为英文、数字、下划线组成的6-20位字符");
        }
        if (StringUtil.isEmpty(code) || !code.equals(redisConfiguration.redisPoolFactory().getResource().get(phone))) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "验证码不正确");
        }
        distUser.setPassword(BCrypt.hashpw(password));
        distUserService.update(distUser);
        return Resp.success("修改成功");
    }

    /**
     * 修改用户资料
     * @param userId
     * @param distUserDTO
     * @return
     */
    /*@PostMapping("/update")
    @ApiOperation(value = "修改资料", notes = "修改资料")
    public Resp update(@UserId String userId,@Valid DistUserDTO distUserDTO,@TokenInfo(property="serviceProviderId")String serviceProviderId){
        DistUser distUser = distUserService.getRepository().findOne(userId);
        String[] grade = distUser.getParentId().split("/");
        String parentId="";
        if(grade.length>0){
            parentId = grade[grade.length - 1];
        }
        //判断用户是否需要修改邀请码
        if(StringUtil.isNotEmpty(distUserDTO.getNewInviteNum())&&!distUserDTO.getNewInviteNum().equals(parentId)){
            if(getDay(new Date(),distUser.getCreateTime())<=60){
                return new Resp().error(Resp.Status.PARAM_ERROR,"注册起60天内不能变更邀请人");
            }
            DistUser parent = distUserService.getRepository().findByInviteNumAndServiceProviderId(distUserDTO.getNewInviteNum(), distUser.getServiceProviderId());
            if(parent==null){
                return new Resp().error(Resp.Status.PARAM_ERROR,"无效的邀请码");
            }
            if(parent.getStatus()==1){
                return new Resp().error(Resp.Status.PARAM_ERROR,"该邀请码已失效");
            }
            StringBuilder newParentId=new StringBuilder();
            for(int i=0;i<grade.length-1;i++){
                newParentId.append(grade[i]+"/");
            }
            parentId=newParentId.append(distUserDTO.getNewInviteNum()+"/").toString();
            distUser.setParentId(parentId);
        }
        if(StringUtil.isNotEmpty(distUserDTO.getHeadImg())){
            distUser.setHeadImg(distUserDTO.getHeadImg());
        }
        if(StringUtil.isNotEmpty(distUserDTO.getName())){
            distUser.setName(distUserDTO.getName());
        }
        if(StringUtil.isNotEmpty(distUserDTO.getCity())){
            distUser.setCity(distUserDTO.getCity());
        }
        if(distUserDTO.getSex()!=null){
            distUser.setSex(distUserDTO.getSex());
        }
        if(StringUtil.isNotEmpty(distUserDTO.getWxNum())){
            distUser.setWxNum(distUserDTO.getWxNum());
        }
        if(StringUtil.isNotEmpty(distUserDTO.getAliName())){
            distUser.setAliName(distUserDTO.getAliName());
        }
        if(StringUtil.isNotEmpty(distUserDTO.getAliNum())){
            distUser.setAliNum(distUserDTO.getAliNum());
        }
        if(StringUtil.isNotEmpty(distUserDTO.getBankName())){
            distUser.setBankName(distUserDTO.getBankName());
        }
        if(StringUtil.isNotEmpty(distUserDTO.getBankNum())){
            distUser.setBankNum(distUserDTO.getBankNum());
        }
        if(StringUtil.isNotEmpty(distUserDTO.getName())){
            distUser.setName(distUserDTO.getName());
        }
        distUser.setServiceProviderId(serviceProviderId);
        distUserService.update(distUser);
        return Resp.success("修改成功");
    }*/

    /**
     * 修改用户资料
     *
     * @param userId
     * @param distUserDTO
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改资料", notes = "修改资料")
    public Resp update(@UserId String userId, @Valid DistUserDTO distUserDTO) {
        DistUser distUser = distUserService.findOne(userId);
        if (StringUtil.isNotEmpty(distUserDTO.getHeadImg())) {
            distUser.setHeadImg(distUserDTO.getHeadImg());
        }
        if (StringUtil.isNotEmpty(distUserDTO.getName())) {
            distUser.setName(distUserDTO.getName());
        }
        if (StringUtil.isNotEmpty(distUserDTO.getCity())) {
            distUser.setCity(distUserDTO.getCity());
        }
        if (distUserDTO.getSex() != null) {
            distUser.setSex(distUserDTO.getSex());
        }
        if (StringUtil.isNotEmpty(distUserDTO.getWxNum())) {
            distUser.setWxNum(distUserDTO.getWxNum());
        }
        distUserService.update(distUser);
        return Resp.success("修改成功");
    }


    /**
     * 修改银行卡/支付宝信息
     *
     * @param userId
     * @param distUserDTO
     * @return
     */
    @PostMapping("/update/bank")
    @ApiOperation(value = "修改银行卡/支付宝信息", notes = "修改银行卡/支付宝信息 0修改银行卡 1修改支付宝")
    public Resp updateBank(@UserId String userId, DistUserDTO distUserDTO) {
        DistUser distUser = distUserService.findOne(userId);
        Jedis resource = redisConfiguration.redisPoolFactory().getResource();
        //校验验证码
        if (StringUtil.isEmpty(distUserDTO.getCode()) || !distUserDTO.getCode().equals(resource.get(distUser.getUserName()))) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "验证码不正确");
        }
        if (distUserDTO.getType() == 0) {//修改银行卡信息
            if (StringUtil.isNotEmpty(distUserDTO.getBankName())) {
                distUser.setBankName(distUserDTO.getBankName());
            }
            if (StringUtil.isNotEmpty(distUserDTO.getBankNum())) {
                distUser.setBankNum(distUserDTO.getBankNum());
            }
            if (StringUtil.isNotEmpty(distUserDTO.getDepositBank())) {
                distUser.setDepositBank(distUserDTO.getDepositBank());
            }
        } else if (distUserDTO.getType() == 1) {//修改支付宝信息
            if (StringUtil.isNotEmpty(distUserDTO.getAliName())) {
                distUser.setAliName(distUserDTO.getAliName());
            }
            if (StringUtil.isNotEmpty(distUserDTO.getAliNum())) {
                distUser.setAliNum(distUserDTO.getAliNum());
            }
        }
        //resource.del(agentUser.getPhone());//删除验证码
        distUserService.update(distUser);
        resource.close();
        return Resp.success("修改成功");
    }


    /**
     * 查询用户信息与上级信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/detail")
    @ApiOperation(value = "用户信息，我的界面里的数据", notes = "用户信息")
    public Resp<DistUserVO> userDetail(@UserId String userId) {
        DistUser distUser = distUserService.findOne(userId);
        if(ParamUtil.isBlank(distUser)){
            return Resp.success("当前分销用户不存在");
        }
        DistUserVO distUserVO = new DistUserVO();
        BeanUtil.copyProperties(distUser, distUserVO);
        Wallet wallet = walletService.getRepository().findByUserId(userId);
        distUserVO.setBonus(wallet.getBonus());
        distUserVO.setBalance(wallet.getBalance());
        Integer teamNum = distUserService.getRepository().countByParentIdLike(distUser.getParentId() + distUser.getInviteNum() + "/%");
        distUserVO.setTeamTotalNum(teamNum);

        Integer teamAgentNum = distUserService.getRepository().countByParentIdLikeAndGradeGreaterThan(distUser.getParentId() + distUser.getInviteNum() + "/%", 0);
        distUserVO.setTeamAgentTotalNum(teamAgentNum);

        Integer directNum = distUserService.getRepository().countByOneLevelId(userId);
        distUserVO.setDirectNum(directNum);

        Integer directAgentNum = distUserService.getRepository().countByOneLevelIdAndGradeGreaterThan(userId, 0);
        distUserVO.setDirectAgentNum(directAgentNum);

        Integer orderNum = shopOrderService.getRepository().countByUserIdAndStatusLessThanEqualAndStatusGreaterThanEqual(userId, 3, 1);
        distUserVO.setOrderTotalNum(orderNum);
        Integer feedBackNum = feedbackService.getRepository().countByUserIdAndStatusAndType(userId, 1, 0);
        distUserVO.setFeedback(feedBackNum);

        Date dayByNum = DateUtils.getDayByNum(new Date(), -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String format = sdf.format(dayByNum);
        Date parse = null;
        try {
            parse = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //昨天的佣金
        CommissionDay yesterday = commissionDayService.getRepository().findAllByCompanyIdAndCreateTimeAndType(userId, parse, 1);
        Date dateByMonth = DateUtils.initDateByMonth();
        //当月的佣金
        List<CommissionDay> month = commissionDayService.getRepository().findAllByCompanyIdAndCreateTimeBetweenAndType(userId, dateByMonth, new Date(), 1);
        //累计佣金
        List<CommissionDay> total = commissionDayService.getRepository().findAllByCompanyIdAndType(userId, 1);
        BigDecimal totalCommission = total.stream().map(CommissionDay::getCommissionTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal monthCommission = month.stream().map(CommissionDay::getCommissionTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        distUserVO.setYesterdayCommission(yesterday == null ? BigDecimal.ZERO : yesterday.getCommissionTotal());
        distUserVO.setMonthCommission(monthCommission);
        distUserVO.setTotalCommission(totalCommission);
        distUserVO.setCreateNum(DateUtils.getDay(new Date(), distUser.getCreateTime()));
        SystemSetup systemSetup = systemSetupService.getRepository().findByServiceProviderId(distUser.getServiceProviderId());
        distUserVO.setRoleName(distUser.getGrade() == 2 ? systemSetup.getOperationName() : (distUser.getGrade() == 1 ? systemSetup.getPrimaryName() : "暂无等级"));
        distUserVO.setBankDate(systemSetup.getBankDate());
        distUserVO.setAliDate(systemSetup.getAliDate());
        return Resp.success(distUserVO);
    }
    /*@GetMapping("/info")
    public Resp info(@UserId String userId){
        DistUser distUser = distUserService.getRepository().findOne(userId);
        DistUserVO distUserVO=new DistUserVO();
        BeanWrap.copyProperties(distUser,distUserVO);
        if(distUser.getGrade()!=4){
            String[] grade = distUser.getParentId().split("/");
            //父级邀请码
            String inviteNum=grade[grade.length - 1];
            //父级信息
            DistUser superior = distUserService.getRepository().findByInviteNumAndServiceProviderId(grade[grade.length - 1], distUser.getServiceProviderId());
            //父级手机号
            distUserVO.setInvitePhone(superior.getUserName());
            //父级邀请码
            distUserVO.setParentNum(inviteNum);
            //父级姓名
            distUserVO.setInviteName(superior.getName());
        }
        if(distUser.getGrade()!=0&&distUser.getGrade()!=4){
            Costs costs = costsService.getRepository().findByTypeAndServiceProviderId(distUser.getGrade(), distUser.getServiceProviderId());
            distUserVO.setGradeName(costs.getName());
        }
        return Resp.success(distUserVO);
    }*/

    /**
     * @param userId
     * @return
     */
    @GetMapping("/team/first")
    @ApiOperation(value = "查询我的直邀团队", notes = "查询我的直邀团队 type=0查询直邀列表 type=1查询间邀列表 type=2查询其他")
    public Resp teamFirst(@UserId String userId, PageVo pageVo, TeamFirstDTO teamFirstDTO) {
        if (StringUtil.isNotEmpty(teamFirstDTO.getSort()) && (teamFirstDTO.getSort().equals("monthCommission-desc") ||
                teamFirstDTO.getSort().equals("monthCommission-asc") || teamFirstDTO.getSort().equals("createTime-desc")
                || teamFirstDTO.getSort().equals("createTime-asc") || teamFirstDTO.getSort().equals("yesterdayCommission-asc")
                || teamFirstDTO.getSort().equals("yesterdayCommission-desc") || teamFirstDTO.getSort().equals("activate-desc") ||
                teamFirstDTO.getSort().equals("activate-asc") || teamFirstDTO.getSort().equals("directNum-asc")
                || teamFirstDTO.getSort().equals("directNum-desc"))) {
            teamFirstDTO.setSort(teamFirstDTO.getSort().replace("-", " "));
        } else {
            teamFirstDTO.setSort(" createTime desc ");
        }
        DistUser distUser = null;
        if (StringUtil.isEmpty(teamFirstDTO.getId())) {
            distUser = distUserService.findOne(userId);
        } else {
            distUser = distUserService.findOne(teamFirstDTO.getId());
        }
        Map<String, Object> result = new HashMap<>();
        List<DistUser> userList = null;

        //查询角色是否是运营中心  2 == 运营中心
        if(distUser.getGrade().equals(Integer.valueOf(2))){
            //直邀
            if (teamFirstDTO.getType() == 0) {
                userList = distUserService.getRepository().findAllByOneLevelId(distUser.getId());
            //间邀
            } else if (teamFirstDTO.getType() == 1) {
                userList = distUserService.getRepository().findAllByZeroLevelId(distUser.getId());
            }
        }else {
            if (teamFirstDTO.getType() == 0) {
                userList = distUserService.getRepository().findAllByOneLevelId(distUser.getId());
            } else if (teamFirstDTO.getType() == 1) {
                userList = distUserService.getRepository().findAllByTwoLevelId(distUser.getId());
            } else if (teamFirstDTO.getType() == 2) {
                userList = distUserService.getRepository().findAllByThreeLevelId(distUser.getId());
            }
        }
        Integer total = userList.size();
        result.put("total", total);//总共几人
        int agentTotal = userList.stream().filter(s -> s.getGrade() > 0).collect(Collectors.toList()).size();
        List<Map> agentUserVOList = distUserService.getListById(teamFirstDTO.getType(), distUser.getId(), teamFirstDTO.getSort(), teamFirstDTO.getParam(), pageVo);
        result.put("agentTotal", agentTotal);//代理人数
        result.put("list", agentUserVOList);
        result.put("totalPages", (total == null || total == 0) ? 0 : ((total - 1) / total + 1));
        result.put("totalElements", total);
        return Resp.success(result);
    }

    /**
     * 我的团队
     *
     * @param userId
     * @param type
     * @return
     */
    @GetMapping("/team")
    @ApiOperation(value = "我的团队", notes = "我的团队")
    public Resp team(@UserId String userId, Integer type) {
        DistUser distUser = distUserService.getRepository().findOne(userId);
        List<DistUserVO> LevelVO = new ArrayList<>();
        if (StringUtil.isNotEmpty(distUser.getInviteNum())) {
            String parentId = distUser.getParentId();
            String[] split = parentId.split("/");
            int length = split.length;

            String grade = distUser.getParentId() + distUser.getInviteNum() + "/";//用户自己的邀请码
            List<DistUser> userList = null;

            userList = distUserService.getRepository().findAllByParentIdLikeAndServiceProviderId(grade + "%", distUser.getServiceProviderId());

            if (userList != null) {
                if (type == null || type == 1) {
                    //一级
                    List<DistUser> firstLevel = userList.stream().filter(s -> s.getParentId().split("/").length == length + 1 && s.getGrade() != 0).collect(Collectors.toList());
                    for (DistUser data : firstLevel) {
                        DistUserVO distUserVO = new DistUserVO();
                        BeanWrap.copyProperties(data, distUserVO);
                        DistUser one = distUserService.findOne(data.getId());
                        Costs costs = costsService.getRepository().findByTypeAndServiceProviderId(one.getGrade(), one.getServiceProviderId());
                        distUserVO.setGradeName(costs.getName());
                        if (StringUtil.isNotEmpty(data.getInviteNum())) {
                            grade = data.getParentId() + data.getInviteNum() + "/";//用户自己的邀请码
                            //查询下级人数
                            userList = distUserService.getRepository().findAllByParentIdLikeAndServiceProviderId(grade + "%", distUser.getServiceProviderId());
                            firstLevel = userList.stream().filter(s -> s.getParentId().split("/").length == length + 1 && s.getGrade() != 0).collect(Collectors.toList());
                            distUserVO.setFirstNum(firstLevel.size());
                        }
                        LevelVO.add(distUserVO);
                    }
                }

                if (type == null || type == 2) {
                    //二级
                    List<DistUser> secondLevel = userList.stream().filter(s -> s.getParentId().split("/").length == length + 2 && s.getGrade() != 0).collect(Collectors.toList());
                    for (DistUser data : secondLevel) {
                        DistUserVO distUserVO = new DistUserVO();
                        DistUser one = distUserService.findOne(data.getId());
                        Costs costs = costsService.getRepository().findByTypeAndServiceProviderId(one.getGrade(), one.getServiceProviderId());
                        distUserVO.setGradeName(costs.getName());
                        if (StringUtil.isNotEmpty(data.getInviteNum())) {
                            grade = data.getParentId() + data.getInviteNum() + "/";//用户自己的邀请码
                            userList = distUserService.getRepository().findAllByParentIdLikeAndServiceProviderId(grade + "%", distUser.getServiceProviderId());
                            secondLevel = userList.stream().filter(s -> s.getParentId().split("/").length == length + 1 && s.getGrade() != 0).collect(Collectors.toList());
                            distUserVO.setFirstNum(secondLevel.size());
                        }
                        LevelVO.add(distUserVO);
                    }
                }
            }
        }
        return Resp.success(LevelVO);
    }

    /**
     * 我的团队-业绩
     *
     * @return
     */
    @GetMapping("/performance")
    @ApiOperation(value = "我的团队-业绩", notes = "我的团队-业绩")
    public Resp performance(@UserId String userId, DistUserDTO distUserDTO) {
        DistUser distUser = distUserService.getRepository().findOne(userId);
        Map<String, Object> map = new HashMap();
        Costs costs = costsService.getRepository().findByTypeAndServiceProviderId(distUser.getGrade(), distUser.getServiceProviderId());
        if (costs != null) {
            map.put("firstRate", costs.getFirstCommissions());//一级提成比例
            map.put("secondRate", costs.getSecondCommissions());//二级提成比例
        }
        List<DistUserVO> LevelVO = new ArrayList<>();
        //查询自己的下级人数
        if (StringUtil.isNotEmpty(distUser.getInviteNum())) {
            String parentId = distUser.getParentId();
            String[] split = parentId.split("/");
            int length = split.length;

            String grade = distUser.getParentId() + distUser.getInviteNum() + "/";//用户自己的邀请码
            List<DistUser> userList = distUserService.getRepository().findAllByParentIdLikeAndServiceProviderIdAndPayTimeBetweenOrderByCreateTimeAsc(grade + "%", distUser.getServiceProviderId(), distUserDTO.getStartTime(), distUserDTO.getEndTime());

            List<DistUser> collect = userList.stream().filter(s -> (s.getParentId().split("/").length == length + 1 || s.getParentId().split("/").length == length + 2) && s.getGrade() != 0).collect(Collectors.toList());

            for (DistUser data : collect) {
                DistOrder distOrder = distOrderService.getRepository().findByUserIdAndTypeAndStatus(data.getId(), data.getGrade(), 1);
                if (distOrder == null) {
                    continue;
                }
                DistUserVO distUserVO = new DistUserVO();
                BeanWrap.copyProperties(data, distUserVO);
                costs = costsService.getRepository().findByTypeAndServiceProviderId(data.getGrade(), distUser.getServiceProviderId());
                distUserVO.setGradeName(costs.getName());
                if (data.getParentId().split("/").length == length + 1) {
                    distUserVO.setRank(1);
                } else if (data.getParentId().split("/").length == length + 2) {
                    distUserVO.setRank(2);
                }
                distUserVO.setFee(distOrder.getPrice().doubleValue());
                LevelVO.add(distUserVO);
            }
        }
        map.put("secondFee", LevelVO);
        return Resp.success(map);
    }

    @GetMapping("/index/top")
    @ApiOperation(value = "主页当月新增以上的信息", notes = "主页")
    public Resp<IndexTopVO> index(@UserId String userId) {
        DistUser distUser = distUserService.getRepository().findOne(userId);
        if(ParamUtil.isBlank(distUser)){
            return Resp.success("当前分销用户不存在");
        }
        SystemSetup systemSetup = systemSetupService.getRepository().findByServiceProviderId(distUser.getServiceProviderId());
        IndexTopVO indexTopVo = new IndexTopVO();
        indexTopVo.setRoleName(distUser.getGrade() == 2 ? systemSetup.getOperationName() : (distUser.getGrade() == 1 ? systemSetup.getPrimaryName() : "暂无等级"));
        BeanUtil.copyProperties(distUser, indexTopVo);
        Date dateByMonth = DateUtils.initDateByMonth();
        String grade = distUser.getParentId() + distUser.getInviteNum() + "/%";//用户自己的邀请码
        //当月的佣金
        List<CommissionDay> month = commissionDayService.getRepository().findAllByCompanyIdAndCreateTimeBetweenAndType(userId, dateByMonth, new Date(), 1);
        BigDecimal monthCommission = month.stream().map(CommissionDay::getCommissionTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        //统计
        List<CommissionDay> total = commissionDayService.getRepository().findAllByCompanyIdAndType(userId, 1);
        BigDecimal totalCommission = total.stream().map(CommissionDay::getCommissionTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        indexTopVo.setMonthCommission(monthCommission);
        indexTopVo.setTotalCommission(totalCommission);
        List<DistUser> allDistUser = distUserService.getRepository().findAllByParentIdLikeAndServiceProviderId(grade, distUser.getServiceProviderId());
        int agentTotal = allDistUser.stream().filter(s -> s.getGrade() > 0).collect(Collectors.toList()).size();
        indexTopVo.setTeamNum(allDistUser.size());
        indexTopVo.setAgentNum(agentTotal);
        List<Merchant> merchantList = merchantService.getRepository().findByManagerIdAndTypeAndDelFlag(userId, 1, CommonConstant.NORMAL_FLAG);
        List<String> merchantIds = merchantList.stream().map(Merchant::getId).collect(Collectors.toList());
        Integer activeNum = equipmentService.getRepository().countByMerchantIdInAndStatus(merchantIds, Equipment.Status.TRUE.getCode());
        indexTopVo.setActivate(activeNum);
        indexTopVo.setMerchantNum(merchantList.size());
        return Resp.success(indexTopVo);
    }

    @GetMapping("/index/mid")
    @ApiOperation(value = "主页当月新增", notes = "主页")
    public Resp<IndexMidVO> mid(@UserId String userId) {
        DistUser distUser = distUserService.getRepository().findOne(userId);
        if(ParamUtil.isBlank(distUser)){
            return Resp.success("当前分销用户不存在");
        }
        String grade = distUser.getParentId() + distUser.getInviteNum() + "/%";//用户自己的邀请码
        Date dateByMonth = DateUtils.initDateByMonth();
        Integer teamNum = distUserService.getRepository().countByParentIdLikeAndServiceProviderIdAndCreateTimeIsGreaterThanEqual(grade, distUser.getServiceProviderId(), dateByMonth);
        Integer agentNum = distUserService.getRepository().countByParentIdLikeAndServiceProviderIdAndCreateTimeGreaterThanEqualAndGradeGreaterThanEqual(grade, distUser.getServiceProviderId(), dateByMonth, 1);
        Integer activateNum = merchantService.getRepository().countByManagerIdAndTypeAndDelFlagAndCreateTimeGreaterThanEqual(userId, 1, CommonConstant.NORMAL_FLAG, dateByMonth);
        List<Merchant> merchantList = merchantService.getRepository().findByManagerIdAndTypeAndDelFlag(userId, 1, CommonConstant.NORMAL_FLAG);
        List<String> merchantIds = merchantList.stream().map(Merchant::getId).collect(Collectors.toList());
        Integer activeNum = equipmentService.getRepository().countByMerchantIdInAndStatusAndActivateTimeGreaterThanEqual(merchantIds, Equipment.Status.TRUE.getCode(), dateByMonth);
        IndexMidVO indexMidVo = new IndexMidVO();
        indexMidVo.setMerchantNum(activateNum);
        indexMidVo.setTeamNum(teamNum);
        indexMidVo.setAgentNum(agentNum);
        indexMidVo.setActivateNum(activeNum);
        return Resp.success(indexMidVo);
    }

    @GetMapping("/index/mid/detail")
    @ApiOperation(value = "每月新增", notes = "主页")
    public Resp<List<IndexMidVO>> midDetail(@UserId String userId, String startTime, String endTime) {
        List<IndexMidVO> indexMidVOList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            List<String> xData = DateUtils.dateRangeList(DateUtils.fmtStrToDate(startTime, "yyyy-MM"), DateUtils.fmtStrToDate(endTime, "yyyy-MM"),
                    DateField.MONTH, "yyyy-MM-dd HH:mm:ss");
            DistUser distUser = distUserService.getRepository().findOne(userId);
            String grade = distUser.getParentId() + distUser.getInviteNum() + "/%";//用户自己的邀请码
            Date dateByMonth = null; //每月的第一天
            Date dateLastDay = null; //每月最后一天

            for (String data : xData) {
                IndexMidVO indexMidVo = new IndexMidVO();

                dateByMonth = DateUtils.fmtStrToDate(data, "yyyy-MM-dd HH:mm:ss");

                //获取最后一天日期
                String lastDayOfMonth = DateUtils.getLastDayOfMonth(data); //最后一天字符串
                Date date = DateUtils.fmtStrToDate(lastDayOfMonth, "yyyy-MM-dd HH:mm:ss");
                dateLastDay = DateUtils.getEndOfDay(date);

                //每月团队人数
                Integer teamNum = distUserService.getRepository().countByParentIdLikeAndServiceProviderIdAndCreateTimeIsGreaterThanEqualAndCreateTimeIsLessThan(grade, distUser.getServiceProviderId(), dateByMonth, dateLastDay);
                indexMidVo.setTeamNum(teamNum);

                //每月代理人数
                Integer agentNum = distUserService.getRepository().countByParentIdLikeAndServiceProviderIdAndCreateTimeGreaterThanEqualAndCreateTimeLessThanAndGradeGreaterThanEqual(grade, distUser.getServiceProviderId(), dateByMonth, dateLastDay, 1);
                indexMidVo.setAgentNum(agentNum);

                //每月发展商户
                Integer activateNum = merchantService.getRepository().countByManagerIdAndTypeAndDelFlagAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(userId, 1, CommonConstant.NORMAL_FLAG, dateByMonth,dateLastDay);
                indexMidVo.setMerchantNum(activateNum);

                //每月发展设备
                List<Merchant> merchantList = merchantService.getRepository().findByManagerIdAndTypeAndDelFlag(userId, 1, CommonConstant.NORMAL_FLAG);
                List<String> merchantIds = merchantList.stream().map(Merchant::getId).collect(Collectors.toList());
                Integer activeNum = equipmentService.getRepository().countByMerchantIdInAndStatusAndActivateTimeGreaterThanEqualAndCreateTimeLessThan(merchantIds, Equipment.Status.TRUE.getCode(), dateByMonth,dateLastDay);
                indexMidVo.setActivateNum(activeNum);

                //每月日期
                String substring = lastDayOfMonth.substring(0, 7);
                indexMidVo.setDate(substring);
                indexMidVOList.add(indexMidVo);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Resp.success(indexMidVOList);
    }

    @GetMapping("/index/commission")
    @ApiOperation(value = "主页佣金数据", notes = "主页")
    public Resp<IndexCommissionVO> commission(@UserId String userId, DataTransactionDTO dataTransactionDTO) {
        IndexCommissionVO indexCommissionVO = companyService.dataTransactionDist(userId, dataTransactionDTO);
        //当前时段的数据
        List<CommissionDay> commissionDaysList = commissionDayService.getRepository().findAllByCompanyIdAndCreateTimeBetweenAndType(userId, dataTransactionDTO.getStartTime(), dataTransactionDTO.getEndTime(), 1);
        indexCommissionVO.setCommissionDayList(commissionDaysList);
        return Resp.success(indexCommissionVO);
    }

    /**
     * 2020-3-2 14:15:12 yy
     * 需要优化
     *
     * @param userId
     * @return
     */
    @GetMapping("/index/merchant_data")
    @ApiOperation(value = "商家佣金排行榜", notes = "主页")
    public Resp<List<IndexMerchantVO>> merchantData(@UserId String userId) {
        DistUser distUser = distUserService.getRepository().findOne(userId);
        if(ParamUtil.isBlank(distUser)){
            return Resp.success("当前分销用户不存在");
        }
        List<IndexMerchantVO> indexMerchantVOList = new ArrayList<>();
        String grade = distUser.getParentId() + distUser.getInviteNum() + "/%";//用户自己的邀请码
        List<DistUser> distUserList = null;
        if (distUser.getGrade() != 2) {
            int level = distUser.getLevel() + 3;
            distUserList = distUserService.getRepository().findAllByParentIdLikeAndLevel(grade, level);
        } else {
            distUserList = distUserService.getRepository().findAllByParentIdLike(grade);
        }
        List<String> ids = distUserList.stream().map(DistUser::getId).collect(Collectors.toList());
        ids.add(distUser.getId());
        List<Merchant> merchantList = merchantService.getRepository().findByManagerIdInAndType(ids, Merchant.Type.DIST.getCode());
        if (merchantList.size() == 0) {
            return Resp.success(indexMerchantVOList);
        }
        List<String> merchantIds = merchantList.stream().map(Merchant::getId).collect(Collectors.toList());
        List<Map> commission = distUserService.getCommission(merchantIds);
        for (Map obj : commission) {
            IndexMerchantVO indexMerchantVO = new IndexMerchantVO();
            Merchant data = merchantList.stream().filter(merchant -> merchant.getId().equals(obj.get("merchantId"))).collect(Collectors.toList()).get(0);
            if (StringUtil.isNotEmpty((String) obj.get("oneLevelId")) && obj.get("oneLevelId").equals(userId)) {
                indexMerchantVO.setTotal(new BigDecimal(obj.get("oneLevelCommission").toString()));
            } else if (StringUtil.isNotEmpty((String) obj.get("twoLevelId")) && obj.get("twoLevelId").equals(userId)) {
                indexMerchantVO.setTotal(new BigDecimal(obj.get("twoLevelCommission").toString()));
            } else if (StringUtil.isNotEmpty((String) obj.get("threeLevelId")) && obj.get("threeLevelId").equals(userId)) {
                indexMerchantVO.setTotal(new BigDecimal(obj.get("threeLevelCommission").toString().toString()));
            } else if (StringUtil.isNotEmpty((String) obj.get("directId")) && obj.get("directId").equals(userId)) {
                if(null != obj.get("directCommission")){
                    indexMerchantVO.setTotal(new BigDecimal(obj.get("directCommission").toString()));
                }
            }
            if (!data.getManagerId().equals(userId)) {
                indexMerchantVO.setManageName(distUserService.getRepository().findOne(data.getManagerId()).getName());
            }
            indexMerchantVO.setName(data.getName());
            //根据商户ID查询有效的交易金额
            String totalPrice = distUserService.getOrderTotalPrice(obj.get("merchantId").toString());
            indexMerchantVO.setTotalPrice(totalPrice);
            indexMerchantVOList.add(indexMerchantVO);
        }
        indexMerchantVOList = indexMerchantVOList.stream().sorted(Comparator.comparingDouble(IndexMerchantVO::getTotal).reversed()).collect(Collectors.toList());
        return Resp.success(indexMerchantVOList);
    }


    @GetMapping("/index/ranking_list")
    @ApiOperation(value = "全国代理排行榜", notes = "主页")
    public Resp rankingList() {
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        List<DistUser> distUserList = distUserService.getRepository().rankList(serviceId);
        List<DistUserVO> distUserVOList = new ArrayList<>();
        for (DistUser data : distUserList) {
            DistUserVO distUserVO = new DistUserVO();
            BeanUtil.copyProperties(data, distUserVO);
            distUserVOList.add(distUserVO);
        }
        return Resp.success(distUserVOList);
    }

}