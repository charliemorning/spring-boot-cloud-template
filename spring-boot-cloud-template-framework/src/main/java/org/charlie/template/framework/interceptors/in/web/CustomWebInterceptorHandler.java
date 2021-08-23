package org.charlie.template.framework.interceptors.in.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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
public class CustomWebInterceptorHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("HandlerInterceptor.preHandle");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.debug("HandlerInterceptor.postHandle");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.debug("HandlerInterceptor.afterCompletion");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}