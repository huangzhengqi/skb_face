package com.fzy.admin.fp.distribution.order.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.StrFormatter;
import com.fzy.admin.fp.common.annotation.TokenInfo;
import com.fzy.admin.fp.common.annotation.UserId;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistUser;
import com.fzy.admin.fp.distribution.order.domain.DistOrder;
import com.fzy.admin.fp.distribution.order.domain.ShopOrder;
import com.fzy.admin.fp.distribution.order.dto.DistOrderDTO;
import com.fzy.admin.fp.distribution.order.dto.ShopOrderDTO;
import com.fzy.admin.fp.distribution.order.service.AfterSaleService;
import com.fzy.admin.fp.distribution.order.service.DistOrderService;
import com.fzy.admin.fp.distribution.order.service.ShopOrderService;
import com.fzy.admin.fp.distribution.order.service.WxUserService;
import com.fzy.admin.fp.distribution.order.vo.DistOrderVO;
import com.fzy.admin.fp.distribution.pc.domain.AfterSale;
import com.fzy.admin.fp.distribution.pc.domain.SendStatus;
import com.fzy.admin.fp.distribution.pc.domain.WxUser;
import com.fzy.admin.fp.distribution.pc.repository.SendStatusRepository;
import com.fzy.admin.fp.distribution.utils.DistUtil;
import com.fzy.admin.fp.sdk.pay.feign.WxConfigServiceFeign;
import io.swagger.annotations.*;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author yy
 * @Date 2019-11-26 11:29:04
 * @Desp 订单管理
 **/

@Slf4j
@RestController
@RequestMapping("/dist/pc/order")
@Api(value = "DistOrderController", tags = {"分销-订单管理"})
public class DistOrderController extends BaseContent {

    @Resource
    private DistOrderService distOrderService;

    @Resource
    private ShopOrderService shopOrderService;

    @Resource
    private AfterSaleService afterSaleService;

    @Resource
    private WxUserService wxUserService;

    @Resource
    private WxConfigServiceFeign wxConfigServiceFeign;

    @Resource
    private HttpServletRequest request;

    @Resource
    private SendStatusRepository sendStatusRepository;

    @GetMapping(value = "/query")
    @ApiOperation(value = "账户明细", notes = "账户明细")
    public Resp query(@TokenInfo(property = "serviceProviderId") String serviceProviderId, PageVo pageVo, DistOrderDTO distOrderDTO) {
        Pageable pageable = PageUtil.initPage(pageVo);
        List<DistOrderVO> distOrderVOList = new ArrayList<>();
        distOrderDTO.setServiceProviderId(serviceProviderId);
        Page<DistOrder> list = distOrderService.list(distOrderDTO, pageable);
        for (DistOrder data : list.getContent()) {
            DistOrderVO distOrderVO = new DistOrderVO();
            BeanUtil.copyProperties(data, distOrderVO);
            distOrderVO.setPhone(data.getDistUser().getUserName());
            distOrderVO.setName(data.getDistUser().getName());
            distOrderVO.setGrade(data.getType());
            distOrderVOList.add(distOrderVO);
        }
        List<DistOrder> orderList = distOrderService.list(distOrderDTO);
        //代理费
        double sum = orderList.stream().filter(s -> s.getStatus() == 1).mapToDouble(s -> s.getPrice().doubleValue()).sum();
        //上级提成
        double superior = orderList.stream().filter(s -> s.getStatus() == 1 && s.getFirstCommissions() != null).mapToDouble(s -> s.getPrice().doubleValue() * (s.getFirstCommissions() * 0.01)).sum();
        //上上级提成
        double upSuperior = orderList.stream().filter(s -> s.getStatus() == 1 && s.getSecondCommissions() != null).mapToDouble(s -> s.getPrice().doubleValue() * (s.getSecondCommissions() * 0.01)).sum();

        Map<String, Object> result = new HashMap();
        //代理费
        result.put("sum", DistUtil.formatDouble(sum));
        //上级提成
        result.put("upSuperior", DistUtil.formatDouble(upSuperior));
        //上上级提成
        result.put("superior", DistUtil.formatDouble(superior));
        result.put("content", distOrderVOList);
        result.put("size", list.getSize());
        result.put("totalPages", list.getTotalPages());
        result.put("totalElements", list.getTotalElements());
        return Resp.success(result);
    }

