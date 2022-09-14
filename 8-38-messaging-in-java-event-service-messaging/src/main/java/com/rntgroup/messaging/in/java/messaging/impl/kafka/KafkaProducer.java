package com.rntgroup.messaging.in.java.messaging.impl.kafka;

import com.rntgroup.messaging.in.java.api.async.EventMessaging;
import com.rntgroup.messaging.in.java.dto.Event;
import com.rntgroup.messaging.in.java.messaging.EventAction;
import com.rntgroup.messaging.in.java.messaging.Notifying;
import com.rntgroup.messaging.in.java.messaging.Requesting;
import com.rntgroup.messaging.in.java.messaging.configuration.KafkaAutoConfiguration;
import com.rntgroup.messaging.in.java.messaging.impl.CallBackUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.function.Consumer;
import java.util.function.Function;

@Component
@ConditionalOnBean(KafkaAutoConfiguration.class)
@ConditionalOnMissingBean(EventMessaging.class)
public class KafkaProducer implements EventMessaging {

    private static final String BROKER_NAME = "Kafka";

    private final KafkaTemplate<String, Event> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, Event> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void createEvent(Event event, Function<Event, Event> callbackFunction) {
        var successCreateConsumer = CallBackUtils
                .successfulRequestConsumer(BROKER_NAME, EventAction.CREATE)
                .andThen(callbackFunction::apply)
                .andThen(ce -> sendNotification(ce, Notifying.CREATE));
        var failCreateConsumer = CallBackUtils.failedRequestConsumer(BROKER_NAME, EventAction.CREATE);

        var callback = new KafkaCallback(event, successCreateConsumer, failCreateConsumer);

        kafkaTemplate.send(Requesting.CREATE, event).addCallback(callback);
    }

    private void sendNotification(Event event, String notificationTopic) {
        kafkaTemplate.send(notificationTopic, event);
    }

    @Override
    public void updateEvent(Event event, Function<Event, Event> callbackFunction) {
        var successUpdateConsumer = CallBackUtils
                .successfulRequestConsumer(BROKER_NAME, EventAction.UPDATE)
                .andThen(callbackFunction::apply)
                .andThen(ue -> sendNotification(ue, Notifying.UPDATE));
        var failUpdateConsumer = CallBackUtils.failedRequestConsumer(BROKER_NAME, EventAction.UPDATE);

        var callback = new KafkaCallback(event, successUpdateConsumer, failUpdateConsumer);

        kafkaTemplate.send(Requesting.UPDATE, event).addCallback(callback);
    }

    @Override
    public void deleteEvent(Event event, Function<Event, Event> callbackFunction) {
        var successDeleteConsumer = CallBackUtils
                .successfulRequestConsumer(BROKER_NAME, EventAction.DELETE)
                .andThen(callbackFunction::apply)
                .andThen(de -> sendNotification(de, Notifying.DELETE));
        var failDeleteConsumer = CallBackUtils.failedRequestConsumer(BROKER_NAME, EventAction.DELETE);

        var callback = new KafkaCallback(event, successDeleteConsumer, failDeleteConsumer);

        kafkaTemplate.send(Requesting.DELETE, event).addCallback(callback);
    }

    private static class KafkaCallback implements ListenableFutureCallback<SendResult<String, Event>> {

        private final Event event;
        private final Consumer<Event> successConsumer;
        private final Consumer<Event> failConsumer;

        KafkaCallback(Event event, Consumer<Event> successConsumer, Consumer<Event> failConsumer) {
            this.event = event;
            this.successConsumer = successConsumer;
            this.failConsumer = failConsumer;
        }

        @Override
        public void onSuccess(SendResult<String, Event> result) {
            successConsumer.accept(event);
        }

        @Override
        public void onFailure(Throwable ex) {
            failConsumer.accept(event);
        }

    }

}
