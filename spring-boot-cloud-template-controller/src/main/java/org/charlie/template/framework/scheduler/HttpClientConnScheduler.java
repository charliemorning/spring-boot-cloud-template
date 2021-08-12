package org.charlie.template.framework.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.charlie.template.common.configs.TemplateConfig;
import org.charlie.template.common.constants.TemplateConstants;
import org.charlie.template.common.utils.io.http.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class HttpClientConnScheduler {

    TemplateConfig templateConfig;

    @Autowired
    public void setTemplateConfig(TemplateConfig templateConfig) {
        this.templateConfig = templateConfig;
    }

    @Scheduled(fixedRate = TemplateConstants.CONNECTION_CLOSE_INTERVAL * 1000)
    public void closeIdleAndExpiredConnections() {
        HttpClientUtil.closeIdleAndExpiredConnections(1, TimeUnit.SECONDS);
    }
}
