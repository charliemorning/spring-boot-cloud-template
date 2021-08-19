package org.charlie.template.framework.configs.thread;

import com.google.common.math.Stats;
import lombok.extern.slf4j.Slf4j;
import org.charlie.template.framework.entities.ThreadPoolStats;
import org.charlie.template.framework.utils.thread.ThreadPoolMonitorUtil;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * This class defines how to handle task when task is rejected by executor.
 */
@Slf4j
public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        ThreadPoolStats threadPoolStats = ThreadPoolMonitorUtil.stat(threadPoolExecutor);
        log.error(String.valueOf(threadPoolStats));
    }
}
