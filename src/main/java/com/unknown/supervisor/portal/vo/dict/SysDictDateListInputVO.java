package com.unknown.supervisor.portal.vo.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author zhongkunming
 */
@Data
public class SysDictDateListInputVO {

    @Schema(description = "字典类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "字典类型不能为空")
    private String type;
}
