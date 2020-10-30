package com.fzy.admin.fp.merchant.management.feignImpl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fzy.admin.fp.common.web.JwtUtil;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.merchant.management.AuthConstant;
import com.fzy.admin.fp.merchant.management.domain.MerchantPermission;
import com.fzy.admin.fp.merchant.management.domain.MerchantUser;
import com.fzy.admin.fp.merchant.management.service.MerchantPermissionService;
import com.fzy.admin.fp.merchant.management.service.MerchantUserService;
import com.fzy.admin.fp.sdk.merchant.feign.MerchantServiceFeign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Created by wtl on 2019-03-14 10:13
 * @description
 */
@Service
public class MerchantServiceFeignImpl implements MerchantServiceFeign {

    @Resource
    private MerchantPermissionService merchantPermissionService;
    @Resource
    private MerchantUserService merchantUserService;
    @Value("${secret.key}")
    private String key;
    @Value("${secret.tokenExpiration}")
    private int tokenExpiration;
    @Value("${secret.tokenRefresh}")
    private int tokenRefresh;


    @Override
    public String getIss() {
        return AuthConstant.AUTH_NAME;
    }

    @Override
    public Resp authentication(String token, String path,String pageUrl) {
        DecodedJWT jwt = JwtUtil.verifier(token, key);
        String iss = jwt.getIssuer();
        String userId = jwt.getSubject();
        if (ParamUtil.isBlank(userId) || ParamUtil.isBlank(iss) || !AuthConstant.AUTH_NAME.equals(iss)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "token信息异常,请重新登录");
        }
        // 如果是商户或者店长登录，拥有全部权限
        MerchantUser user = merchantUserService.findOne(userId);
        //获取用户权限树
        List<MerchantPermission> permissions = merchantPermissionService.findByUserIdAndType(userId);
        //除了商户都需要鉴权
        if (!user.getUserType().equals("1")) {
//            if (ParamUtil.isBlank(permissions)) {
//                return new AuthenticationResult("权限不足", AuthenticationResult.ResultStatus.NO_ACCESS);
//            }
            //匹配路径是否符合用户权限
            boolean isAccess = false;
            for (MerchantPermission permission : permissions) {
                if (permission.getPath().equalsIgnoreCase(path)) {
                    isAccess = true;
                    break;
                }
            }
            // 订单模块放行，但需要token，所以不放在鉴权忽略列表
            if (path.startsWith("/order")) {
                isAccess = true;
            }
            if (!isAccess) {
                return new Resp().error(Resp.Status.NO_ACCESS, "权限不足");
            }
        }
        //判断用户token是否需要刷新
        Date expires = jwt.getExpiresAt();
        Date now = new Date();
        if ((now.getTime() - expires.getTime()) < tokenRefresh * 60 * 1000) {
            token = JwtUtil.copyToken(token, tokenExpiration, key);
        }
        return Resp.success(token);
    }


}
