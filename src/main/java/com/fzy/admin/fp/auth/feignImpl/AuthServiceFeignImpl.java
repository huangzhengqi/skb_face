package com.fzy.admin.fp.auth.feignImpl;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.JwtUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.auth.AuthConstant;
import com.fzy.admin.fp.auth.domain.Company;
import com.fzy.admin.fp.auth.domain.Permission;
import com.fzy.admin.fp.auth.domain.User;
import com.fzy.admin.fp.auth.domain.YunHorn;
import com.fzy.admin.fp.auth.repository.CompanyRepository;
import com.fzy.admin.fp.auth.service.PermissionService;
import com.fzy.admin.fp.auth.service.UserService;
import com.fzy.admin.fp.auth.service.YunHornService;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.web.JwtUtil;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.app.service.DistUserService;
import com.fzy.admin.fp.sdk.auth.domain.YunhornParam;
import com.fzy.admin.fp.sdk.auth.feign.AuthServiceFeign;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Created by zk on 2019-01-02 15:29
 * @description
 */
@Slf4j
@Service
public class AuthServiceFeignImpl implements AuthServiceFeign {
    @Resource
    private PermissionService permissionService;
    @Value("${secret.tokenExpiration}")
    private int tokenExpiration;
    @Value("${secret.tokenRefresh}")
    private int tokenRefresh;
    @Resource
    private UserService userService;
    @Resource
    private HttpServletRequest request;

    @Resource
    private CompanyRepository companyRepository;

    @Resource
    private YunHornService yunHornService;

    @Resource
    private DistUserService distUserService;

    @Override
    public String getIss() {
        return AuthConstant.AUTH_NAME;
    }

