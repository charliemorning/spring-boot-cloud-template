package org.charlie.example.framework.interceptors.out.httpclient;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class ExampleHttpResponseHandler implements HttpResponseInterceptor {
    @Override
    public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        // TODO: add business error handle
    }
}
