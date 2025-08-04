package com.unknown.supervisor.portal.vo.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 导出字典数据VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "导出字典数据VO")
public class SysDictDataExportInputVO {

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
     * 状态（0正常 1停用）
     */
    @Schema(description = "状态（0正常 1停用）")
    private String status;
}