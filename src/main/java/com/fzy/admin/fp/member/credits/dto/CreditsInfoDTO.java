package com.fzy.admin.fp.member.credits.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author lb
 * @date 2019/6/20 14:30
 * @Description pc积分账户dto
 */
@Data
public class CreditsInfoDTO {

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tradeTime;//交易时间

    private String creditsType;//积分类型

    private Integer tradeScores;//交易积分正负代表增减

    private Integer avaCredits;//交易后可用积分

    private String operator;//操作店铺

    public CreditsInfoDTO() {
    }

    public CreditsInfoDTO(Date tradeTime, String creditsType, Integer tradeScores, Integer avaCredits, String operator) {
        this.tradeTime = tradeTime;
        this.creditsType = creditsType;
        this.tradeScores = tradeScores;
        this.avaCredits = avaCredits;
        this.operator = operator;
    }
}
