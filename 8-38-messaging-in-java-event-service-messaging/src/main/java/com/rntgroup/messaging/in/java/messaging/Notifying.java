package com.rntgroup.messaging.in.java.messaging;

public final class Notifying {

    public static final String CREATE = "create-event-notification";
    public static final String UPDATE = "update-event-notification";
    public static final String DELETE = "delete-event-notification";

    private Notifying() {
        throw new UnsupportedOperationException(
                "It's an utility class and can't be instantiated"
        );
    }

}
