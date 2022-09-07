package com.rntgroup.big.data.task6.producer;

import com.rntgroup.big.data.task6.domain.Bottle;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The class representing a simple Kafka Producer scheduled to send a message in a period of time
 */
@Component
public class BottleProducer {

    /**
     * A simple callback to know what happened to a produced message
     */
    private static class Callback implements ListenableFutureCallback<SendResult<String, Bottle>> {

        private final String message;

        Callback(String message) {
            this.message = message;
        }

        @Override
        public void onFailure(Throwable ex) {
            System.out.println("Unable to send Bottle [" + message + "] due to : " + ex.getMessage());
        }

        @Override
        public void onSuccess(SendResult<String, Bottle> result) {
            System.out.println("Sent Bottle [" + message + "]");
        }
    }

    private final KafkaTemplate<String, Bottle> kafkaTemplate;

    public BottleProducer(KafkaTemplate<String, Bottle> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRateString = "${scheduling.producer}")
    public void sendRandomBottle() {

        var someValue = ThreadLocalRandom.current().nextInt();
        String message = "Very valuable secret message: " + someValue;
        var listenableFuture = kafkaTemplate.send(
                Bottle.TOPIC, new Bottle(message)
        );

        listenableFuture.addCallback(new Callback(message));
    }

}
