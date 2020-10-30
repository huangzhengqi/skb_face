package com.fzy.admin.fp.advertise.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author lb
 * @date 2019/7/1 15:18
 * @Description 投放管理
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lysj_advertise_on_management")
public class OnManagement extends BaseEntity {

    @Getter
    @AllArgsConstructor
    public enum AdvertiseType {
        CPC(1, "点击量"),
        CPM(2, "展示数"),
        CPV(3, "跳转量");
        private Integer code;
        private String message;
    }

    @Getter
    @AllArgsConstructor
    public enum Status {
        USE(1, "投放中"),
        NO_USE(2, "未投放"),
        OVER_USER(3, "结束投放");
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

    @NotNull(message = "投放名称必填")
    private String onName;//投放名称

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date startTime;//开始时间

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")//Date转换成String  一般后台传值给前台时用
    @DateTimeFormat(pattern = "yyyy-MM-dd")//将String转换成Date，一般前台给后台传值时用
    private Date endTime;//结束时间

    @NotNull(message = "广告Id必填")
    private String advertiseId;//广告Id

    @NotNull(message = "策略Id必填")
    private String strategicId;//策略Id

    @NotNull(message = "请指定投放金额")
    private Double money;//投放金额

    @Column(columnDefinition = "double(10,2)")
    private Double costMoney;//花费金额  预设

    private Integer showNumber;//展示数  预设

    private Integer clickNumber;//点击量  预设

    private Double clickRate;//点击率   预设

    private Integer runNumber;//跳转量  预设

    private Double clickCost;//点击或跳转单价

    private Double showCost;//千次展示花费

    private Double runCost;//跳转单价

    private String remark;//备注

    @NotNull(message = "请指定广告类型")
    private Integer advertiseType;//广告类型

    private Integer status;//投放状态  预设

    @NotNull(message = "请指定终端类型")
    private Integer appType;//pc app

    @Transient
    private AdvertiseManagement advertiseManagement;//投放顺带
    @Transient
    private StrategicManagement strategicManagement;

}
