package com.wyl.demo.infrastructure.exception;


import com.wyl.demo.infrastructure.response.ClientExceptionCode;

/**
 * 用于服务的原因造成的异常访问,错误信息不可作为message返回给掉用方
 *
 * @author wyl19940929@163.com
 * @see org.springframework.http.HttpStatus#BAD_REQUEST
 */
@SuppressWarnings("checkstyle:DesignForExtension")
public class ServiceException extends AbstractException {

    private ServiceException(final ClientExceptionCode responseCode, final String message, final String clientShowMessage) {
        super(responseCode, message, clientShowMessage);
    }
}
