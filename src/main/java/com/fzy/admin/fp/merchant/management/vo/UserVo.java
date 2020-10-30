package com.fzy.admin.fp.merchant.management.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @ Author ：drj.
 * @ Date  ：Created in 21:10 2019/5/4
 * @ Description:
 **/
@Data
public class UserVo {

    private String id;

    @Excel(name = "姓名",orderNum = "1",height = 20,width = 20)
    private String name;//姓名

    @Excel(name = "用户名",orderNum = "2",height = 20,width = 20)
    private String username;//用户名，登录账号，必填

    @Excel(name = "手机号",orderNum = "3",height = 20,width = 20)
    private String phone; // 手机号码

    @Excel(name = "性别",orderNum = "4",replace = {"男_1" ,"女_2"},height = 20,width = 20)
    private Integer sex; // 状态，1：男；2：女

    private String storeId; // 门店名称

    @Excel(name = "所属门店",orderNum = "5",height = 20,width = 20)
    private String storeName; // 门店名称

    @Excel(name = "角色",orderNum = "6",replace = {"商户_1","店长_2","员工_3"},height = 20,width = 20)
    private String userType; // 用户类型，1：商户；2：店长；3：员工

    @Excel(name = "状态",orderNum = "7",replace = {"启用_1","注销_2"},height = 20,width = 20)
    private Integer status; // 状态，1：启用；2：注销

    private String photoId;//图片

    public UserVo(String id, String name, String username, String phone, Integer sex, String storeId, String userType, Integer status, String photoId) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.sex = sex;
        this.storeId = storeId;
        this.userType = userType;
        this.status = status;
        this.photoId = photoId;
    }
}
