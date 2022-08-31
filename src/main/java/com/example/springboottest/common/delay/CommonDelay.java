package com.example.springboottest.common.delay;

import lombok.Data;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟任务设置类
 * @param <T>
 */
@Data
public class CommonDelay<T> implements Delayed {

    public CommonDelay(){}

    /**
     * 创建延迟任务
     * @param times 定时时间（分钟）
     * @param className 调用方法所在的类名称 用于spring的bean中的方法调用
     * @param methodName 调用方法名称
     * @param args 调用方法参数集合
     * @param t 调用方法所在的类  用于非spring的bean中的方法调用
     * @implNote  t和className二选一，不能同时写两个参数，也不能两个都不写。有且只有一个
     */
    public CommonDelay(int times,String className,String methodName,Object[] args,T t){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE,times);
        this.times = times;
        this.expirationTime = cal.getTime();
        this.className = className;
        this.methodName = methodName;
        this.args = args;
        this.t = t;
    }

    /**
     * 延迟时间（分钟）
     */
    private int times;
    /**
     * 执行任务时间
     */
    private Date expirationTime;
    /**
     * 延迟调用方法参数集合
     */
    private Object[] args;
    /**
     * 延迟方法所在的类名
     */
    private  String className;
    /**
     * 延迟调用方法名称
     */
    private String methodName;
    /**
     * 错误次数
     */
    private int failTimes;
    /**
     * 最大错误次数
     */
    private int maxFailTimes = 5;
    /**
     * 延迟方法所在的类
     */
    private T t;
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expirationTime.getTime() - System.currentTimeMillis(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        CommonDelay commonDelay = (CommonDelay)o;
        long time = commonDelay.getExpirationTime().getTime();
        long time1 = this.getExpirationTime().getTime();
        return time == time1 ? 0 : time < time1 ? 1 : -1;
    }
}
