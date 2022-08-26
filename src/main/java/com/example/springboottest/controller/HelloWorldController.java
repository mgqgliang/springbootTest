package com.example.springboottest.controller;

import com.example.springboottest.common.commonstatic.CommonCode;
import com.example.springboottest.common.commonstatic.CommonMessage;
import com.example.springboottest.common.CommonResult;
import com.example.springboottest.common.FailResultException;
import com.example.springboottest.common.commonstatic.SuccessOrFail;
import com.example.springboottest.common.delay.CommonDelay;
import com.example.springboottest.common.delay.Delay;
import com.example.springboottest.service.HelloWorldService;
import com.example.springboottest.util.SelectUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello/")
public class HelloWorldController {


    HelloWorldService helloWorldService;

    public HelloWorldController(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @GetMapping("/hello-world")
    public CommonResult<String> helloWorld(){
        CommonResult<String> result;
        try{
            String s = helloWorldService.helloWorld();
            result = new CommonResult<>(SuccessOrFail.SUCCESS.result,CommonCode.CODE_40001, CommonMessage.SUCCESS_MESSAGE,s);
        } catch(FailResultException e){
            result = new CommonResult<>(SuccessOrFail.FAIL.result,e.getCode(),e.getMessages());
        }
        return result;
    }

    @GetMapping("/delaySpring")
    public CommonResult<String> delaySpring(){
        String message = "HelloWorld";
        int code = 1;
        Object[] objs = new Object[]{message,code};
        CommonDelay<Boolean> springDelay = new CommonDelay<>(1,"helloWorldServiceImpl","testDelay",objs,null);
        Delay.queue.add(springDelay);
        return new CommonResult<String>(SuccessOrFail.FAIL.result,CommonCode.CODE_40001,CommonMessage.SUCCESS_MESSAGE);
    }
    @GetMapping("/delaySpring2")
    public CommonResult<String> delaySpring2(){
        Object[] objs = new Object[]{};
        CommonDelay<Boolean> springDelay = new CommonDelay<>(1,"helloWorldServiceImpl","getProjectFileList",objs,null);
        Delay.queue.add(springDelay);
        return new CommonResult<String>(SuccessOrFail.FAIL.result,CommonCode.CODE_40001,CommonMessage.SUCCESS_MESSAGE);
    }

    @GetMapping("/delayCommon")
    public CommonResult<String> delayCommon(){
        String message = "HelloWorld";
        Object[] objs = new Object[]{message};
        CommonDelay<SelectUtil> commonDelay = new CommonDelay<SelectUtil>(1,null,"testUtils",objs,new SelectUtil());
        Delay.queue.add(commonDelay);
        return new CommonResult<String>(SuccessOrFail.SUCCESS.result,CommonCode.CODE_40001,CommonMessage.SUCCESS_MESSAGE);
    }

    @GetMapping("/delayCommon2")
    public CommonResult<String> delayCommon2(){
        Object[] objs = new Object[]{};
        //java.lang.NoSuchMethodException: com.example.springboottest.util.SelectUtil.getProjectFileList()
        //因为getProjectFileList方法使用Spring自动装配，所以报错
        CommonDelay<SelectUtil> commonDelay = new CommonDelay<SelectUtil>(1,null,"getProjectFileList",objs,new SelectUtil());
        Delay.queue.add(commonDelay);
        return new CommonResult(SuccessOrFail.FAIL.result,CommonCode.CODE_40001,CommonMessage.SUCCESS_MESSAGE);
    }
}
