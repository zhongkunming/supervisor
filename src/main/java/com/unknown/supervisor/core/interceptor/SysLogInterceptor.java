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

import java.nio.charset.StandardCharsets;

import static com.unknown.supervisor.core.filter.ContextConst.START_TIME;
import static com.unknown.supervisor.core.filter.ContextConst.TRANS_NO;

/**
 * 系统日志拦截器
 *
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
        sysLog.setTransNo((String) request.getAttribute(TRANS_NO));
        sysLog.setMethod(request.getMethod());
        sysLog.setUri(request.getRequestURI());
        sysLog.setQueryString(request.getQueryString());

        // 计算请求耗时
        Long startTime = (Long) request.getAttribute(START_TIME);
        if (startTime != null) {
            sysLog.setCost(System.currentTimeMillis() - startTime);
        }

        // 客户端信息
        sysLog.setClientIp(getClientIp(request));
        sysLog.setServerIp(request.getLocalAddr());
        sysLog.setUserAgent(request.getHeader("User-Agent"));

        // 操作员信息
        try {
            String operNo = JwtUtils.getOperNo(request);
            sysLog.setOperNo(operNo);
            sysLog.setCreateOperNo(operNo);
            sysLog.setUpdateOperNo(operNo);
        } catch (Exception e) {
            sysLog.setOperNo("anno");
            sysLog.setCreateOperNo("anno");
            sysLog.setUpdateOperNo("anno");
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
        sysLogService.saveLog(sysLog);
    }

    /**
     * 获取客户端IP地址
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 处理多个IP的情况，取第一个
        if (StringUtils.isNotBlank(ip) && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
