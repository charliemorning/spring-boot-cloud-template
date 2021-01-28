package org.charlie.template.manager.middleware.kafka;

import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

//    @KafkaListener(topics = {"app_log"})
    public void kafkaConsume(String message){
        System.out.println("app_log--消费消息:" + message);
    }


    public void comsume(String message) {
    }
}
