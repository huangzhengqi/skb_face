package com.fzy.admin.fp.common.web;

import cn.hutool.core.util.StrUtil;

import java.security.MessageDigest;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 参数工具类
 *
 * @author soul
 */
public class ParamUtil {

    /**
     * 判断一个集合对象是否为null或者为空集合
     *
     * @return
     */
    public static boolean isBlank(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    /**
     * 判断一个字符串是否为null或者为空
     *
     * @return
     */
    public static boolean isBlank(String string) {
        return string == null || string.trim().equals("");
    }

    /**
     * 判断数字是否为null或者为0
     *
     * @return
     */
    public static boolean isBlank(Number num) {
        return num == null;
    }

    /**
     * 判断一个long[]是否为null或者长度为0
     *
     * @return
     */
    public static boolean isBlank(Long[] ids) {
        return ids == null || ids.length == 0;
    }

    /**
     * 判断一个String[]是否为null或者长度为0
     *
     * @return
     */
    public static boolean isBlank(String[] strings) {
        return strings == null || strings.length == 0;
    }

    /**
     * 判断一个Object是否为null
     *
     * @return
     */
    public static boolean isBlank(Object obj) {
        return obj == null;
    }

    /**
     * 校验一个字符串是否为空指针或者空字符串并去前后空格
     *
     * @param string
     * @return 32位MD5摘要过后的字符串
     */
    public static String md5(String string) {
        if (string == null || string.trim().equals("")) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] data = digest.digest(string.getBytes("utf-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                String result = Integer.toHexString(data[i] & 0xff);
                String temp = null;
                if (result.length() == 1) {
                    temp = "0" + result;
                } else {
                    temp = result;
                }
                sb.append(temp);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成uuid
     *
     * @param
     * @return 32位的UUID字符串
     */
    public static String uuid() {
        return UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }

    /**
     * 从一个字符串中获取数字
     *
     * @param str
     * @return
     */
    public static String selectNum(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 驼峰法转下划线
     */
    public static String camel2Underline(String str) {
        if (StrUtil.isBlank(str)) {
            return "";
        }
        if (str.length() == 1) {
            return str.toLowerCase();
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                sb.append("_" + Character.toLowerCase(str.charAt(i)));
            } else {
                sb.append(str.charAt(i));
            }
        }
        return (str.charAt(0) + sb.toString()).toLowerCase();
    }

    /**
     * 首字母小写
     */
    public static String first2LowerCase(String str) {
        if (StrUtil.isBlank(str)) {
            return "";
        }
        if (str.length() == 1) {
            return str.toLowerCase();
        }
        StringBuffer sb = new StringBuffer();
        sb.append(Character.toLowerCase(str.charAt(0)));
        sb.append(str.substring(1, str.length()));
        return sb.toString();
    }

    /**
     * @author Created by zk on 2018/12/25 15:44
     * @Description 对字符串进行缩略
     */
    public static String omitStr(String str, int begin, int end) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, begin)).append("...").append(str.substring(str.length() - end, str.length()));
        return sb.toString();
    }


    /*
     * @author drj
     * @date 2019-04-28 14:43
     * @Description ：生成随机数包含数字，字母
     */
    public static String getItemID(int n) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            String str = random.nextInt(2) % 2 == 0 ? "num" : "char";
            if ("char".equalsIgnoreCase(str)) { // 产生字母
                int nextInt = random.nextInt(2) % 2 == 0 ? 65 : 97;
                // System.out.println(nextInt + "!!!!"); 1,0,1,1,1,0,0
                val += (char) (nextInt + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(str)) { // 产生数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

}
