package fr.esiea.web.controllers;

import fr.esiea.model.market.Product;
import fr.esiea.web.SupermarketService;
import fr.esiea.web.exceptions.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supermarket/products")
public class ProductController {

	public SupermarketService service = SupermarketService.SERVICE;
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@GetMapping(value = "", produces = "application/json")
	public ResponseEntity<List<Product>> getProducts() {
		return new ResponseEntity<List<Product>>(service.getProducts(),HttpStatus.OK);
	}

	@GetMapping(value = "/{name}", produces = "application/json")
	public ResponseEntity<Product> getProduct(@PathVariable String name) {
		Product p = service.getProduct(name);
		if(p != null){
			return new ResponseEntity<Product>(p, HttpStatus.OK);
		}else{
			throw new ProductNotFoundException();
		}
	}

	@PostMapping(value = "", produces = "application/json")
	public ResponseEntity<Product> addProduct(@RequestBody Product newProduct){
		service.addProduct(newProduct);
		return new ResponseEntity<Product>(newProduct,HttpStatus.OK);

	}

	@DeleteMapping(value = "/{name}", produces = "application/json")
	public ResponseEntity<Product> removeProduct(@PathVariable String name){
		Product p = service.removeProduct(name);
		if(p!=null){
			return new ResponseEntity<Product>(p,HttpStatus.OK);
		}else{
			throw new ProductNotFoundException();
		}
	}

}
