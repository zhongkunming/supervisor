package com.unknown.supervisor.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhongkunming
 */
@Getter
@AllArgsConstructor
public enum ResultCodeSystem implements ResultCode {

    SUCCESS("0", null),
    ERROR("99999", null),
    SYS_ERROR("99998", "系统异常"),
    ;


    private final String code;

    private final String msg;
}
