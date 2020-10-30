package com.fzy.admin.fp.common.util;

import java.util.UUID;

/*
 * @Author LZY
 * @Description UUID公共类
 * @Date 2020/7/11 2020/7/11
 * @Param
 * @return
 **/
public class UUIDUtil {

    public static String getUUID(){
      String uuid=  UUID.randomUUID().toString().replace("-", "");
      return uuid;
    }
}
