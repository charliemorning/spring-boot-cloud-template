package org.charlie.template.framework.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

/**
 * @author Charlie
 * response entity for global exception handler
 * not permitted to be used in controller
 * @param <T>
 */
@Data
@Builder
public class ResponseEntity<T> {

    @JsonAlias("code")
    private String code;

    @JsonAlias("message")
    private String message;

    @JsonAlias("data")
    private T data;
}
