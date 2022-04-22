package org.charlie.example.configs.thread;

import lombok.extern.slf4j.Slf4j;
import org.charlie.example.common.entities.ThreadPoolStats;
import org.charlie.example.common.utils.thread.ThreadPoolMonitorUtil;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * This class defines how to handle task when task is rejected by executor.
 *
 * FIXME: delete this class if it is not necessary.
 *
 * @author Charlie
 */
@Slf4j
public class ExampleRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        ThreadPoolStats threadPoolStats = ThreadPoolMonitorUtil.stat(threadPoolExecutor);
        log.error("Thread rejection happened.");
        log.error(String.valueOf(threadPoolStats));
    }
}
