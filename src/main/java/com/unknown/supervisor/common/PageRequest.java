package com.unknown.supervisor.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhongkunming
 */
@Getter
@Setter
public abstract class PageRequest {

    @Min(value = 1, message = "每页条数最小为1")
    @NotNull(message = "每页条数不能为空")
    @Schema(description = "每页条数", defaultValue = "10")
    private Long size;

    @Min(value = 1, message = "页码最小为1")
    @NotNull(message = "页码不能为空")
    @Schema(description = "页码", defaultValue = "1")
    private Long current;

    public <T> IPage<T> toPage() {
        Page<T> page = new Page<>();
        page.setMaxLimit(1000L);
        page.setSize(size);
        page.setCurrent(current);
        return page;
    }
}
