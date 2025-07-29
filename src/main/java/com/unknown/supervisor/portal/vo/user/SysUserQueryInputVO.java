package com.unknown.supervisor.portal.vo.user;

import com.unknown.supervisor.core.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户查询输入VO
 *
 * @author zhongkunming
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统用户查询输入信息")
public class SysUserQueryInputVO extends PageRequest {

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