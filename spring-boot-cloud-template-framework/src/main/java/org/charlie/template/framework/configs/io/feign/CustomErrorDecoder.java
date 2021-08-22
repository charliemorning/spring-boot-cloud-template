package org.charlie.template.framework.configs.io.feign;

import feign.Response;
import feign.codec.ErrorDecoder;


public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()){
            default:
                return new Exception("Generic error");
        }
    }
}