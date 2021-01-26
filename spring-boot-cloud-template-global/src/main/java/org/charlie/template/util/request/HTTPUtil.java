package org.charlie.template.util.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author zhangchen
 * @version 0.1.0
 */
@Slf4j
public class HTTPUtil {

    private final static String POST_HEADER_KEY = "Content-type";

    private final static String POST_HEADER_VALUE = "application/json; charset=utf-8";

    private final static String ENTITY_CHARSET = "UTF-8";

    public static String doPost(String url, Map<String, Object> parameters) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String paramJsonString = mapper.writeValueAsString(parameters);
        return doPost(url, paramJsonString);
    }

    public static String doPost(String url, String paramJsonString) {

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(POST_HEADER_KEY, POST_HEADER_VALUE);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        httpPost.setEntity(new StringEntity(paramJsonString, ENTITY_CHARSET));

        String responseString = null;
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(httpPost);
            responseString = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            log.error(ExceptionUtils.getMessage(e));
            try {
                httpClient.close();
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
