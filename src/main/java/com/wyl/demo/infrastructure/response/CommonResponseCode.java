package com.wyl.demo.infrastructure.response;


import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * 常用错误码表
 *
 * @author wyl19940929@163.com
 */
public enum CommonResponseCode implements ResponseCode {
    /**
     * 请求成功
     */
    SUCCESS("1", "Success", HttpStatus.OK),
    /**
     * 请求失败
     */
    FAILED("2", "Failed", HttpStatus.BAD_REQUEST),
    /**
     * 未知错误
     */
    ERROR("0", "Error", HttpStatus.INTERNAL_SERVER_ERROR);


    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    CommonResponseCode(final String code, final String message, final HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }


    public static CommonResponseCode forValue(final String code) {
        for (final CommonResponseCode responseCode : CommonResponseCode.values()) {
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
