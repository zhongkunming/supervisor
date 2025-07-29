package com.unknown.supervisor.core.interceptor;

import com.unknown.supervisor.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author zhongkunming
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    @SuppressWarnings("NullableProblems")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = JwtUtils.getToken(request);
        boolean flag = JwtUtils.validateToken(token);
        if (flag) {
            // todo 验证token有效性
        }
        return flag;
    }
}
