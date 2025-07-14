package com.unknown.supervisor.module.portal.common;

import com.unknown.supervisor.common.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhongkunming
 */
@Getter
@AllArgsConstructor
public enum PortalResultCode implements ResultCode {

    USER_NOT_EXIST("61000", "用户不存在"),
    USERNAME_EXIST("61001", "用户名已存在");

    private final String code;

    private final String msg;
}
