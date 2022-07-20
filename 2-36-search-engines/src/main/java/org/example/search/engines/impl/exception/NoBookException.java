package org.example.search.engines.impl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoBookException extends RuntimeException {
    public NoBookException(String bookId) {
        super(String.format("Books %s doesn't exist", bookId));
    }
}
