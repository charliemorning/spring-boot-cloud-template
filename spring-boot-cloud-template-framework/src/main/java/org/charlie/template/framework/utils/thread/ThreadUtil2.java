package org.charlie.template.framework.utils.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * To define a utility class with ThreadPoolExecutor.
 * <p>
 * Delete this class if using <class>org.charlie.template.framework.utils.thread.ThreadUtil</class>.
 *
 * @author Charlie
 */
@Slf4j
@Component
public class ThreadUtil2 {

    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    public void execute(Runnable t) {
        threadPoolExecutor.execute(t);
    }

    public void remove(Runnable t) {
        threadPoolExecutor.remove(t);
    }
}
