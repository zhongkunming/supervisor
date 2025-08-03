package com.unknown.supervisor.portal.vo.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 更新字典类型VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "更新字典类型VO")
public class SysDictTypeUpdateInputVO {

    /**
     * 主键ID
     */
    @NotNull(message = "字典类型ID不能为空")
    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    @Schema(description = "字典类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    @Schema(description = "字典名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "状态（0正常 1停用）")
    private String status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}