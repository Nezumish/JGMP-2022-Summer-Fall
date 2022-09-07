package com.rntgroup.big.data.task6.consumer;

import com.rntgroup.big.data.task6.consumer.service.BottleService;
import com.rntgroup.big.data.task6.domain.Bottle;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * The class representing a consumer group of a topic with bottles
 * Each @KafkaListener method reads its own partition of a topic from the configuration
 */
@Component
public class BottleConsumerGroup {

    private final BottleService bottleService;

    public BottleConsumerGroup(BottleService bottleService) {
        this.bottleService = bottleService;
    }

    @KafkaListener(
            topicPartitions = @TopicPartition(topic = Bottle.TOPIC, partitions = {"0"}),
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeMessageFromPartition0(@Payload Bottle bottle, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println(getConsumerMessage(bottle, partition));
        bottleService.saveBottle(bottle);
    }

    @KafkaListener(
            topicPartitions = @TopicPartition(topic = Bottle.TOPIC, partitions = {"1"}),
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeMessageFromPartition1(@Payload Bottle bottle, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println(getConsumerMessage(bottle, partition));
        bottleService.saveBottle(bottle);
    }

    @KafkaListener(
            topicPartitions = @TopicPartition(topic = Bottle.TOPIC, partitions = {"2"}),
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeMessageFromPartition2(@Payload Bottle bottle, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println(getConsumerMessage(bottle, partition));
        bottleService.saveBottle(bottle);
    }

    private String getConsumerMessage(Bottle bottle, int consumer) {
        return "CONSUMER " + consumer + ": Found a Bottle, containing the message: " + bottle.getMessage();
    }

}
