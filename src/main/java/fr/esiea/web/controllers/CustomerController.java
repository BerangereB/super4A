package fr.esiea.web.controllers;

import fr.esiea.model.market.ProductQuantity;
import fr.esiea.model.marketReceipt.Receipt;
import fr.esiea.model.marketReceipt.ShoppingCart;
import fr.esiea.web.SupermarketService;
import fr.esiea.web.exceptions.CustomerNotFoundException;
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

	public SupermarketService service = SupermarketService.SERVICE;

	@GetMapping(value = "", produces = "application/json")
	public ResponseEntity<Map<Integer, ShoppingCart>> getCustomers() {
		return new ResponseEntity<Map<Integer, ShoppingCart>>(service.getCustomers(),HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<ShoppingCart> getCustomerById(@PathVariable final int id) {
		ShoppingCart cart = service.getCustomerById(id);
		if(cart != null){
			return new ResponseEntity<ShoppingCart>(cart,HttpStatus.OK);
		} else {
			throw new CustomerNotFoundException();
		}
	}


	@PostMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<ProductQuantity> addProductToCart(@PathVariable final int id, @RequestBody ProductQuantity pq) {
		boolean res = service.addProductToCart(id, pq);
		if(res){
			return new ResponseEntity<ProductQuantity>(pq,HttpStatus.OK);
		} else {
			throw new CustomerNotFoundException();
		}
	}

	// To perfect
	@DeleteMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
	public ResponseEntity<ProductQuantity> removeProductToCart(@PathVariable final int id, @RequestBody ProductQuantity pq) {
		boolean res = service.removeProductToCart(id, pq);
		if(res){
			return new ResponseEntity<ProductQuantity>(pq,HttpStatus.OK);
		} else {
			throw new CustomerNotFoundException();
		}
	}


	@GetMapping(value = "/{id}/receipt", produces = "text/plain")
	public ResponseEntity<String>  getReceipt(@PathVariable final int id) {
		Receipt receipt = service.getCustomerReceipt(id);
		if(receipt != null){
			return new ResponseEntity<String>(receipt.getTotalPrice().toString(),HttpStatus.OK);
		} else {
			throw new CustomerNotFoundException();
		}
	}

	@GetMapping(value = "/{id}/printedReceipt", produces = "text/plain")
	public ResponseEntity<String> getPrintedReceipt(@PathVariable final int id) {
		String receipt = service.getCustomerPrintedReceipt(id);
		if(receipt != null){
			return new ResponseEntity<String>(receipt,HttpStatus.OK);
		} else {
			throw new CustomerNotFoundException();
		}
	}
}