    @GetMapping(value = "/shop/query")
    @ApiOperation(value = "商城订单", notes = "商城订单")
    public Resp shopQuery(@TokenInfo(property = "serviceProviderId") String serviceProviderId, PageVo pageVo, ShopOrderDTO shopOrderDTO) {
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification<ShopOrder> specification = new Specification<ShopOrder>() {
            @Override
            public Predicate toPredicate(Root<ShopOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("serviceProviderId"), serviceProviderId));
                if (shopOrderDTO.getType() == 0) {//0已支付
                    predicates.add(cb.greaterThan(root.get("status").as(Integer.class), 0));
                    if (shopOrderDTO.getStatus() != null) {
                        predicates.add(cb.equal(root.get("status").as(Integer.class), shopOrderDTO.getStatus()));
                    }
                } else {//1未支付
                    predicates.add(cb.equal(root.get("status").as(Integer.class), 0));
                }
                //日期查询
                if (shopOrderDTO.getStartTime() != null && shopOrderDTO.getEndTime() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), shopOrderDTO.getStartTime()));
                    predicates.add(cb.lessThanOrEqualTo(root.get("createTime").as(Date.class), shopOrderDTO.getEndTime()));
                }
                predicates.add(cb.equal(root.get("delFlag"), 1));
                //Join<ShopOrder, OrderGoods> oderJoin = root.join("orderGoods", JoinType.LEFT);
                Join<ShopOrder, DistUser> join = root.join("distUser", JoinType.LEFT);
                predicates.add(cb.equal(join.get("delFlag"), 1));
                //两张表关联查询
                //predicates.add(cb.equal(oderJoin.get("delFlag"), 1));
                Predicate[] pre = new Predicate[predicates.size()];
                Predicate Pre_And = cb.and(predicates.toArray(pre));

                //or条件查询
                if (StringUtil.isNotEmpty(shopOrderDTO.getOrderNumber())) {
                    List<Predicate> listPermission = new ArrayList<>();
                    listPermission.add(cb.like(root.get("phone"), shopOrderDTO.getOrderNumber() + "%"));
                    listPermission.add(cb.like(root.get("orderNumber"), shopOrderDTO.getOrderNumber() + "%"));
                    Predicate[] predicatesPermissionArr = new Predicate[listPermission.size()];
                    Predicate predicatesPermission = cb.or(listPermission.toArray(predicatesPermissionArr));
                    return query.where(Pre_And, predicatesPermission).getRestriction();
                }
                return query.where(Pre_And).getRestriction();
            }
        };
        Page<ShopOrder> all = shopOrderService.findAll(specification, pageable);
        Map<String, Object> result = new HashMap<>();
        if (shopOrderDTO.getOrderNumber() == null) {
            shopOrderDTO.setOrderNumber("");
        }
        List<ShopOrder> list = null;
        if (shopOrderDTO.getStartTime() != null && shopOrderDTO.getStartTime() != null) {
            list = shopOrderService.getRepository().findAllByServiceProviderIdAndOrderNumberLikeAndPhoneLikeAndCreateTimeBetween(serviceProviderId, shopOrderDTO.getOrderNumber() + "%",
                    shopOrderDTO.getOrderNumber() + "%", shopOrderDTO.getStartTime(), shopOrderDTO.getEndTime());
        } else {
            list = shopOrderService.getRepository().findAllByServiceProviderIdAndOrderNumberLikeAndPhoneLike(serviceProviderId, shopOrderDTO.getOrderNumber() + "%",
                    shopOrderDTO.getOrderNumber() + "%");
        }
        //全部
        result.put("total", list.size());
        //待发货（已付款）
        result.put("payNum", list.stream().filter(order -> order.getStatus() == 1).collect(Collectors.toList()).size());
        //已发货
        result.put("deliverNum", list.stream().filter(order -> order.getStatus() == 2).collect(Collectors.toList()).size());
        //已完成
        result.put("overNum", list.stream().filter(order -> order.getStatus() == 3).collect(Collectors.toList()).size());
        //已退款
        result.put("refundNum", list.stream().filter(order -> order.getStatus() == 4).collect(Collectors.toList()).size());
        //待退款
        result.put("overRefundNum", list.stream().filter(order -> order.getStatus() == 5).collect(Collectors.toList()).size());
        result.put("page", all);
        return Resp.success(result);
    }

    @GetMapping(value = "/shop/delivery")
    @ApiOperation(value = "商城发货", notes = "商城发货")
    public Resp deliveryGoods(@TokenInfo(property = "serviceProviderId") String serviceProviderId, String id, String trackNumber) {
        ShopOrder shopOrder = shopOrderService.getRepository().findByServiceProviderIdAndId(serviceProviderId, id);
        if (shopOrder == null) {
            return new Resp().error(Resp.Status.PARAM_ERROR, "参数错误,请重新登录");
        }
        shopOrder.setStatus(2);
        shopOrder.setTrackNumber(trackNumber);
        shopOrder.setDeliveryTime(new Date());
        shopOrderService.update(shopOrder);
        return Resp.success("操作成功");
    }

    /* @GetMapping("/refund")
     @ApiOperation(value = "商城退款", notes = "商城退款")
     public Resp refund(@TokenInfo(property="serviceProviderId")String serviceProviderId,String id) {
         ShopOrder shopOrder = shopOrderService.getRepository().findByServiceProviderIdAndId(serviceProviderId, id);
         return Resp.success(distPayService.refund(shopOrder));
     }
 */
    @GetMapping(value = "/shop/order")
    @ApiOperation(value = "订单信息", notes = "订单信息")
    public Resp shopOrder(ShopOrderDTO shopOrderDTO, PageVo pageVo) {
        final String serviceId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        Pageable pageable = PageUtil.initPage(pageVo);
        Specification<ShopOrder> specification = new Specification<ShopOrder>() {
            @Override
            public Predicate toPredicate(Root<ShopOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("serviceProviderId"), serviceId));
                predicates.add(cb.equal(root.get("userId"), shopOrderDTO.getUserId()));
                predicates.add(cb.equal(root.get("delFlag"), CommonConstant.NORMAL_FLAG));
                //日期查询
                if (shopOrderDTO.getStartTime() != null && shopOrderDTO.getEndTime() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), shopOrderDTO.getStartTime()));
                    predicates.add(cb.lessThanOrEqualTo(root.get("createTime").as(Date.class), shopOrderDTO.getEndTime()));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                Predicate Pre_And = cb.and(predicates.toArray(pre));
                return query.where(Pre_And).getRestriction();
            }
        };
        Page<ShopOrder> all = shopOrderService.findAll(specification, pageable);
        return Resp.success(all);
    }

    /**
     * 添加售后电话
     */
    @PostMapping("/set_after_sale")
    @ApiOperation(value = "添加/修改售后信息", notes = "添加/修改售后信息")
    public Resp addAfterSale(AfterSale afterSaleOld) {

        final String serviceProviderId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        AfterSale afterSale = afterSaleService.getRepository().findByServiceProviderId(serviceProviderId);
        if (afterSale == null) {
            afterSaleOld.setServiceProviderId(serviceProviderId);
            afterSaleService.save(afterSaleOld);
            return Resp.success(afterSaleOld, "新增成功");
        }
        afterSaleOld.setServiceProviderId(serviceProviderId);
        afterSaleOld.setId(afterSale.getId());
        afterSaleService.update(afterSaleOld);
        return Resp.success(afterSaleOld, "修改成功");
    }

    /**
     * 查询售后电话
     */
    @GetMapping(value = "/query_after_sale")
    @ApiOperation(value = "查询售后信息", notes = "查询售后信息")
    public Resp query() {
        final String serviceProviderId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        return Resp.success(afterSaleService.getRepository().findByServiceProviderId(serviceProviderId));
    }

    /**
     * 获取code
     */
    @PostMapping("/get_code")
    @ApiOperation(value = "获取code", notes = "获取code")
    public Resp getCode() throws IOException {

        String domainName = request.getServerName();
        String serviceProviderId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        // 微信支付配置
        Map<String, String> configMap = wxConfigServiceFeign.getWxConfig(serviceProviderId);
        if ("error".equals(configMap.get("msg"))) {
            throw new BaseException("请先配置微信支付参数", Resp.Status.PARAM_ERROR.getCode());
        }
        String appId = configMap.get("appId");

        // 用户授权后腾讯重定向的地址 &转义成 %26 否则&后的参数会被微信截取掉
        //微信获取code同步回调
        final String url = "https://" + domainName + "/dist/pc/order/redirect_uri";
        log.info("微信获取code同步回调" + url);
        // 微信授权链接
        String redirectUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={}&redirect_uri={}&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        // 授权链接补全参数
        redirectUrl = StrFormatter.format(redirectUrl, appId, url);
        log.info("微信授权地址，{}", redirectUrl);

        String encode = URLEncoder.encode(redirectUrl, "UTF-8");
        //二维码
        String erweima = "https://pay.hstypay.com/pay/qrcode?uuid=" + encode;
        log.info("二维码链接 ----------  " + erweima);
        return Resp.success(erweima, "获取成功");
    }

    /**
     * 回调链接
     *
     * @return
     */
    @GetMapping("/redirect_uri")
    @ApiOperation(value = "微信信息用户回调", notes = "微信信息用户回调")
    public Resp redirectUri() throws IOException {

        response.setCharacterEncoding("UTF-8");
        String serviceProviderId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);

        String code = request.getParameter("code");
        if (ParamUtil.isBlank(code)) {
            throw new BaseException("请在微信浏览器中打开");
        }
        //通过code换取网页授权access_token openId
        Map map = distOrderService.getAccessToken(code, serviceProviderId);
        String openid = map.get("openid").toString();
        String accessToken = map.get("access_token").toString();
        //获取微信用户信息
        Map wxUserMap = distOrderService.getWxUser(openid, accessToken);
        String wxOpenid = wxUserMap.get("openid").toString();
        String nickname = wxUserMap.get("nickname").toString();
        WxUser wxUser = wxUserService.getRepository().findAllByOpenid(wxOpenid);
        if (wxUser == null) {
            WxUser wxUser1 = new WxUser();
            wxUser1.setOpenid(wxOpenid);
            wxUser1.setNickname(nickname);
            wxUser1.setServiceProviderId(serviceProviderId);
            wxUserService.save(wxUser1);
        } else {
            wxUser.setNickname(nickname);
            wxUserService.update(wxUser);
        }
        response.sendRedirect("/web/agent/static/img/wxbandin.png");
