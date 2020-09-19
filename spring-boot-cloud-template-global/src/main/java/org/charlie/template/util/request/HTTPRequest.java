package org.charlie.template.util.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author zhangchen
 * @version 0.1.0
 */
public class HTTPRequest {

    private final static String ENTITY_CHARSET = "UTF-8";

    private static String doPost(String url, Map<String, Object> parameters) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String paramJsonString = mapper.writeValueAsString(parameters);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "application/json; charset=utf-8");

        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();

        httpPost.setEntity(new StringEntity(paramJsonString, ENTITY_CHARSET));
        CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
        String responseString = EntityUtils.toString(response.getEntity());

        return responseString;
    }
}
