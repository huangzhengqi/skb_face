package com.fzy.admin.fp.member.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by zk on 2019-06-03 10:41
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenIdAndKeyDTO {
    private String openId;

    /**
     * 微信的session_key
     */
    private String sessionKey;
}
