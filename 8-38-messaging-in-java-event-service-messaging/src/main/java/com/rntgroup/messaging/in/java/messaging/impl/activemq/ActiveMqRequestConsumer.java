package com.rntgroup.messaging.in.java.messaging.impl.activemq;

import com.rntgroup.messaging.in.java.dto.Event;
import com.rntgroup.messaging.in.java.messaging.EventAction;
import com.rntgroup.messaging.in.java.messaging.Requesting;
import com.rntgroup.messaging.in.java.messaging.impl.CallBackUtils;
import org.springframework.jms.annotation.JmsListener;

public class ActiveMqRequestConsumer {

    private static final String BROKER_NAME = "ActiveMq";

    @JmsListener(destination = Requesting.CREATE)
    public void consumeCreateEventRequest(Event event) {
        CallBackUtils.successfulRequestConsumer(BROKER_NAME, EventAction.CREATE)
                .accept(event);
    }

    @JmsListener(destination = Requesting.UPDATE)
    public void consumeUpdateEventRequest(Event event) {
        CallBackUtils.successfulRequestConsumer(BROKER_NAME, EventAction.UPDATE)
                .accept(event);
    }

    @JmsListener(destination = Requesting.DELETE)
    public void consumeDeleteEventRequest(Event event) {
        CallBackUtils.successfulRequestConsumer(BROKER_NAME, EventAction.DELETE)
                .accept(event);
    }

}
