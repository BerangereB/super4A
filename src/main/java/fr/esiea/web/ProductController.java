package fr.esiea.web;

import com.sun.deploy.net.HttpResponse;
import fr.esiea.model.market.Product;
import fr.esiea.web.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

	@GetMapping(value = "/supermarket/products", produces = "application/json")
	public List<Product> getProducts() {
		return SupermarketService.getProducts();
	}

	@GetMapping(value = "/supermarket/products/{name}", produces = "application/json")
	public Product getProduct(@PathVariable String name) {
		Product p = SupermarketService.getProduct(name);
		if(p!=null){
			return p;
		}else{
			throw new ProductNotFoundException();
		}
	}

	@PostMapping(value = "/supermarket/products", produces = "application/json")
	public ResponseEntity addProduct(@RequestBody Product newProduct){
		SupermarketService.getCatalog().addProduct(newProduct);
		Product p = SupermarketService.getProduct(newProduct.getName());
		if(p!=null){
			return new ResponseEntity(HttpStatus.OK);
		}else{
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/supermarket/products/{name}", produces = "application/json")
	public ResponseEntity removeProduct(@PathVariable String name){
		SupermarketService.getCatalog().getProducts().remove(name);
		Product p = SupermarketService.getProduct(name);
		if(p==null){
			return new ResponseEntity(HttpStatus.OK);
		}else{
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
