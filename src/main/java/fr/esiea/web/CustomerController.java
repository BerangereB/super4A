package fr.esiea.web;

import fr.esiea.model.market.Product;
import fr.esiea.model.marketReceipt.Receipt;
import fr.esiea.model.marketReceipt.ReceiptPrinter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author Quentin on 24/02/2019
 * @project super4A - fr.esiea.web
 */
@RestController
public class CustomerController {
	@GetMapping(value = "/supermarket/customers/{id}", produces = "text/plain")
	public ResponseEntity getReceipt(@PathVariable final int id) {
		return new ResponseEntity(HttpStatus.OK);
	}
}
