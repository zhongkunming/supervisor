package com.unknown.supervisor.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhongkunming
 */
@Getter
@AllArgsConstructor
public enum GlobalResultCode implements ResultCode {

    SUCCESS("0", null),
    PARAM_VERIFICATION_FAILED("80000", "参数校验未通过: {0}"),
    ERROR("99999", null),
    SYS_ERROR("99998", "系统异常"),
    ;


    private final String code;

    private final String msg;
}
