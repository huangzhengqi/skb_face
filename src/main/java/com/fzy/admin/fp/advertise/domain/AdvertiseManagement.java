package com.fzy.admin.fp.advertise.domain;

import com.fzy.admin.fp.common.spring.base.BaseEntity;
import com.fzy.admin.fp.common.validation.annotation.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author lb
 * @date 2019/7/1 15:16
 * @Description 广告管理
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "lysj_advertise_advertise_management")
public class AdvertiseManagement extends BaseEntity {

    @Getter
    @AllArgsConstructor
    public enum Type {
        CPC(1, "点击量"),
        CPM(2, "展示数"),
        CPV(3, "跳转量");
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
    public enum AppType {
        PC(1, "支付后广告"),
        APP(2, "app广告");
        private Integer code;
        private String message;
    }

    @NotNull(message = "广告名称不能为空")
    private String advertiseName;//广告名称

    private String imageId;//广告图片Id

    private String imageLink;//广告图片跳转链接

    private String remark;//广告说明

    @NotNull(message = "广告类型不能为空")
    private Integer type;//广告类型

    @NotNull(message = "使用情况不能为空")
    private Integer sourceType;//该类型是否使用中

    @NotNull(message = "终端类型不能为空")
    private Integer appType;//广告pc或者app类型 1支付后类型2app类型

}
