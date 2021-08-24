package org.charlie.example.framework.utils.thread;

import lombok.extern.slf4j.Slf4j;
import org.charlie.example.framework.constants.thread.ThreadConstants;

import java.util.concurrent.*;

/**
 * To define a utility class with static ThreadPoolExecutor.
 * <p>
 * Delete this class if using <class>org.charlie.example.framework.utils.thread.ThreadUtil2</class>..
 *
 * @author Charlie
 */
@Slf4j
public class ThreadStaticUtil {

    private static ThreadPoolExecutor threadPoolExecutor;

    static {
        threadPoolExecutor = new ThreadPoolExecutor(
                ThreadConstants.THREAD_POOL_DEFAULT_NUM,
                ThreadConstants.THREAD_POOL_DEFAULT_MAX_NUM,
                ThreadConstants.THREAD_KEEP_ALIVE_SECOND,
                TimeUnit.SECONDS,
                new SynchronousQueue<>()
        );
    }

    public static void execute(Runnable t) {
        threadPoolExecutor.execute(t);
    }

    public static void remove(Runnable t) {
        threadPoolExecutor.remove(t);
    }
}
