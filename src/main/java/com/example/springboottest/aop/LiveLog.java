package com.example.springboottest.aop;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class LiveLog {
    // 请求路径
    private String requestPath;
    // 请求方法
    private String requestMethod;
    // 当前的时间戳
    private Long currentTimeMills;
    // 参数类型
    private Map<String, String> argsType;
    // 所有入参
    private List<Object> allArgs;
    // 响应数据
    private String responseData;
    // 当前执行耗时
    private String executeTimeMills;
    // 当前请求的方法路径：（类路径 + 方法名字）
    private String classMethodLocation;
    // 请求的IP
    private String remoteAddr;
    // 当前日期（yyyy-MM-dd HH:mm:ss）
    private String nowTime;
    // 响应数据类型
    private String responseType;
}


