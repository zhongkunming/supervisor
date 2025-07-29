package com.unknown.supervisor.portal.common;

import com.unknown.supervisor.core.common.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Portal模块错误码
 *
 * @author zhongkunming
 */
@Getter
@AllArgsConstructor
public enum PortalResultCode implements ResultCode {

    // 用户相关错误 41xxx
    USER_NOT_FOUND("41001", "用户不存在"),
    USER_ALREADY_EXISTS("41002", "用户已存在"),
    USER_STATUS_INVALID("41003", "用户状态无效"),
    USER_PASSWORD_INCORRECT("41004", "用户密码错误"),
    USER_ACCOUNT_LOCKED("41005", "用户账户已锁定"),
    USER_ACCOUNT_EXPIRED("41006", "用户账户已过期"),
    USER_PERMISSION_DENIED("41007", "用户权限不足"),

    // 认证相关错误 42xxx
    AUTH_LOGIN_REQUIRED("42001", "请先登录"),
    AUTH_TOKEN_EXPIRED("42002", "登录已过期，请重新登录"),
    AUTH_TOKEN_INVALID("42003", "登录凭证无效"),
    AUTH_LOGOUT_FAILED("42004", "退出登录失败"),
    AUTH_REFRESH_TOKEN_INVALID("42005", "刷新令牌无效"),
    ;

    private final String code;

    private final String msg;
}