package com.example.springboottest.common;

import com.example.springboottest.common.commonstatic.CommonCode;
import com.example.springboottest.common.commonstatic.CommonMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class FailResultException extends Exception{
    public FailResultException(){};

    public FailResultException(CommonCode code, CommonMessage message){
        this.code = code.value;
        this.messages = message.message;
    }

    public FailResultException(CommonCode code, String message){
        this.code = code.value;
        this.messages = message;
    }
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String messages;
    /**
     * 错误次数
     */
    private Integer failTimes;
    /**
     * 做大错误次数 默认为5
     */
    private Integer maxFailTimes = 5;
}
