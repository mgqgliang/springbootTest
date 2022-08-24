package com.example.springboottest.common.delay;

import com.example.springboottest.common.commonstatic.CommonCode;
import com.example.springboottest.common.commonstatic.CommonMessage;
import com.example.springboottest.common.FailResultException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class TimeTask  implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        TimeTask.applicationContext = applicationContext;
    }

    /**
     * 获取applicationContext
     *
     * @return applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取Bean.
     *
     * @param name 类名
     * @return bean
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
           new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("===========延迟任务处理方法开始执行===========");
                    while (true){
                        CommonDelay take = null;
                        try{
                            take = Delay.queue.take();
                        } catch (InterruptedException e1){
                            e1.printStackTrace();
                        }
                        try{
                            String methodName = take.getMethodName();
                            if(StringUtils.isBlank(methodName)){
                                throw new FailResultException(CommonCode.CODE_50001,CommonMessage.ERROR_DELAY_NOMETHOD);
                            }
                            Object owner;
                            if(StringUtils.isNotEmpty(take.getClassName())){
                                owner = getBean(take.getClassName());
                            } else if(take.getT() != null){
                                owner = take.getT();
                            } else {
                                throw new FailResultException(CommonCode.CODE_50001, CommonMessage.ERROR_DELAY_NOCLASS);
                            }

                            Object[] args = take.getArgs();
                            Class[] classes;
                            if(args != null){
                                classes = new Class[args.length];
                                for (int i = 0 ; i < args.length; i++ ) {
                                    classes[i] = args[i].getClass();
                                }
                            }else{
                                classes = new Class[]{};
                            }
                            System.out.println();
                            invokeMethod(owner,classes,methodName,args);
                        }catch (Exception e){
                            System.out.println("==============方法执行失败==============" );
                            e.printStackTrace();
                            CommonDelay common = new CommonDelay(take.getTimes(),take.getClassName(),take.getMethodName(),take.getArgs(),take.getT());
                            Delay.queue.offer(common);
                        }
                    }
                }
            });
        }
    }

    private Object invokeMethod(Object owner,Class[] argsClass,String methodName,Object[] args) throws Exception {
        Class ownerClass = owner.getClass();
        Method method = ownerClass.getMethod(methodName,argsClass);
        Object objRtn = method.invoke(owner,args);
        return objRtn;
    }
}
