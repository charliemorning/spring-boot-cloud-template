package org.charlie.template.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @author Charlie
 */
@Component
@Slf4j
public class FooScheduler {

    @Scheduled(fixedRate = 1 * 1000)
    private void cycleRun() {
        log.info("Run each 1 second.");
    }
}
