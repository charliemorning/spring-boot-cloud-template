package org.charlie.example.common.utils.io.http;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.charlie.example.common.constants.io.http.HttpConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Slf4j
@Component
public class HttpClientUtil {

    private CloseableHttpClient httpClient;

    private PoolingHttpClientConnectionManager connManager;

    @Autowired
    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Autowired
    public void setConnManager(PoolingHttpClientConnectionManager connManager) {
        this.connManager = connManager;
    }


    public void clearIdleAndExpiredConnections() {
        connManager.closeExpiredConnections();
        connManager.closeIdleConnections(HttpConstants.IDLE_CONNECTION_WAIT_SECONDS, TimeUnit.SECONDS);
        log.info(connManager.getTotalStats().toString());
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

    public String request(String url, Method method, int timeout) throws JsonProcessingException {
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
    public String request(String url, Map<String, Object> parameters, Method method, int timeout) throws JsonProcessingException {

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

    private String execute(HttpRequestBase requester) {

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

    private String post(String url, Map<String, Object> parameters) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String paramJsonString = mapper.writeValueAsString(parameters);
        return post(url, paramJsonString);
    }

    private String post(String url, String paramJsonString) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, HttpConstants.CONTENT_TYPE_JSON_VALUE);
        httpPost.setEntity(new StringEntity(paramJsonString, HttpConstants.CHARSET_UTF8));
        return execute(httpPost);
    }

    private String get(String url, Map<String, Object> parameters) {
        String urlWithParam = concatParamToUrl(url, parameters);
        HttpGet httpGet = new HttpGet(urlWithParam);
        return execute(httpGet);
    }

    private String put(String url, Map<String, Object> parameters) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String paramJsonString = mapper.writeValueAsString(parameters);
        return put(url, paramJsonString);
    }

    private String put(String url, String paramJsonString) {
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader(HttpHeaders.CONTENT_TYPE, HttpConstants.CONTENT_TYPE_JSON_VALUE);
        httpPut.setEntity(new StringEntity(paramJsonString, HttpConstants.CHARSET_UTF8));
        return execute(httpPut);
    }

    private String patch(String url, Map<String, Object> parameters) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String paramJsonString = mapper.writeValueAsString(parameters);
        return patch(url, paramJsonString);
    }

    private String patch(String url, String paramJsonString) {
        HttpPatch httpPatch = new HttpPatch(url);
        httpPatch.setHeader(HttpHeaders.CONTENT_TYPE, HttpConstants.CONTENT_TYPE_JSON_VALUE);
        httpPatch.setEntity(new StringEntity(paramJsonString, HttpConstants.CHARSET_UTF8));
        return execute(httpPatch);
    }

    private String delete(String url) {
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setHeader(HttpHeaders.CONTENT_TYPE, HttpConstants.CONTENT_TYPE_JSON_VALUE);
        return execute(httpDelete);
    }
}
