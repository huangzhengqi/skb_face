package com.fzy.admin.fp.gateway;

import com.fzy.admin.fp.gateway.config.IgnoredUrls;
import com.fzy.admin.fp.common.spring.SpringContextUtil;
import com.fzy.admin.fp.gateway.config.IgnoredUrls;
import com.fzy.admin.fp.sdk.auth.feign.AuthenticationInterface;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by zk on 2019-01-29 23:04
 * @description
 */
@Component
public class GateApplicationRunner implements ApplicationRunner {
    @Resource
    private IgnoredUrls ignoredUrls;
    public static List<String> authIgnoreReg = new ArrayList<>();
    //iss值-AuthenticationInterface
    public static Map<String, AuthenticationInterface> authenticationInterfaceMap = new HashMap<>();

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        for (String s : ignoredUrls.getUrls()) {
            authIgnoreReg.add(getRegPath(s.toUpperCase()));
        }
        final Map<String, AuthenticationInterface> map = SpringContextUtil.getBeanOfType(AuthenticationInterface.class);
        map.values().forEach(authenticationInterface -> authenticationInterfaceMap.put(authenticationInterface.getIss(), authenticationInterface));
    }

    private String getRegPath(String path) {
        char[] chars = path.toCharArray();
        int len = chars.length;
        StringBuilder sb = new StringBuilder();
        boolean preX = false;
        for (int i = 0; i < len; i++) {
            if (chars[i] == '*') {//遇到*字符
                if (preX) {//如果是第二次遇到*，则将**替换成.*
                    sb.append(".*");
                    preX = false;
                } else if (i + 1 == len) {//如果是遇到单星，且单星是最后一个字符，则直接将*转成[^/]*
                    sb.append("[^/]*");
                } else {//否则单星后面还有字符，则不做任何动作，下一把再做动作
                    preX = true;
                    continue;
                }
            } else {//遇到非*字符
                if (preX) {//如果上一把是*，则先把上一把的*对应的[^/]*添进来
                    sb.append("[^/]*");
                    preX = false;
                }
                if (chars[i] == '?') {//接着判断当前字符是不是?，是的话替换成.
                    sb.append('.');
                } else {//不是?的话，则就是普通字符，直接添进来
                    sb.append(chars[i]);
                }
            }
        }
        return sb.toString();
    }
}
