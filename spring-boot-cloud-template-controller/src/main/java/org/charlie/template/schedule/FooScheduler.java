package org.charlie.template.schedule;

import lombok.extern.slf4j.Slf4j;
import org.charlie.template.Application;
import org.charlie.template.util.mq.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FooScheduler {

    @Autowired
    KafkaProducer kafkaProducer;

    @Scheduled(fixedRate = 1 * 1000)
    private void cycleRun() {
        kafkaProducer.send("topic1:1:3", "hehe");
        log.info("Run each 1 second.");
    }
}
