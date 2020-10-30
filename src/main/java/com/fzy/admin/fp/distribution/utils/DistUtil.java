package com.fzy.admin.fp.distribution.utils;

import java.util.Date;

/**
 * @Author yy
 * @Date 2019-11-21 09:15:22
 * @Desp 分销管理的工具类
 **/
public class DistUtil {

    public static String  getParentInvite(String grade){
        String[] split = grade.split("/");
        return split[split.length-1];
    }

    public static double formatDouble(double d) {
        return (double)Math.round(d*100)/100;
    }

    //单号
    public static String orderNub() {
        String sNow="";
        Date date=new Date();
        sNow += date.getYear();
        sNow += date.getMonth()+1;
        sNow += date.getDate();
        sNow += date.getHours();
        sNow += date.getMinutes();
        sNow += date.getSeconds();
        sNow += date.getTime();
        return sNow;
    }
}
