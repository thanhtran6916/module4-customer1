package com.customer.loger;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class Logger {

    @AfterThrowing(pointcut = "execution(public * com.customer.service.customer.CustomerService.getById(..)))")
    public void log() {
        System.out.println("khong tim thay customer");
    }
}
