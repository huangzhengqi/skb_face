package com.fzy.admin.fp.order.order.service;

import cn.hutool.core.date.DateTime;
import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import com.fzy.admin.fp.common.exception.BaseException;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.spring.pagination.PageUtil;
import com.fzy.admin.fp.common.spring.pagination.PageVo;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.auth.domain.BaiDuVoice;
import com.fzy.admin.fp.auth.service.BaiDuVoiceService;
import com.fzy.admin.fp.common.spring.base.BaseContent;
import com.fzy.admin.fp.common.web.ParamUtil;
import com.fzy.admin.fp.common.web.Resp;
import com.fzy.admin.fp.distribution.app.domain.DistWindow;
import com.fzy.admin.fp.order.order.domain.Order;
import com.fzy.admin.fp.order.order.vo.SpeechMsgVo;
import com.fzy.admin.fp.sdk.merchant.feign.StoreServiceFeign;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author ：drj.
 * @Date ：Created in 11:53 2019/6/19
 * @Description: 语音播报用于前段订单请求
 **/
@Slf4j
@Service
@Transactional
public class SpeechOrderService extends BaseContent {

    //设置APPID/AK/SK
    private static final String APP_ID = "16542730";
    private static final String API_KEY = "kqqRTTOzVHsP2EceOUzoDsra";
    private static final String SECRET_KEY = "196X1ku2gjVIgIHxMeoVNE58kEAyGvlm";

    @Value("${speech.filePath}")
    public String filePath;

    @Resource
    private BaiDuVoiceService baiDuVoiceService;

    @Resource
    private OrderService orderService;

    @Resource
    private StoreServiceFeign storeServiceFeign;


//    public Map<String, Object> speechInfos(String merchantId, String storeId, String beginTime, String serviceProviderId) {
//        Map<String, Object> speechMap = new HashMap<>();
//        Date beginDate;
//        if (ParamUtil.isBlank(beginTime)) {
//            beginDate = new Date();
//        } else {
//            beginDate = new Date(Long.valueOf(beginTime));
//        }
//        Date endDate = new Date(System.currentTimeMillis()*2);
//        List<SpeechMsgVo> speechMsgVos = new LinkedList<>();
//        //如果门店id为空,获取商户的默认门店id
//        if (ParamUtil.isBlank(storeId)) {
//            storeId = storeServiceFeign.findDefaultByMchid(merchantId).getStoreId();
//        }
//        //查询出语音播报的订单
//        List<Order> orderList = orderService.getRepository().
//                findTop5ByStoreIdAndStatusAndPayTimeBetweenOrderByPayTimeDesc(storeId, Order.Status.SUCCESSPAY.getCode(), beginDate, endDate);
//        //获取对应服务商的百度语音配置
//        BaiDuVoice baiDuVoice = baiDuVoiceService.getRepository().findByServiceProviderId(serviceProviderId);
//
//        for (Order order : orderList) {
//            SpeechMsgVo speechMsgVo = new SpeechMsgVo();
//            Map<String, String> map;
//            //获取语言文字跟语音
//            map = msgAndFileName(order);
//            speechMsgVo.setSpeechMsg(map.get("speechMsg"));
//            speechMsgVo.setSpeechVoice(map.get("fileName"));
//            speechMsgVos.add(speechMsgVo);
//            String fileName = map.get("fileName");
//            String path = filePath + fileName;
//            File filePathDir = new File(filePath);
//            if (!filePathDir.exists()) {
//                filePathDir.mkdirs();
//            }
//            File file = new File(path);
//            if (file.exists()) {
//                continue;
//            }
//            // 初始化一个AipSpeech,若百度语音未设置配置信息,读取默认配置
//            AipSpeech client = new AipSpeech(baiDuVoice.getAppId() == null ? APP_ID : baiDuVoice.getAppId(),
//                    baiDuVoice.getApiKey() == null ? API_KEY : baiDuVoice.getApiKey(), baiDuVoice.getSecretKey() == null ? SECRET_KEY : baiDuVoice.getSecretKey());
//            // 调用接口
//            TtsResponse res = client.synthesis(map.get("speechMessage"), "zh", 5, null);
//            byte[] data = res.getData();
//            if (data != null) {
//                try {
//                    Util.writeBytesToFileSystem(data, path);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        speechMap.put("speechMsgVos", speechMsgVos);
//        speechMap.put("beginTime", endDate.getTime());
//        return speechMap;
//    }
//
//
//    //语音到账提醒msg
//    private Map<String, String> msgAndFileName(Order order) {
//        Map<String, String> map = new HashMap<>();
//        StringBuilder speechMsg = new StringBuilder(); //语音播报内容
//        StringBuilder speechMessage = new StringBuilder(); //返回给前端的语音信息
//        StringBuilder fileName = new StringBuilder(); //文件名
//        if (Order.PayWay.WXPAY.getCode().equals(order.getPayWay())) {
//            speechMessage.append("微 信 ");
//            speechMsg.append("微信");
//            fileName.append("wx_");
//        }
//        if (Order.PayWay.ALIPAY.getCode().equals(order.getPayWay())) {
//            speechMsg.append("支付宝");
//            speechMessage.append("支付宝");
//            fileName.append("alipay_");
//        }
//
//        speechMsg.append("到账").append(order.getTotalPrice()).append("元");
//        speechMessage.append("到账").append(order.getTotalPrice()).append("元");
//        fileName.append("to_").append(order.getTotalPrice());
//        map.put("speechMsg", speechMsg.toString());
//        map.put("speechMessage", speechMessage.toString());
//        map.put("fileName", fileName + ".mp3");
//        return map;
//    }


