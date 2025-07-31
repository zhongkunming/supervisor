package com.unknown.supervisor.portal.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 删除参数配置VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "删除参数配置VO")
public class SysConfigDeleteInputVO {

    /**
     * 配置ID列表
     */
    @NotEmpty(message = "配置ID列表不能为空")
    @Schema(description = "配置ID列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> ids;
}