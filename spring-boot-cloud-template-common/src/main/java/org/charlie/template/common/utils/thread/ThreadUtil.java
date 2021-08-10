package org.charlie.template.common.utils.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Slf4j
public class ThreadUtil {

    private final static int DEFAULT_THREAD_COUNT = 200;

    private static ExecutorService executorService;

    static {
        executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_COUNT);
    }

    public static void run(Runnable t) {
        executorService.execute(t);
    }

    public static void submit(Runnable t) {
        executorService.submit(t);
    }
}
