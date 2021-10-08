package com.tianji.chain.utils;

/**
 * @description: 字符串处理工具类
 * @author: xionglin
 * @create: 2021-09-29 10:36
 */
public class StrUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isAllEmpty(String[] str) {
        if (str == null || str.length == 0) {
            return false;
        }
        for (int i = 0; i < str.length; i++) {
            if (isNotEmpty(str[i])) {
                return false;
            }
        }
        return true;
    }
}
