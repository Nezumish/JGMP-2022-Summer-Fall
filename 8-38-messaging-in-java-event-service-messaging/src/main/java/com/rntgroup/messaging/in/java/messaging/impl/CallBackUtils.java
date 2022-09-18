package com.rntgroup.messaging.in.java.messaging.impl;

import com.rntgroup.messaging.in.java.messaging.EventAction;

import java.util.function.Consumer;
import java.util.function.Function;

public final class CallBackUtils {

    private CallBackUtils() {
        throw new UnsupportedOperationException("It's an utility class and can't be instantiated");
    }

    public static <T> Consumer<T> successfulRequestConsumer(String brokerName, EventAction action) {
        return e -> System.out.printf("%s: SUCCEEDED TO REQUEST TO %s the event:\n %s \n", brokerName, action, e);
    }

    public static <T extends Throwable> Function<T, Void> throwableFunction(String brokerName, EventAction action) {
        return t -> Void.TYPE.cast(System.out.printf("%s: FAILED TO PERFORM %s ACTION\n", brokerName, action));
    }

}
