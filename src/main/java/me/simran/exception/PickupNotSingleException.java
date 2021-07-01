package me.simran.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Creating Custom Exceptions
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PickupNotSingleException extends RuntimeException {

    public PickupNotSingleException(String message) {
        super(message);
    }
}