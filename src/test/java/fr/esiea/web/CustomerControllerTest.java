package fr.esiea.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esiea.model.marketReceipt.ShoppingCart;
import fr.esiea.web.controllers.CustomerController;
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

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CustomerControllerTest {
	private final String URL = "/supermarket/customers";
	private final ObjectMapper mapper = new ObjectMapper();
	private static ShoppingCart cart1;
	private static ShoppingCart cart2;
	@Autowired
	private MockMvc mvc;

	@Autowired
	private CustomerController controller;

	@BeforeClass
	public static void init(){
		cart1 = new ShoppingCart();
		cart1.addItemQuantity("toothbrush",2);
		cart1.addItemQuantity("toothpaste",1);

		cart2 = new ShoppingCart();
		cart2.addItemQuantity("apples",2.15);
		cart2.addItemQuantity("bananas",2.5);
	}

	@Before
	public void setUp(){
		controller.service.reset();
	}

	@Test
	public void testGetCustomers() throws Exception {
		Map<Integer, ShoppingCart> expected = new LinkedHashMap<>();
		expected.put(1,cart1);
		expected.put(2,cart2);
		String json = mapper.writeValueAsString(expected);
		mvc.perform(MockMvcRequestBuilders.get(URL)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(json));
	}

}
