package com.unknown.supervisor.portal.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 参数配置VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "参数配置VO")
public class SysConfigVO {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 参数键名
     */
    @Schema(description = "参数键名")
    private String key;

    /**
     * 参数名称
     */
    @Schema(description = "参数名称")
    private String name;

    /**
     * 参数键值
     */
    @Schema(description = "参数键值")
    private String value;

    /**
     * 系统内置（Y是 N否）
     */
    @Schema(description = "系统内置（Y是 N否）")
    private String type;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createDt;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateDt;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}