package com.unknown.supervisor.common;

import lombok.Data;

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
