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
	private static ObjectMapper mapper;

	public static void main(String[] args) {

		catalog = new SimpleSupermarketCatalog();
		mapper = new ObjectMapper();

		SpringApplication.run(SpringBootWebApplication.class, args);
	}

	@GetMapping(value = "/supermarket/products", produces = "application/json")
	public static String getProducts() {

		ObjectNode catalog_ = JsonNodeFactoryUtils.getCatalog(catalog);

		String ret = null;
		try {
			ret = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(catalog_);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@GetMapping(value = "/supermarket/products/{name}", produces = "application/json")
	public static String readProduct(@PathVariable String name) {
		Product p = catalog.getProducts().get(name);

		ObjectNode productAndPrice = JsonNodeFactoryUtils.getProduct(p.getName(),p.getUnit(),catalog.getUnitPrice(p));

		String ret = null;
		try {
			ret = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(productAndPrice);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@GetMapping(value = "/supermarket/products/update", produces = "application/json")
	public static RedirectView updateProductPrice(@RequestParam("name") String name, @RequestParam("price") Double price) {

		Product p = catalog.getProducts().get(name);
		if(p != null) { // replace value if exists
			catalog.addProduct(p, price);
		}
		return new RedirectView("/supermarket/products");
	}


	@GetMapping(value = "/supermarket/products/add", produces = "application/json")
	public static RedirectView addProduct(@RequestParam("name") String name,@RequestParam("unit") String unit,@RequestParam("price") Double price) {

		unit = unit.substring(0, 1).toUpperCase() + unit.substring(1);

		Product p = new Product(name, ProductUnit.valueOf(unit));
		catalog.addProduct(p,price);
		return new RedirectView("/supermarket/products");
	}

	@RequestMapping(value = "/supermarket/products/remove/{name}", produces = "application/json")
	public static RedirectView removeProduct(@PathVariable String name) {

		catalog.removeProduct(name);

		// removeOffersWithThisProduct(String name)

		return new RedirectView("/supermarket/products");
	}
}