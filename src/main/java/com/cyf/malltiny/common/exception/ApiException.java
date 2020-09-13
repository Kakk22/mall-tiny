package com.cyf.malltiny.common.exception;

import com.cyf.malltiny.common.api.IErrorCode;
import lombok.Getter;
import lombok.Setter;

/**自定义API异常
 * @author by cyf
 * @date 2020/9/13.
 */
public class ApiException extends  RuntimeException {
    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
