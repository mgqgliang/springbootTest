package com.example.springboottest.common.commonstatic;

public enum CommonMessage {

    ERROR_MESSAGE("系统错误，请联系管理员"),
    SUCCESS_MESSAGE("操作成功"),

    ERROR_DELAY_NOCLASS("未指定执行延时任务的类"),

    ERROR_DELAY_NOMETHOD("未指定执行延时任务的方法"),

    ERROR_DELAY("延时任务设置失败"),

    EXPORT_NO_FILENAME("导出无文件名"),

    IMOPRT_NO_FILENAME("导入文件不存在"),
    ;
    CommonMessage(String message){
        this.message = message;
    }
    public String message;

}
