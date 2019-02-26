package fr.esiea.web;

import fr.esiea.model.market.Product;
import fr.esiea.model.marketReceipt.Receipt;
import fr.esiea.model.marketReceipt.ReceiptPrinter;
import fr.esiea.model.marketReceipt.ShoppingCart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * @author Quentin on 24/02/2019
 * @project super4A - fr.esiea.web
 */
@RestController
public class CustomerController {

	SupermarketService service = new SupermarketService();

	@GetMapping(value = "/supermarket/customers/", produces = "application/json")
	public Map<Integer, ShoppingCart> getCustomers() {
		return service.getCustomers();
	}

	@GetMapping(value = "/supermarket/customers/{id}", produces = "application/json")
	public ShoppingCart getCustomerById(@PathVariable final int id) {
		return service.getCustomerById(id);
	}

	// To perfect
	@PostMapping(value = "/supermarket/customers/{id}", produces = "application/json", consumes = "application/json")
	public ResponseEntity addProductToCart(@PathVariable final int id, @RequestBody String product) {
		boolean res = service.addProductToCart(id, product);
		if(res){
			return new ResponseEntity(HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping(value = "/supermarket/customers/{id}", produces = "text/plain")
	public ResponseEntity getReceipt(@PathVariable final int id) {
		//service.
		return new ResponseEntity(HttpStatus.OK);
	}
}
