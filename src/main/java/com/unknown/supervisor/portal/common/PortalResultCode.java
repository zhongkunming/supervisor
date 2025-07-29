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
    USER_PASSWORD_INCORRECT("41003", "密码错误"),
    USER_ACCOUNT_LOCKED("41004", "用户账户已锁定"),
    ;

    private final String code;

    private final String msg;
}