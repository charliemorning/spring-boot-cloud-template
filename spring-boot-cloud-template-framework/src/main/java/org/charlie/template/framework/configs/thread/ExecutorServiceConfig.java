package org.charlie.template.framework.configs.thread;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.charlie.template.framework.configs.TemplateConfig;
import org.charlie.template.framework.constants.thread.ThreadConstants;
import org.charlie.template.framework.utils.thread.ThreadPoolMonitorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Objects;
import java.util.concurrent.*;


/**
 * To define thread pool executor.
 *
 * @author Charlie
 */
@Slf4j
@Configuration
@EnableScheduling
public class ExecutorServiceConfig {

    private TemplateConfig templateConfig;

    @Autowired
    public void setTemplateConfig(TemplateConfig templateConfig) {
        this.templateConfig = templateConfig;
    }

    /**
     * To configure RejectedExecutionHandler
     * @return
     */
    @Bean
    public RejectedExecutionHandler rejectPolicy() {
        return new ThreadPoolExecutor.AbortPolicy();
    }

    /**
     *
     * @return
     */
    @Bean
    public BlockingQueue<Runnable> blockingQueue() {
        return new SynchronousQueue<>();
    }

    /**
     * configure ThreadPoolExecutor
     * @return
     */
    @Bean
    public ExecutorService threadPoolExecutor() {

        return new ThreadPoolExecutor(
                ThreadConstants.THREAD_POOL_DEFAULT_NUM,
                ThreadConstants.THREAD_POOL_DEFAULT_MAX_NUM,
                ThreadConstants.THREAD_KEEP_ALIVE_SECOND,
                TimeUnit.SECONDS,
                blockingQueue(),
                threadFactory(),
                rejectPolicy()
                );
    }

    /**
     * To configure a thread factory.
     * Rename thread in pool.
     *
     * @return
     */
    @Bean
    public ThreadFactory threadFactory() {
        return new ThreadFactoryBuilder()
                .setUncaughtExceptionHandler(new CustomUncaughtExceptionHandler())
                .setNameFormat(ThreadConstants.THREAD_NAME_FORMAT)
                .build();
    }

    /**
     * To configure a thread pool monitor.
     * Log the thread pool statistics.
     *
     * @return a runnable executed in fixed rate scheduled by Spring Scheduler.
     */
    @Bean
    public Runnable threadPoolMonitor(ExecutorService executorService) {
        return new Runnable() {
            @Scheduled(fixedDelay = ThreadConstants.THREAD_POOL_MONITOR_MS_INTERVAL * 1000)
            @Override
            public void run() {
                // Monitor thread pool statistics
                if (Objects.nonNull(executorService)) {
                    log.info(String.valueOf(ThreadPoolMonitorUtil.stat(executorService)));
                }
            }
        };
    }
}
