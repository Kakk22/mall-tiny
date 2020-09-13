package com.cyf.malltiny.common.exception;

import com.cyf.malltiny.common.api.IErrorCode;

/** 断言处理类，用于抛出各种API异常
 * @author by cyf
 * @date 2020/9/13.
 */
public class Asserts {

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
