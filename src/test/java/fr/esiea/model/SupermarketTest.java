package fr.esiea.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SupermarketTest {

	@Test
	public void testSomething() {
		SupermarketCatalog catalog = new FakeCatalog();
		Product toothbrush = new Product("toothbrush", ProductUnit.Each);
		catalog.addProduct(toothbrush, 0.99);
		Product apples = new Product("apples", ProductUnit.Kilo);
		catalog.addProduct(apples, 1.99);

		ShoppingCart cart = new ShoppingCart();
		cart.addItemQuantity(apples, 2.5);

		Teller teller = new Teller(catalog);
		teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, toothbrush, 10.0);

		Receipt receipt = teller.checksOutArticlesFrom(cart);
		Double price = receipt.getTotalPrice();
		Double expected = 2.5 * 1.99;
		Assertions.assertEquals(expected, price);


		// Todo: complete this test
	}

	@Test
	public void BuyTwoToothbrushGetOneFree() {
		SupermarketCatalog catalog = new FakeCatalog();
		final int quantity = 2;
		final double unitToothbrushPrice = 0.99;

		Product toothbrush = new Product("toothbrush", ProductUnit.Each);
		catalog.addProduct(toothbrush, unitToothbrushPrice);
		ShoppingCart cart = new ShoppingCart();
		cart.addItemQuantity(toothbrush, quantity);

		Teller teller = new Teller(catalog);
		teller.addSpecialOffer(SpecialOfferType.TwoForAmount, toothbrush, unitToothbrushPrice);

		Receipt receipt = teller.checksOutArticlesFrom(cart);
		Double price = receipt.getTotalPrice();
		//System.out.println(price);

		Double expected = unitToothbrushPrice;
		Assertions.assertEquals(expected, price);

	}
}
