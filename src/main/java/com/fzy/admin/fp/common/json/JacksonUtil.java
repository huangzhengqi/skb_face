package com.fzy.admin.fp.common.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fzy.admin.fp.common.web.ParamUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Jackson工具
 *
 * @author Linyz
 * @data 2018年02月06日
 */
public class JacksonUtil {

    /**
     * 将一个对象转化为Json字符串
     *
     * @param object
     * @return String字符串
     */
    public static String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 将一个Json转化为对象
     *
     * @param json
     * @return String字符串
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将一个Json字符串转化为一个Map<String,String>
     *
     * @param json 字符串
     */
    public static Map<String, String> toStringMap(String json) {
        if (ParamUtil.isBlank(json)) {
            return Collections.emptyMap();
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<String, String>();
        try {
            map = mapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将一个Json字符串转化为一个Map<String,String>
     *
     * @param json 字符串
     */
    public static Map<String, Object> toObjectMap(String json) {
        if (ParamUtil.isBlank(json)) {
            return Collections.emptyMap();
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map = mapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * @author Created by zk on 2018/12/25 15:46
     * @Description
     */
    public static <T> T toObj(String jsonStr, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Object> toObjectMap(Object object) {
        return toObjectMap(toJson(object));
    }

}
