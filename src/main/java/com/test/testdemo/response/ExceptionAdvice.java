package com.test.testdemo.response;

import com.test.testdemo.util.Helpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述：异常统一处理,能被spring加载扫描到即可
 */
@ResponseBody//响应json转换,没有回出现404
@ControllerAdvice//定义成全局的handler
public class ExceptionAdvice {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionAdvice.class);

    /**
     * 参数验证异常
     *
     * @return msg
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> validHandler(MethodArgumentNotValidException ve) {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        ResStatus failed = ResStatus.BAD_REQUEST;
        FieldError error = ve.getBindingResult().getFieldError();

        jsonMap.put("code", failed.getCode());
        jsonMap.put("success", failed.isSuccess());
        jsonMap.put("msg", error.getDefaultMessage());
        jsonMap.put("data", "");
        return jsonMap;
    }

    /**
     * sql异常
     *
     * @return msg
     */
    @ExceptionHandler(SQLException.class)
    public Map<String, Object> sqlHandler(SQLException se) {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        ResStatus failed = ResStatus.FAILED;
        LOG.error("code:" + se.getErrorCode() + "___" + "message:" + se.getMessage());

        jsonMap.put("code", failed.getCode());
        jsonMap.put("success", failed.isSuccess());
        jsonMap.put("msg", "请求错误,请联系管理员");
        jsonMap.put("data", "");
        return jsonMap;
    }

    /**
     * 通用异常
     *
     * @return msg
     */
    @ExceptionHandler(ResException.class)
    public Map<String, Object> resHandler(ResException res) {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        ResStatus failed = ResStatus.FAILED;

        jsonMap.put("code", failed.getCode());
        jsonMap.put("success", failed.isSuccess());
        jsonMap.put("msg", Helpers.isNotNullAndEmpty(res.getMessage()) ? res.getMessage() : failed.getMsg());
        jsonMap.put("data", "");
        return jsonMap;
    }
}
