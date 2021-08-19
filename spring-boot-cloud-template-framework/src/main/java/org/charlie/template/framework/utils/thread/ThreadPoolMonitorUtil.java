package org.charlie.template.framework.utils.thread;


import org.charlie.template.framework.entities.ThreadPoolStats;

import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolMonitorUtil {

    public static ThreadPoolStats stat(ThreadPoolExecutor threadPoolExecutor) {
        if (Objects.isNull(threadPoolExecutor )) {
            return ThreadPoolStats.builder().build();
        }
        return ThreadPoolStats.builder()
                .activeCount(threadPoolExecutor.getActiveCount())
                .corePoolSize(threadPoolExecutor.getCorePoolSize())
                .largestPoolSize(threadPoolExecutor.getLargestPoolSize())
                .maximumPoolSize(threadPoolExecutor.getMaximumPoolSize())
                .poolSize(threadPoolExecutor.getPoolSize())
                .build();
    }
}
