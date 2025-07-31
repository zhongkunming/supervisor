package com.unknown.supervisor.portal.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 获取参数配置VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "获取参数配置VO")
public class SysConfigGetInputVO {

    /**
     * 主键ID
     */
    @NotNull(message = "配置ID不能为空")
    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
}