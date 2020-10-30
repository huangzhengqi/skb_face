package com.fzy.admin.fp.member.member.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author ：drj.
 * @Date  ：Created in 14:57 2019/6/11
 * @Description:
 **/
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_member_member_receive_card")
public class MemberReceiveCard extends BaseEntity {

    @ApiModelProperty("会员id")
    private String memberId;

    @ApiModelProperty("code序列号")
    private String userCardCode;

    @ApiModelProperty("领取场景值，用于领取渠道数据统计。可在生成二维码接口及添加Addcard接口中自定义该字段的字符串值。")
    private String outerStr;

}
