package com.unknown.supervisor.core.interceptor;

import com.unknown.supervisor.portal.entity.SysLog;
import com.unknown.supervisor.portal.service.SysLogService;
import com.unknown.supervisor.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author zhongkunming
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SysLogInterceptor implements HandlerInterceptor {

    private static volatile String serverIp;
    private final SysLogService sysLogService;

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            // 记录请求日志
            recordRequestLog(request, response);
        } catch (Exception e) {
            log.error("记录请求日志失败", e);
        }
    }

    /**
     * 记录请求日志
     */
    private void recordRequestLog(HttpServletRequest request, HttpServletResponse response) {
        SysLog sysLog = new SysLog();

        // 基本信息
        sysLog.setTransNo((String) request.getAttribute("requestId"));
        sysLog.setMethod(request.getMethod());
        sysLog.setUri(request.getRequestURI());
        sysLog.setQueryString(request.getQueryString());

        // 计算请求耗时
        Long startTime = (Long) request.getAttribute("startTime");
        if (startTime != null) {
            sysLog.setCost(System.currentTimeMillis() - startTime);
        }

        // 客户端信息
        sysLog.setClientIp(getClientIp(request));
        sysLog.setServerIp(getServerIp());
        sysLog.setUserAgent(request.getHeader("User-Agent"));

        // 操作员信息
        try {
            String operNo = JwtUtils.getOperNo(request);
            sysLog.setOperNo(operNo);
            sysLog.setCreateOperNo(operNo);
            sysLog.setUpdateOperNo(operNo);
        } catch (Exception e) {
            // 忽略JWT获取失败的情况
        }

        // 请求体和响应体
        if (request instanceof ContentCachingRequestWrapper wrapper) {
            byte[] content = wrapper.getContentAsByteArray();
            String contentType = wrapper.getContentType();
            if (content.length > 0 &&
                    !Strings.CS.containsAny(contentType, "multipart", "stream")) {
                String requestBody = new String(content, StandardCharsets.UTF_8);
                sysLog.setRequestBody(requestBody);
            }
        }

        if (response instanceof ContentCachingResponseWrapper wrapper) {
            byte[] content = wrapper.getContentAsByteArray();
            String contentType = wrapper.getContentType();
            if (content.length > 0 &&
                    !Strings.CS.containsAny(contentType, "multipart", "stream")) {
                String responseBody = new String(content, StandardCharsets.UTF_8);
                sysLog.setResponseBody(responseBody);
            }
        }

        // 异步保存日志
        sysLogService.saveAsync(sysLog);
    }

    /**
     * 获取客户端IP
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * 获取服务器IP
     */
    private String getServerIp() {
        if (Objects.nonNull(serverIp)) return serverIp;

        synchronized (SysLogInterceptor.class) {
            if (Objects.nonNull(serverIp)) return serverIp;
            try {
                serverIp = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception e) {
                log.warn("获取服务器IP失败，使用默认IP", e);
                serverIp = "127.0.0.1";
            }
            return serverIp;
        }
    }
}
