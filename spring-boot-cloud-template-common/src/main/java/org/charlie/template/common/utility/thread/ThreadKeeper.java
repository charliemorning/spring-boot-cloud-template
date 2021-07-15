package org.charlie.template.common.utility.thread;

import org.charlie.template.common.config.TemplateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class ThreadKeeper {

    private TemplateConfig templateConfig;

    private ExecutorService executorService;

    @Autowired
    public void setTemplateConfig(TemplateConfig templateConfig) {
        this.templateConfig = templateConfig;
    }

    public ThreadKeeper() {
        executorService = Executors.newFixedThreadPool(templateConfig.getThreadNum());
    }

    public void run(Runnable t) {
        executorService.execute(t);
    }

    public void submit(Runnable t) {
        executorService.submit(t);
    }
}
