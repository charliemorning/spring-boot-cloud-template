package org.charlie.template.service.implement;


import org.charlie.template.service.RequestServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Primary
@Service
public class RestTemplateRequestServiceTest implements RequestServiceTest {

    RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String get(String url) {
        return restTemplate.getForObject(url, String.class);
    }

    @Override
    public String post(String url) {
        return null;
    }

    @Override
    public String patch(String url) {
        return null;
    }

    @Override
    public String put(String url) {
        return null;
    }

    @Override
    public String delete(String url) {
        return null;
    }
}
