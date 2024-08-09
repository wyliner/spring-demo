package com.wyl.demo.infrastructure.exception;

/**
 * 异常接口
 *
 * @param <C> 错误吗类型
 * @author wyl19940929@163.com
 */

import com.wyl.demo.infrastructure.response.ResponseCode;

public abstract class AbstractException extends RuntimeException {
    private final ResponseCode responseCode;
    private final String message;
    private final String clientShowMessage;

    protected AbstractException(final ResponseCode responseCode, final String message, final String clientShowMessage) {
        this.responseCode = responseCode;
        this.message = message;
        this.clientShowMessage = clientShowMessage;
    }

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    public ResponseCode getResponseCode() {
        return this.responseCode;
    }

    /**
     * 获取错误消息
     *
     * @return 错误消息
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * 获取返回给客户端的错误消息
     *
     * @return 返回给客户端的错误消息
     */
    public String getClientShowMessage() {
        return this.clientShowMessage == null ? responseCode.getMessage() : this.clientShowMessage;
    }
}
