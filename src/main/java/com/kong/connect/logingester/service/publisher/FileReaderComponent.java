package com.kong.connect.logingester.service.publisher;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
@Component
public class FileReaderComponent {

    @Autowired
    private EventPublisher eventPublisher;

    @Value("${kafka.topic}")
    private String topic;

    private String file = "/data.txt";

    private int publishedCount;

    public void readFile() {
        InputStream inputStream = FileReaderComponent.class.getResourceAsStream(file);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                eventPublisher.sendMessage(this.topic, line);
                this.publishedCount++;
            }
            log.info("total messages published to kafka {}",this.publishedCount );
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
