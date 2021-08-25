package org.charlie.example.framework.interceptors.out.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.charlie.example.framework.constants.Constants;
import org.charlie.example.framework.entities.Session;
import org.charlie.example.framework.utils.bean.SessionContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class GlobalTraceIdRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        Session session = SessionContext.getSession();
        if (Objects.nonNull(session)) {
            String globalTraceId = session.getGlobalTraceId();
            if (Objects.nonNull(globalTraceId)) {
                template.header(Constants.TRACE_ID_HEADER_KEY, globalTraceId);
            }
        }
    }
}