package com.fzy.admin.fp.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fzy.assist.wraps.LogWrap;
import com.fzy.assist.wraps.StringWrap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtils{

        private static final Logger LOGGER = LogWrap.getLogger(com.fzy.assist.wraps.RequestWrap.class);

        private static final String METHOD_GET = "GET";

        private static final String IP_UNKNOWN = "unknown";


        public static Map<String, Object> getRequestMap() { return getRequestMap(getRequest()); }



        public static Map<String, Object> getParameterMap() { return getParameterMap(getRequest()); }



        public static Map<String, Object> getBodyMap() { return getBodyMap(getRequest()); }



        public static String getIPAddress() { return getIPAddress(getRequest()); }



        public static String getContextPath() { return getContextPath(getRequest()); }







        public static String getFullPath(String relativePath) { return getFullPath(getRequest(), relativePath); }











        public static Map<String, Object> getRequestMap(HttpServletRequest request) {
            if ("GET".equals(request.getMethod().toUpperCase()))
                return getParameterMap(request);
            if (request.getContentLength() > 0) {
                return getBodyMap(request);
            }
            return getParameterMap(request);
        }








        public static Map<String, Object> getParameterMap(HttpServletRequest request) {
            Map<String, String[]> requestParamMap = request.getParameterMap();
            Map<String, Object> paramMap = new HashMap<String, Object>(requestParamMap.size());
            for (Map.Entry<String, String[]> entry : requestParamMap.entrySet()) {
                String key = (String)entry.getKey();
                String[] values = (String[])entry.getValue();
                String formatKey = key.replaceAll("\\[\\]", "");
                if (values.length > 1) {
                    paramMap.put(formatKey, Arrays.asList(values));
                } else {
                    paramMap.put(formatKey, values[0]);
                }
                LOGGER.debug("request parameter: {} = {}", new Object[] { formatKey, paramMap.get(formatKey) });
            }
            return paramMap;
        }








        public static Map<String, Object> getBodyMap(HttpServletRequest request) {
            StringBuffer sb = new StringBuffer();
            if (request.getContentLength() > 0) {
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(request.getInputStream());
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    inputStreamReader.close();

                    if (sb.length() > 0) {
                        return parseJSON2Map(JSONObject.parseObject(sb.toString()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new HashMap(0);
        }







        public static Map<String, Object> parseJSON2Map(JSONObject json) {
            Map<String, Object> map = new HashMap<String, Object>((json != null) ? json.size() : 0);
            if (json != null) {
                for (Object k : json.keySet()) {
                    Object o = json.get(k);
                    if (o instanceof JSONObject) {
                        map.put((String)k, parseJSON2Map((JSONObject)o)); continue;
                    }  if (o instanceof JSONArray) {
                        map.put((String)k, ((JSONArray)o).toArray()); continue;
                    }
                    map.put((String)k, o);
                }
            }

            return map;
        }








        public static String getIPAddress(HttpServletRequest request) {
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            return ip;
        }








        public static String getContextPath(HttpServletRequest request) {
            String contextPath = request.getContextPath();
            return StringWrap.endBy(contextPath, "/");
        }








        public static String getFullPath(HttpServletRequest request, String relativePath) {
            String contextPath = getContextPath(request);
            return StringUtils.isBlank(relativePath) ? contextPath : (contextPath + relativePath);
        }








        public static String getRealPath(String relativePath) {
            String realPath = StringWrap.endBy(com.fzy.assist.wraps.RequestWrap.class.getClassLoader().getResource("").getPath(), File.separator);
            return StringUtils.isBlank(relativePath) ? realPath : (realPath + relativePath);
        }







        public static HttpServletRequest getRequest() { return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); }








        public static HttpServletResponse getResponse() { return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse(); }

}
