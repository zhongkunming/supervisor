package com.unknown.supervisor.core.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

/**
 * 分页响应结果
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "分页响应结果")
public class PageResult<R> implements Serializable {

    @Serial
    private static final long serialVersionUID = -8586054880473556418L;

    @Schema(description = "总页数", example = "10")
    private Long pages;

    @Schema(description = "总条数", example = "100")
    private Long total;

    @Schema(description = "每页条数", example = "10")
    private Long size;

    @Schema(description = "当前页码", example = "1")
    private Long current;

    @Schema(description = "分页数据列表")
    private List<R> records;

    public static <R> PageResult<R> trans(IPage<R> page) {
        PageResult<R> result = new PageResult<>();
        result.setPages(page.getPages());
        result.setTotal(page.getTotal());
        result.setSize(page.getSize());
        result.setCurrent(page.getCurrent());
        result.setRecords(page.getRecords());
        return result;
    }

    public static <T, R> PageResult<T> trans(IPage<R> page, List<T> newList) {
        PageResult<T> result = new PageResult<>();
        result.setPages(page.getPages());
        result.setTotal(page.getTotal());
        result.setSize(page.getSize());
        result.setCurrent(page.getCurrent());
        result.setRecords(newList);
        return result;
    }

    public static <T, R> PageResult<T> trans(IPage<R> page, Function<R, T> function) {
        List<T> list = page.getRecords().stream()
                .map(function)
                .toList();
        PageResult<T> result = new PageResult<>();
        result.setPages(page.getPages());
        result.setTotal(page.getTotal());
        result.setSize(page.getSize());
        result.setCurrent(page.getCurrent());
        result.setRecords(list);
        return result;
    }

    public static <T, R> PageResult<T> trans(PageResult<R> page, List<T> newList) {
        PageResult<T> result = new PageResult<>();
        result.setPages(page.getPages());
        result.setTotal(page.getTotal());
        result.setSize(page.getSize());
        result.setCurrent(page.getCurrent());
        result.setRecords(newList);
        return result;
    }
}