    @Override
    public Resp authentication(String token, String path, String pageUrl) {
        Map<String, Object> payloadMap = JwtUtil.getPayloadMap(token);
        String userId = payloadMap.get("sub").toString();
        String iss = payloadMap.get("iss").toString();
        if(payloadMap.containsKey("loginType")){
            DecodedJWT jwt = JwtUtil.verifier(token, payloadMap.get("userName").toString());
            //判断用户token是否需要刷新
            Date expires = jwt.getExpiresAt();
            Date now = new Date();
            if ((now.getTime() - expires.getTime()) < tokenRefresh * 60 * 1000) {
                token = JwtUtil.copyToken(payloadMap, tokenExpiration,  payloadMap.get("userName").toString());
            }
            return Resp.success(token);
        }
        if (ParamUtil.isBlank(userId) || ParamUtil.isBlank(iss) || !AuthConstant.AUTH_NAME.equals(iss)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "token信息异常,请重新登录");
        }
        User user = userService.findOne(userId);
        if (user == null) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "查无该token信息,请重新登录");
        }
        String key = user.getUsername();
        DecodedJWT jwt = JwtUtil.verifier(token, key);
        //获取用户权限树
        List<Permission> permissions = permissionService.findByUserIdAndType(userId);
        if (ParamUtil.isBlank(permissions)) {
            return new Resp().error(Resp.Status.NO_ACCESS, "您尚未拥有该权限");
        }
        //匹配路径是否符合用户权限
        boolean isAccess = false;
        for (Permission permission : permissions) {
            if (permission.getPath().equalsIgnoreCase(path)) {
                //判断页面地址是否一致
                if (checkPageUrl(permission,pageUrl)) {
                    isAccess = true;
                    break;
                }

            }
        }
        if (!isAccess) {
            return new Resp().error(Resp.Status.NO_ACCESS, "您尚未拥有该权限");
        }
        //判断用户token是否需要刷新
        Date expires = jwt.getExpiresAt();
        Date now = new Date();
        if ((now.getTime() - expires.getTime()) < tokenRefresh * 60 * 1000) {
            token = JwtUtil.copyToken(payloadMap, tokenExpiration, key);
        }
        return Resp.success(token);
    }

    //判断页面地址是否一致
    private boolean checkPageUrl(Permission permission, String pageUrl) {
        if (StringUtils.isEmpty(pageUrl)) {
            return true;
        }
        if (pageUrl.equals(getPermissionPageUrl(permission))) {
            return true;
        } else {
            return false;
        }
    }

    private String getPermissionPageUrl(Permission permission) {
        Permission second = permissionService.findOne(permission.getParentId());
        Permission first = permissionService.findOne(second.getParentId());
        return first.getPath()+"/"+second.getPath();


    }


    @Override
    public String getCompanyId(String username) {
        User user = userService.getRepository().findByUsernameAndServiceProviderIdAndDelFlag(username, (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID), CommonConstant.NORMAL_FLAG);
        if (null == user) {
            return "";
        }
        return user.getCompanyId();
    }

    @Override
    public Map<String, String> getCompanyName() {
        Map<String, String> map = new HashMap<>();
        List<Company> companyList = companyRepository.findAll();
        for (Company company : companyList) {
            map.put(company.getId(), ParamUtil.isBlank(company.getName()) ? "" : company.getName());
        }
        return map;
    }

    @Override
    public Map<String, String> getUserName() {
        Map<String, String> map = new HashMap<>();
        List<User> userList = userService.getRepository().findAll();
        for (User user : userList) {
            map.put(user.getId(), ParamUtil.isBlank(user.getName()) ? "" : user.getName());
        }
        return map;
    }

    @Override
    public Map<String, String> getDistUserName() {
        Map<String, String> map = new HashMap<>();
        List<DistUser> distUserList = distUserService.getRepository().findAll();
        for (DistUser distUser : distUserList) {
            map.put(distUser.getId(), ParamUtil.isBlank(distUser.getName()) ? "" : distUser.getName());
        }
        return map;
    }


    @Override
    public List<String> findByOperaId(String companyId) {
        List<Company> companyList = companyRepository.findByParentIdAndDelFlag(companyId, CommonConstant.NORMAL_FLAG);
        return companyList.stream().map(Company::getId).collect(Collectors.toList());
    }

    @Override
    public String yunHornPlay(YunhornParam model) {
        //查询服务商对应的云喇叭
        YunHorn yunHorn = yunHornService.getRepository().findByServiceProviderId(model.getServiceProviderId());
        if (ParamUtil.isBlank(yunHorn)) {
            return "";
        }
        //调用云喇叭设备接口
        Map<String, Object> map = new HashMap<>();
        map.put("id", yunHorn.getHornSerial() == null ? "" : yunHorn.getHornSerial());
        map.put("price", 1);
        map.put("token", yunHorn.getHornToken() == null ? "" : yunHorn.getHornToken());
        map.put("pt", 13);
        String result = HttpUtil.get("http://cloudspeaker.smartlinkall.com/add.php", map);
        log.info("云喇叭接口返回结果:{}" + result);
        //将结果转成map
        Map<String, Object> resultMap = JacksonUtil.toObjectMap(result);
        log.info("云喇叭接口返回提示信息:{}" + resultMap.get("errmsg"));
        return (String) resultMap.get("errmsg");
    }


    public static void main(String args[]){
        Map<String, Object> payloadMap = JwtUtil.getPayloadMap("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMTkxNjI0ODAyOTcyMjQxOTIwIiwic2VydmljZVByb3ZpZGVySWQiOiIxMTkwNTA3ODc5MjQwODYzNzQ0IiwibWVyY2hhbnRJZCI6IjExOTE2MjQ4MDEyNTY3NzE1ODQiLCJpc3MiOiJhdXRoIiwiZXhwIjoxNTc0NjcyNDY4fQ.qcbDt_Do82hueLOR7_Lk_5W-PpcZO6DEGjK7cedyge1T7h33tJ7l6Bcv4_h_bPxC4OJP-tghdI4QCepENtXzwA");
        String userId = payloadMap.get("sub").toString();
        System.out.println(userId);

    }
}
