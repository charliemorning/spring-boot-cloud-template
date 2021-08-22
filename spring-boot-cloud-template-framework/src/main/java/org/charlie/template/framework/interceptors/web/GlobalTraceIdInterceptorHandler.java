package org.charlie.template.framework.interceptors.web;

import lombok.extern.slf4j.Slf4j;
import org.charlie.template.framework.constants.TemplateConstants;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * To take trace id from request and add it to logs.
 *
 * @author Charlie
 */
@Slf4j
public class GlobalTraceIdInterceptorHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = request.getHeader(TemplateConstants.TRACE_ID_HEADER_KEY);
        MDC.put(TemplateConstants.TRACE_ID_MDC_KEY, String.valueOf(traceId));
        log.debug(String.format("Trace ID: %s", traceId));
        return true;
    }
}
