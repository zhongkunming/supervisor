package com.unknown.supervisor.core.interceptor;

import com.unknown.supervisor.portal.entity.SysLog;
import com.unknown.supervisor.portal.service.SysLogService;
import com.unknown.supervisor.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.nio.charset.StandardCharsets;

/**
 * @author zhongkunming
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SysLogInterceptor implements HandlerInterceptor {

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
        sysLog.setRequestId((String) request.getAttribute("requestId"));
        sysLog.setMethod(request.getMethod());
        sysLog.setUri(request.getRequestURI());
        sysLog.setQueryString(request.getQueryString());
        sysLog.setStatusCode(response.getStatus());

        // 计算持续时间
        Long startTime = (Long) request.getAttribute("startTime");
        if (startTime != null) {
            sysLog.setDuration(System.currentTimeMillis() - startTime);
        }

        // 客户端信息
        sysLog.setClientIp(getClientIp(request));
        sysLog.setServerIp(getServerIp(request));
        sysLog.setUserAgent(request.getHeader("User-Agent"));

        // 操作员信息
        try {
            String operNo = JwtUtils.getOperNo(request);
            sysLog.setOperNo(operNo);
        } catch (Exception e) {
            // 忽略JWT获取失败的情况
        }

        // 请求体和响应体
        if (request instanceof ContentCachingRequestWrapper wrapper) {
            byte[] content = wrapper.getContentAsByteArray();
            if (content.length > 0) {
                String requestBody = new String(content, StandardCharsets.UTF_8);
                // 限制请求体长度
                if (requestBody.length() > 5000) {
                    requestBody = requestBody.substring(0, 5000) + "...";
                }
                sysLog.setRequestBody(requestBody);
            }
        }

        if (response instanceof ContentCachingResponseWrapper wrapper) {
            byte[] content = wrapper.getContentAsByteArray();
            if (content.length > 0) {
                String responseBody = new String(content, StandardCharsets.UTF_8);
                // 限制响应体长度
                if (responseBody.length() > 5000) {
                    responseBody = responseBody.substring(0, 5000) + "...";
                }
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
    private String getServerIp(HttpServletRequest request) {
        return request.getLocalAddr();
    }

}
