package fr.esiea.web.controllers;

import fr.esiea.model.market.ProductQuantity;
import fr.esiea.model.marketReceipt.Receipt;
import fr.esiea.model.marketReceipt.ShoppingCart;
import fr.esiea.web.SupermarketService;
import fr.esiea.web.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Quentin on 24/02/2019
 * @project super4A - fr.esiea.web
 */
@RequestMapping("/supermarket/customers")
@RestController
public class CustomerController {

	public SupermarketService service = SupermarketService.INSTANCE;

	@GetMapping(value = "", produces = "application/json")
	public Map<Integer, ShoppingCart> getCustomers() {
		return service.getCustomers();
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ShoppingCart getCustomerById(@PathVariable final int id) {
		ShoppingCart cart = service.getCustomerById(id);
		if(cart != null){
			return cart;
		} else {
			throw new CustomerNotFoundException();
		}
	}


	@PostMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
	public ProductQuantity addProductToCart(@PathVariable final int id, @RequestBody ProductQuantity pq) {
		boolean res = service.addProductToCart(id, pq);
		if(res){
			return pq;
		} else {
			throw new CustomerNotFoundException();
		}
	}

	// To perfect
	@DeleteMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
	public ProductQuantity removeProductToCart(@PathVariable final int id, @RequestBody ProductQuantity pq) {
		boolean res = service.removeProductToCart(id, pq);
		if(res){
			return pq;
		} else {
			throw new CustomerNotFoundException();
		}
	}


	@GetMapping(value = "/{id}/receipt", produces = "text/plain")
	public String  getReceipt(@PathVariable final int id) {
		Receipt receipt = service.getCustomerReceipt(id);
		if(receipt != null){
			return receipt.getTotalPrice().toString();
		} else {
			throw new CustomerNotFoundException();
		}
	}

	@GetMapping(value = "/{id}/printedReceipt", produces = "text/plain")
	public String getPrintedReceipt(@PathVariable final int id) {
		String receipt = service.getCustomerPrintedReceipt(id);
		if(receipt != null){
			return receipt;
		} else {
			throw new CustomerNotFoundException();
		}
	}
}
