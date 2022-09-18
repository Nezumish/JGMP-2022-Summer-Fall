package com.rntgroup.messaging.in.java.messaging.impl.rabbit;

import com.rntgroup.messaging.in.java.dto.Event;
import com.rntgroup.messaging.in.java.messaging.Notifying;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class RabbitMqNotificationConsumer {

    @RabbitListener(queues = Notifying.CREATE)
    public void consumeCreateEventNotification(Event event) {
        System.out.println(
                "RabbitMq, NOTIFICATION: new event: \n" + event
        );
    }

    @RabbitListener(queues = Notifying.UPDATE)
    public void consumeUpdateEventNotification(Event event) {
        System.out.println(
                "RabbitMq, NOTIFICATION: updated event: \n" + event
        );
    }

    @RabbitListener(queues = Notifying.DELETE)
    public void consumeDeleteEventNotification(Event event) {
        System.out.println(
                "RabbitMq, NOTIFICATION: deleted event: \n" + event
        );
    }

}
