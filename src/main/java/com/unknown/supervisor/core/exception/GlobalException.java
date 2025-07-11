package com.unknown.supervisor.core.exception;

import com.unknown.supervisor.common.ResultCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhongkunming
 */
@Getter
public class GlobalException extends RuntimeException {

    private final ResultCode resultCode;

    private final Object[] objs;

    public GlobalException(ResultCode resultCode, Object... objs) {
        this.resultCode = resultCode;
        this.objs = objs;
    }

    public GlobalException(Exception e, ResultCode resultCode, Object... objs) {
        super(e);
        this.resultCode = resultCode;
        this.objs = objs;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (StringUtils.isBlank(message)) {
            return this.resultCode.conversionMessage(objs);
        }
        return message;
    }
}
