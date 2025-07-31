package com.unknown.supervisor.portal.vo.dicttype;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 获取字典类型VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "获取字典类型VO")
public class SysDictTypeGetInputVO {

    /**
     * 主键ID
     */
    @NotNull(message = "字典类型ID不能为空")
    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
}