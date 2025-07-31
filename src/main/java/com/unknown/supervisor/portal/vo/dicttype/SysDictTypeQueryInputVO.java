package com.unknown.supervisor.portal.vo.dicttype;

import com.unknown.supervisor.core.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 查询字典类型VO
 *
 * @author zhongkunming
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "查询字典类型VO")
public class SysDictTypeQueryInputVO extends PageRequest {

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 字典编码
     */
    @Schema(description = "字典编码")
    private String code;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String name;

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    private String type;

    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "状态（0正常 1停用）")
    private String status;

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