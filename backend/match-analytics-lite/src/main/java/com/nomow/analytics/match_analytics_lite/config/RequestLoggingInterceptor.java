package com.nomow.analytics.match_analytics_lite.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    // ANSI color codes
    private static final String RESET = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String method = request.getMethod();
        String path = request.getRequestURI();

        log.info("{}[REQUEST]{} {}{}{} {}",
                CYAN, RESET, GREEN, method, RESET, path);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String method = request.getMethod();
        String path = request.getRequestURI();
        int status = response.getStatus();

        String color = status >= 500 ? RED : status >= 400 ? YELLOW : GREEN;

        log.info("{}[RESPONSE]{} {}{}{} {} - Status: {}{}{}",
                CYAN, RESET, GREEN, method, RESET, path, color, status, RESET);
    }
}
