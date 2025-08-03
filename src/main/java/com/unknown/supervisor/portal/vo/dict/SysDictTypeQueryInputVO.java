package com.unknown.supervisor.portal.vo.dict;

import com.unknown.supervisor.core.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

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
     * 字典类型
     */
    @Schema(description = "字典类型")
    private String type;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String name;

    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "状态（0正常 1停用）")
    private String status;

    /**
     * 创建时间-开始
     */
    @Schema(description = "创建时间开始")
    private LocalDate createDtBegin;

    /**
     * 创建时间-结束
     */
    @Schema(description = "创建时间结束")
    private LocalDate createDtEnd;
}