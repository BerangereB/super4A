package fr.esiea.web.controllers;

import fr.esiea.model.market.Product;
import fr.esiea.web.SupermarketService;
import fr.esiea.web.exceptions.ProductNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

	public SupermarketService service = new SupermarketService();

	@GetMapping(value = "/supermarket/products", produces = "application/json")
	public List<Product> getProducts() {
		return service.getProducts();
	}

	@GetMapping(value = "/supermarket/products/{name}", produces = "application/json")
	public Product getProduct(@PathVariable String name) {
		Product p = service.getProduct(name);
		if(p != null){
			return p;
		}else{
			throw new ProductNotFoundException();
		}
	}

	@PostMapping(value = "/supermarket/products", produces = "application/json")
	public Product addProduct(@RequestBody Product newProduct){
		return service.addProduct(newProduct);

	}

	@DeleteMapping(value = "/supermarket/products/remove/{name}", produces = "application/json")
	public Product removeProduct(@PathVariable String name){
		Product p = service.removeProduct(name);
		if(p!=null){
			return p;
		}else{
			throw new ProductNotFoundException();
		}
	}

}
