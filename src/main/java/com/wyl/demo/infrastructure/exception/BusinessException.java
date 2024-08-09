package com.wyl.demo.infrastructure.exception;


import com.wyl.demo.infrastructure.response.BusinessExceptionCode;

/**
 * 业务逻辑处理过程中的可预测异常,错误信息返回给掉用方
 *
 * @author wyl19940929@163.com
 */
@SuppressWarnings("checkstyle:DesignForExtension")
public class BusinessException extends AbstractException {

    public BusinessException(final BusinessExceptionCode responseCode, final String message, final String clientShowMessage) {
        super(responseCode, message, clientShowMessage);
    }

    public static BusinessException create(final BusinessExceptionCode responseCode, final String message, final String clientShowMessage) {
        return new BusinessException(responseCode, message, clientShowMessage);
    }

    public static BusinessException create(final BusinessExceptionCode responseCode, final String message) {
        return new BusinessException(responseCode, message, null);
    }

}
