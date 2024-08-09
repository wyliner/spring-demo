package com.wyl.demo.infrastructure.filter;

import com.alibaba.fastjson2.JSON;
import com.wyl.demo.infrastructure.Constants;
import com.wyl.demo.infrastructure.exception.AbstractException;
import com.wyl.demo.infrastructure.response.ResponseResult;
import com.wyl.demo.infrastructure.response.ServiceExceptionCode;
import com.wyl.demo.util.ServletUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author wyl19940929@163.com
 */
@Slf4j
@Component
public class AccessLogFilter extends OncePerRequestFilter {
    // Method URI HttpStatus duration Request Response Headers clientIp
    public static final String ACCESS_LOG_FORMAT = "ACCESS {} {} {} {}ms Request:{} Response:{} Headers:{} {}";

    @Override
    public void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                 final FilterChain filterChain) throws ServletException, IOException {
        // 请求开始时间
        final long start = System.currentTimeMillis();
        // 客户端ip
        final String clientIpAddress = ServletUtils.getIpv4Address(request);
        // trace id
        final String traceId = getTraceId(request);
        final String requestURI = request.getRequestURI();
        final String method = request.getMethod();
        final int status = response.getStatus();
        // 复制请求信息
        final ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        // 复制响应信息
        final ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        // 获取请求头信息
        final String headers = getHeaders(request);
        String requestContent;
        // 日志添加trace-id
        try (MDC.MDCCloseable ignored = MDC.putCloseable(Constants.TRACE_ID, traceId)) {
            filterChain.doFilter(requestWrapper, responseWrapper);
            requestContent = getRequestContent(requestWrapper);
            // 将完整的缓存正文内容复制到响应中
            responseWrapper.copyBodyToResponse();
            final String responseContent = getResponseContent(responseWrapper);
            log.info(ACCESS_LOG_FORMAT,
                    method,
                    requestURI,
                    status,
                    System.currentTimeMillis() - start,
                    requestContent,
                    responseContent,
                    headers,
                    clientIpAddress);
        } catch (final AbstractException abstractException) {
            log.error("AbstractException", abstractException);
            final ResponseResult<Object> responseResult = ResponseResult.error(abstractException);
            requestContent = getRequestContent(requestWrapper);
            write(response, abstractException.getResponseCode().getHttpStatus().value(), responseResult);
            log.error(ACCESS_LOG_FORMAT,
                    method,
                    requestURI,
                    status,
                    System.currentTimeMillis() - start,
                    requestContent,
                    JSON.toJSONString(responseResult),
                    headers,
                    clientIpAddress);
        } catch (final Exception e) {
            log.error("Exception", e);
            final ResponseResult<Object> responseResult = ResponseResult.error(ServiceExceptionCode.INTERNAL_SERVER_ERROR);
            requestContent = getRequestContent(requestWrapper);
            write(response, HttpStatus.INTERNAL_SERVER_ERROR.value(), responseResult);
            log.error(ACCESS_LOG_FORMAT,
                    method,
                    requestURI,
                    status,
                    System.currentTimeMillis() - start,
                    requestContent,
                    JSON.toJSONString(responseResult),
                    headers,
                    clientIpAddress);
        }
    }

    private static String getRequestContent(final ContentCachingRequestWrapper requestWrapper) {
        String requestContent = new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
        if (StringUtils.isBlank(requestContent)) {
            requestContent = JSON.toJSONString(requestWrapper.getParameterMap());
        }
        return removeWhitespace(requestContent);
    }

    private static String getResponseContent(final ContentCachingResponseWrapper requestWrapper) {
        String requestContent = new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
        return removeWhitespace(requestContent);
    }

    private static String getTraceId(final HttpServletRequest request) {
        String traceId = request.getHeader("Trace-ID");
        if (traceId == null) {
            final String uuid = UUID.randomUUID().toString();
            traceId = uuid.replace("-", "").substring(0, 16);
        }
        return traceId;
    }

    private static <T> void write(final HttpServletResponse response, final int status, final T body) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(body));
    }

    private static String getHeaders(final HttpServletRequest request) {
        final String clientId = request.getHeader("Client-ID");
        return String.format("{\"client-id\":\"%s\"}", clientId);
    }

    private static String removeWhitespace(final String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        try {
            final Object obj = JSON.parseObject(text, Object.class);
            return JSON.toJSONString(obj);
        } catch (final Exception exception) {
            return text;
        }
    }
}
