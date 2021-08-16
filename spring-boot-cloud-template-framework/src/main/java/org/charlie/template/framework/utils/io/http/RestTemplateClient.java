package org.charlie.template.framework.utils.io.http;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class RestTemplateClient<T> {

    final Class<T> tClass;

    private String url;

    private RestTemplate restTemplate;

    public RestTemplateClient(RestTemplate restTemplate, String host, int port, String resourcePath, Class<T> tClass) {
        this.restTemplate = restTemplate;
        this.url = host + ":" + port + resourcePath;
        this.tClass = tClass;
    }

    public ResponseEntity<T> getForEntity(long id) {
        ResponseEntity<T> entity = restTemplate.getForEntity(url + "/{id}",
                tClass,
                Long.toString(id));
        return entity;
    }
}
