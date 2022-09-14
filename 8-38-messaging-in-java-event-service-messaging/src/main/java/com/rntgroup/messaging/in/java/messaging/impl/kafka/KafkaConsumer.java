package com.rntgroup.messaging.in.java.messaging.impl.kafka;

import com.rntgroup.messaging.in.java.dto.Event;
import com.rntgroup.messaging.in.java.messaging.Notifying;
import com.rntgroup.messaging.in.java.messaging.configuration.KafkaAutoConfiguration;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnBean(KafkaAutoConfiguration.class)
//@ConditionalOnProperty(name = "broker", havingValue = "kafka")
public class KafkaConsumer {

    @KafkaListener(topics = Notifying.CREATE)
    public void createEvent(List<Event> events) {
        System.out.println(
                "Kafka, NOTIFICATION: new events: \n" + eventsListAsString(events)
        );
    }

    private String eventsListAsString(List<Event> events) {
        return Strings.join(events, '\n');
    }

    @KafkaListener(topics = Notifying.UPDATE)
    public void updateEvent(List<Event> events) {
        System.out.println(
                "Kafka, NOTIFICATION: updated events: \n" + eventsListAsString(events)
        );
    }

    @KafkaListener(topics = Notifying.DELETE)
    public void deleteEvent(List<Long> eventIds) {
        System.out.println(
                "Kafka, NOTIFICATION: deleted events: \n" + eventIds
        );
    }

}
