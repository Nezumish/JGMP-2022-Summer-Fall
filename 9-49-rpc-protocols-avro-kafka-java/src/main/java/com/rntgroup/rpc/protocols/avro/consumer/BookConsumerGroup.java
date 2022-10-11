package com.rntgroup.rpc.protocols.avro.consumer;

import com.rntgroup.rpc.protocols.avro.schema.Book;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class BookConsumerGroup {

    @KafkaListener(
            topicPartitions = @TopicPartition(topic = "books", partitions = {"0"}),
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeMessageFromPartition0(@Payload Book book, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        System.out.println("Received a book: " + book);
    }

}
