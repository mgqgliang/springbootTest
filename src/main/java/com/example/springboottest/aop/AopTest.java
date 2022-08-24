package com.example.springboottest.aop;

//import java.util.HashMap;
//import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
//@Slf4j
public class AopTest {
    @Pointcut("execution(public * com.example.springboottest.controller..*.*(..))")
    public void webLog(){}
    @Before("webLog()")
    public void before(JoinPoint joinPrint){
//        Object[] params = joinPrint.getArgs();
        Object param = joinPrint.getTarget();
        System.out.println(param);
    }
//    @Around("webLog()")
//    public Object before(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//
//        String requestURI = request.getRequestURI();
//        String requestMethod = request.getMethod();
//        String remoteAddr = request.getRemoteAddr();
//
//        Long startTime = System.currentTimeMillis();// 步入时间戳
//        MethodSignature method = (MethodSignature) proceedingJoinPoint.getSignature();
//        Class<?> currentClass = proceedingJoinPoint.getTarget().getClass();
//        Object proceed = proceedingJoinPoint.proceed();
//        List<Object> allArgs = Arrays.asList(proceedingJoinPoint.getArgs());
//        List<Object> args = allArgs.stream().map(arg -> {
//            if (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)) {
//                return arg;
//            } else {
//                return null;
//            }
//        }).filter(arg -> arg != null).collect(Collectors.toList());
//
//        LiveLog liveLog = new LiveLog(requestURI, requestMethod, startTime,
//                getMethodArgumentTypeName(method), args, proceed != null ? proceed.toString() : "null",
//                (System.currentTimeMillis() - startTime) + "ms", currentClass.getName() + "." + method.getName(),
//                remoteAddr, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), method.getReturnType().getName());
//
//        log.info(JSONObject.toJSONString(liveLog));
//        return proceed;
//    }

//    private Map<String, String> getMethodArgumentTypeName(MethodSignature method) {
//        Map<String, String> map = new HashMap<>();
//        String[] argTypeNames = method.getParameterNames();
//        Class[] parameterTypes = method.getParameterTypes();
//        for (int i = 0; i < parameterTypes.length; i++) {
//            map.put(parameterTypes[i].getName(), argTypeNames[i]);
//        }
//        return map;
//    }
}
