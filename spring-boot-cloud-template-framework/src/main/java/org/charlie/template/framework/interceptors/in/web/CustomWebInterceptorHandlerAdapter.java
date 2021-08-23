package org.charlie.template.framework.interceptors.in.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * To define interceptor.
 * <p>
 * Delete this class if it is not necessary.
 *
 * @author Charlie
 */
@Slf4j
public class CustomWebInterceptorHandlerAdapter extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        log.debug("HandlerInterceptorAdapter.postHandle");
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.debug("HandlerInterceptorAdapter.afterCompletion");
    }

    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("HandlerInterceptorAdapter.afterConcurrentHandlingStarted");
    }

}