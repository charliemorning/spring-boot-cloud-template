package org.charlie.example.framework.interceptors.out.httpclient;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.charlie.example.framework.constants.Constants;
import org.charlie.example.framework.utils.bean.Session;
import org.charlie.example.framework.utils.bean.SessionContext;

import java.io.IOException;
import java.util.Objects;


/**
 * To add global trace id to request header.
 *
 * @author charlie
 */
public class GlobalTraceIdHttpRequestInterceptor implements HttpRequestInterceptor {
    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Session session = SessionContext.getSession();
        if (Objects.nonNull(session)) {
            String globalTraceId = session.getGlobalTraceId();
            if (Objects.nonNull(globalTraceId)) {
                httpRequest.setHeader(Constants.TRACE_ID_HEADER_KEY, globalTraceId);
            }
        }
    }
}
