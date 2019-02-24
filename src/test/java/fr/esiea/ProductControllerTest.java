package fr.esiea;

import fr.esiea.model.market.Product;
import fr.esiea.model.market.ProductUnit;
import fr.esiea.web.ProductController;
import fr.esiea.web.SupermarketService;
import fr.esiea.web.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTest {

	@Autowired
	private ProductController controller;

	@Before
	public void reset(){
		SupermarketService.reset();
	}

	@Test
	public void testDisplayProducts(){

		List<Product> expected = Arrays.asList(
			new Product("toothbrush", ProductUnit.Each,0.99),
		    new Product("toothpaste", ProductUnit.Each,0.89),
		    new Product("apples", ProductUnit.Kilo,1.99),
		    new Product("bananas", ProductUnit.Kilo,2.99));

		List<Product> result = controller.getProducts();

		assertTrue(expected.size() == result.size() && expected.containsAll(result));
	}

	@Test
	public void testDisplayProduct(){
		Product p = new Product("toothpaste", ProductUnit.Each,0.89);
		assertThat(p).isEqualTo(controller.getProduct("toothpaste"));
	}

	@Test(expected = ProductNotFoundException.class)
	public void testDisplayProduct_doesnot_exist_return_error_404(){
		controller.getProduct("toothpa");
	}



	@Test
	public void testAddSpoonFromCatalog(){
		Product p  = new Product("spoon", ProductUnit.Each,1.99);
		controller.addProduct(p);
		List<Product> products = SupermarketService.getProducts();

		assertThat(products).contains(p);
	}

	@Test
	public void testRemoveToothpasteFromCatalog(){
		Product p = controller.removeProduct("toothpaste");
		List<Product> products = SupermarketService.getProducts();

		assertThat(products).doesNotContain(p);
	}

	@Test(expected = ProductNotFoundException.class)
	public void testRemoveSpoonFromCatalog_error_expected(){
		controller.removeProduct("spoon");
	}
}
