package com.unknown.supervisor.utils;

import com.unknown.supervisor.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

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
}
