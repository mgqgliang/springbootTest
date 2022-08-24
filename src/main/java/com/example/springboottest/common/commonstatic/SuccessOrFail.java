package com.example.springboottest.common.commonstatic;

public enum SuccessOrFail {

    SUCCESS(true),
    FAIL(false);
    SuccessOrFail(Boolean result){
        this.result = result;
    }
    public boolean result;
}
