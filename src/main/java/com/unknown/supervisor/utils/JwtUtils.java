package com.unknown.supervisor.utils;

import com.unknown.supervisor.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * JWT工具类
 * 用于生成、验证和解析JWT令牌
 *
 * @author zhongkunming
 * @since 1.0.0
 */
@Slf4j
public class JwtUtils {

    /**
     * 设置JWT配置
     */
    @Setter
    private static JwtConfig jwtConfig;

    /**
     * 私有构造函数，防止实例化
     */
    private JwtUtils() {
        // 工具类不应被实例化
    }

    /**
     * 获取JWT配置
     *
     * @return JWT配置对象
     */
    private static JwtConfig getJwtConfig() {
        if (jwtConfig == null) {
            throw new IllegalStateException("JwtConfig未初始化，请先调用setJwtConfig方法");
        }
        return jwtConfig;
    }

    /**
     * 获取签名密钥
     *
     * @return 签名密钥
     */
    private static SecretKey getSigningKey() {
        JwtConfig config = getJwtConfig();
        return Keys.hmacShaKeyFor(config.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成JWT令牌
     *
     * @param subject 主题（通常是用户ID或用户名）
     * @param claims  自定义声明
     * @return JWT令牌
     */
    public static String generateToken(String subject, Map<String, Object> claims) {
        Date now = new Date();

        JwtBuilder builder = Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .signWith(getSigningKey());

        if (claims != null && !claims.isEmpty()) {
            builder.claims(claims);
        }

        return builder.compact();
    }

    /**
     * 生成JWT令牌（无自定义声明）
     *
     * @param subject 主题（通常是用户ID或用户名）
     * @return JWT令牌
     */
    public static String generateToken(String subject) {
        return generateToken(subject, null);
    }

    /**
     * 从JWT令牌中获取主题
     *
     * @param token JWT令牌
     * @return 主题
     */
    public static String getSubjectFromToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.getSubject();
        } catch (Exception e) {
            log.error("获取JWT主题失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 从JWT令牌中获取声明
     *
     * @param token JWT令牌
     * @return 声明
     */
    public static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 验证JWT令牌
     *
     * @param token   JWT令牌
     * @param subject 期望的主题
     * @return 是否有效
     */
    public static boolean validateToken(String token, String subject) {
        try {
            String tokenSubject = getSubjectFromToken(token);
            return subject.equals(tokenSubject);
        } catch (Exception e) {
            log.error("验证JWT令牌失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 验证JWT令牌格式和签名
     *
     * @param token JWT令牌
     * @return 是否有效
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT令牌验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 从HttpServletRequest中获取JWT令牌
     *
     * @param request HTTP请求对象
     * @return JWT令牌，如果未找到则返回null
     */
    public static String getTokenFromRequest(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        JwtConfig config = getJwtConfig();
        String headerName = config.getHeaderName();
        String tokenPrefix = config.getTokenPrefix();
        String tokenParam = config.getTokenParam();

        // 首先尝试从请求头获取
        String authHeader = request.getHeader(headerName);
        if (StringUtils.isNotBlank(authHeader) && authHeader.startsWith(tokenPrefix)) {
            return authHeader.substring(tokenPrefix.length()).trim();
        }

        // 如果请求头中没有，尝试从请求参数获取
        String tokenFromParam = request.getParameter(tokenParam);
        if (StringUtils.isNotBlank(tokenFromParam)) {
            return tokenFromParam;
        }

        return null;
    }

    /**
     * 从当前请求上下文中获取JWT令牌
     *
     * @return JWT令牌，如果未找到则返回null
     */
    public static String getTokenFromContext() {
        HttpServletRequest request = getCurrentRequest();
        return getTokenFromRequest(request);
    }

    /**
     * 从HttpServletRequest中获取操作员信息（用户主题）
     *
     * @param request HTTP请求对象
     * @return 操作员信息，如果获取失败则返回null
     */
    public static String getOperatorFromRequest(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return getSubjectFromToken(token);
    }

    /**
     * 从当前请求上下文中获取操作员信息（用户主题）
     *
     * @return 操作员信息，如果获取失败则返回null
     */
    public static String getOperatorFromContext() {
        HttpServletRequest request = getCurrentRequest();
        return getOperatorFromRequest(request);
    }

    /**
     * 从HttpServletRequest中获取JWT声明
     *
     * @param request HTTP请求对象
     * @return JWT声明，如果获取失败则返回null
     */
    public static Claims getClaimsFromRequest(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        try {
            return getClaimsFromToken(token);
        } catch (Exception e) {
            log.error("从请求中获取JWT声明失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 从当前请求上下文中获取JWT声明
     *
     * @return JWT声明，如果获取失败则返回null
     */
    public static Claims getClaimsFromContext() {
        HttpServletRequest request = getCurrentRequest();
        return getClaimsFromRequest(request);
    }

    /**
     * 验证HttpServletRequest中的JWT令牌
     *
     * @param request HTTP请求对象
     * @param subject 期望的主题
     * @return 是否有效
     */
    public static boolean validateTokenFromRequest(HttpServletRequest request, String subject) {
        String token = getTokenFromRequest(request);
        if (StringUtils.isBlank(token)) {
            return false;
        }
        return validateToken(token, subject);
    }

    /**
     * 验证HttpServletRequest中的JWT令牌格式和签名
     *
     * @param request HTTP请求对象
     * @return 是否有效
     */
    public static boolean validateTokenFromRequest(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (StringUtils.isBlank(token)) {
            return false;
        }
        return validateToken(token);
    }

    /**
     * 验证当前请求上下文中的JWT令牌
     *
     * @param subject 期望的主题
     * @return 是否有效
     */
    public static boolean validateTokenFromContext(String subject) {
        HttpServletRequest request = getCurrentRequest();
        return validateTokenFromRequest(request, subject);
    }

    /**
     * 验证当前请求上下文中的JWT令牌格式和签名
     *
     * @return 是否有效
     */
    public static boolean validateTokenFromContext() {
        HttpServletRequest request = getCurrentRequest();
        return validateTokenFromRequest(request);
    }

    /**
     * 获取当前请求的HttpServletRequest对象
     *
     * @return HttpServletRequest对象，如果不在Web上下文中则返回null
     */
    private static HttpServletRequest getCurrentRequest() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return Objects.nonNull(attributes) ? attributes.getRequest() : null;
        } catch (Exception e) {
            log.debug("获取当前请求上下文失败: {}", e.getMessage());
            return null;
        }
    }
}
