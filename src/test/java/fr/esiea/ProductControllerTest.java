package fr.esiea;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.ProductUnit;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT) //8080 by default
public class ProductControllerTest {

	private final String URL = "http://localhost:8080/supermarket/products";


	@Test
	public void testDisplayProducts() throws IOException {
		HttpUriRequest request = new HttpGet(URL);
		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		String responseContent = EntityUtils.toString(response.getEntity());

		List<Product> result = new ObjectMapper().readValue(responseContent, new TypeReference<List<Product>>(){});

		assertThat(result).isEqualTo(new ArrayList<Product>(SupermarketService.getCatalog().getProducts().values()));
	}



	@Test
	public void testDisplayProduct() throws IOException {

		HttpUriRequest request = new HttpGet(URL + "/toothpaste");
		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		String responseContent = EntityUtils.toString(response.getEntity());

		Product result = new ObjectMapper().readValue(responseContent, Product.class);

		Product p = new Product("toothpaste", ProductUnit.Each,0.89);
		assertThat(p).isEqualTo(result);
	}

	@Test
	public void testDisplayProduct_doesnot_exist_return_error_404() throws IOException {

		HttpUriRequest request = new HttpGet(URL + "/bjk");
		HttpResponse response = HttpClientBuilder.create().build().execute(request);


		int status = response.getStatusLine().getStatusCode();
		assertThat(404).isEqualTo(status);
	}
}
