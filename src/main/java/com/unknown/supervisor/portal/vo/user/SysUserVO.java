package com.unknown.supervisor.portal.vo.user;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户VO
 *
 * @author zhongkunming
 */
@Data
public class SysUserVO {

    /**
     * 主键ID
     */
    private Long id;

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
     * 账号状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志
     */
    private Boolean isDelete;

    /**
     * 密码最后更新时间
     */
    private LocalDateTime pwdUpdateDate;

    /**
     * 创建时间
     */
    private LocalDateTime createDt;

    /**
     * 更新时间
     */
    private LocalDateTime updateDt;

    /**
     * 备注
     */
    private String remark;
}