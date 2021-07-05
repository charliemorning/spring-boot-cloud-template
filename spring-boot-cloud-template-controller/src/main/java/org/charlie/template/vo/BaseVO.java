package org.charlie.template.vo;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseVO {

    @JsonAlias("result_code")
    public String resultCode;

    @JsonAlias("result_message")
    public String resultMessage;
}
