package com.unknown.supervisor.portal.vo.dicttype;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 创建字典类型VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "创建字典类型VO")
public class SysDictTypeCreateInputVO {

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空")
    @Schema(description = "字典编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    @Schema(description = "字典名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    @Schema(description = "字典类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

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