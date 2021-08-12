package org.charlie.template.common.utils.io.http;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
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
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


/**
 * @author Charlie
 */
@Slf4j
public class HttpClientUtil {

    private final static String POST_HEADER_KEY = "Content-type";

    private final static String POST_HEADER_VALUE = "application/json; charset=utf-8";

    private final static String ENTITY_CHARSET = "UTF-8";

    private static final Charset CHAR_SET = Charset.forName("utf-8");

    private final static int HTTP_CLIENT_CONN_MANAGER_TTL = 60000;
    private final static int HTTP_CLIENT_MAX_CONN = 200;
    private final static int HTTP_CLIENT_MAX_PER_ROUTE = 100;
    private final static int HTTP_CLIENT_TIMEOUT = 1000; // ms
    private final static int HTTP_CLIENT_RETRY = 3;

    private static CloseableHttpClient httpClient;

    private static final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(HTTP_CLIENT_CONN_MANAGER_TTL,
            TimeUnit.MILLISECONDS);

    static {
        connManager.setMaxTotal(HTTP_CLIENT_MAX_CONN);
        connManager.setDefaultConnectionConfig(
                ConnectionConfig
                        .custom()
                        .setCharset(CHAR_SET)
                        .build()
        );
        connManager.setDefaultMaxPerRoute(HTTP_CLIENT_MAX_PER_ROUTE);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(HTTP_CLIENT_TIMEOUT) // 获取连接超时时间
                .setConnectTimeout(HTTP_CLIENT_TIMEOUT) // 请求超时时间
                .setSocketTimeout(HTTP_CLIENT_TIMEOUT) // 响应超时时间
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

        httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(retry)
                .setConnectionManager(connManager)
                .setConnectionManagerShared(true)
                .build();
    }

    public static void closeIdleAndExpiredConnections(int idleTimeout, TimeUnit timeUnit) {
        try {
            connManager.closeExpiredConnections();
            log.info(connManager.getTotalStats().toString());
            connManager.closeIdleConnections(idleTimeout, timeUnit);
        } catch (Throwable t) {
            log.error(ExceptionUtils.getMessage(t));
        }
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
                .setConnectionManagerShared(true)
                .build();

    }

    public static boolean checkUrl(String url) {
        // TODO: add implementation here
        return true;
    }

    private static String concatParamToUrl(String url, Map<String, Object> parameters) {
        StringBuffer fullUrlStringBuffer = new StringBuffer();
        fullUrlStringBuffer.append(url);
        if (parameters.size() > 0) {
            fullUrlStringBuffer.append("?");
        }
        int i = 0;
        for (Map.Entry<String, Object> param: parameters.entrySet()) {
            fullUrlStringBuffer.append(param.getKey());
            fullUrlStringBuffer.append("=");
            fullUrlStringBuffer.append(param.getValue());
            if (i < parameters.size() - 1) {
                fullUrlStringBuffer.append("&");
            }
        }
        return fullUrlStringBuffer.toString();
    }

    public static String request(String url, Method method, int timeout) throws JsonProcessingException {
        return request(url, Maps.newHashMap(), method, timeout);
    }

    /**
     *
     * @param url
     * @param parameters
     * @param method
     * @param timeout
     * @return
     * @throws JsonProcessingException
     */
    public static String request(String url, Map<String, Object> parameters, Method method, int timeout) throws JsonProcessingException {
        Preconditions.checkNotNull(url, "[url] is null.");
        Preconditions.checkState(checkUrl(url), String.format("[url:%s] is invalid.", url));

        Preconditions.checkNotNull(parameters, "[parameters] is null.");

        Preconditions.checkArgument(timeout > 0,
                String.format("Negative [timeout:%s] is illegal.", String.valueOf(timeout)));

        String responseString = null;
        if (Method.GET.equals(method)) {
            responseString = get(url, parameters, timeout);
        } else if (Method.POST.equals(method)) {
            responseString = post(url, parameters, timeout);
        } else if (Method.PUT.equals(method)) {
            // TODO: add implementation
        } else if (Method.PATCH.equals(method)) {
            // TODO: add implementation
        } else if (Method.DELETE.equals(method)) {
            // TODO: add implementation
        } else {
            // TODO: add implementation
        }
        return responseString;
    }

    private static String execute(HttpRequestBase requester, int timeout) {

        String responseString = null;
        CloseableHttpResponse response = null;
//        CloseableHttpClient httpClient = getHttpClient(timeout);

        try {
            response = httpClient.execute(requester);
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

            requester.releaseConnection();

            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("Cannot close response.");
                    log.error(ExceptionUtils.getMessage(e));
                }
            }
        }

        return responseString;
    }

    private static String post(String url, Map<String, Object> parameters, int timeout) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String paramJsonString = mapper.writeValueAsString(parameters);
        return post(url, paramJsonString, timeout);
    }

    private static String post(String url, String paramJsonString, int timeout) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(POST_HEADER_KEY, POST_HEADER_VALUE);
        httpPost.setEntity(new StringEntity(paramJsonString, ENTITY_CHARSET));
        return execute(httpPost, timeout);
    }

    private static String get(String url, Map<String, Object> parameters, int timeout) {
        String urlWithParam = concatParamToUrl(url, parameters);
        HttpGet httpGet = new HttpGet(urlWithParam);
        return execute(httpGet, timeout);
    }
}
