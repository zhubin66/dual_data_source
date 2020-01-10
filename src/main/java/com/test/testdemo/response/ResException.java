package com.test.testdemo.response;

/**
 * 描述：异常类
 */
public class ResException extends RuntimeException {
    private final ResStatus status;

    public ResException(ResStatus status) {
        super(status.getMsg());
        this.status = status;
    }

    public ResException(String message, ResStatus status) {
        super(message);
        this.status = status;
    }

    public ResException(String message) {
        super(message);
        this.status = ResStatus.FAILED;
    }

    public ResStatus getStatus() {
        return status;
    }
}
