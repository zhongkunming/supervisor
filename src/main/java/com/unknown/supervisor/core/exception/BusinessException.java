package com.unknown.supervisor.core.exception;

import com.unknown.supervisor.common.ResultCode;
import lombok.Getter;

/**
 * @author zhongkunming
 */
@Getter
public class BusinessException extends GlobalException {

    public BusinessException(ResultCode result, Object... objs) {
        super(result, objs);
    }

    public BusinessException(Exception e, ResultCode result, Object... objs) {
        super(e, result, objs);
    }
}
