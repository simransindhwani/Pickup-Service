package me.simran.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Creating Custom Exceptions
 */
@ResponseStatus(code = HttpStatus.NOT_MODIFIED)
public class PickupNotCancelledException extends RuntimeException {

    public PickupNotCancelledException(String message) {
        super(message);
    }
}