    public Map<String, Object> speechInfos(String merchantId, String storeId, String beginTime, String serviceProviderId) {
        Date beginDate;
        Map<String, Object> speechMap = new HashMap<String, Object>();

        if (ParamUtil.isBlank(beginTime)) {
            beginDate = new Date();
        } else {
            beginDate = new Date(Long.valueOf(beginTime).longValue());
        }

        Date endDate = new Date(System.currentTimeMillis() * 1L);
        List<SpeechMsgVo> speechMsgVos = new LinkedList<SpeechMsgVo>();

        if (ParamUtil.isBlank(storeId)) {
            storeId = this.storeServiceFeign.findDefaultByMchid(merchantId).getStoreId();
        }

        //获取订单列表最近的一条数据
        PageVo pageVo = new PageVo();
        pageVo.setPageNumber(0);
        pageVo.setPageSize(1);
        pageVo.setPageSort("payTime");
        pageVo.setPageOrder("desc");
        Pageable pageable = PageUtil.initPage(pageVo);

        String finalStoreId = storeId;
        Specification<Order> specification = new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("serviceProviderId"), serviceProviderId));
                predicates.add(cb.equal(root.get("storeId"), finalStoreId));
                predicates.add(cb.equal(root.get("status"), Order.Status.SUCCESSPAY.getCode()));
                //日期查询
                predicates.add(cb.greaterThanOrEqualTo(root.get("payTime").as(Date.class), beginDate));
                predicates.add(cb.lessThanOrEqualTo(root.get("payTime").as(Date.class), endDate));
                Predicate[] pre = new Predicate[predicates.size()];
                Predicate Pre_And = cb.and(predicates.toArray(pre));
                return query.where(Pre_And).getRestriction();
            }
        };
        Page<Order> all = this.orderService.getRepository().findAll(specification, pageable);
        List<Order> orderList = all.getContent();
        //List<Order> orderList = this.orderService.getRepository().findTop5ByStoreIdAndStatusAndPayTimeBetweenOrderByPayTimeDesc(storeId, Order.Status.SUCCESSPAY.getCode(), beginDate, endDate);

        BaiDuVoice baiDuVoice = this.baiDuVoiceService.getRepository().findByServiceProviderId(serviceProviderId);

        for (Order order : orderList) {
            SpeechMsgVo speechMsgVo = new SpeechMsgVo();

            Map<String, String> map = msgAndFileName(order);
            speechMsgVo.setSpeechMsg((String) map.get("speechMsg"));
            speechMsgVo.setSpeechVoice((String) map.get("fileName"));
            speechMsgVos.add(speechMsgVo);
            String fileName = (String) map.get("fileName");
            String path = this.filePath + fileName;
            File filePathDir = new File(this.filePath);
            if (!filePathDir.exists()) {
                filePathDir.mkdirs();
            }
            File file = new File(path);
            if (file.exists()) {
                continue;
            }


            AipSpeech client = new AipSpeech((baiDuVoice == null || baiDuVoice.getAppId() == null) ? "16542730" : baiDuVoice.getAppId(), (baiDuVoice == null || baiDuVoice.getApiKey() == null) ? "kqqRTTOzVHsP2EceOUzoDsra" : baiDuVoice.getApiKey(), (baiDuVoice == null || baiDuVoice.getSecretKey() == null) ? "196X1ku2gjVIgIHxMeoVNE58kEAyGvlm" : baiDuVoice.getSecretKey());

            TtsResponse res = client.synthesis((String) map.get("speechMessage"), "zh", 5, null);
            byte[] data = res.getData();
            if (data != null) {
                try {
                    Util.writeBytesToFileSystem(data, path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        speechMap.put("speechMsgVos", speechMsgVos);
        speechMap.put("beginTime", Long.valueOf(endDate.getTime()));
        return speechMap;
    }


    private Map<String, String> msgAndFileName(Order order) {
        Map<String, String> map = new HashMap<String, String>();
        StringBuilder speechMsg = new StringBuilder();
        StringBuilder speechMessage = new StringBuilder();
        StringBuilder fileName = new StringBuilder();
        if (Order.PayWay.WXPAY.getCode().equals(order.getPayWay())) {
            speechMessage.append("微 信 ");
            speechMsg.append("微信");
            fileName.append("wx_");
        }
        if (Order.PayWay.ALIPAY.getCode().equals(order.getPayWay())) {
            speechMsg.append("支付宝");
            speechMessage.append("支付宝");
            fileName.append("alipay_");
        }

        speechMsg.append("到账").append(order.getTotalPrice()).append("元");
        speechMessage.append("到账").append(order.getTotalPrice()).append("元");
        fileName.append("to_").append(order.getTotalPrice());
        map.put("speechMsg", speechMsg.toString());
        map.put("speechMessage", speechMessage.toString());
        map.put("fileName", fileName + ".mp3");
        return map;
    }

    public Map<String, Object> trainingOrder(String serviceProviderId, String orderNumber) {
        Map<String, Object> speechMap = new HashMap<String, Object>();
        List<SpeechMsgVo> speechMsgVos = new LinkedList<SpeechMsgVo>();
        Order order = this.orderService.getRepository().findAllByOrderNumber(orderNumber);
        BaiDuVoice baiDuVoice = this.baiDuVoiceService.getRepository().findByServiceProviderId(serviceProviderId);
        SpeechMsgVo speechMsgVo = new SpeechMsgVo();

        Map<String, String> map = msgAndFileName(order);
        speechMsgVo.setSpeechMsg((String) map.get("speechMsg"));
        speechMsgVo.setSpeechVoice((String) map.get("fileName"));
        speechMsgVos.add(speechMsgVo);
        String fileName = (String) map.get("fileName");
        String path = this.filePath + fileName;
        File filePathDir = new File(this.filePath);
        if (!filePathDir.exists()) {
            filePathDir.mkdirs();
        }
        File file = new File(path);
//        if (file.exists()) {
//            continue;
//        }

        AipSpeech client = new AipSpeech((baiDuVoice == null || baiDuVoice.getAppId() == null) ? "16542730" : baiDuVoice.getAppId(), (baiDuVoice == null || baiDuVoice.getApiKey() == null) ? "kqqRTTOzVHsP2EceOUzoDsra" : baiDuVoice.getApiKey(), (baiDuVoice == null || baiDuVoice.getSecretKey() == null) ? "196X1ku2gjVIgIHxMeoVNE58kEAyGvlm" : baiDuVoice.getSecretKey());
        TtsResponse res = client.synthesis((String) map.get("speechMessage"), "zh", 5, null);
        byte[] data = res.getData();
        if (data != null) {
            try {
                Util.writeBytesToFileSystem(data, path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        speechMap.put("speechMsgVos", speechMsgVos);
        return speechMap;
    }


}
