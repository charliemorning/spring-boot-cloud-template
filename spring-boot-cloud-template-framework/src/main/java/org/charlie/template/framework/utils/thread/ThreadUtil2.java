package org.charlie.template.framework.utils.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
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

    private ExecutorService executorService;

    @Autowired
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void execute(Runnable t) {
        executorService.execute(t);
    }

    public Future submit(Runnable t) {
        return executorService.submit(t);
    }
}
