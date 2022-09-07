package com.rntgroup.big.data.task6.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The most dumb domain object
 */
@Document("bottles")
public final class Bottle {

    public static final String TOPIC = "secret-messages";

    @Id
    private String id;

    private String message;

    public Bottle() {
        this(null, null);
    }

    public Bottle(String message) {
        this(null, message);
    }

    public Bottle(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Bottle: " + this.getMessage();
    }

}
