//package com.example.springboottest.controller;
//
//import com.example.springboottest.websocket.WebSocket;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//@Api(tags = "WebSocket测试")
//@RestController
//@RequestMapping("/HelloSocket")
//public class HelloWorldSocket {
//
//    @Resource
//    WebSocket webSocket;
//
//    @ApiOperation(value = "测试1")
//    @GetMapping("/test1")
//    public void testOne() throws InterruptedException {
//        String msg = "";
//        int a = 0;
//        for(int i = 0 ; i <= 100 ; i++){
//            msg = String.valueOf(a);
//            Thread.sleep(100);
//            webSocket.sendAllMessage(msg);
//            a++;
//        }
//    }
//}
