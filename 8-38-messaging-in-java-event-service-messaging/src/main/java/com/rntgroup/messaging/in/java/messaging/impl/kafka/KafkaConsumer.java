package com.rntgroup.messaging.in.java.messaging.impl.kafka;

import com.rntgroup.messaging.in.java.dto.Event;
import com.rntgroup.messaging.in.java.messaging.Notifying;
import com.rntgroup.messaging.in.java.messaging.configuration.KafkaMessagingAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(KafkaMessagingAutoConfiguration.class)
public class KafkaConsumer {

    @KafkaListener(topics = Notifying.CREATE)
    public void createEvent(Event event) {
        System.out.println(
                "Kafka, NOTIFICATION: new event: \n" + event
        );
    }

    @KafkaListener(topics = Notifying.UPDATE)
    public void updateEvent(Event event) {
        System.out.println(
                "Kafka, NOTIFICATION: updated event: \n" + event
        );
    }

    @KafkaListener(topics = Notifying.DELETE)
    public void deleteEvent(Long eventId) {
        System.out.println(
                "Kafka, NOTIFICATION: deleted event: \n" + eventId
        );
    }

}
