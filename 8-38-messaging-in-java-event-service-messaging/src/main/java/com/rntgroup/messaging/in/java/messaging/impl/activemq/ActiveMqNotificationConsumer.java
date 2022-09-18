package com.rntgroup.messaging.in.java.messaging.impl.activemq;

import com.rntgroup.messaging.in.java.dto.Event;
import com.rntgroup.messaging.in.java.messaging.Notifying;
import org.springframework.jms.annotation.JmsListener;

public class ActiveMqNotificationConsumer {

    @JmsListener(destination = Notifying.CREATE)
    public void consumeCreateEventNotification(Event event) {
        System.out.println(
                "ActiveMq, NOTIFICATION: new event: \n" + event
        );
    }

    @JmsListener(destination = Notifying.UPDATE)
    public void consumeUpdateEventNotification(Event event) {
        System.out.println(
                "ActiveMq, NOTIFICATION: updated event: \n" + event
        );
    }

    @JmsListener(destination = Notifying.DELETE)
    public void consumeDeleteEventNotification(Event event) {
        System.out.println(
                "ActiveMq, NOTIFICATION: deleted event: \n" + event
        );
    }

}
