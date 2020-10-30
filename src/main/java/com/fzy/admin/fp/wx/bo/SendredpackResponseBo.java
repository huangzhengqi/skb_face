package com.fzy.admin.fp.wx.bo;

/**
 * @Author hzq
 * @Date 2020/9/9 15:22
 * @Version 1.0
 * @description
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
public class SendredpackResponseBo {
    /**
     * 返回状态码 必填 String(16) SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
     */
    private String return_code;
    /**
     * 返回信息 非必填 String(128) 返回信息，如非空，为错误原因 签名失败 参数格式校验错误
     */
    private String return_msg;

    // 以下字段在return_code为SUCCESS的时候有返回

    /**
     * 签名 必填 String(32)
     */
    private String sign;
    /**
     * 业务结果 必填 String(16) SUCCESS/FAIL
     */
    private String result_code;
    /**
     * 错误代码 非必填 String(32) 错误码信息
     */
    private String err_code;
    /**
     * 错误代码描述 非必填 String(128) 结果信息描述
     */
    private String err_code_des;

    // 以下字段在return_code和result_code都为SUCCESS的时候有返回

    /**
     * 商户订单号 必填 String(28) 商户订单号（每个订单号必须唯一） 组成：mch_id+yyyymmdd+10位一天内不能重复的数字
     */
    private String mch_billno;
    /**
     * 商户号 必填 String(32) 微信支付分配的商户号
     */
    private String mch_id;

    /**
     * 公众账号appid 必填 String(32) 微信分配的公众账号ID（企业号corpid即为此appId）。
     * 接口传入的所有appid应该为公众号的appid
     * （在mp.weixin.qq.com申请的），不能为APP的appid（在open.weixin.qq.com申请的）。
     */
    private String wxappid;

    /**
     * 用户openid 必填 String(32) 接受红包的用户,用户在wxappid下的openid
     */
    private String re_openid;
    /**
     * 付款金额 必填 int 付款金额，单位分
     */
    private String total_amount;
    /**
     * 微信单号 必填 String(32) 红包订单的微信单号
     */
    private String send_listid;

    /**
     * 红包状态
     */
    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * return_code
     *
     * @return the return_code
     */
    public String getReturn_code() {
        return return_code;
    }

    /**
     * @param return_code the return_code to set
     */
    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    /**
     * return_msg
     *
     * @return the return_msg
     */
    public String getReturn_msg() {
        return return_msg;
    }

    /**
     * @param return_msg the return_msg to set
     */
    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    /**
     * sign
     *
     * @return the sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * @param sign the sign to set
     */
    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * result_code
     *
     * @return the result_code
     */
    public String getResult_code() {
        return result_code;
    }

    /**
     * @param result_code the result_code to set
     */
    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    /**
     * err_code
     *
     * @return the err_code
     */
    public String getErr_code() {
        return err_code;
    }

    /**
     * @param err_code the err_code to set
     */
    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    /**
     * err_code_des
     *
     * @return the err_code_des
     */
    public String getErr_code_des() {
        return err_code_des;
    }

    /**
     * @param err_code_des the err_code_des to set
     */
    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    /**
     * mch_billno
     *
     * @return the mch_billno
     */
    public String getMch_billno() {
        return mch_billno;
    }

    /**
     * @param mch_billno the mch_billno to set
     */
    public void setMch_billno(String mch_billno) {
        this.mch_billno = mch_billno;
    }

    /**
     * mch_id
     *
     * @return the mch_id
     */
    public String getMch_id() {
        return mch_id;
    }

    /**
     * @param mch_id the mch_id to set
     */
    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    /**
     * wxappid
     *
     * @return the wxappid
     */
    public String getWxappid() {
        return wxappid;
    }

    /**
     * @param wxappid the wxappid to set
     */
    public void setWxappid(String wxappid) {
        this.wxappid = wxappid;
    }

    /**
     * re_openid
     *
     * @return the re_openid
     */
    public String getRe_openid() {
        return re_openid;
    }

    /**
     * @param re_openid the re_openid to set
     */
    public void setRe_openid(String re_openid) {
        this.re_openid = re_openid;
    }

    /**
     * total_amount
     *
     * @return the total_amount
     */
    public String getTotal_amount() {
        return total_amount;
    }

    /**
     * @param total_amount the total_amount to set
     */
    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    /**
     * send_listid
     *
     * @return the send_listid
     */
    public String getSend_listid() {
        return send_listid;
    }

    /**
     * @param send_listid the send_listid to set
     */
    public void setSend_listid(String send_listid) {
        this.send_listid = send_listid;
    }

}
