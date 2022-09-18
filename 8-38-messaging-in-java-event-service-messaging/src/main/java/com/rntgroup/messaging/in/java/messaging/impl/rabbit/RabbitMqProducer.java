package com.rntgroup.messaging.in.java.messaging.impl.rabbit;

import com.rntgroup.messaging.in.java.api.async.EventMessaging;
import com.rntgroup.messaging.in.java.dto.Event;
import com.rntgroup.messaging.in.java.messaging.EventAction;
import com.rntgroup.messaging.in.java.messaging.Notifying;
import com.rntgroup.messaging.in.java.messaging.Requesting;
import com.rntgroup.messaging.in.java.messaging.impl.CallBackUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public class RabbitMqProducer implements EventMessaging {

    private static final String BROKER_NAME = "RabbitMq";

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void createEvent(Event event, Function<Event, Event> callbackFunction) {
        eventConsumer(EventAction.CREATE, Requesting.CREATE, Notifying.CREATE, callbackFunction)
                .accept(event);
    }

    private Consumer<Event> eventConsumer(EventAction action,
                                          String requestTopic,
                                          String notificationTopic,
                                          Function<Event, Event> callbackFunction) {
        return event -> CompletableFuture.completedFuture(event)
                .thenAcceptAsync((e) -> {
                    rabbitTemplate.convertAndSend(requestTopic, e);
                    var consumedEvent = callbackFunction.apply(e);

                    rabbitTemplate.convertAndSend(notificationTopic, consumedEvent);
                }).exceptionally(CallBackUtils.throwableFunction(BROKER_NAME, action));
    }

    @Override
    public void updateEvent(Event event, Function<Event, Event> callbackFunction) {
        eventConsumer(EventAction.UPDATE, Requesting.UPDATE, Notifying.UPDATE, callbackFunction)
                .accept(event);
    }

    @Override
    public void deleteEvent(Event event, Function<Event, Event> callbackFunction) {
        eventConsumer(EventAction.DELETE, Requesting.DELETE, Notifying.DELETE, callbackFunction)
                .accept(event);
    }

}
