package org.charlie.example.framework.interceptors.out.resttemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;


/**
 * To implement apache http client request interceptor for rest example.
 * <p>
 * Delete this class if it is not necessary.
 *
 * @author Charlie
 */
@Slf4j
public class ExampleClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {
        /*Session session = SessionContext.getSession();
        if (Objects.nonNull(session)) {
            String globalTraceId = session.getGlobalTraceId();
            if (Objects.nonNull(globalTraceId)) {
                HttpHeaders headers = request.getHeaders();
                headers.add(TemplateConstants.TRACE_ID_HEADER_KEY, globalTraceId);
            }
        }*/
        return execution.execute(request, bytes);
    }
}