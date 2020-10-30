package com.fzy.admin.fp.advertise.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author lb
 * @date 2019/7/1 15:16
 * @Description 策略管理
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lysj_advertise_strategic_management")
public class StrategicManagement extends BaseEntity {

    @Getter
    @AllArgsConstructor
    public enum PayEntrys {
        WX_PAY(1, "微信"),
        ALI_PAY(2, "支付宝");
        private Integer code;
        private String message;
    }

    @Getter
    @AllArgsConstructor
    public enum Range {
        All(1, "全部商户"),
        SOME(2, "运渠");
        private Integer code;
        private String message;
    }

    @Getter
    @AllArgsConstructor
    public enum SourceType {
        USE(1, "正在使用中"),
        NO_USE(2, "未使用");
        private Integer code;
        private String message;
    }

    @Getter
    @AllArgsConstructor
    public enum RegionType {
        All(1, "全国"),
        SOME(2, "部分地区");
        private Integer code;
        private String message;
    }

    @Getter
    @AllArgsConstructor
    public enum TimeRange {
        All(1, "全部时段"),
        SOME(2, "部分时段");
        private Integer code;
        private String message;
    }

    @Getter
    @AllArgsConstructor
    public enum AppType {
        PC(1, "支付后广告"),
        APP(2, "app广告");
        private Integer code;
        private String message;
    }

    @NotNull(message = "策略名称不能为空")
    private String strategicName;//策略名称

    @NotNull(message = "商户范围需要指定")
    private Integer operatorRange;//商户范围

    @Transient
    private List<StrategicDistributors> distributors;//渠道商

    @NotNull(message = "时间范围需要指定")
    private Integer timeRange;//时间范围

    @NotNull(message = "时间天数需要指定")
    private String weekDay;//拼接天数

    @Transient
    private List<StrategicTime> strategicTimeList;//策略时间集合

    @NotNull(message = "地域范围需要指定")
    private Integer regionType;//地域范围

    @Transient
    private List<RegionCity> city;//城市

    @NotNull(message = "使用平台不能为空")
    private String payEntrys;//1 微信 2 支付宝  1,2拼接

    @NotNull(message = "使用状态不能为空")
    private Integer SourceType;//该策略是否在使用

    @NotNull(message = "终端类型不能为空")
    private Integer appType;//策略端类型pcapp

}
