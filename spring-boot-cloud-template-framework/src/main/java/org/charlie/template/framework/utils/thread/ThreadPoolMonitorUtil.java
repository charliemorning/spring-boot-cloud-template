package org.charlie.template.framework.utils.thread;


import lombok.extern.slf4j.Slf4j;
import org.charlie.template.framework.entities.ThreadPoolStats;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * Utility class for getting statistic of thread pool.
 *
 * @author Charlie
 */
@Slf4j
public class ThreadPoolMonitorUtil {

    public static ThreadPoolStats stat(ThreadPoolExecutor threadPoolExecutor) {
        if (Objects.isNull(threadPoolExecutor )) {
            return ThreadPoolStats.builder().build();
        }
        return ThreadPoolStats.builder()
                .activeCount(threadPoolExecutor.getActiveCount()) //
                .corePoolSize(threadPoolExecutor.getCorePoolSize())
                .largestPoolSize(threadPoolExecutor.getLargestPoolSize())
                .maximumPoolSize(threadPoolExecutor.getMaximumPoolSize())
                .poolSize(threadPoolExecutor.getPoolSize())
                .build();
    }

    public static ThreadPoolStats stat(ExecutorService executorService) {
        ThreadPoolExecutor threadPoolExecutor = null;
        if (executorService instanceof ThreadPoolExecutor) {
            threadPoolExecutor = (ThreadPoolExecutor) executorService;
        } else {
            log.error("executorService is not an instance of ThreadPoolExecutor");
        }
        return stat(threadPoolExecutor);
    }
}
