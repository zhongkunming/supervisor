package com.unknown.supervisor.core.interceptor;

import com.unknown.supervisor.core.cache.CacheModule;
import com.unknown.supervisor.core.cache.CacheService;
import com.unknown.supervisor.core.common.GlobalResultCode;
import com.unknown.supervisor.core.exception.BusinessException;
import com.unknown.supervisor.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author zhongkunming
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final CacheService cacheService;

    @SuppressWarnings("NullableProblems")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = JwtUtils.getToken(request);
        if (!JwtUtils.validateToken(token))
            throw new BusinessException(GlobalResultCode.JWT_TOKEN_ERROR);

        String operNo = JwtUtils.getSubject(token);
        String operNoCache = cacheService.get(CacheModule.TOKEN, token);
        if (!Strings.CS.equals(operNo, operNoCache))
            throw new BusinessException(GlobalResultCode.JWT_OPER_NO_ERROR);

        return true;
    }
}
