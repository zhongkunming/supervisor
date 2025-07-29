package com.unknown.supervisor.core.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 统一响应结果
 *
 * @author zhongkunming
 */
@Data
@Schema(description = "统一响应结果")
public class JsonResult<T> {

    @Schema(description = "响应码", example = "200")
    private String code;

    @Schema(description = "响应消息", example = "操作成功")
    private String msg;

    @Schema(description = "响应数据")
    private T data;

    private JsonResult() {
    }

    private JsonResult(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public static <T> JsonResult<T> success() {
        return new JsonResult<>(GlobalResultCode.SUCCESS);
    }

    public static <T> JsonResult<T> success(T data) {
        JsonResult<T> result = new JsonResult<>(GlobalResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static <T> JsonResult<T> buildResult(ResultCode resultCode, Object... objs) {
        JsonResult<T> result = new JsonResult<>(resultCode);
        result.setMsg(resultCode.conversionMessage(objs));
        return result;
    }
}
