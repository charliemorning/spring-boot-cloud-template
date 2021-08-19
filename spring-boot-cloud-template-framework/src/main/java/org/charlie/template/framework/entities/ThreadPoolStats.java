package org.charlie.template.framework.entities;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class ThreadPoolStats {
    private int activeCount;
    private int corePoolSize;
    private int largestPoolSize;
    private int maximumPoolSize;
    private int poolSize;
}