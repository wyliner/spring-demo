package com.wyl.demo.infrastructure.response;

import org.springframework.http.HttpStatus;

/**
 * @author wyl19940929@163.com
 */
public interface ResponseCode {

    /**
     * 获取Http状态
     *
     * @return Http状态
     */
    HttpStatus getHttpStatus();

    /**
     * 获取响应状态吗
     *
     * @return 响应状态码
     */
    String getCode();

    /**
     * 获取消息内容
     *
     * @return 消息内容
     */
    String getMessage();
}
