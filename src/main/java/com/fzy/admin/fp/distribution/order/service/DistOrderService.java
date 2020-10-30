package com.fzy.admin.fp.distribution.order.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.http.HttpUtil;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.json.JacksonUtil;
import com.fzy.admin.fp.common.spring.base.BaseRepository;
import com.fzy.admin.fp.common.spring.base.BaseService;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.order.domain.DistOrder;
import com.fzy.admin.fp.distribution.order.dto.DistOrderDTO;
import com.fzy.admin.fp.distribution.order.repository.DistOrderRepository;
import com.fzy.admin.fp.distribution.pc.dto.SalesmanDTO;
import com.fzy.admin.fp.goods.controller.GoodsCategoryController;
import com.fzy.admin.fp.sdk.pay.feign.WxConfigServiceFeign;
import io.swagger.models.auth.In;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author yy
 * @Date 2019-11-16 16:18:32
 **/
@Service
public class DistOrderService implements BaseService<DistOrder> {

    private static final Logger log = LoggerFactory.getLogger(GoodsCategoryController.class);

    @Resource
    private DistOrderRepository distOrderRepository;

    @Resource
    private WxConfigServiceFeign wxConfigServiceFeign;


    public DistOrderRepository getRepository() {
        return distOrderRepository;
    }

    public Page<DistOrder> list(DistOrderDTO distOrderDTO, Pageable pageable) {
        Page all = distOrderRepository.findAll(new Specification<DistOrder>() {
            @Override
            public Predicate toPredicate(Root<DistOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("serviceProviderId").as(String.class), distOrderDTO.getServiceProviderId()));
                if (distOrderDTO.getStartTime() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), distOrderDTO.getStartTime()));
                }
                if (distOrderDTO.getEndTime() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("createTime").as(Date.class), distOrderDTO.getEndTime()));
                }
                if (distOrderDTO.getType() != null) {
                    predicates.add(cb.equal(root.get("type").as(Integer.class), distOrderDTO.getType()));
                }
                predicates.add(cb.equal(root.get("status").as(Integer.class), 1));
                Join<DistOrder, DistUser> join = root.join("distUser", JoinType.LEFT);
                predicates.add(cb.equal(join.get("delFlag"), 1));
                Predicate[] pre = new Predicate[predicates.size()];
                criteriaQuery.where(predicates.toArray(pre));
                return cb.and(predicates.toArray(pre));
            }
        }, pageable);
        return all;
    }

    public List<DistOrder> list(DistOrderDTO distOrderDTO) {
        List all = distOrderRepository.findAll(new Specification<DistOrder>() {
            @Override
            public Predicate toPredicate(Root<DistOrder> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(distOrderDTO.getType()!=null){
                    predicates.add(cb.lessThanOrEqualTo(root.get("type").as(Integer.class), distOrderDTO.getType()));
                }
                predicates.add(cb.equal(root.get("serviceProviderId").as(String.class), distOrderDTO.getServiceProviderId()));
                if (distOrderDTO.getStartTime() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), distOrderDTO.getStartTime()));
                }
                if (distOrderDTO.getEndTime() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("createTime").as(Date.class), distOrderDTO.getEndTime()));
                }
                if (distOrderDTO.getType() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("type").as(Integer.class), distOrderDTO.getType()));
                }
                if (distOrderDTO.getType() != null) {
                    predicates.add(cb.equal(root.get("status").as(Integer.class), 1));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                criteriaQuery.where(predicates.toArray(pre));
                return cb.and(predicates.toArray(pre));
            }
        });
        return all;
    }

    public Map getAccessToken(String code,String serviceProviderId) {

        if (ParamUtil.isBlank(code)) {
            throw new BaseException("code参数异常,请重新授权", Resp.Status.INNER_ERROR.getCode());
        }

        Map<String, String> configMap = wxConfigServiceFeign.getWxConfig(serviceProviderId);
        if ("error".equals(configMap.get("msg"))) {
            throw new BaseException("请先配置微信支付参数", Resp.Status.PARAM_ERROR.getCode());
        }
        // 获取openid的链接
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={}&secret={}&code={}&grant_type=authorization_code";
        url = StrFormatter.format(url, configMap.get("appId"), configMap.get("appSecret"), code);
        log.info("openid url----------->>>{}",url);
        String tokenJson = HttpUtil.get(url);
        Map<String, String> map = JacksonUtil.toStringMap(tokenJson);
        log.info("get openid result------------>>>,{}", map);
        if (map.get("openid") == null) {
            throw new BaseException("参数异常,请重新授权", Resp.Status.INNER_ERROR.getCode());
        }
        return map;
    }

    public Map getWxUser(String openid, String accessToken) {
        if (ParamUtil.isBlank(openid)) {
            throw new BaseException("openid参数异常,请重新授权", Resp.Status.INNER_ERROR.getCode());
        }

        if (ParamUtil.isBlank(accessToken)) {
            throw new BaseException("accessToken参数异常,请重新授权", Resp.Status.INNER_ERROR.getCode());
        }

        // 获取用户信息链接
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token={}&openid={}&lang=zh_CN";
        url = StrFormatter.format(url,accessToken ,openid);
        String tokenJson = HttpUtil.get(url);
        log.info("获取微信用户内容 ========>:  {}",tokenJson);
        Map<String, String> map = JacksonUtil.toStringMap(tokenJson);
        return map;

    }
}
