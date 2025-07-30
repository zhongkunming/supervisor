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

    // 角色相关错误 42xxx
    ROLE_NOT_FOUND("42001", "角色不存在"),
    ROLE_CODE_EXISTS("42002", "角色编码已存在"),

    // 菜单相关错误 43xxx
    MENU_NOT_FOUND("43001", "菜单不存在"),
    MENU_CODE_EXISTS("43002", "菜单编码已存在"),
    MENU_HAS_CHILDREN("43003", "菜单存在子菜单，无法删除"),
    ;

    private final String code;

    private final String msg;
}