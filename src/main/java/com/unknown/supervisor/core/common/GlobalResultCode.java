package com.unknown.supervisor.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhongkunming
 */
@Getter
@AllArgsConstructor
public enum GlobalResultCode implements ResultCode {

    // 成功
    SUCCESS("200", "操作成功"),

    // 客户端错误 - 参数相关
    PARAM_INVALID("40001", "参数格式错误: {0}"),
    RESOURCE_NOT_FOUND("40002", "请求的资源不存在"),

    // 认证授权错误 - JWT相关（核心组件）
    JWT_TOKEN_ERROR("40101", "JWT令牌获取失败"),
    JWT_OPER_NO_ERROR("40102", "操作员号获取失败"),
    JWT_CLAIMS_ERROR("40103", "JWT声明信息获取失败"),

    // 服务器错误 5xxxx
    ERROR("50000", "系统内部错误，请联系管理员"),
    ;


    private final String code;

    private final String msg;
}
