package com.rntgroup.big.data.task6.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableKafka
@EnableScheduling
@SpringBootApplication
public class ProducerApplication {

    /**
     * Launches producer to write messages to a topic given in the application configuration
     */
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class);
    }

}
