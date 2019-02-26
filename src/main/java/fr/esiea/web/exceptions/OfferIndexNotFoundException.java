package fr.esiea.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Offer index not found")
public class OfferIndexNotFoundException extends RuntimeException  {
}
