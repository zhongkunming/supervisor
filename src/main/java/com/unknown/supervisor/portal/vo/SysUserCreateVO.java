package com.unknown.supervisor.portal.vo;

import lombok.Data;

/**
 * 创建用户VO
 *
 * @author zhongkunming
 */
@Data
public class SysUserCreateVO {
    /**
     * 操作员号
     */
    private String operNo;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户类型（00系统用户）
     */
    private String userType;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号状态（0正常 1停用）
     */
    private String status;

    /**
     * 备注
     */
    private String remark;
}