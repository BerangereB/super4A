package fr.esiea;

import fr.esiea.model.market.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class ProductController {

	@GetMapping(value = "/supermarket/products", produces = "application/json")
	public List<Product> getProducts() {
		return SupermarketService.getProducts();
	}

	@GetMapping(value = "/supermarket/products/{name}", produces = "application/json")
	public Product readProduct(@PathVariable String name) {
		Product p = SupermarketService.getProduct(name);
		if(p!=null){
			return p;
		}else{
			throw new ProductNotFoundException();
		}
	}

}


@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Product not found")
class ProductNotFoundException extends RuntimeException {

}
