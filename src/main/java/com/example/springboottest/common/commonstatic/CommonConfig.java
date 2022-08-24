package com.example.springboottest.common.commonstatic;

public enum CommonConfig {

    WX_APP_ID("wxb96bd5d9e4b8c027"),
    WX_("操作成功"),

    ERROR_DELAY_NOCLASS("未指定执行延时任务的类"),

    ERROR_DELAY_NOMETHOD("未指定执行延时任务的方法"),

    ERROR_DELAY("延时任务设置失败"),
    ;
    CommonConfig(String message){
        this.message = message;
    }
    private String message;

    public String getMessage(){
        return this.message;
    }
}
