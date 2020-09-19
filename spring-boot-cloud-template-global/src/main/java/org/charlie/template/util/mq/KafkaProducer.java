package org.charlie.template.util.mq;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;


@Component
@Slf4j
public class KafkaProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topicName, String message){
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
