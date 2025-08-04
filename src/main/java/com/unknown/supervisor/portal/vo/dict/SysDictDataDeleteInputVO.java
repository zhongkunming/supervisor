package com.unknown.supervisor.portal.vo.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 删除字典数据VO
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "删除字典数据VO")
public class SysDictDataDeleteInputVO {

    /**
     * 字典数据ID列表
     */
    @NotEmpty(message = "字典数据ID列表不能为空")
    @Schema(description = "字典数据ID列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> ids;
}