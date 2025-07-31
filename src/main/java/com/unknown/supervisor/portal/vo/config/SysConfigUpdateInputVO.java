package com.unknown.supervisor.portal.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 更新参数配置VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "更新参数配置VO")
public class SysConfigUpdateInputVO {

    /**
     * 主键ID
     */
    @NotNull(message = "配置ID不能为空")
    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    /**
     * 参数键名
     */
    @NotBlank(message = "参数键名不能为空")
    @Schema(description = "参数键名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String key;

    /**
     * 参数名称
     */
    @NotBlank(message = "参数名称不能为空")
    @Schema(description = "参数名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    /**
     * 参数键值
     */
    @NotBlank(message = "参数键值不能为空")
    @Schema(description = "参数键值", requiredMode = Schema.RequiredMode.REQUIRED)
    private String value;

    /**
     * 系统内置（Y是 N否）
     */
    @Schema(description = "系统内置（Y是 N否）")
    private String type;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}