package com.fzy.admin.fp.auth.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.auth.repository.LevelAliasConfigRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.AuthConstant;
import com.fzy.admin.fp.auth.VerifyCodeApplicationRunner;
import com.fzy.admin.fp.auth.domain.*;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.auth.repository.LevelAliasConfigRepository;
import com.fzy.admin.fp.auth.service.CompanyService;
import com.fzy.admin.fp.auth.service.SiteInfoService;
import com.fzy.admin.fp.auth.service.UserRoleService;
import com.fzy.admin.fp.auth.service.UserService;
import com.fzy.admin.fp.auth.utils.SmsService;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by zk on 2019-01-02 17:53
 * @description
 */
@RestController
@RequestMapping("/auth/admin")
public class ManagementController extends BaseContent {
    @Resource
    private UserService userService;

    @Resource
    private SmsService smsService;

    @Resource
    private CompanyRepository companyRepository;

    @Resource
    LevelAliasConfigRepository levelAliasConfigRepository;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private SiteInfoService siteInfoService;


    @Value("${secret.tokenExpiration}")
    private int tokenExpiration;


    @PostMapping("/login")
    public Resp login(String username, String password, HttpServletRequest request) {
        User user = userService.getRepository().findByUsernameAndDelFlag(username, CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(user)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "用户名不存在");
        }
        if (user.getStatus().equals(User.Status.DISABLE.getCode())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "用户已被禁用");
        }
        if (!BCrypt.checkpw(password, user.getPassword())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "密码错误");
        }
        Company company = companyRepository.findOne(user.getCompanyId());
        if (company == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "用户数据异常");
        }
        //如果该公司是非签约状态
        if (!Company.Status.SIGNED.getCode().equals(company.getStatus())) {
            //如果是运营商
            if (Company.Type.OPERATOR.getCode().equals(company.getType())) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "该运营商尚未签约");
            }
            //如果是渠道商
            if (Company.Type.DISTRIBUTUTORS.getCode().equals(company.getType())) {
                return new Resp().error(Resp.Status.PARAM_ERROR, "该渠道商商尚未签约");
            }
        }
        final List<Role> roles = userRoleService.findUserRoleByUserId(user.getId());
        Integer level = Role.LEVEL.COMMON.getCode();
        for (Role role : roles) {
            level = role.getLevel();
            if (Role.LEVEL.TOP.getCode().equals(role.getLevel())) {
                //如果是高级角色
                break;
            }
        }
        String key = user.getUsername();
        /**
         * 检验当前登陆用户的域名是都正确
         */
        //服务商id
        String serviceId = request.getAttribute(CommonConstant.SERVICE_PROVIDERID).toString();
        if (company.getType().equals(1)) {
            if (!company.getId().equals(serviceId)) {
                return varifyDoMain(company.getId());
            }

        } else {
            if (!company.getType().equals(-1)) {
                String userServiceId = company.getIdPath().split("\\|")[1];
                if (!serviceId.equals(userServiceId)) {
                    return varifyDoMain(userServiceId);
                }
            }
        }

        String token = JWT.create().withIssuer(AuthConstant.AUTH_NAME).withSubject(user.getId())
                .withClaim("companyId", company.getId())
                .withClaim("level", level)
                .withClaim(CommonConstant.SERVICE_PROVIDERID, user.getServiceProviderId())
                .withExpiresAt(new Date(new Date().getTime() + tokenExpiration * 60 * 1000))
                .sign(Algorithm.HMAC512(key));
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("companyId", company.getId());
        map.put("userId", user.getId());


        //别名
        LevelAliasConfig levelAliasConfig = null;
        Company oem = companyRepository.findOne(serviceId);
        if (oem != null) {
            levelAliasConfig = levelAliasConfigRepository.findOne(oem.getId());
            //贴牌商未配置就用超级后台的配置
            if (levelAliasConfig == null) {
                levelAliasConfig = levelAliasConfigRepository.findOne(oem.getParentId());
            }
        }

        //查询用户对应的公司
        if (company.getType().equals(Company.Type.PROVIDERS.getCode())) {
            map.put("userType", 1);
        }
        if (company.getType().equals(Company.Type.OPERATOR.getCode())) {
            map.put("userType", 2);

        }
        if (company.getType().equals(Company.Type.DISTRIBUTUTORS.getCode())) {
            map.put("userType", 3);
        }
        if (company.getType().equals(Company.Type.THIRDAGENT.getCode())) {
            map.put("userType", 4);
        }

        map.put("name", user.getName());
        map.put("level", level);
        map.put("levelAlias", levelAliasConfig);
        return Resp.success(map, "登录成功");
    }

    /**
     * 校验域名
     *
     * @param companyId
     * @return
     */
    private Resp varifyDoMain(String companyId) {
        SiteInfo siteInfo = siteInfoService.getRepository().findByServiceProviderId(companyId);
        if (StringUtils.isEmpty(siteInfo)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "当前服务商未配置域名！");
        } else {
            return new Resp().error(Resp.Status.PARAM_ERROR, "当前域名错误，当前账号服务商域名为：：" + siteInfo.getDomainName());
        }
    }


    /*
     * @author drj
     * @date 2019-05-07 20:40
     * @Description :根据用户名查询手机号码
     */
    @GetMapping("/phone_find_by_username")
    public Resp phoneFindByUsername(String username) {
        User user = userService.getRepository().findByUsernameAndServiceProviderIdAndDelFlag(username, (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID), CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(user)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "用户名输入有误");
        }
        if (ParamUtil.isBlank(user.getPhone())) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "该用户未设置手机信息");
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
        boolean flag = smsService.sendSms(phone, code);
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
        User user = userService.getRepository().findByUsernameAndServiceProviderIdAndDelFlag(username, (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID), CommonConstant.NORMAL_FLAG);
        if (ParamUtil.isBlank(user)) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误");
        }
        if (!password.matches("^[a-zA-Z0-9_-]{6,20}$")) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "密码应为英文、数字、下划线组成的6-20位字符");
        }
        user.setPassword(BCrypt.hashpw(password));
        userService.save(user);
        return Resp.success("设置成功");
    }

    //根据服务商id查询网站信息
    @GetMapping("/get_siteinfo")
    public Resp getSiteInfo(@RequestAttribute(CommonConstant.SERVICE_PROVIDERID) String serviceProviderId) {
        return Resp.success(siteInfoService.getRepository().findByServiceProviderId(serviceProviderId));
    }

}
