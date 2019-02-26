package fr.esiea.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.ProductQuantity;
import fr.esiea.model.market.ProductUnit;
import fr.esiea.model.offers.BundleOfferFactory;
import fr.esiea.model.offers.Offer;
import fr.esiea.model.offers.OfferType;
import fr.esiea.model.offers.SimpleOfferFactory;
import fr.esiea.web.controllers.OffersController;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class OffersControllerTest {

	private static Offer offer1;
	private static Offer offer2;
	private static Offer offer3;

	private final String URL = "/supermarket/offers";
	private final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private MockMvc mvc;

	@Autowired
	private OffersController controller;

	@Before
	public void setUp(){
		controller.service.reset();
	}

	@BeforeClass
	public static void initOffers(){
		SimpleOfferFactory simpleOfferFactory = new SimpleOfferFactory();
		BundleOfferFactory bundleOfferFactory = new BundleOfferFactory();

		Product toothbrush = new Product("toothbrush", ProductUnit.Each,0.99);
		Product toothpaste = new Product("Toothpaste", ProductUnit.Each,0.89);
		List<ProductQuantity> productsBundle = new ArrayList<ProductQuantity>();
		productsBundle.add(new ProductQuantity(toothbrush.getName(),2));
		productsBundle.add(new ProductQuantity(toothpaste.getName(),2));

		offer1 = simpleOfferFactory.getOffer(OfferType.ThreeForTwo, toothbrush.getName(), 0.0);
		offer2 = simpleOfferFactory.getOffer(OfferType.FiveForAmount, toothpaste.getName(), 2.5);
		offer3 = bundleOfferFactory.getOffer(OfferType.PercentBundle, productsBundle, 20);

	}

	@Test
	public void testDisplayAllOffers() throws Exception {
		List<Offer> offers = Arrays.asList(offer1,offer2,offer3);
		String json = mapper.writeValueAsString(offers);
		mvc.perform(MockMvcRequestBuilders.get(URL + "/active").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(json));
	}

	@Test
	public void testDisplaySimpleOffers() throws Exception {

		List<Offer> expected = Arrays.asList(offer1,offer2);

		String json = mapper.writeValueAsString(expected);
		mvc.perform(MockMvcRequestBuilders.get(URL + "/active/simple").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(json));

	}

	@Test
	public void testDisplayInactiveOffersAfterDeactivateOne() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get(URL + "/deactivate/0").accept(MediaType.APPLICATION_JSON));

		List<Offer> offers = Arrays.asList(offer2,offer3);
		String json = mapper.writeValueAsString(offers);
		mvc.perform(MockMvcRequestBuilders.get(URL + "/active").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(json));
	}




	@Test
	public void testDisplayBundleOffers() throws Exception {
		String json = mapper.writeValueAsString(offer3);
		mvc.perform(MockMvcRequestBuilders.get(URL + "/active/bundle").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string("[" + json + "]"));
	}




	@Test
	public void testDeactivateOffer_atIndex_0_inActiveOffers() throws Exception {
		String json = mapper.writeValueAsString(offer1);
		mvc.perform(MockMvcRequestBuilders.get(URL + "/deactivate/0")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(json));

		List<Offer> expected = Arrays.asList(offer2,offer3);
		json = mapper.writeValueAsString(expected);
		mvc.perform(MockMvcRequestBuilders.get(URL + "/active").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(json));

		json = mapper.writeValueAsString(offer1);
		mvc.perform(MockMvcRequestBuilders.get(URL + "/inactive").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string("[" + json + "]"));
	}

	@Test
	public void testDeactivateOffer_atIndex_3_inActiveOffers_return_exception() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(URL + "/deactivate/3")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().is(404));
	}



	@Test
	public void testActivateOffer_atIndex_0_inInactiveOffers() throws Exception {
		testDeactivateOffer_atIndex_0_inActiveOffers();

		String json = mapper.writeValueAsString(offer1);
		mvc.perform(MockMvcRequestBuilders.get(URL + "/activate/0")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(json));

		List<Offer> expected = Arrays.asList(offer2,offer3,offer1);
		json = mapper.writeValueAsString(expected);
		mvc.perform(MockMvcRequestBuilders.get(URL + "/active").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(json));

		mvc.perform(MockMvcRequestBuilders.get(URL + "/inactive").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string("[]"));
	}

	@Test
	public void testActivateOffer_atIndex_0_inInactiveOffers_while_no_offer_inactive_return_exception() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(URL + "/activate/0")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().is(404));
	}

}

