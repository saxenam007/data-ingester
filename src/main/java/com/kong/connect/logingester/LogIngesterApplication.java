package com.kong.connect.logingester;

import com.kong.connect.logingester.service.publisher.FileReaderComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogIngesterApplication implements CommandLineRunner {

	@Autowired
	private FileReaderComponent fileReaderComponent;

	public static void main(String[] args) {
		SpringApplication.run(LogIngesterApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// Pass file path and Kafka topic name
		fileReaderComponent.readFile();
	}
}
