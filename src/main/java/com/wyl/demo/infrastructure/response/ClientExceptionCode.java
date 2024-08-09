package com.wyl.demo.infrastructure.response;

import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * 客户端原因导致的异常响应码
 */
public enum ClientExceptionCode implements ResponseCode {
    /**
     * 丢失参数
     */
    MISSING_PARAMETER("1101", "Missing request params", HttpStatus.BAD_REQUEST),
    /**
     * 丢失请求头
     */
    MISSING_HEADERS("1102", "Missing request header parameter", HttpStatus.BAD_REQUEST),
    /**
     * 无效的参数
     */
    INVALID_PARAMETER("1103", "Invalid parameter", HttpStatus.BAD_REQUEST),
    /**
     * 无效的签名
     */
    INVALID_SIGNATURE("1104", "Invalid signature", HttpStatus.FORBIDDEN),
    /**
     * 未授权的操作
     */
    UNAUTHORIZED("1105", "Unauthorized access", HttpStatus.UNAUTHORIZED),
    /**
     * 请求超时
     */
    REQUEST_TIMEOUT("1106", "Request timed out", HttpStatus.REQUEST_TIMEOUT),
    /**
     * 请求次数过多
     */
    TOO_MANY_REQUESTS("1107", "Too many requests", HttpStatus.TOO_MANY_REQUESTS);


    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ClientExceptionCode(final String code, final String message, final HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public static ClientExceptionCode forValue(final String code) {
        for (final ClientExceptionCode responseCode : ClientExceptionCode.values()) {
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
