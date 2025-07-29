package com.unknown.supervisor.portal.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统用户输出VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "系统用户输出信息")
public class SysUserVO {

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "操作员编号")
    private String operNo;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "状态（1-启用，0-禁用）")
    private Integer status;

    @Schema(description = "状态名称")
    private String statusName;

    @Schema(description = "创建时间")
    private LocalDateTime createDt;

    @Schema(description = "更新时间")
    private LocalDateTime updateDt;
}