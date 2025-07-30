package com.unknown.supervisor.core.filter;

import com.unknown.supervisor.utils.IdUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

import static com.unknown.supervisor.core.filter.ContextConst.START_TIME;
import static com.unknown.supervisor.core.filter.ContextConst.TRANS_NO;

/**
 * @author zhongkunming
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalRequestFilter extends OncePerRequestFilter {

    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 生成请求ID
        String transNo = IdUtils.nextIdStr();
        String uri = request.getRequestURI();
        log.info("uri: {}, transNo: {}", uri, transNo);
        request.setAttribute(TRANS_NO, transNo);

        // 设置请求开始时间
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);

        // 包装请求和响应对象以便读取请求体和响应体
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            chain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            // 记录请求完成信息，包括响应状态和处理时间
            long duration = System.currentTimeMillis() - startTime;
            log.info("uri: {}, transNo: {}, duration: {}ms", uri, transNo, duration);

            // 复制响应内容到原始响应
            wrappedResponse.copyBodyToResponse();
        }
    }
}
