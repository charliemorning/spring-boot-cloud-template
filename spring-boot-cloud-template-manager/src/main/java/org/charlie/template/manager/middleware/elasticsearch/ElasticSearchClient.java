package org.charlie.template.manager.middleware.elasticsearch;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.charlie.template.config.ESConfig;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;


//@Component
public class ElasticSearchClient {

    @Autowired
    ESConfig esConfig;

    RestClient restClient;

    public ElasticSearchClient() {

        String[] urls = esConfig.getUrls();
        HttpHost[] httpHosts = new HttpHost[urls.length];
        for (int i = 0; i < urls.length; i++) {
            String url = urls[i];
            String[] splits = url.split(":");
            String ip = splits[0];
            int port = Integer.parseInt(splits[1]);
            httpHosts[i] = new HttpHost(ip, port, "http");
        }

        restClient = RestClient.builder(httpHosts).build();
    }

    public void createIndex(Index index, IndexSetting setting) {
        Request request = new Request("PUT", index.getName());
        request.setEntity(new NStringEntity(
                "{\"json\":\"text\"}",
                ContentType.APPLICATION_JSON));
    }

    public void deleteIndex(Index index) {
        Request request = new Request("DELETE", index.getName());
    }

    private String performRequest(Request request) throws IOException {
        Response response = restClient.performRequest(request);
        RequestLine requestLine = response.getRequestLine();
        HttpHost host = response.getHost();
        int statusCode = response.getStatusLine().getStatusCode();
        Header[] headers = response.getHeaders();
        String responseBody = EntityUtils.toString(response.getEntity());
        return responseBody;
    }

    private String performRequestAsync(Request request) {
        /*Cancellable cancellable = restClient.performRequestAsync(request,
            new ResponseListener() {
                @Override
                public void onSuccess(Response response) {

                }

                @Override
                public void onFailure(Exception exception) {

                }
            });*/
        return null;
    }

    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http")).build();

        Request request = new Request(
                "PUT",
                "text/_doc/1");
        Response response = restClient.performRequest(request);
        RequestLine requestLine = response.getRequestLine();
        HttpHost host = response.getHost();
        int statusCode = response.getStatusLine().getStatusCode();
        Header[] headers = response.getHeaders();
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println(responseBody);
    }
}
