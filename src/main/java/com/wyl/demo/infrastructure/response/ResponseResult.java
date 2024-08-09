package com.wyl.demo.infrastructure.response;

import com.alibaba.fastjson2.annotation.JSONField;
import com.wyl.demo.infrastructure.Constants;
import com.wyl.demo.infrastructure.exception.AbstractException;
import org.slf4j.MDC;

/**
 * @author wyl19940929@163.com
 */
public final class ResponseResult<T> {

    private String code;
    private String message;
    private T data;
    @JSONField(name = "trace_id")
    private String traceId;
    private Long ts;

    public ResponseResult() {
    }

    private ResponseResult(final ResponseCode responseCode, final T data, final String message) {
        this.traceId = MDC.get(Constants.TRACE_ID);
        this.ts = System.currentTimeMillis();
        this.code = responseCode.getCode();
        this.data = data;
        this.message = message == null ? responseCode.getMessage() : message;
    }

    private ResponseResult(final ResponseCode responseCode, final String message) {
        this(responseCode, null, message);
    }

    private ResponseResult(final ResponseCode responseCode) {
        this(responseCode, null);
    }


    public static <T> ResponseResult<T> success(final ResponseCode responseCode, final T data) {
        return new ResponseResult<>(responseCode, data, null);
    }

    public static <T> ResponseResult<T> success(final T data) {
        return success(CommonResponseCode.SUCCESS, data);
    }

    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    public static <T> ResponseResult<T> error(final ResponseCode responseCode) {
        return new ResponseResult<>(responseCode, null);
    }


    public static <T> ResponseResult<T> error(final ResponseCode responseCode, final String message) {
        return new ResponseResult<>(responseCode, message);
    }

    public static <T> ResponseResult<T> error(final AbstractException exception, final String message) {
        return new ResponseResult<>(exception.getResponseCode(), message == null ? exception.getClientShowMessage() : message);
    }

    public static <T> ResponseResult<T> error(final AbstractException exception) {
        return new ResponseResult<>(exception.getResponseCode(), null);
    }

}
