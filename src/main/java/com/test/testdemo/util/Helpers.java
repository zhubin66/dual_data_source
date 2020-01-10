package com.test.testdemo.util;

import com.test.testdemo.response.ResException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * 工具类
 */
public class Helpers {
    private static final String FILTER_SQL_INJECT = "'# and # exec # insert # select # delete # # update # count # * # % # chr # mid # master # truncate # char # declare #;# or #,";
    private static final String[] INJECT_STRING_ARRAY = "'# and # exec # insert # select # delete # # update # count # * # % # chr # mid # master # truncate # char # declare #;# or #,".split("#");

    public Helpers() {
    }

    public static boolean isNotEmptyAndZero(Object str) {
        return str != null && !str.toString().trim().equals("") && Integer.valueOf(str.toString().trim()) != 0;
    }

    public static boolean isNotNullAndEmpty(Object str) {
        return str != null && !str.toString().trim().equals("");
    }

    public static <T> T getToEntity(String str, TypeReference<T> typeReference) {
        ObjectMapper om = new ObjectMapper();
        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return om.readValue(str, typeReference);
        } catch (IOException ex) {
            throw new RuntimeException("解析出错");
        }
    }

    public static <T> T getToEntity(String str, T t) {
        ObjectMapper om = new ObjectMapper();
        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return (T) om.readValue(str, t.getClass());
        } catch (IOException ex) {
            throw new RuntimeException("解析出错");
        }
    }

    public static void requireNonNull(String message, Object... args) {
        boolean flag = Arrays.stream(args).anyMatch(Helpers::isNull);

        if (flag) {
            throw new ResException(message);
        }
    }

    private static boolean isNull(Object obj) {
        //普通非空
        if (obj == null)
            return true;

        //字符串
        if (obj instanceof String) {
            return obj.toString().trim().equals("");
        }

        //集合,大小不为空
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }

        //Map,键值对为为空
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        //不满足条件
        return false;
    }

    public static String filterSQLCondition(String condition) {
        if (condition != null && !condition.equals("")) {
            String[] var1 = INJECT_STRING_ARRAY;
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                String s = var1[var3];
                condition = condition.replace(s, "");
            }

            return condition;
        } else {
            return "";
        }
    }
}
