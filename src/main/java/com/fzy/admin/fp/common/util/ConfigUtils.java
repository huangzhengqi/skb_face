package com.fzy.admin.fp.common.util;

import org.apache.commons.lang3.StringUtils;

public class ConfigUtils
{
    public static String getStringValue(String key) {
        try {
            return StringUtils.trim(SpringUtils.getApplicationContent().getEnvironment().getProperty(key));
        } catch (Exception e) {
            return "";
        }
    }








    public static String getStringValue(String key, String defaultValue) {
        try {
            String value = StringUtils.trim(SpringUtils.getApplicationContent().getEnvironment().getProperty(key));
            return StringUtils.isNotBlank(value) ? value : defaultValue;
        } catch (Exception e) {
            return "";
        }
    }







    public static Integer getIntegerValue(String key) {
        String value = getStringValue(key);
        return StringUtils.isBlank(value) ? null : Integer.valueOf(Integer.parseInt(value));
    }
}
