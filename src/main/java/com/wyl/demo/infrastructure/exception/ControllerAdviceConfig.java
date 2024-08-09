package com.wyl.demo.infrastructure.exception;

import com.wyl.demo.infrastructure.response.ClientExceptionCode;
import com.wyl.demo.infrastructure.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author wyl19940929@163.com
 */
@RestControllerAdvice
@Slf4j
public class ControllerAdviceConfig {
    /**
     * 处理GET请求参数验证抛出的异常
     *
     * @param ex BindException 实例
     * @return 错误响应结果
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseResult<Object>> bindExceptionException(final BindException ex) {
        String errorMessage = ex.getFieldError() != null ? ex.getFieldError().getDefaultMessage() : null;
        log.error("bindExceptionException", ex);
        final ClientExceptionCode code = ClientExceptionCode.MISSING_PARAMETER;
        final ResponseResult<Object> result = ResponseResult.error(code, errorMessage);
        return ResponseEntity.status(code.getHttpStatus()).body(result);
    }

    /**
     * 响应状态码: 400
     *
     * @param exception ClientException
     * @return ResponseResult
     */
    @ExceptionHandler(ClientException.class)
    public ResponseEntity<Object> handleClientException(final ClientException exception) {
        log.warn("ClientException", exception);
        final ResponseResult<Object> result = ResponseResult.error(exception, exception.getClientShowMessage());
        return ResponseEntity.status(exception.getResponseCode().getHttpStatus()).body(result);
    }

    /**
     * 响应状态码: 500
     *
     * @param exception ServiceException
     * @return ResponseResult
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleServiceException(final ServiceException exception) {
        log.warn("ServiceException", exception);
        final ResponseResult<Object> result = ResponseResult.error(exception.getResponseCode(), exception.getMessage());
        return ResponseEntity.status(exception.getResponseCode().getHttpStatus()).body(result);
    }

    /**
     * 处理POST请求参数验证抛出的异常
     *
     * @param ex MethodArgumentNotValidException 实例
     * @return 错误响应结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseResult<Object>> methodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        // 没有全部输出
        final BindingResult bindingResult = ex.getBindingResult();
        var errorMessage = bindingResult.getFieldError() != null ? bindingResult.getFieldError().getDefaultMessage() : null;
        log.error("bindExceptionException", ex);
        final ResponseResult<Object> result = ResponseResult.error(ClientExceptionCode.INVALID_PARAMETER, errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
