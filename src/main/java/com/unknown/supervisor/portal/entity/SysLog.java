package com.unknown.supervisor.portal.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统日志实体类
 *
 * @author zhongkunming
 */
@Data
@TableName("sys_log")
@Schema(description = "系统日志")
public class SysLog {

    @TableId(type = IdType.NONE)
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "请求ID")
    private String requestId;

    @Schema(description = "请求方法")
    private String method;

    @Schema(description = "请求URI")
    private String uri;

    @Schema(description = "查询参数")
    private String queryString;

    @Schema(description = "请求体")
    private String requestBody;

    @Schema(description = "响应体")
    private String responseBody;

    @Schema(description = "状态码")
    private Integer statusCode;

    @Schema(description = "请求持续时间(毫秒)")
    private Long duration;

    @Schema(description = "客户端IP")
    private String clientIp;

    @Schema(description = "服务器IP")
    private String serverIp;

    @Schema(description = "用户代理")
    private String userAgent;

    @Schema(description = "操作员编号")
    private String operNo;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建操作员编号")
    private String createOperNo;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createDt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新操作员编号")
    private String updateOperNo;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间")
    private LocalDateTime updateDt;
}