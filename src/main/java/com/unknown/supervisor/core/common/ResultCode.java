package com.unknown.supervisor.core.common;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * @author zhongkunming
 */
public interface ResultCode {

    String getCode();

    String getMsg();

    default String conversionMessage(Object... objs) {
        String msg = getMsg();
        if (Objects.nonNull(objs) && objs.length > 0) {
            return MessageFormat.format(msg, objs);
        }
        return msg;
    }
}
