package fr.esiea.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Customer not found - unknown ID")
public class CustomerNotFoundException extends RuntimeException {
}
