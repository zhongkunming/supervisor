package com.unknown.supervisor.core.common;

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
    JWT_TOKEN_NOT_FOUND("80001", "JWT令牌未找到"),
    JWT_TOKEN_INVALID("80002", "JWT令牌无效或已过期"),
    JWT_OPERATOR_NOT_FOUND("80003", "操作员信息未找到"),
    JWT_CLAIMS_NOT_FOUND("80004", "JWT声明信息未找到"),
    ERROR("99999", "系统未知异常，请联系管理员。"),
    ;


    private final String code;

    private final String msg;
}
