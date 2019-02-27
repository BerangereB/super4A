package fr.esiea.web.controllers;

import fr.esiea.model.market.Product;
import fr.esiea.web.SupermarketService;
import fr.esiea.web.exceptions.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supermarket/products")
public class ProductController {

	public SupermarketService service = SupermarketService.INSTANCE;
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@GetMapping(value = "", produces = "application/json")
	public List<Product> getProducts() {
		return service.getProducts();
	}

	@GetMapping(value = "/{name}", produces = "application/json")
	public Product getProduct(@PathVariable String name) {
		Product p = service.getProduct(name);
		if (p != null) {
			return p;
		} else {
			throw new ProductNotFoundException();
		}
	}

	@PostMapping(value = "", produces = "application/json")
	public Product addProduct(@RequestBody Product newProduct) {
		service.addProduct(newProduct);
		return newProduct;

	}

	@DeleteMapping(value = "/{name}", produces = "application/json")
	public Product removeProduct(@PathVariable String name) {
		Product p = service.removeProduct(name);
		if (p != null) {
			return p;
		} else {
			throw new ProductNotFoundException();
		}
	}

}
