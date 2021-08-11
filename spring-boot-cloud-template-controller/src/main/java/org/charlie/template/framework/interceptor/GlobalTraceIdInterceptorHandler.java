package org.charlie.template.framework.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.charlie.template.common.constants.TemplateConstants;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
public class GlobalTraceIdInterceptorHandler implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = request.getHeader(TemplateConstants.TRACE_ID_HEADER_KEY);
        log.info(String.format("Trace ID: %s", traceId));
        MDC.put(TemplateConstants.TRACE_ID_HEADER_KEY, String.valueOf(traceId));
        return true;
    }
}
