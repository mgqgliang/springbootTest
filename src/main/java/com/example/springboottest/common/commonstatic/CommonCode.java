package com.example.springboottest.common.commonstatic;


public enum CommonCode {

    CODE_1(1),
    /**
     * 系统正常返回
     */
    CODE_40001(40001),
    /**
     * 系统异常返回
     */
    CODE_50001(50001),
    /**
     * 输入参数错误
     */
    CODE_60001(60001);
    CommonCode(int value){
        this.value = value;
    }
    public int value;
}
