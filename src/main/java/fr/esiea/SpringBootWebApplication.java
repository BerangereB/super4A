package fr.esiea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringBootWebApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}


/*




	@GetMapping(value="/supermarket/offers", produces = "application/json")
	public String getDiscount(){
		Teller teller = SpringBootWebApplication.teller;
		ObjectMapper mapper = SpringBootWebApplication.mapper;

		List<Offer> offers = teller.getOffers();
		ObjectNode offersNode = JsonNodeFactoryUtils.getOffers(offers);

		String ret = null;
		try {
			ret = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(offersNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return ret;

	}
*/
}
