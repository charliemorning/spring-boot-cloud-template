package org.charlie.template.framework.configs.io.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;


/**
 * http client response error handler
 *
 * @author Charlie
 */
@Slf4j
public class CustomClientErrorHandler implements ResponseErrorHandler {

    /**
     * treat http status code 4xx and 5xx as error
     * @param clientHttpResponse
     * @return
     * @throws IOException
     */
    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        // TODO: add error conditions here, or modify error condition
        return clientHttpResponse.getStatusCode().is4xxClientError() ||
                clientHttpResponse.getStatusCode().is5xxServerError();
    }

    /**
     * add error handle code
     * @param clientHttpResponse
     * @throws IOException
     */
    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        // TODO: add error handler here
        log.error("Error handler not implemented yet. HTTP Status Code: " + clientHttpResponse.getStatusCode().value());
    }
}