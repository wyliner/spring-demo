package com.wyl.demo.infrastructure.response;

import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * 响应码: 服务端原因导致的异常
 */
public enum ServiceExceptionCode implements ResponseCode {
    /**
     * 内部服务错误
     */
    INTERNAL_SERVER_ERROR("1001", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);


    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ServiceExceptionCode(final String code, final String message, final HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public static ServiceExceptionCode forValue(final String code) {
        for (final ServiceExceptionCode responseCode : ServiceExceptionCode.values()) {
            if (Objects.equals(responseCode.code, code)) {
                return responseCode;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
