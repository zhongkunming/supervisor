package com.unknown.supervisor.portal.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统用户DTO
 *
 * @author zhongkunming
 */
@Data
public class SysUserDTO {

    /**
     * 主键ID
     */
    private Long id;

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

    /**
     * 创建时间
     */
    private LocalDateTime createDt;

    /**
     * 更新操作员编号
     */
    private String updateOperNo;

    /**
     * 更新时间
     */
    private LocalDateTime updateDt;
}