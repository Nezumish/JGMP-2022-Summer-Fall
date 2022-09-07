package com.rntgroup.big.data.task6.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@EnableMongoRepositories
@SpringBootApplication
public class ConsumerApplication {

    /**
     * Launches consumers to process a topic given in the application configuration
     */
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