//       return  Resp.success("<html><img src=\"static/web/agent/static/img/wxbandin.png\" ></html>");
        return Resp.success("绑定成功");
    }

    /**
     * 显示微信用户
     */
    @GetMapping("/query_wx_user")
    @ApiOperation(value = "显示微信用户", notes = "显示微信用户")
    public Resp queryWxUser() {
        final String serviceProviderId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        return Resp.success(wxUserService.getRepository().findAllByServiceProviderId(serviceProviderId));
    }

    /**
     * 删除微信用户信息
     */
    @PostMapping("/delete_wx")
    @ApiOperation(value = "删除微信用户", notes = "删除微信用户")
    public Resp deleteWx(String id) {
        wxUserService.getRepository().delete(id);
        return Resp.success("删除成功");
    }

    @PostMapping("/update_status")
    @ApiOperation(value = "修改状态", notes = "修改状态")
    public Resp updateStatus(SendStatus SendStatusOld) {

        final String serviceProviderId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        SendStatus sendStatus = sendStatusRepository.findByServiceProviderId(serviceProviderId);
        if (sendStatus == null) {
            SendStatusOld.setServiceProviderId(serviceProviderId);
            sendStatusRepository.save(SendStatusOld);
            return Resp.success(SendStatusOld, "新增成功");
        }
        SendStatusOld.setServiceProviderId(serviceProviderId);
        SendStatusOld.setId(sendStatus.getId());
        sendStatusRepository.saveAndFlush(SendStatusOld);
        return Resp.success(SendStatusOld, "修改成功");
    }

    @GetMapping("/query_status")
    @ApiOperation(value = "查询状态", notes = "查询状态")
    public Resp queryStatus() {
        final String serviceProviderId = (String) request.getAttribute(CommonConstant.SERVICE_PROVIDERID);
        return Resp.success(sendStatusRepository.findByServiceProviderId(serviceProviderId));
    }

    @ApiOperation(value = "修改订单价格", notes = "修改订单价格")
    @PostMapping("update/price/order")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", dataType = "String", value = "订单id"),
            @ApiImplicitParam(paramType = "query", name = "price", dataType = "BigDecimal", value = "订单价格")})
    @ApiResponse(code = 200,message = "OK",response = ShopOrder.class)
    public Resp updatePriceOrder(@UserId String userId, String id, BigDecimal price) {
        if (ParamUtil.isBlank(userId)) {
            return new Resp().error(Resp.Status.TOKEN_ERROR, "token参数错误");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BaseException("请输入正确的退款金额", Resp.Status.PARAM_ERROR.getCode());
        }
        if (price.scale() > 2) {
            throw new BaseException("修改订单金额最低为0.01", Resp.Status.PARAM_ERROR.getCode());
        }
        ShopOrder shopOrder = shopOrderService.findOne(id);
        //重新生成订单号
        String orderNub = DistUtil.orderNub();
        shopOrder.setPrice(price);
        shopOrder.setUpdatePriceTime(new Date());
        shopOrder.setOrderNumber(orderNub);
        return Resp.success(shopOrderService.update(shopOrder),"修改订单价格成功");
    }
}
