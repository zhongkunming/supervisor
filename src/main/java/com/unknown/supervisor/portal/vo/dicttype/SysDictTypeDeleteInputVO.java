package com.unknown.supervisor.portal.vo.dicttype;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 删除字典类型VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "删除字典类型VO")
public class SysDictTypeDeleteInputVO {

    /**
     * 字典类型ID列表
     */
    @NotEmpty(message = "字典类型ID列表不能为空")
    @Schema(description = "字典类型ID列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> ids;
}