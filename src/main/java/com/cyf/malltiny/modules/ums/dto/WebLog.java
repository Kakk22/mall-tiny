package com.cyf.malltiny.modules.ums.dto;

import lombok.Getter;
import lombok.Setter;

/** Controller  日志封装类
 * @author by cyf
 * @date 2020/9/17.
 */
@Getter
@Setter
public class WebLog {
    /**
     * 操作描述
     */
    private String description;
    /**
     * 操作用户
     */
    private String username;
    /**
     * 操作时间
     */
    private Long startTime;
    /**
     * 消耗时间
     */
    private Integer spendTime;
    /**
     * 根路径
     */
    private String basePath;
    /**
     * URI
     */
    private String uri;
    /**
     * URL
     */
    private String url;
    /**
     * 请求类型
     */
    private String method;
    /**
     * 请求ip
     */
    private String ip;
    /**
     * 请求参数
     */
    private Object parameter;
    /**
     * 返回参数
     */
    private Object result;
}
