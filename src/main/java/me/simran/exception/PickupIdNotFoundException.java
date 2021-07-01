package me.simran.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Creating Custom Exceptions
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
/** Setting the error code to 404*/
public class PickupIdNotFoundException extends RuntimeException {

        public PickupIdNotFoundException(String message) {
            super(message);
        }
}
