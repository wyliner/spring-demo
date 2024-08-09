package com.wyl.demo.infrastructure.exception;


import com.wyl.demo.infrastructure.response.ClientExceptionCode;

/**
 * 用于客户端原因造成的异常访问,例如参数传递错误,错误信息返回给掉用方
 *
 * @author wyl19940929@163.com
 */
@SuppressWarnings("checkstyle:DesignForExtension")
public class ClientException extends AbstractException {
    private ClientException(final ClientExceptionCode responseCode, final String message, final String clientShowMessage) {
        super(responseCode, message, clientShowMessage);
    }
}
