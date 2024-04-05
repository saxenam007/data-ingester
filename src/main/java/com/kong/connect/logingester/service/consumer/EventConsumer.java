package com.kong.connect.logingester.service.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class EventConsumer {

    private final EventProcessor eventProcessor;

    @Autowired
    public EventConsumer(EventProcessor eventProcessor){
        this.eventProcessor = eventProcessor;
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${kafka.topic}")
    public void processEvent (ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment){
        log.debug("ConsumerRecord From Kafka: {} ", consumerRecord);
        eventProcessor.processEvent(consumerRecord, acknowledgment);
        }

}
