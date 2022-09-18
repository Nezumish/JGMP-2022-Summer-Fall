package com.rntgroup.messaging.in.java.messaging.impl.kafka;

import com.rntgroup.messaging.in.java.api.async.EventMessaging;
import com.rntgroup.messaging.in.java.dto.Event;
import com.rntgroup.messaging.in.java.messaging.EventAction;
import com.rntgroup.messaging.in.java.messaging.Notifying;
import com.rntgroup.messaging.in.java.messaging.Requesting;
import com.rntgroup.messaging.in.java.messaging.impl.CallBackUtils;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.function.Function;

public class KafkaProducer implements EventMessaging {

    private static final String BROKER_NAME = "Kafka";

    private final KafkaTemplate<String, Event> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Event> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void createEvent(Event event, Function<Event, Event> callbackFunction) {
        kafkaTemplate.send(Requesting.CREATE, event).completable()
                .thenApplyAsync(sendResult -> {
                    var requestedEvent = sendResult.getProducerRecord().value();
                    CallBackUtils.successfulRequestConsumer(BROKER_NAME, EventAction.CREATE).accept(requestedEvent);

                    return callbackFunction.apply(requestedEvent);
                }).whenCompleteAsync((createdEvent, throwable) -> kafkaTemplate.send(Notifying.CREATE, createdEvent));
    }

    @Override
    public void updateEvent(Event event, Function<Event, Event> callbackFunction) {
        kafkaTemplate.send(Requesting.UPDATE, event).completable()
                .thenApplyAsync(sendResult -> {
                    var createdEvent = sendResult.getProducerRecord().value();
                    CallBackUtils.successfulRequestConsumer(BROKER_NAME, EventAction.UPDATE).accept(event);

                    return callbackFunction.apply(createdEvent);
                }).whenCompleteAsync((createdEvent, throwable) -> kafkaTemplate.send(Notifying.UPDATE, createdEvent));
    }

    @Override
    public void deleteEvent(Event event, Function<Event, Event> callbackFunction) {
        kafkaTemplate.send(Requesting.DELETE, event).completable()
                .thenApplyAsync(sendResult -> {
                    var createdEvent = sendResult.getProducerRecord().value();
                    CallBackUtils.successfulRequestConsumer(BROKER_NAME, EventAction.DELETE).accept(event);

                    return callbackFunction.apply(createdEvent);
                }).whenCompleteAsync((createdEvent, throwable) -> kafkaTemplate.send(Notifying.DELETE, createdEvent));
    }

}
