package org.charlie.example.framework.schedulers;


import org.charlie.example.framework.constants.io.http.HttpConstants;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static org.charlie.example.framework.utils.io.http.HttpClientStaticUtil.clearIdleAndExpiredConnections;

/**
 *
 */
@Component
public class IdleConnectionsCloseScheduler {

    @Scheduled(fixedRate = HttpConstants.IDLE_CONNECTION_CLOSE_INTERVAL * 1000)
    public void run() {
        clearIdleAndExpiredConnections();
    }
}
