package com.unknown.supervisor.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * 密码工具类
 * 提供密码加密和验证功能
 *
 * @author zhongkunming
 * @since 1.0.0
 */
public class PasswdUtils {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int DEFAULT_SALT_LENGTH = 16;

    /**
     * 生成默认长度的盐值
     *
     * @return 盐值
     */
    private static String generateSalt() {
        return generateSalt(DEFAULT_SALT_LENGTH);
    }

    /**
     * 生成指定长度的盐值
     *
     * @param length 盐值长度
     * @return 盐值
     */
    private static String generateSalt(int length) {
        byte[] salt = new byte[length];
        SECURE_RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * 使用盐值对密码进行哈希加密
     *
     * @param password 原始密码
     * @param salt     盐值
     * @return 加密后的密码
     */
    private static String hashPassword(String password, String salt) {
        if (StringUtils.isBlank(password) || StringUtils.isBlank(salt)) {
            throw new IllegalArgumentException("Password and salt cannot be blank");
        }
        return DigestUtils.sha256Hex(password + salt);
    }

    /**
     * 对密码进行加密，自动生成盐值
     *
     * @param password 原始密码
     * @return 格式为"盐值:加密密码"的字符串
     */
    public static String encryptPassword(String password) {
        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);
        return salt + ":" + hashedPassword;
    }

    /**
     * 验证密码是否正确
     *
     * @param password       原始密码
     * @param storedPassword 存储的加密密码（格式：盐值:加密密码）
     * @return 验证结果
     */
    public static boolean verifyPassword(String password, String storedPassword) {
        if (StringUtils.isBlank(password) || StringUtils.isBlank(storedPassword)) {
            return false;
        }

        String[] parts = storedPassword.split(":", 2);
        if (parts.length != 2) {
            return false;
        }

        String salt = parts[0];
        String hashedPassword = parts[1];

        return hashedPassword.equals(hashPassword(password, salt));
    }
}