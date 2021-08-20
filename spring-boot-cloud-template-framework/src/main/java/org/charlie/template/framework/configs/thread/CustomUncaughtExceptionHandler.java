package org.charlie.template.framework.configs.thread;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * To catch exceptions in thread.
 */
@Slf4j
public class CustomUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        log.error(ExceptionUtils.getMessage(throwable));
    }
}
