package org.charlie.template.common.utility.http;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
public class HttpClientUtil {

    private final static String POST_HEADER_KEY = "Content-type";

    private final static String POST_HEADER_VALUE = "application/json; charset=utf-8";

    private final static String ENTITY_CHARSET = "UTF-8";

    private final static int HTTP_CLIENT_CONN_MANAGER_TTL = 60000;
    private final static int HTTP_CLIENT_MAX_CONN = 200;
    private final static int HTTP_CLIENT_MAX_PER_ROUTE = 5;
    private final static int HTTP_CLIENT_TIMEOUT = 1000; // ms
    private final static int HTTP_CLIENT_RETRY = 3;

    private static final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(HTTP_CLIENT_CONN_MANAGER_TTL,
            TimeUnit.MILLISECONDS);

    static {
        connManager.setMaxTotal(HTTP_CLIENT_MAX_CONN);
        connManager.setDefaultMaxPerRoute(HTTP_CLIENT_MAX_PER_ROUTE);
    }

    public static CloseableHttpClient getHttpClient(int timeout) {

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(timeout) // 获取连接超时时间
                .setConnectTimeout(timeout) // 请求超时时间
                .setSocketTimeout(timeout) // 响应超时时间
                .build();

        HttpRequestRetryHandler retry = (exception, executionCount, context) -> {
            if (executionCount >= HTTP_CLIENT_RETRY) {
                return false;
            }
            if (exception instanceof NoHttpResponseException) { // 如果服务器丢掉了连接
                return true;
            }
            if (exception instanceof SSLHandshakeException) { // SSL握手异常
                return false;
            }
            if (exception instanceof InterruptedIOException) { // 超时
                return true;
            }
            if (exception instanceof UnknownHostException) { // 目标服务器不可达
                return false;
            }
            if (exception instanceof SSLException) { // ssl握手异常
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等的，就再次尝试
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                return true;
            }
            return false;
        };
        return HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(retry)
                .setConnectionManager(connManager)
                .build();
    }

    public static String doPost(String url, Map<String, Object> parameters, int timeout) throws JsonProcessingException {

        Preconditions.checkNotNull(url, "[url] is null.");
        Preconditions.checkNotNull(parameters, "[parameters] is null.");
        Preconditions.checkArgument(timeout > 0, "Negative [timeout] is illegal.");

        ObjectMapper mapper = new ObjectMapper();
        String paramJsonString = mapper.writeValueAsString(parameters);
        return doPost(url, paramJsonString, timeout);
    }

    public static String doPost(String url, String paramJsonString, int timeout) {

        Preconditions.checkNotNull(url, "[url] is null.");
        Preconditions.checkNotNull(paramJsonString, "[parameters] is null.");

        HttpPost httpPost = new HttpPost(url);

        httpPost.setHeader(POST_HEADER_KEY, POST_HEADER_VALUE);
        httpPost.setEntity(new StringEntity(paramJsonString, ENTITY_CHARSET));

        String responseString = null;
        CloseableHttpResponse response = null;

        CloseableHttpClient httpClient = getHttpClient(timeout);

        try {
            response = httpClient.execute(httpPost);
            responseString = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            log.error(ExceptionUtils.getMessage(e));
            try {
                httpClient.close();
                httpClient = null;
            } catch (IOException e2) {
                log.error("Cannot close http client.");
                log.error(ExceptionUtils.getMessage(e2));
            }
        } finally {

            httpPost.releaseConnection();

            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("Cannot close response.");
                    log.error(ExceptionUtils.getMessage(e));
                }
            }

            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    log.error("Cannot close http client.");
                    log.error(ExceptionUtils.getMessage(e));
                }
            }
        }
        return responseString;
    }
}
