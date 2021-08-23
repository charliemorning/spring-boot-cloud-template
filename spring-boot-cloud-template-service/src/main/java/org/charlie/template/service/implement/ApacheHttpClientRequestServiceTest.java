package org.charlie.template.service.implement;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.charlie.template.framework.utils.io.http.HttpClientUtil;
import org.charlie.template.framework.utils.io.http.Method;
import org.charlie.template.service.RequestServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ApacheHttpClientRequestServiceTest implements RequestServiceTest {

    private HttpClientUtil httpClientUtil;

    @Autowired
    public void setHttpClientUtil(HttpClientUtil httpClientUtil) {
        this.httpClientUtil = httpClientUtil;
    }

    @Override
    public String get(String url) {
        try {
            log.info(url);
            return httpClientUtil.request(url, Method.GET, 1);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
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
