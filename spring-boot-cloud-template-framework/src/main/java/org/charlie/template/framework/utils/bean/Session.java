package org.charlie.template.framework.utils.bean;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Session {
    private String globalTraceId;
}
