package org.charlie.template.manager.middleware.kafka;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;


/**
 * @author Charlie
 * @version 0.1.0
 */
@Component
@Slf4j
public class KafkaProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produce(String topicName, String message){
        ListenableFuture<?> future = kafkaTemplate.send(topicName, message);
        future.addCallback(new SuccessCallback<Object>() {
            @Override
            public void onSuccess(Object o) {
                log.info(String.valueOf(o));
            }
        }, new FailureCallback() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info(String.valueOf(throwable));
            }
        });
    }
}
