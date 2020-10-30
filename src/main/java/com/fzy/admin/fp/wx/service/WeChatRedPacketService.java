package com.fzy.admin.fp.wx.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.fzy.admin.fp.auth.utils.DateUtils;
import com.fzy.admin.fp.common.constant.CommonConstant;
import com.fzy.admin.fp.merchant.merchant.domain.Merchant;
import com.fzy.admin.fp.merchant.merchant.service.MerchantService;
import com.fzy.admin.fp.pay.pay.domain.TopConfig;
import com.fzy.admin.fp.pay.pay.repository.TopConfigRepository;
import com.fzy.admin.fp.pay.pay.util.SignUtils;
import com.fzy.admin.fp.wx.bo.SendredpackResponseBo;
import com.fzy.admin.fp.wx.domain.WxRedPackDetail;
import com.fzy.admin.fp.wx.domain.WxRewardDetail;
import com.fzy.admin.fp.wx.util.XmlParseUtil;
import com.fzy.assist.wraps.DateWrap;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @Author hzq
 * @Date 2020/9/9 11:23
 * @Version 1.0
 * @description 微信红包业务层
 */
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class WeChatRedPacketService {

    private final String FAIL = "FAIL";

    @PersistenceContext
    private EntityManager em;
    @Resource
    private TopConfigRepository topConfigRepository;
    @Resource
    private WxRedPackDetailService wxRedPackDetailService;
    @Resource
    private MerchantService merchantService;
    @Resource
    private WxRewardDetailService wxRewardDetailService;

    /**
     * 已领取
     */
    private final String RECEIVED = "RECEIVED";

    public SendredpackResponseBo sendRedPack(String url, Map<String, String> redPackMap, TopConfig topConfig) throws Exception {
        SendredpackResponseBo resp = null;
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        // 读取证书
        FileInputStream instream = new FileInputStream(new File(topConfig.getWxCertPath()));
        try {
            // password是商户号
            keyStore.load(instream, topConfig.getWxMchId().toCharArray());
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
            System.err.println(e);
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, topConfig.getWxMchId().toCharArray()).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {
            HttpPost httpPost = new HttpPost(url);
            log.info("executing request：" + httpPost.getRequestLine());
            String xml = WXPayUtil.mapToXml(redPackMap);
            log.info(xml);
            httpPost.setEntity(new ByteArrayEntity(xml.getBytes("UTF-8")));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                System.err.println(response.getStatusLine());
                if (entity != null) {
                    log.info("Response content length: " + entity.getContentLength());
                    String result = EntityUtils.toString(entity);
                    log.info(result);
                    resp = (SendredpackResponseBo) XmlParseUtil.xmlToBean(result, SendredpackResponseBo.class);
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return resp;
    }


    public Map<String, String> getRedPackMap() {
        String mchBillno = "";
        mchBillno = DateUtil.format(new Date(), "YYYYMMddHHmmss") + RandomUtil.randomNumbers(4);
        Map<String, String> map = new HashMap<String, String>();
        map.put("mch_billno", mchBillno);
        map.put("mch_id", "1553555941");
        map.put("wxappid", "wx547f960108ed17c1");
        map.put("send_name", "锋之云科技有限公司");
        map.put("re_openid", "o0PbBw-eFC06BIDVNzJIXIeiQA54");
        map.put("total_amount", Math.round(Double.parseDouble(new BigDecimal("0.3").toString()) * 100d) + "");
        map.put("total_num", Math.round(Double.parseDouble(new BigDecimal("1").toString())) + "");
        map.put("wishing", "祝各位商户生意兴隆");
        map.put("client_ip", "47.107.132.130");
        map.put("act_name", "每日返佣金");
        map.put("remark", "请核实");
        map.put("scene_id", "PRODUCT_5");
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        String sign = SignUtils.createSign(map, "f75e3e8da4f04ef882901799b4ce1234");
        map.put("sign", sign);
        return map;
    }

    /**
     * 绑定的商户发红包
     */
    public boolean bindSendredpack() {
        //得到昨天的时间
        Date dayByNum = DateUtils.getDayByNum(new Date(), -1);
        String begin = DateWrap.format(dayByNum, "yyyy-MM-dd 00:00:00");
        String end = DateWrap.format(dayByNum, "yyyy-MM-dd 23:59:59");
        List<Merchant> merchantList = merchantService.getRepository().findByRebateTypeAndDelFlag(Integer.valueOf(2), Integer.valueOf(1));
        log.info("返佣商户数 ============ >:  {}" + merchantList.size());
        if (merchantList == null || merchantList.size() == 0) {
            return false;
        }
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag("1190507879240863744", CommonConstant.NORMAL_FLAG);
        for (Merchant merchant : merchantList) {
            //订单号
            String mchBillno = DateUtil.format(new Date(), "YYYYMMddHHmmss") + RandomUtil.randomNumbers(4);
            //发放红包明细
            WxRedPackDetail wxRedPackDetail = wxRedPackDetailService.createWxRedPackDetail(merchant, mchBillno.toString(), topConfig.getServiceProviderId());
            //当天实际营收的佣金 实际营收*商户返佣比例
            BigDecimal actualMoney = this.findMerchantYesterdayCommissions(merchant, begin, end, wxRedPackDetail);
            //判断是否超出了佣金返佣的时间限制
            if (merchant.getStartRebateTime() != null && merchant.getEndRebateTime() != null) {
                boolean effectiveDate = isEffectiveDate(new Date(), merchant.getStartRebateTime(), merchant.getEndRebateTime());
                if (!effectiveDate) {
                    wxRedPackDetailService.updateWxRedPackDetailAndMerchant(merchant, actualMoney, wxRedPackDetail, "超出了佣金返佣的时间设置", Integer.valueOf(0));
                    continue;
                }
            }
            //判断是否设置了返佣门槛笔数
            if (merchant.getRebateNum() != null) {
                //当天的交易笔数且单笔付款金额》= 2 元
                Boolean num = this.findMerchantYesterdayNumber(merchant, begin, end);
                if (!num) {
                    wxRedPackDetailService.updateWxRedPackDetailAndMerchant(merchant, actualMoney, wxRedPackDetail, "当天交易笔数不满足门槛笔数", Integer.valueOf(0));
                    continue;
                }
            }
            //判断佣金是否超出限额
            if (merchant.getLimitRebate() != null) {
                if (actualMoney.add(merchant.getCumulationRebate() == null ? new BigDecimal("0") : merchant.getCumulationRebate()).compareTo(merchant.getLimitRebate()) == 1) {
                    wxRedPackDetailService.updateWxRedPackDetailAndMerchant(merchant, actualMoney, wxRedPackDetail, "佣金超出了限额", Integer.valueOf(0));
                    continue;
                }
            }
            //返佣金额大于0.3或者等于0.3都可以发放红包
            if ((actualMoney.compareTo(new BigDecimal("0.3")) == -1)) {
                wxRedPackDetailService.updateWxRedPackDetailAndMerchant(merchant, actualMoney, wxRedPackDetail, "您昨天的佣金是:(" + actualMoney + ")少于0.3元,不支持发放", Integer.valueOf(0));
                continue;
            }
            //判断待结算是否为负数
            if (merchant.getWaitRebate() == null) {
                merchant.setWaitRebate(new BigDecimal("0"));
            } else {
                if (merchant.getWaitRebate().compareTo(new BigDecimal("0")) == -1) {
                    log.info("进入待结算方法");
                    //返佣金额+待结算金额
                    actualMoney = actualMoney.add(merchant.getWaitRebate());
                    if (actualMoney.compareTo(new BigDecimal("0")) != -1) {
                        merchant.setWaitRebate(new BigDecimal("0"));
                        merchantService.update(merchant);
                    }
                }
            }
            //是否绑定了微信
            if (merchant.getOpenId() == null || merchant.getOpenId().equals("")) {
                wxRedPackDetail.setDescription("您当前还未绑定，请去公众号搜索（刷脸支付之源）进行绑定");
                wxRedPackDetail.setReturnType(Integer.valueOf(2));
                wxRedPackDetail.setTotalAmount(actualMoney);
                wxRedPackDetailService.update(wxRedPackDetail);
                continue;
            }
            //待结算最终金额大于0.3或者等于0.3都可以发放红包
            if ((actualMoney.compareTo(new BigDecimal("0.3")) == -1)) {
                wxRedPackDetailService.updateWxRedPackDetailAndMerchant(merchant, actualMoney, wxRedPackDetail, "您待结算佣金是:(" + actualMoney + ")少于0.3元,不支持发放", Integer.valueOf(0));
                continue;
            }
            //创建构造参数
            Map<String, String> redPackMap = redPackMap(merchant, topConfig, actualMoney, mchBillno, "刷客宝商户返佣", "每日返佣金");
            SendredpackResponseBo resp = null;
            try {
                // 发送请求
                resp = sendRedPack("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", redPackMap, topConfig);
            } catch (Exception e) {
                log.error("微信红包异常，请稍后再试！");
                wxRedPackDetail.setDescription("微信红包异常，请稍后再试！");
                wxRedPackDetail.setReturnType(Integer.valueOf(0));
                wxRedPackDetail.setTotalAmount(new BigDecimal("0"));
                wxRedPackDetailService.update(wxRedPackDetail);
                continue;
            }
            //校验结果
            checkResp(resp, wxRedPackDetail, merchant);
        }
        return true;
    }

    private void checkResp(SendredpackResponseBo resp, WxRedPackDetail wxRedPackDetail, Merchant merchant) {
        if (resp != null) {
            String returnCode = resp.getReturn_code();
            String returnMsg = resp.getReturn_msg();
            //通信标识(失败)
            if (returnCode.equals(FAIL)) {
                wxRedPackDetail.setReturnType(Integer.valueOf(0));
                wxRedPackDetail.setDescription(returnMsg);
            } else {
                String resultCode = resp.getResult_code();
                //业务结果（失败）
                if (resultCode.equals(FAIL)) {
                    wxRedPackDetail.setReturnType(Integer.valueOf(0));
                    wxRedPackDetail.setDescription(resp.getErr_code_des());
                } else {
                    wxRedPackDetail.setReturnType(Integer.valueOf(1));
                    wxRedPackDetail.setWxAppid(resp.getWxappid());
                    BigDecimal bigDecimal = new BigDecimal(resp.getTotal_amount()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
                    BigDecimal divide = bigDecimal.divide(new BigDecimal("100").setScale(2, BigDecimal.ROUND_HALF_DOWN));
                    wxRedPackDetail.setTotalAmount(divide);
                    wxRedPackDetail.setSendListid(resp.getSend_listid());
                    wxRedPackDetail.setDescription(resp.getErr_code_des());
                    //如果没有设置限制佣金就不存累积金额
                    if (merchant.getLimitRebate() != null) {
                        BigDecimal cumulationRebate = merchant.getCumulationRebate() == null ? new BigDecimal("0") : merchant.getCumulationRebate();
                        merchant.setCumulationRebate(cumulationRebate.add(divide));
                    }
                }
            }
            wxRedPackDetailService.update(wxRedPackDetail);
        } else {
            log.info("没有进行微信通信");
        }
    }

    private BigDecimal findMerchantYesterdayCommissions(Merchant merchant, String begin, String end, WxRedPackDetail wxRedPackDetail) {
        //交易金额（已支付的钱）
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT IFNULL(sum( act_pay_price ) ,0)  AS actPayPrice FROM lysj_order_order WHERE merchant_id = :merchantId  AND `status` = 2 AND act_pay_price BETWEEN 2 AND 200 AND pay_type = 3  AND pay_time BETWEEN :begin AND :end ");
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("merchantId", merchant.getId());
        query.setParameter("begin", begin);
        query.setParameter("end", end);
        List<Map> list = query.getResultList();
        String totalPrice = "";
        for (Object obj : list) {
            Map row = (Map) obj;
            totalPrice = row.get("actPayPrice").toString();
        }
        BigDecimal actPayPrice = new BigDecimal(totalPrice).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        wxRedPackDetail.setActPayPrice(actPayPrice);
        //退款金额
        StringBuilder sb2 = new StringBuilder();
        sb2.append("SELECT IFNULL(sum( refund_pay_price ) ,0)  AS refundPrice FROM lysj_order_order WHERE merchant_id = :merchantId  AND act_pay_price >= 2 AND pay_type = 3  AND refund_time BETWEEN :begin AND :end  AND pay_time NOT BETWEEN :begin AND :end ");
        Query query2 = em.createNativeQuery(sb2.toString());
        query2.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query2.setParameter("merchantId", merchant.getId());
        query2.setParameter("begin", begin);
        query2.setParameter("end", end);
        List<Map> list2 = query2.getResultList();
        String refundPrice = "";
        for (Object obj : list2) {
            Map row = (Map) obj;
            refundPrice = row.get("refundPrice").toString();
        }
        BigDecimal refundPayPrice = new BigDecimal(refundPrice).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        wxRedPackDetail.setRefundPayPrice(refundPayPrice);
        //实际营收
        BigDecimal actualMoney = actPayPrice.subtract(refundPayPrice);
        wxRedPackDetail.setActualMoney(actualMoney);
        //实际营收乘以返佣比例
        actualMoney = actualMoney.multiply(merchant.getSiteRate() == null ? new BigDecimal("0") : merchant.getSiteRate()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return actualMoney;
    }

    /**
     * 商户红包参数
     *
     * @param merchant
     * @param topConfig
     * @param actPayPrice
     * @param mchBillno
     * @param sendName
     * @param actName
     * @return
     */
    public Map<String, String> redPackMap(Merchant merchant, TopConfig topConfig, BigDecimal actPayPrice, String mchBillno, String sendName, String actName) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mch_billno", mchBillno);
        map.put("mch_id", topConfig.getWxMchId());
        map.put("wxappid", topConfig.getWxAppId());
        map.put("send_name", sendName);
        map.put("re_openid", merchant.getOpenId());
        map.put("total_amount", Math.round(Double.parseDouble(actPayPrice.toString()) * 100d) + "");
        map.put("total_num", Math.round(Double.parseDouble(new BigDecimal("1").toString())) + "");
        map.put("wishing", "祝各位老板生意兴隆");
        map.put("client_ip", "47.107.132.130");
        map.put("act_name", actName);
        map.put("remark", "请核实");
        map.put("scene_id", "PRODUCT_5");
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        String sign = SignUtils.createSign(map, topConfig.getWxAppKey());
        map.put("sign", sign);
        return map;
    }


    /**
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author sunran   判断当前时间在时间区间内
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 当天的交易笔数
     *
     * @param merchant
     * @param begin
     * @param end
     */
    private Boolean findMerchantYesterdayNumber(Merchant merchant, String begin, String end) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(1) as num from lysj_order_order WHERE merchant_id = :merchantId and  `status` = 2 and pay_type = 3 and act_pay_price >= 2 and create_time BETWEEN :begin AND :end ");
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("merchantId", merchant.getId());
        query.setParameter("begin", begin);
        query.setParameter("end", end);
        List<Map> list = query.getResultList();
        int num = 0;
        for (Object obj : list) {
            Map row = (Map) obj;
            num = Integer.valueOf(row.get("num").toString()).intValue();
        }
        if (num >= merchant.getRebateNum().intValue()) {
            return true;
        }
        return false;
    }


    /**
     * 每日早上8点开始发放返佣红包
     */
    @Scheduled(cron = "0 0 8 * * ?")
    public void createbindSendredpack() {
        boolean b = bindSendredpack();
        if (b) {
            log.info("发放返佣红包完成");
        }
        log.info("无商户发放");
    }

    /**
     * 每月3号早上7点开始发放返佣红包
     */
    @Scheduled(cron = "0 0 7 3 * ?")
    public void createbindReward() {
        boolean b = reward();
        if (b) {
            log.info("发放奖励红包完成");
        }
        log.info("无商户发放");
    }

    /**
     * 每天早上8点半点查询昨天发放的返佣红包
     */
    @Scheduled(cron = "0 30 8 * * ?")
    public void findBindSendredpac() {
        log.info("每天早上8点半点查询昨天发放的返佣红包");
        Date yesterday = DateUtils.getYesterday(new Date());
        Date statrTime = DateUtils.getStartOfDay(yesterday);
        Date endTime = DateUtils.getEndOfDay(yesterday);

        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag("1190507879240863744", CommonConstant.NORMAL_FLAG);
        //查询红包明细
        List<WxRedPackDetail> wxRedPackDetailList = wxRedPackDetailService.getRepository().findByReturnTypeAndStatusIsNullAndCreateTimeBetween(Integer.valueOf(1), statrTime, endTime);
        if (wxRedPackDetailList == null || wxRedPackDetailList.size() <= 0) {
            log.info("红包明细列表 ============ > {}", wxRedPackDetailList.size());
            return;
        }
        for (WxRedPackDetail wxRedPackDetail : wxRedPackDetailList) {
            //查询红包参数
            Map<String, String> repMap = findRepMap(wxRedPackDetail.getMchBillno(), topConfig.getWxMchId(), topConfig.getWxAppId(), topConfig.getWxAppKey());
            SendredpackResponseBo resp = null;
            try {
                // 发送请求
                resp = sendRedPack("https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo", repMap, topConfig);
            } catch (Exception e) {
                log.error("微信红包查询异常，请稍后再试！");
                wxRedPackDetail.setDescription("微信红包异常，请稍后再试！");
                wxRedPackDetail.setReturnType(Integer.valueOf(1));
                wxRedPackDetailService.update(wxRedPackDetail);
                continue;
            }
            //校验查询结果
            checkfindResp(resp, wxRedPackDetail, 1, null);
        }
    }

    /**
     * 每月4号早上9点查询奖励红包
     */
    @Scheduled(cron = "0 0 9 4 * ?")
    public void findReward() {
        log.info("查询奖励红包");
        Date yesterday = DateUtils.getYesterday(new Date());
        Date statrTime = DateUtils.getStartOfDay(yesterday);
        Date endTime = DateUtils.getEndOfDay(yesterday);
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag("1190507879240863744", CommonConstant.NORMAL_FLAG);
        //查询红包明细
        List<WxRewardDetail> rewardDetails = wxRewardDetailService.getRepository().findByReturnTypeAndStatusIsNullAndCreateTimeBetween(Integer.valueOf(1), statrTime, endTime);
        if (rewardDetails == null || rewardDetails.size() <= 0) {
            log.info("奖励红包明细列表 ============ > {}", rewardDetails.size());
            return;
        }
        for (WxRewardDetail wxRewardDetail : rewardDetails) {
            //查询红包参数
            Map<String, String> repMap = findRepMap(wxRewardDetail.getMchBillno(), topConfig.getWxMchId(), topConfig.getWxAppId(), topConfig.getWxAppKey());
            SendredpackResponseBo resp = null;
            try {
                // 发送请求
                resp = sendRedPack("https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo", repMap, topConfig);
            } catch (Exception e) {
                log.info("微信红包查询异常 ======== >" + e.getMessage());
                wxRewardDetail.setDescription("微信红包异常，请稍后再试！");
                wxRewardDetail.setReturnType(Integer.valueOf(1));
                wxRewardDetailService.update(wxRewardDetail);
                continue;
            }
            //校验查询结果
            checkfindResp(resp, null, 2, wxRewardDetail);
        }
    }

    /**
     * 校验红包查询结果
     *
     * @param resp
     * @param wxRedPackDetail
     * @param type            1 返佣红包 2奖励红包
     */
    private void checkfindResp(SendredpackResponseBo resp, WxRedPackDetail wxRedPackDetail, Integer type, WxRewardDetail wxRewardDetail) {
        String returnCode = resp.getReturn_code();
        String returnMsg = resp.getReturn_msg();
        //通信标识(失败)
        if (returnCode.equals(FAIL)) {
            if (type.equals(Integer.valueOf(1))) {
                wxRedPackDetail.setDescription(returnMsg);
            } else {
                wxRewardDetail.setDescription(returnMsg);
            }
        } else {
            String resultCode = resp.getResult_code();
            //业务结果（失败）
            if (resultCode.equals(FAIL)) {
                if (type.equals(Integer.valueOf(1))) {
                    wxRedPackDetail.setDescription(resp.getErr_code_des());
                } else {
                    wxRewardDetail.setDescription(resp.getErr_code_des());
                }
            } else {
                String status = resp.getStatus();
                if (status.equals(RECEIVED)) {
                    if (type.equals(Integer.valueOf(1))) {
                        wxRedPackDetail.setStatus(Integer.valueOf(1));
                    } else {
                        wxRewardDetail.setStatus(Integer.valueOf(1));
                    }
                } else {
                    if (type.equals(Integer.valueOf(1))) {
                        wxRedPackDetail.setStatus(Integer.valueOf(0));
                    } else {
                        wxRewardDetail.setStatus(Integer.valueOf(0));
                    }
                }
            }
        }
        if (type.equals(Integer.valueOf(1))) {
            wxRedPackDetail.setFindStatus(Integer.valueOf(1));
            wxRedPackDetailService.update(wxRedPackDetail);
        } else {
            wxRewardDetail.setFindStatus(Integer.valueOf(1));
            wxRewardDetailService.update(wxRewardDetail);
        }


    }

    /**
     * 查询红包查询
     */
    public Map<String, String> findRepMap(String mchBillno, String mchId, String appId, String wxAppKey) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mch_billno", mchBillno);
        map.put("mch_id", mchId);
        map.put("appid", appId);
        map.put("bill_type", "MCHT");
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        String sign = SignUtils.createSign(map, wxAppKey);
        map.put("sign", sign);
        return map;
    }

    /**
     * 发放奖励红包
     *
     * @return
     */
    public boolean reward() {
        List<Merchant> merchantList = merchantService.getRepository().findAllByRewardTypeAndDelFlag(Integer.valueOf(2), Integer.valueOf(1));
        log.info("奖励红包商户数 ============ >:  {}", merchantList.size());
        if (merchantList == null || merchantList.size() == 0) {
            return false;
        }
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag("1190507879240863744", CommonConstant.NORMAL_FLAG);
        for (Merchant merchant : merchantList) {

            boolean sameDate = com.fzy.admin.fp.distribution.app.utils.DateUtil.isSameDate(merchant.getStartRewardTime(), new Date());
            if (sameDate) {
                log.info("当前月份和活动月份一致，需下个月才进行返还");
                continue;
            }

            long nowTime = System.currentTimeMillis();
            //投放时间
            long beginTime = merchant.getStartRewardTime().getTime();
            //结束时间
            long endTime = merchant.getEndRewardTime().getTime();
            int type = com.fzy.admin.fp.distribution.app.utils.DateUtil.hourMinuteBetween(nowTime, beginTime, endTime);
            if (type == 3) {
                log.info("该商户已超过了奖励月数 ======>:    " + merchant);
                continue;
            } else if (type == 1) {
                log.info("活动尚未开始" + "商户 ======>:    " + merchant);
                continue;
            }
            //订单号
            String mchBillno = DateUtil.format(new Date(), "YYYYMMddHHmmss") + RandomUtil.randomNumbers(4);
            //发放奖励红包明细
            WxRewardDetail wxRewardDetail = wxRewardDetailService.createWxRedPackDetail(merchant, mchBillno.toString(), topConfig.getServiceProviderId());
            int count = this.findMerchantLastMonthNumber(merchant);
            wxRewardDetail.setActFaceNum(count);
            //判断是否设置了奖励门槛笔数
            if (merchant.getRewardNum() != null) {
                //上个月的交易笔数且单笔付款金额》= 2 元
                if (count < merchant.getRewardNum().intValue()) {
                    wxRewardDetailService.updateWxRedPackDetailAndMerchant(wxRewardDetail, "上个月交易笔数不满足门槛笔数", Integer.valueOf(0));
                    continue;
                }
            }
            //是否绑定了微信
            if (merchant.getOpenId() == null || merchant.getOpenId().equals("")) {
                wxRewardDetail.setDescription("您当前还未绑定，请去公众号搜索（刷脸支付之源）进行绑定");
                wxRewardDetail.setReturnType(Integer.valueOf(2));
                wxRewardDetailService.update(wxRewardDetail);
                continue;
            }
            //创建构造参数
            Map<String, String> redPackMap = redPackMap(merchant, topConfig, merchant.getRewardPrice(), mchBillno, "刷客宝商户押金奖励", "每月返押金奖励");
            SendredpackResponseBo resp = null;
            try {
                // 发送请求
                resp = sendRedPack("https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack", redPackMap, topConfig);
            } catch (Exception e) {
                log.info("[错误信息] ======== > {}", e.getMessage());
                wxRewardDetail.setDescription("微信红包异常，请稍后再试！");
                wxRewardDetail.setReturnType(Integer.valueOf(0));
                wxRewardDetailService.update(wxRewardDetail);
                continue;
            }
            //校验结果
            checkRespWxRewardDetail(resp, wxRewardDetail);
        }
        return false;
    }

    private void checkRespWxRewardDetail(SendredpackResponseBo resp, WxRewardDetail wxRewardDetail) {
        if (resp != null) {
            String returnCode = resp.getReturn_code();
            String returnMsg = resp.getReturn_msg();
            //通信标识(失败)
            if (returnCode.equals(FAIL)) {
                wxRewardDetail.setReturnType(Integer.valueOf(0));
                wxRewardDetail.setDescription(returnMsg);
            } else {
                String resultCode = resp.getResult_code();
                //业务结果（失败）
                if (resultCode.equals(FAIL)) {
                    wxRewardDetail.setReturnType(Integer.valueOf(0));
                    wxRewardDetail.setDescription(resp.getErr_code_des());
                } else {
                    wxRewardDetail.setReturnType(Integer.valueOf(1));
                    wxRewardDetail.setWxAppid(resp.getWxappid());
                    wxRewardDetail.setSendListid(resp.getSend_listid());
                    wxRewardDetail.setDescription(resp.getErr_code_des());
                }
            }
            wxRewardDetailService.update(wxRewardDetail);
        } else {
            log.info("没有进行微信通信");
        }
    }

    /**
     * 上个月的笔数统计
     *
     * @param merchant
     * @return
     */
    private int findMerchantLastMonthNumber(Merchant merchant) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(1) as num from lysj_order_order WHERE merchant_id = :merchantId and  `status` = 2 and pay_type = 3 and act_pay_price >= 2 and  PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( create_time, '%Y%m' ) ) = 1 ");
        Query query = em.createNativeQuery(sb.toString());
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        query.setParameter("merchantId", merchant.getId());
        List<Map> list = query.getResultList();
        int num = 0;
        for (Object obj : list) {
            Map row = (Map) obj;
            num = Integer.valueOf(row.get("num").toString()).intValue();
        }
        return num;
    }

    public void create(String description, Merchant merchant) {
        WxRewardDetail wxRewardDetail = new WxRewardDetail();
        wxRewardDetail.setDescription(description);
        wxRewardDetail.setActFaceNum(Integer.valueOf(0));
        wxRewardDetail.setReturnType(Integer.valueOf(0));
        wxRewardDetail.setMerchantId(merchant.getId());
        wxRewardDetail.setServiceProviderId(merchant.getServiceProviderId());
        wxRewardDetailService.save(wxRewardDetail);
    }

    public SendredpackResponseBo find(String mchBillno, Integer type) {
        log.info("查询红包");
        TopConfig topConfig = topConfigRepository.findByServiceProviderIdAndDelFlag("1190507879240863744", CommonConstant.NORMAL_FLAG);

        //查询红包参数
        Map<String, String> repMap = findRepMap(mchBillno, topConfig.getWxMchId(), topConfig.getWxAppId(), topConfig.getWxAppKey());
        SendredpackResponseBo resp = null;
        try {
            // 发送请求
            resp = sendRedPack("https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo", repMap, topConfig);
        } catch (Exception e) {
            log.info("微信红包查询 ======== >" + e.getMessage());
            log.info("resp =========== >" + resp.toString());
        }
        if (type.equals(Integer.valueOf(1))) {
            WxRedPackDetail wxRedPackDetail = wxRedPackDetailService.getRepository().findByMchBillno(mchBillno);
            checkfindResp(resp, wxRedPackDetail, 1, null);
        } else {
            WxRewardDetail wxRewardDetail = wxRewardDetailService.getRepository().findByMchBillno(mchBillno);
            checkfindResp(resp, null, 2, wxRewardDetail);
        }
        return resp;
    }


}
