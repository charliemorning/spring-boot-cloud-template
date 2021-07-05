package org.charlie.template.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Response<T> {

    @JsonAlias("code")
    private String code;

    @JsonAlias("message")
    private String message;

    @JsonAlias("data")
    private T data;
}
