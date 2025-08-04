package com.unknown.supervisor.portal.vo.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 更新字典数据VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "更新字典数据VO")
public class SysDictDataUpdateInputVO {

    /**
     * 主键ID
     */
    @NotNull(message = "字典数据ID不能为空")
    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    @Schema(description = "字典类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    /**
     * 字典标签
     */
    @NotBlank(message = "字典标签不能为空")
    @Schema(description = "字典标签", requiredMode = Schema.RequiredMode.REQUIRED)
    private String label;

    /**
     * 字典键值
     */
    @NotBlank(message = "字典键值不能为空")
    @Schema(description = "字典键值", requiredMode = Schema.RequiredMode.REQUIRED)
    private String value;

    /**
     * 样式属性（其他样式扩展）
     */
    @Schema(description = "样式属性（其他样式扩展）")
    private String cssClass;

    /**
     * 表格回显样式
     */
    @Schema(description = "表格回显样式")
    private String listClass;

    /**
     * 是否默认
     */
    @Schema(description = "是否默认")
    private Boolean isDefault;

    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "状态（0正常 1停用）")
    private String status;

    /**
     * 字典排序
     */
    @Schema(description = "字典排序")
    private Integer orderNum;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}