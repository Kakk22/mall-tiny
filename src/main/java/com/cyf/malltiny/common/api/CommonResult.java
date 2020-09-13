package com.cyf.malltiny.common.api;

import lombok.Getter;
import lombok.Setter;

/**通用返回对象
 * @author by cyf
 * @date 2020/9/13.
 */
@Getter
@Setter
public class CommonResult<T> {
    private long code;
    private String message;
    private T data;

    public CommonResult() {
    }

    public CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> success(T data){
        return new CommonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }
}
