package com.unknown.supervisor.portal.dto.user;

import com.unknown.supervisor.core.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户分页查询DTO
 *
 * @author zhongkunming
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserQueryInputDTO extends PageRequest {

    /**
     * 操作员编号
     */
    private String operNo;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 状态（1-启用，0-禁用）
     */
    private Integer status;
}