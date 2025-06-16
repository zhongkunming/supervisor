package com.unknown.supervisor.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhongkunming
 */
@Getter
@AllArgsConstructor
public enum ResultCodeBusiness implements ResultCode {

    PARAM_VALID_FAIL("80000", "参数校验位未通过: {0}"),
    USER_NOT_EXIST("81000", "用户不存在")
    ;


    private final String code;

    private final String msg;
}
