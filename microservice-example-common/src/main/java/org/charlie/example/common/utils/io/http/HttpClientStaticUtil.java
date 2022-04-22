package org.charlie.example.common.utils.io.http;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHeaders;
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
import org.apache.http.pool.PoolStats;
import org.apache.http.util.EntityUtils;
import org.charlie.example.common.constants.io.http.HttpConstants;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * To implement simple http method in pooling connection manager.
 *
 * @author Charlie
 */
@Slf4j
public class HttpClientStaticUtil {

    private static CloseableHttpClient httpClient;

    private static final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
            HttpConstants.HTTP_CLIENT_CONN_MANAGER_TTL, TimeUnit.MILLISECONDS);

    static {
        connManager.setMaxTotal(HttpConstants.DEFAULT_POOL_MANAGER_MAX_TOTAL_CONNECTIONS + 1);
        connManager.setDefaultConnectionConfig(
                ConnectionConfig
                        .custom()
                        .setCharset(StandardCharsets.UTF_8)
                        .build()
        );
        connManager.setDefaultMaxPerRoute(HttpConstants.POOL_MANAGER_MAX_ROUTE_DEFAULT_CONNECTIONS);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(HttpConstants.DEFAULT_CONNECTION_TIMEOUT_MS) // 获取连接超时时间
                .setConnectTimeout(HttpConstants.DEFAULT_REQUEST_TIMEOUT_MS) // 请求超时时间
                .setSocketTimeout(HttpConstants.DEFAULT_SOCKET_TIMEOUT_MS) // 响应超时时间
                .build();

        HttpRequestRetryHandler retry = (exception, executionCount, context) -> {
            if (executionCount >= HttpConstants.DEFAULT_HTTP_CLIENT_RETRY) {
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

    public static void clearIdleAndExpiredConnections() {
        connManager.closeExpiredConnections();
        connManager.closeIdleConnections(HttpConstants.IDLE_CONNECTION_WAIT_SECONDS, TimeUnit.SECONDS);
        log.info(connManager.getTotalStats().toString());
    }

    public static PoolStats stat() {
        return connManager.getTotalStats();
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
            responseString = get(url, parameters);
        } else if (Method.POST.equals(method)) {
            responseString = post(url, parameters);
        } else if (Method.PUT.equals(method)) {
            responseString = put(url, parameters);
        } else if (Method.PATCH.equals(method)) {
            responseString = patch(url, parameters);
        } else if (Method.DELETE.equals(method)) {
            responseString = delete(url);
        } else {
            throw new IllegalArgumentException(String.format("%s is not permitted.", String.valueOf(method)));
        }
        return responseString;
    }

    private static String execute(HttpRequestBase requester) {

        String responseString = null;
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(requester);
            responseString = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            log.error(ExceptionUtils.getMessage(e));
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

    private static String post(String url, Map<String, Object> parameters) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String paramJsonString = mapper.writeValueAsString(parameters);
        return post(url, paramJsonString);
    }

    private static String post(String url, String paramJsonString) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, HttpConstants.CONTENT_TYPE_JSON_VALUE);
        httpPost.setEntity(new StringEntity(paramJsonString, HttpConstants.CHARSET_UTF8));
        return execute(httpPost);
    }

    private static String get(String url, Map<String, Object> parameters) {
        String urlWithParam = concatParamToUrl(url, parameters);
        HttpGet httpGet = new HttpGet(urlWithParam);
        return execute(httpGet);
    }

    private static String put(String url, Map<String, Object> parameters) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String paramJsonString = mapper.writeValueAsString(parameters);
        return put(url, paramJsonString);
    }

    private static String put(String url, String paramJsonString) {
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader(HttpHeaders.CONTENT_TYPE, HttpConstants.CONTENT_TYPE_JSON_VALUE);
        httpPut.setEntity(new StringEntity(paramJsonString, HttpConstants.CHARSET_UTF8));
        return execute(httpPut);
    }

    private static String patch(String url, Map<String, Object> parameters) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String paramJsonString = mapper.writeValueAsString(parameters);
        return patch(url, paramJsonString);
    }

    private static String patch(String url, String paramJsonString) {
        HttpPatch httpPatch = new HttpPatch(url);
        httpPatch.setHeader(HttpHeaders.CONTENT_TYPE, HttpConstants.CONTENT_TYPE_JSON_VALUE);
        httpPatch.setEntity(new StringEntity(paramJsonString, HttpConstants.CHARSET_UTF8));
        return execute(httpPatch);
    }

    private static String delete(String url) {
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setHeader(HttpHeaders.CONTENT_TYPE, HttpConstants.CONTENT_TYPE_JSON_VALUE);
        return execute(httpDelete);
    }
}
