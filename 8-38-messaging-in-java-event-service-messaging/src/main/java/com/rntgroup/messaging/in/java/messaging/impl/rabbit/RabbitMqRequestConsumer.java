package com.rntgroup.messaging.in.java.messaging.impl.rabbit;

import com.rntgroup.messaging.in.java.dto.Event;
import com.rntgroup.messaging.in.java.messaging.EventAction;
import com.rntgroup.messaging.in.java.messaging.Requesting;
import com.rntgroup.messaging.in.java.messaging.impl.CallBackUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class RabbitMqRequestConsumer {

    private static final String BROKER_NAME = "RabbitMq";

    @RabbitListener(queues = Requesting.CREATE)
    public void consumeCreateEventRequest(Event event) {
        CallBackUtils.successfulRequestConsumer(BROKER_NAME, EventAction.CREATE)
                .accept(event);
    }

    @RabbitListener(queues = Requesting.UPDATE)
    public void consumeUpdateEventRequest(Event event) {
        CallBackUtils.successfulRequestConsumer(BROKER_NAME, EventAction.UPDATE)
                .accept(event);
    }

    @RabbitListener(queues = Requesting.DELETE)
    public void consumeDeleteEventRequest(Event event) {
        CallBackUtils.successfulRequestConsumer(BROKER_NAME, EventAction.DELETE)
                .accept(event);
    }

}
