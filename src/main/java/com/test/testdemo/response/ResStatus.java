package com.test.testdemo.response;

/**
 * 描述：操作结果枚举
 */
public enum ResStatus {
    SUCCESS(200, "操作成功", Boolean.TRUE),
    BAD_REQUEST(400, "参数错误", Boolean.FALSE),
    UNAUTHORIZED(401, "登录超时", Boolean.FALSE),
    FAILED(500, "操作失败", Boolean.FALSE);

    private int code;
    private String msg;
    private boolean success;

    ResStatus(int code, String msg, boolean success) {
        this.code = code;
        this.msg = msg;
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return success;
    }
}
