package fr.esiea.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esiea.model.marketReceipt.ShoppingCart;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class CustomerControllerTest {
	private final String URL = "/supermarket/customers";
	private final ObjectMapper mapper = new ObjectMapper();
	private static ShoppingCart cart1;
	private static ShoppingCart cart2;

	@Autowired
	private MockMvc mvc;

	@Before
	public void reset() {
		SupermarketService.INSTANCE.reset();
	}

	@BeforeClass
	public static void init() {
		cart1 = new ShoppingCart();
		cart1.addItemQuantity("toothbrush", 2);
		cart1.addItemQuantity("toothpaste", 1);

		cart2 = new ShoppingCart();
		cart2.addItemQuantity("apples", 2.15);
		cart2.addItemQuantity("bananas", 2.5);
	}


	@Test
	public void testGetCustomers() throws Exception {
		Map<Integer, ShoppingCart> expected = new LinkedHashMap<>();
		expected.put(1, cart1);
		expected.put(2, cart2);
		String json = mapper.writeValueAsString(expected);
		mvc.perform(MockMvcRequestBuilders.get(URL)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(json));
	}

	@Test
	public void testGetCustomer1() throws Exception {
		String json = mapper.writeValueAsString(cart1);
		mvc.perform(MockMvcRequestBuilders.get(URL + "/1")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(json));
	}

	@Test
	public void testGetCustomer0() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(URL + "/0")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().is(404));
	}

	@Test
	public void testGetCustomer1Receipt() throws Exception {
		String expected = "2.87";
		mvc.perform(MockMvcRequestBuilders.get(URL + "/1/receipt")
			.accept(MediaType.TEXT_PLAIN))
			.andExpect(status().isOk())
			.andExpect(content().string(expected));
	}

	@Test
	public void testGetCustomer0Receipt() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(URL + "/0/receipt")
			.accept(MediaType.TEXT_PLAIN))
			.andExpect(status().is(404));
	}

	@Test
	public void testGetCustomer1PrintedReceipt() throws Exception {
		String expected = "toothbrush                          1.98\n" +
			"  0.99 * 2\n" +
			"toothpaste                          0.89\n" +
			"\n" +
			"Total:                              2.87";
		mvc.perform(MockMvcRequestBuilders.get(URL + "/1/printedReceipt")
			.accept(MediaType.TEXT_PLAIN))
			.andExpect(status().isOk())
			.andExpect(content().string(expected));
	}

	@Test
	public void testGetCustomer0PrintedReceipt() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(URL + "/0/printedReceipt")
			.accept(MediaType.TEXT_PLAIN))
			.andExpect(status().is(404));
	}


	@Test
	public void testAddToothbrushesToCart1() throws Exception {
		String p = "{\"Product\": \"toothbrush\",\"Quantity\": 2}";
		mvc.perform(MockMvcRequestBuilders.post(URL + "/1")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(p))
			.andExpect(status().isOk());


		String json = "{\"1\":{\"Products\":{\"toothbrush\":4.0,\"toothpaste\":1.0}},\"2\":{\"Products\":{\"apples\":2.15,\"bananas\":2.5}}}";
		mvc.perform(MockMvcRequestBuilders.get(URL).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(json));

	}

	@Test
	public void testAddProductToCart0() throws Exception {
		String p = "{\"Product\": \"toothbrush\",\"Quantity\": 2}";
		mvc.perform(MockMvcRequestBuilders.post(URL + "/0")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(p))
			.andExpect(status().is(404));
	}


	@Test
	public void testRemoveToothbrushesFromCart1() throws Exception {
		String p = "{\"Product\": \"toothbrush\",\"Quantity\": 2}";
		mvc.perform(MockMvcRequestBuilders.delete(URL + "/1")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(p))
			.andExpect(status().isOk());

		Map<Integer, ShoppingCart> customers = new LinkedHashMap<Integer, ShoppingCart>();
		cart1.removeItemQuantity("toothbrush",2);
		customers.put(1,cart1);
		customers.put(2,cart2);

		String json = mapper.writeValueAsString(customers);
		mvc.perform(MockMvcRequestBuilders.get(URL).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(json));

	}

	@Test
	public void testDeleteProductFromCart0() throws Exception {
		String p = "{\"Product\": \"toothbrush\",\"Quantity\": 2}";
		mvc.perform(MockMvcRequestBuilders.delete(URL + "/0")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(p))
			.andExpect(status().is(404));
	}

}
