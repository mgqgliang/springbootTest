package com.example.springboottest.common;

import com.example.springboottest.common.commonstatic.CommonCode;
import com.example.springboottest.common.commonstatic.CommonMessage;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.util.List;

@Data
@MappedSuperclass
public class CommonResult<T> {

    public CommonResult(){}

    public CommonResult(Boolean status,int code, String message){
        this.status = status;
        this.code = code;
        this.messages = message;
    }
    public CommonResult(Boolean status,CommonCode code, CommonMessage message){
        this.status = status;
        this.code = code.value;
        this.messages = message.message;
    }


    public CommonResult(Boolean status,CommonCode code,CommonMessage message,T t){
        this.status = status;
        this.code = code.value;
        this.messages = message.message;
        this.result = t;
    }
    public CommonResult(Boolean status,CommonCode code,CommonMessage message,List<T> tList){
        this.status = status;
        this.code = code.value;
        this.messages = message.message;
        this.resultList = tList;
    }
    public CommonResult(Boolean status,CommonCode code,CommonMessage message,T t,List<T> tList){
        this.status = status;
        this.code = code.value;
        this.messages = message.message;
        this.result = t;
        this.resultList = tList;
    }
    private Boolean status;
    private Integer code;

    private String messages;

    private T result;

    private List<T> resultList;


}
