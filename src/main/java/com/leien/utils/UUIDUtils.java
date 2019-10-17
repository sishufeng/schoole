package com.leien.utils;

import java.util.UUID;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/10/17 10:49
 * @Description: 生成UUID工具类
 * @ClassName: UUIDUtils
 */
public class UUIDUtils {
    /**
     * 生成不带 - 的UUID 全部大写的字符串
     * @return
     */
    public static String getUuid(){
        String uuid = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        return uuid;
    }
}
