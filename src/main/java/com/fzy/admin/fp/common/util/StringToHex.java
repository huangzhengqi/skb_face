package com.fzy.admin.fp.common.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author LZY
 * @Description
 * @Date 2020/7/11 2020/7/11
 **/
@Slf4j
public class StringToHex {
    /**
     * 字符串转化成为16进制字符串
     * @param s
     * @return
     */
    public static  Pattern PATTERN = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            if (s4.length() == 1) {
                s4 = '0' + s4;
            }
            str = str + s4;
        }
        return str;
    }


    /**
     * 将16进制字符串转换成汉字
     * @param str
     * @return
     */
    public static String deUnicode(String str) {
    byte[] bytes = new byte[str.length() / 2];
    byte tempByte = 0;
    byte tempHigh = 0;
    byte tempLow = 0;
        for (int i = 0, j = 0; i < str.length(); i += 2, j++) {
            tempByte = (byte) (((int) str.charAt(i)) & 0xff);
            if (tempByte >= 48 && tempByte <= 57) {
            tempHigh = (byte) ((tempByte - 48) << 4);
            } else if (tempByte >= 97 && tempByte <= 101) {
            tempHigh = (byte) ((tempByte - 97 + 10) << 4);
            }
            tempByte = (byte) (((int) str.charAt(i + 1)) & 0xff);
            if (tempByte >= 48 && tempByte <= 57) {
            tempLow = (byte) (tempByte - 48);
            } else if (tempByte >= 97 && tempByte <= 101) {
            tempLow = (byte) (tempByte - 97 + 10);
            }
            bytes[j] = (byte) (tempHigh | tempLow);
        }
        String result = null;
        try {
            result = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
        }
        return result;
    }

    /**
     * 字符串转换为UTF-8
     * @param str
     * @return
     */
    public static String toUtf8(String str) {
        try {
            return new String(str.getBytes("UTF-8"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.info("字符转换异常"+e.getMessage());
             return e.getMessage();
        }
    }

    /**
     * 把十六进制Unicode编码字符串转换为中文字符串
     */
    public static String unicodeToString(String str) {
        Matcher matcher = PATTERN.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

    /**
     * 16进制转换成为string类型字符串
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 将字符串转为16进制
     * @param str
     * @return
     */
    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return sb.toString().trim();
    }
}
