package org.charlie.example.common.entities;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Session {
    private String globalTraceId;
}
