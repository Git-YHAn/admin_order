package com.yang.server.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;


@Aspect
@Order(0)
public class ControllerMethodAspect {

    private final Logger logger = LoggerFactory.getLogger(ControllerMethodAspect.class);

    @Pointcut("@target(org.springframework.web.bind.annotation.RestController)")
    public void restController() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMethod() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMappingMethod() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMappingMethod() {
    }

    @Around(value = "restController() && (requestMethod() || postMappingMethod() || getMappingMethod()) && args(requestDtoBase,..)")
    public Object doValidate(ProceedingJoinPoint pjp) throws Throwable {
        return pjp.proceed();
    }

}