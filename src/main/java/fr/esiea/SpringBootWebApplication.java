package fr.esiea;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import fr.esiea.web.CustomCatalogSerializer;
import fr.esiea.model.market.SimpleSupermarketCatalog;
import fr.esiea.model.market.SupermarketCatalog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootWebApplication {

	private static SupermarketCatalog catalog;
	private static ObjectMapper objectMapper;

	public static void main(String[] args) {

		catalog = new SimpleSupermarketCatalog();

		//Configuration MAPPER JSON
		objectMapper = new ObjectMapper();

		SpringApplication.run(SpringBootWebApplication.class, args);
	}

	@GetMapping("/supermarket/products")
	public static String getProducts() {

		try {
			String res = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(catalog);
			System.out.println(res);
			return res;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	


}
