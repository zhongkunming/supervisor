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

    // 用户相关错误码 41xxx
    USER_NOT_FOUND("41001", "用户不存在"),
    USER_ALREADY_EXISTS("41002", "用户已存在: {0}"),
    USER_STATUS_INVALID("41003", "用户状态无效"),
    USER_PASSWORD_ERROR("41004", "密码错误"),
    USER_DISABLED("41005", "用户已被停用"),

    // 角色相关错误码 42xxx
    ROLE_NOT_FOUND("42001", "角色不存在"),
    ROLE_ALREADY_EXISTS("42002", "角色已存在: {0}"),
    ROLE_IN_USE("42003", "角色正在使用中，无法删除"),
    ROLE_CODE_INVALID("42004", "角色编码无效"),

    // 菜单相关错误码 43xxx
    MENU_NOT_FOUND("43001", "菜单不存在"),
    MENU_ALREADY_EXISTS("43002", "菜单已存在: {0}"),
    MENU_HAS_CHILDREN("43003", "菜单存在子菜单，无法删除"),
    MENU_PARENT_NOT_FOUND("43004", "父菜单不存在"),

    // 字典相关错误码 44xxx
    DICT_TYPE_NOT_FOUND("44001", "字典类型不存在"),
    DICT_TYPE_ALREADY_EXISTS("44002", "字典类型已存在: {0}"),
    DICT_DATA_NOT_FOUND("44003", "字典数据不存在"),
    DICT_DATA_ALREADY_EXISTS("44004", "字典数据已存在: {0}"),
    DICT_TYPE_IN_USE("44005", "字典类型正在使用中，无法删除"),

    // 配置相关错误码 45xxx
    CONFIG_NOT_FOUND("45001", "配置不存在"),
    CONFIG_ALREADY_EXISTS("45002", "配置已存在: {0}"),
    CONFIG_KEY_INVALID("45003", "配置键名无效"),

    // 用户角色关联相关错误码 47xxx
    USER_ROLE_NOT_FOUND("47001", "用户角色关联不存在"),
    USER_ROLE_ALREADY_EXISTS("47002", "用户角色关联已存在"),

    // 角色菜单关联相关错误码 48xxx
    ROLE_MENU_NOT_FOUND("48001", "角色菜单关联不存在"),
    ROLE_MENU_ALREADY_EXISTS("48002", "角色菜单关联已存在"),

    // 权限相关错误码 46xxx
    LOGIN_FAILED("46001", "登录失败"),
    LOGIN_USER_NOT_FOUND("46002", "登录用户不存在"),
    LOGIN_PASSWORD_ERROR("46003", "用户名或密码错误"),
    LOGOUT_FAILED("46004", "退出登录失败"),
    PERMISSION_DENIED("46005", "权限不足"),
    TOKEN_EXPIRED("46006", "登录已过期，请重新登录"),
    ;

    private final String code;
    private final String msg;
}