package com.jike.demo.aspect;

import com.jike.demo.annotation.AvoidReCommit;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author qukun
 * @date 2019/6/11
 */
@Aspect
@Component
public class AvoidReCommitAspect {
    private static final Logger log = LoggerFactory.getLogger(AvoidReCommitAspect.class);
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @param point
     */
    @Around("@annotation(com.jike.demo.annotation.AvoidReCommit)")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        HttpServletRequest request  = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = request.getServerName();
        //获取注解
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        //目标类、方法、参数
        String className = method.getDeclaringClass().getName();
        String name = method.getName();
        Object[] args = point.getArgs();
        long hachCode = 0L;
        for (Object arg : args) {
            hachCode += arg.hashCode();
        }

        String ipKey = String.format("%s#%s",className,name);
        String key = String.format("%s_%d",ip,hachCode);
        log.info("ipKey={},hashCode={},key={}",ipKey,hachCode,key);
        AvoidReCommit avoidRepeatableCommit =  method.getAnnotation(AvoidReCommit.class);
        long timeout = avoidRepeatableCommit.timeout();
        if (timeout < 0){
            //过期时间5分钟
            timeout = 60*5;
        }
        Boolean isAbsent = redisTemplate.opsForValue().setIfAbsent(key, Strings.EMPTY);
        if (isAbsent != null && !isAbsent){
            return "请勿重复提交";
        }
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        //执行方法
        Object object = point.proceed();
        return object;
    }
}
