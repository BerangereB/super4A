package fr.esiea;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import fr.esiea.model.CustomCatalogSerializer;
import fr.esiea.model.SimpleSupermarketCatalog;
import fr.esiea.model.SupermarketCatalog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@RestController
public class SpringBootWebApplication {

	private static SupermarketCatalog catalog;
	private static ObjectMapper objectMapper;

	public static void main(String[] args) {

		catalog = new SimpleSupermarketCatalog();

		//Configuration MAPPER JSON
		objectMapper = new ObjectMapper();
		CustomCatalogSerializer serializer = new CustomCatalogSerializer(SupermarketCatalog.class);

		SimpleModule module = new SimpleModule("Catalog Serializer",new Version(2,9,8,null,null,null));
		module.addSerializer(SupermarketCatalog.class,serializer);
		objectMapper.registerModule(module);

		SpringApplication.run(SpringBootWebApplication.class, args);
	}

	@GetMapping("/supermarket/products")
	public static String getProducts() {

		try {
			System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(catalog));

			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(catalog);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}


}
