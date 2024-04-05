package com.kong.connect.logingester.service.publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // Keeping this per message for simplicity
    //Alternatively we can group the records, add compression and send it as a batch.
    public void sendMessage(String topic, String message) {
        this.kafkaTemplate.send(topic, message);
    }

}
