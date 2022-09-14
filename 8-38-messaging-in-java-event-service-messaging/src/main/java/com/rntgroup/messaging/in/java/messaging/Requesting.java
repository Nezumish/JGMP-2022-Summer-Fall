package com.rntgroup.messaging.in.java.messaging;

public final class Requesting {

    public static final String CREATE = "create-event-request";
    public static final String UPDATE = "update-event-request";
    public static final String DELETE = "delete-event-request";

    private Requesting() {
        throw new UnsupportedOperationException(
                "It's an utility class and can't be instantiated"
        );
    }

}
