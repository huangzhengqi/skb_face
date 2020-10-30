package com.fzy.admin.fp.merchant;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fzy.admin.fp.common.web.Resp;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: huxiangqiang
 * @since: 2019/8/9
 */
@Slf4j
public class YunlabaUtil {

    //微亮剑的Token 需要有第三方给
    public static final String TOKEN = "112711351234";

    /**
     * 绑定/解绑设备
     *
     * @param id  云音响id
     * @param m   0解绑 1绑定 4强制绑定
     * @param uid 用户账号id
     */
    public static Resp bind(String id, String m, String uid) {
        String url = "http://cloudspeaker.smartlinkall.com/bind.php?id={}&m={}&uid={}&token={}";
        url = StrFormatter.format(url, id, m, uid, TOKEN);
        String result = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        if (jsonObject.get("errcode").equals(0)) {
            return Resp.success(jsonObject.get("errcode"), jsonObject.get("errmsg").toString());
        } else {
            return new Resp().error(Resp.Status.PARAM_ERROR, jsonObject.get("errmsg").toString());
        }


    }



    /**
     * 语音推送
     *
     * @param id    设备id
     * @param price 价格 单位分
     * @param pt    模式 0 通用（ 播报自定义前缀）
     *              1 支付宝
     *              2 微信支付
     *              3 云支付
     *              4 余额支付
     *              5 微信储值
     *              6 微信买单
     *              7 银联刷卡
     *              8 会员卡消费
     *              9 会员卡充值
     *              10 翼支付
     *              11 退款
     *              12 支付宝退款
     *              13 微信退款
     *              14 银行卡退款
     *              15 银联退款
     *              16 工行 e 支付
     */
    public static Resp sendVoice(String id, String price, String pt, Integer suffix) {
        String url;
        if (suffix == 1) {
            url = "http://cloudspeaker.smartlinkall.com/add.php?id={}&price={}&pt={}&token={}&suffix=0";
        } else {
            url = "http://cloudspeaker.smartlinkall.com/add.php?id={}&price={}&pt={}&token={}";
        }

        url = StrFormatter.format(url, id, price, pt, TOKEN);
        String result = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        if (jsonObject.get("errcode").equals(0)) {
            return Resp.success(jsonObject.get("errcode"), jsonObject.get("errmsg").toString());
        } else {
            return new Resp().error(Resp.Status.PARAM_ERROR, jsonObject.get("errmsg").toString());
        }
    }


    /**
     * sp200-更改语音提示
     *
     * @param id    设备id
     * @param sound 语音内容
     * @param type  0 表示开机欢迎声音
     *              1 支付信息播报前缀
     *              2 支付信息播报后缀
     */
    public static Resp modify(String id, String sound, String type) {
        String url = "http://cloudspeaker.smartlinkall.com/modify_bootvoicewav.php?id={}&sound={}&type={}&token={}";
        url = StrFormatter.format(url, id, sound, type, TOKEN);
        String result = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        if (jsonObject.get("errcode").equals(0)) {
            return Resp.success(jsonObject.get("errcode"), jsonObject.get("errmsg").toString());
        } else {
            return new Resp().error(Resp.Status.PARAM_ERROR, jsonObject.get("errmsg").toString());
        }

    }

    public static void main(String[] args) {
        String url = "http://cloudspeaker.smartlinkall.com/add.php?id=20012070&price=100&token=109741423542&pt=0";
        String result = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        Resp resp = null;
        if (jsonObject.get("errcode").equals(0)) {
            resp = Resp.success(jsonObject.get("errcode"), jsonObject.get("errmsg").toString());
        } else {
            resp = new Resp().error(Resp.Status.PARAM_ERROR, jsonObject.get("errmsg").toString());
        }
        resp = null;

    }


}
