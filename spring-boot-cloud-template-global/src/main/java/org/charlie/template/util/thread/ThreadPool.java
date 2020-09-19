package org.charlie.template.util.thread;

import org.charlie.template.constants.SystemConstants;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author zhangchen
 * @version 0.1.0
 */
@Component
public class ThreadPool {

    private ExecutorService executorService = Executors.newFixedThreadPool(SystemConstants.THREADING_POOL_SIZE);

    public Future<?> submit(Runnable runnable) {
        return executorService.submit(runnable);
    }

    public Future<?> submit(Callable<?> callable) {
        return executorService.submit(callable);
    }

    public void excute(Runnable runnable) {
        executorService.execute(runnable);
    }
}
