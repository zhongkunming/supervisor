package com.unknown.supervisor.common;

import lombok.Data;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * @author zhongkunming
 */
@Data
public class JsonResult<T> {

    private String code;

    private String msg;

    private T data;

    private JsonResult() {
    }

    private JsonResult(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public static <T> JsonResult<T> success() {
        return new JsonResult<>(ResultCodeSystem.SUCCESS);
    }

    public static <T> JsonResult<T> success(T data) {
        JsonResult<T> result = new JsonResult<>(ResultCodeSystem.SUCCESS);
        result.setData(data);
        return result;
    }

    public static <T> JsonResult<T> buildResult(ResultCode resultCode, Object... objs) {
        JsonResult<T> result = new JsonResult<>(resultCode);
        if (Objects.nonNull(objs) && objs.length > 0) {
            result.setMsg(MessageFormat.format(resultCode.getMsg(), objs));
        }
        return result;
    }
}
