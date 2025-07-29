package com.unknown.supervisor.core.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

/**
 * 分页请求基类
 *
 * @author zhongkunming
 */
@Getter
@Setter
@Schema(description = "分页请求基类")
public abstract class PageRequest {

    @Range(min = 1, message = "每页条数最小为1")
    @NotNull(message = "每页条数不能为空")
    @Schema(description = "每页条数", defaultValue = "10")
    private Long size;

    @Range(min = 1, message = "页码最小为1")
    @NotNull(message = "页码不能为空")
    @Schema(description = "页码", defaultValue = "1")
    private Long current;

    public <T> Page<T> toPage() {
        Page<T> page = new Page<>();
        page.setMaxLimit(1000L);
        page.setSize(size);
        page.setCurrent(current);
        return page;
    }
}
