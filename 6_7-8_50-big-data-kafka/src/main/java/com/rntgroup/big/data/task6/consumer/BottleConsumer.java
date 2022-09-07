package com.rntgroup.big.data.task6.consumer;

import com.rntgroup.big.data.task6.consumer.service.StorageService;
import com.rntgroup.big.data.task6.domain.Bottle;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class BottleConsumer {

    private final StorageService storageService;

    public BottleConsumer(StorageService storageService) {
        this.storageService = storageService;
    }

    @KafkaListener(
            topicPartitions = @TopicPartition(topic = Bottle.TOPIC, partitions = {"0"}),
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeMessageFromPartition0(@Payload Bottle bottle, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println(getConsumerMessage(bottle, partition));
        storageService.saveBottle(bottle);
    }

    @KafkaListener(
            topicPartitions = @TopicPartition(topic = Bottle.TOPIC, partitions = {"1"}),
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeMessageFromPartition1(@Payload Bottle bottle, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println(getConsumerMessage(bottle, partition));
        storageService.saveBottle(bottle);
    }

    @KafkaListener(
            topicPartitions = @TopicPartition(topic = Bottle.TOPIC, partitions = {"2"}),
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeMessageFromPartition2(@Payload Bottle bottle, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println(getConsumerMessage(bottle, partition));
        storageService.saveBottle(bottle);
    }

    private String getConsumerMessage(Bottle bottle, int consumer) {
        return "CONSUMER " + consumer + ": Found a Bottle, containing the message: " + bottle.getMessage();
    }

}
