package org.charlie.example.common.entities;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;


/**
 * Class to hold thread pool statistics.
 *
 * @author Charlie
 */
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