package com.unknown.supervisor.framework.exception;

import com.unknown.supervisor.common.JsonResult;
import com.unknown.supervisor.common.ResultCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhongkunming
 */
@Getter
public class SystemException extends RuntimeException {

    private final ResultCode resultCode;

    private final Object[] objs;

    public SystemException(ResultCode resultCode, Object... objs) {
        this.resultCode = resultCode;
        this.objs = objs;
    }

    public SystemException(Exception e, ResultCode resultCode, Object... objs) {
        super(e);
        this.resultCode = resultCode;
        this.objs = objs;
    }


    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (StringUtils.isBlank(message)) {
            message = JsonResult.buildResult(resultCode, objs).getMsg();
        }
        return message;
    }
}
