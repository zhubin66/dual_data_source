package com.test.testdemo.response;

import com.test.testdemo.base.util.PageBean;
import com.test.testdemo.util.Helpers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：定义返回实体
 */
public class ResEntity {

    /**
     * 查询一条数据的对象处理
     *
     * @param data 一条数据
     * @return
     */
    public static <T> Map<String, Object> success(T data) {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        ResStatus success = ResStatus.SUCCESS;
        jsonMap.put("success", success.isSuccess());
        jsonMap.put("code", success.getCode());
        jsonMap.put("msg", success.getMsg());
        jsonMap.put("data", data);
        return jsonMap;
    }

    /**
     * 查询集合数据的对象处理
     *
     * @param data 集合
     * @return
     */
    public static <T> Map<String, Object> success(List<T> data) {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        ResStatus success = ResStatus.SUCCESS;
        jsonMap.put("success", success.isSuccess());
        jsonMap.put("code", success.getCode());
        jsonMap.put("msg", success.getMsg());
        jsonMap.put("data", data == null ? new ArrayList<>() : data);
        return jsonMap;
    }

    /**
     * todo 分页查询的对象处理
     *
     * @param pb 分页
     * @return
     */
    public static Map<String, Object> success(PageBean pb) {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        ResStatus success = ResStatus.SUCCESS;
        jsonMap.put("success", success.isSuccess());
        jsonMap.put("code", success.getCode());
        jsonMap.put("msg", success.getMsg());
        jsonMap.put("page", pb.getPageNo());
        jsonMap.put("pageTotal", pb.getPageTotal());
        jsonMap.put("total", pb.getRowsCount());
        jsonMap.put("data", pb.getData());
        return jsonMap;
    }


    /**
     * 异常消息捕捉
     *
     * @param e
     * @return
     */
    public static Map<String, Object> failed(Exception e) {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        ResStatus failed = ResStatus.FAILED;
        jsonMap.put("success", failed.isSuccess());
        jsonMap.put("code", failed.getCode());
        jsonMap.put("msg", Helpers.isNotNullAndEmpty(e.getMessage()) ? e.getMessage() : failed.getMsg());
        jsonMap.put("data", "");
        return jsonMap;
    }

    /**
     * 异常消息捕捉
     *
     * @param e
     * @return
     */
    public static Map<String, Object> failed(Exception e, ResStatus status) {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        jsonMap.put("success", status.isSuccess());
        jsonMap.put("code", status.getCode());
        jsonMap.put("msg", Helpers.isNotNullAndEmpty(e.getMessage()) ? e.getMessage() : status.getMsg());
        jsonMap.put("data", "");
        return jsonMap;
    }
}
