package org.charlie.example.framework.configs.io.feign;

import feign.Response;
import feign.codec.ErrorDecoder;


public class ExampleErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()){
            default:
                return new Exception("Generic error");
        }
    }
}