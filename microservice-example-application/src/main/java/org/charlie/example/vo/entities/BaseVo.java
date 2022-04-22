package org.charlie.example.vo.entities;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseVo {

    @JsonAlias("result_code")
    public String resultCode;

    @JsonAlias("result_message")
    public String resultMessage;
}
