package fr.esiea.web;

import fr.esiea.model.market.Product;
import fr.esiea.web.exceptions.ProductNotFoundException;
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

	@PostMapping(value = "/supermarket/products/add", produces = "application/json")
	public Product addProduct(@RequestBody Product newProduct){
		return SupermarketService.addProduct(newProduct);

	}

	@DeleteMapping(value = "/supermarket/products/remove/{name}", produces = "application/json")
	public Product removeProduct(@PathVariable String name){
		Product p = SupermarketService.removeProduct(name);
		if(p!=null){
			return p;
		}else{
			throw new ProductNotFoundException();
		}
	}

}
