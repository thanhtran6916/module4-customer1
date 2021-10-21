package com.customer.loger;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHelper {
    @ExceptionHandler(Exception.class)
    public String notFound() {
        return "404";
    }
}
