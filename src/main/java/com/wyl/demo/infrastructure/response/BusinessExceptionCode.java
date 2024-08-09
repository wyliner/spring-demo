package com.wyl.demo.infrastructure.response;

import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * 响应码: 正常业务逻辑错误导致的异常
 */
public enum BusinessExceptionCode implements ResponseCode {
    /**
     * 用户不存在
     */
    USER_NOT_EXIST("1201", "User not exist", HttpStatus.BAD_REQUEST);


    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    BusinessExceptionCode(final String code, final String message, final HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public static BusinessExceptionCode forValue(final String code) {
        for (final BusinessExceptionCode responseCode : BusinessExceptionCode.values()) {
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
