package com.wyl.demo.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * @author wyl19940929@163.com
 */
public final class ServletUtils {

    private static final String UNKNOWN = "unknown";

    private ServletUtils() {

    }

    /**
     * 获取客户端的ipv4地址
     *
     * @param exchange ServerWebExchange
     * @return ipv4地址
     */
    public static String getIpv4Address(final ServerWebExchange exchange) {
        final ServerHttpRequest request = exchange.getRequest();
        final HttpHeaders headers = request.getHeaders();

        String clientIP = headers.getFirst("X-Forwarded-For");

        if ((clientIP == null || clientIP.isEmpty()) || UNKNOWN.equalsIgnoreCase(clientIP)) {
            clientIP = headers.getFirst("X-Real-IP");
        }

        if ((clientIP == null || clientIP.isEmpty()) || UNKNOWN.equalsIgnoreCase(clientIP)) {
            clientIP = Objects.requireNonNull(request.getRemoteAddress()).getAddress().getHostAddress();
        }

        if (!(clientIP == null || clientIP.isEmpty()) && clientIP.contains(",")) {
            clientIP = clientIP.split(",")[0].trim();
        }

        // 排除 IPv6 地址
        if (isIPv6Address(clientIP)) {
            return null;
        }

        return clientIP;
    }

    /**
     * 根据请求获取ipv4地址
     *
     * @param request HttpServletRequest
     * @return ip地址
     */
    public static String getIpv4Address(final HttpServletRequest request) {
        final String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        String ip;
        for (final String header : headers) {
            ip = request.getHeader(header);
            final boolean unknown = (ip == null || ip.isEmpty()) || "unknown".equalsIgnoreCase(ip);
            if (!unknown) {
                return getMultistageReverseProxyIp(ip);
            }
        }
        ip = request.getRemoteAddr();
        return getMultistageReverseProxyIp(ip);
    }

    public static boolean isIPv6Address(final String address) {
        try {
            final InetAddress inetAddress = InetAddress.getByName(address);
            return inetAddress instanceof Inet6Address;
        } catch (final UnknownHostException e) {
            return false;
        }
    }

    private static String getMultistageReverseProxyIp(final String sourceIp) {
        String ip = sourceIp;
        // 多级反向代理检测
        if (ip != null && ip.indexOf(",") > 0) {
            final String[] ips = ip.trim().split(",");
            for (final String subIp : ips) {
                final boolean unknown = (subIp == null || subIp.isEmpty()) || UNKNOWN.equalsIgnoreCase(subIp);
                if (!unknown) {
                    ip = subIp;
                    break;
                }
            }
        }
        return ip;
    }
}
