package org.charlie.example.framework.entities;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Session {
    private String globalTraceId;
}
