package org.charlie.example.framework.utils.bean;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Session {
    private String globalTraceId;
}
