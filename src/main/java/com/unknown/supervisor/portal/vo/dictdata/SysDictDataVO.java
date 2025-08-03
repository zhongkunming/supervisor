package com.unknown.supervisor.portal.vo.dictdata;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字典数据VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "字典数据VO")
public class SysDictDataVO {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    private String type;

    /**
     * 字典标签
     */
    @Schema(description = "字典标签")
    private String label;

    /**
     * 字典键值
     */
    @Schema(description = "字典键值")
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