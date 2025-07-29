package com.unknown.supervisor.portal.dto.user;

import lombok.Data;

/**
 * 系统用户创建DTO
 *
 * @author zhongkunming
 */
@Data
public class SysUserCreateInputDTO {

    /**
     * 操作员编号
     */
    private String operNo;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 状态（1-启用，0-禁用）
     */
    private Integer status;

    /**
     * 创建操作员编号
     */
    private String createOperNo;
}