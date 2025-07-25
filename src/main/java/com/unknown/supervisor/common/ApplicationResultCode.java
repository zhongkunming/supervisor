package com.unknown.supervisor.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhongkunming
 */
@Getter
@AllArgsConstructor
public enum ApplicationResultCode implements ResultCode {

    SUCCESS("0", null),
    PARAM_VERIFICATION_FAILED("80000", "参数校验未通过: {0}"),
    ERROR("99999", "系统未知异常，请联系管理员。"),
    ;


    private final String code;

    private final String msg;
}
