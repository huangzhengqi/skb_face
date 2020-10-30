package com.fzy.admin.fp.pay.pay.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author Created by wtl on 2019-05-31 10:26
 * @description 统统收支付参数构建
 */
@Data
public class TtsPayDTO {

    private String privateKey; // 商户私钥
    private String publicKey; // 平台公钥

    private Map<String, Object> params; // 需要签名的参数
}
