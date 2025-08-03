package com.unknown.supervisor.portal.vo.dictdata;

import com.unknown.supervisor.core.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询字典数据VO
 *
 * @author zhongkunming
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "查询字典数据VO")
public class SysDictDataQueryInputVO extends PageRequest {

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