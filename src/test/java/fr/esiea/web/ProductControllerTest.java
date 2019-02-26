package fr.esiea.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.ProductUnit;
import fr.esiea.web.controllers.ProductController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ProductControllerTest {

	private static Product toothbrush;
	private static Product toothpaste;
	private static Product apples;
	private static Product bananas;

	private final String URL = "/supermarket/products";
	private final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ProductController controller;

	@BeforeEach
	public void setUp(){
		controller.service.reset();
	}


	@BeforeAll
	public static void initProducts(){
		toothbrush = new Product("toothbrush", ProductUnit.Each,0.99);
		toothpaste = new Product("toothpaste", ProductUnit.Each,0.89);
		apples = new Product("apples", ProductUnit.Kilo,1.99);
		bananas = new Product("bananas", ProductUnit.Kilo,2.99);
	}
	@Test
	public void testDisplayProducts() throws Exception {

		List<Product> expected = Arrays.asList(toothbrush,toothpaste,apples,bananas);

		String json = mapper.writeValueAsString(expected);
		mvc.perform(MockMvcRequestBuilders.get(URL).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(json));
	}

	@Test
	public void testDisplayProduct() throws Exception {
		String json = mapper.writeValueAsString(toothpaste);
		mvc.perform(MockMvcRequestBuilders.get(URL + "/toothpaste").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(json));
	}

	@Test
	public void testDisplayProduct_doesnot_exist_return_error_404() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(URL + "/toothpte").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().is(404));
	}



	@Test
	public void testAddSpoonInCatalog() throws Exception {
		Product spoon  = new Product("spoon", ProductUnit.Each,1.99);
		String json = mapper.writeValueAsString(spoon);
		mvc.perform(MockMvcRequestBuilders.post(URL)
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(json))
			.andExpect(status().isOk());

		List<Product> expected = Arrays.asList(toothbrush,toothpaste,apples,bananas,spoon);
		json = mapper.writeValueAsString(expected);
		mvc.perform(MockMvcRequestBuilders.get(URL).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(json));
	}

	@Test
	public void testRemoveToothpasteFromCatalog() throws Exception {
		String json = mapper.writeValueAsString(toothpaste);
		mvc.perform(MockMvcRequestBuilders.delete(URL + "/remove/toothpaste")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(json));

		List<Product> expected = Arrays.asList(toothbrush,apples,bananas);
		json = mapper.writeValueAsString(expected);
		mvc.perform(MockMvcRequestBuilders.get(URL).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(json));
	}

	@Test
	public void testRemoveSpoonFromCatalog_error_expected() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete(URL + "/remove/spoon")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().is(404));
	}
}
