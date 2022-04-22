package org.charlie.example.schedulers;


import org.charlie.example.common.constants.io.http.HttpConstants;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static org.charlie.example.common.utils.io.http.HttpClientStaticUtil.clearIdleAndExpiredConnections;

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
