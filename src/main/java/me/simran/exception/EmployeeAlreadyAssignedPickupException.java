package me.simran.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Creating Custom Exceptions
 */
@ResponseStatus(code = HttpStatus.IM_USED)
public class EmployeeAlreadyAssignedPickupException extends RuntimeException {

    public EmployeeAlreadyAssignedPickupException(String message) {
        super(message);
    }
}