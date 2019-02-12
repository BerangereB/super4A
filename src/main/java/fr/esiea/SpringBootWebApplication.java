package fr.esiea;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.ProductUnit;
import fr.esiea.model.market.SimpleSupermarketCatalog;
import fr.esiea.model.market.SupermarketCatalog;
import fr.esiea.web.JsonNodeFactoryUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@SpringBootApplication
@RestController
public class SpringBootWebApplication {

	private static SupermarketCatalog catalog;
	private static ObjectMapper objectMapper;

	public static void main(String[] args) {

		catalog = new SimpleSupermarketCatalog();
		objectMapper = new ObjectMapper();

		SpringApplication.run(SpringBootWebApplication.class, args);
	}

	@GetMapping("/supermarket/products")
	public static String getProducts() {

		ObjectNode catalog_ = JsonNodeFactoryUtils.getCatalog(catalog);

		return catalog_.toString();
	}

	@GetMapping("/supermarket/products/{name}")
	public static String readProduct(@PathVariable String name) {

		Product p = catalog.getProducts().get(name);
		double price = catalog.getPrices().get(name);

		ObjectNode productAndPrice = JsonNodeFactoryUtils.getProduct(p.getName(),p.getUnit(),catalog.getUnitPrice(p));
		return productAndPrice.toString();
	}

	@GetMapping("/supermarket/products/add")
	public static RedirectView addProduct(@RequestParam("name") String name,@RequestParam("unit") String unit,@RequestParam("price") String price) {

		Product p = new Product(name, ProductUnit.valueOf(unit));
		catalog.addProduct(p,Double.valueOf(price));

		ObjectNode productAndPrice = JsonNodeFactoryUtils.getProduct(p.getName(),p.getUnit(),catalog.getUnitPrice(p));
		return new RedirectView("/supermarket/products");
	}

	@RequestMapping("/supermarket/products/remove/{name}")
	public static RedirectView removeProduct(@PathVariable String name) {

		catalog.removeProduct(name);

		// removeOffersWithThisProduct(String name)

		return new RedirectView("/supermarket/products");
	}
}
