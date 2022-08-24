package com.example.springboottest.common.delay;

import org.springframework.context.annotation.Configuration;

import java.util.concurrent.DelayQueue;

@Configuration
public class Delay {
    public static DelayQueue<CommonDelay> queue;

    static {
        queue = new DelayQueue<>();
    }
}
