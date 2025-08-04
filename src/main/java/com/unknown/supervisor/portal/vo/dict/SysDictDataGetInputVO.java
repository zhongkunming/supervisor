package com.unknown.supervisor.portal.vo.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 获取字典数据VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "获取字典数据VO")
public class SysDictDataGetInputVO {

    /**
     * 主键ID
     */
    @NotNull(message = "字典数据ID不能为空")
    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
}