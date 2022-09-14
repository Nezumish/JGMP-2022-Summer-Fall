package com.rntgroup.messaging.in.java.messaging.impl;

import com.rntgroup.messaging.in.java.dto.Event;
import com.rntgroup.messaging.in.java.messaging.EventAction;

import java.util.function.Consumer;

public final class CallBackUtils {

    private CallBackUtils() {
        throw new UnsupportedOperationException("It's an utility class and can't be instantiated");
    }

    public static Consumer<Event> successfulRequestConsumer(String brokerName, EventAction action) {
        return e -> System.out.printf("%s: SUCCEEDED TO REQUEST TO %s the event:\n %snn", brokerName, action, e);
    }

    public static Consumer<Event> failedRequestConsumer(String brokerName, EventAction action) {
        return e -> System.out.printf("%s: FAILED TO REQUEST TO %s the event:\n %s\n", brokerName, action, e);
    }

}
