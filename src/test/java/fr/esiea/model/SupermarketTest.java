package fr.esiea.model;


import fr.esiea.ReceiptPrinter;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SupermarketTest {

	@Test
	void testSomething() {
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
		Double current = receipt.getTotalPrice();
		Double expected = 2.5 * 1.99;

		assertThat(current).isEqualTo(expected);
	}

	/*
	 * buyTwoToothbrushGetOneFree
	 */
	@Test
	void TwoForAmount() {
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
		Double current = receipt.getTotalPrice();
		Double expected = unitToothbrushPrice;

		assertThat(current).isEqualTo(expected);
	}

	@Test
	void ThreeForTwo(){
		final double 	price = 0.99;
		final int 		quantity = 3;
		final double 	expected = 2 * price;
		final Product 	toothbrush = new Product("toothbrush", ProductUnit.Each);

		SupermarketCatalog catalog = new FakeCatalog();
		Teller teller = new Teller(catalog);
		ShoppingCart cart = new ShoppingCart();

		catalog.addProduct(toothbrush, price);
		teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, 0.99);
		cart.addItemQuantity(toothbrush, quantity);

		Receipt receipt = teller.checksOutArticlesFrom(cart);
		double current = receipt.getTotalPrice();

		assertThat(current).as("Receipt total price").isEqualTo(expected);
	}

	@Test
	void fiveForAmount(){
		final double 	price = 0.99;
		final int 		quantity = 4;
		final double 	expected = price;
		final Product 	toothbrush = new Product("toothbrush", ProductUnit.Each);

		SupermarketCatalog catalog = new FakeCatalog();
		Teller teller = new Teller(catalog);
		ShoppingCart cart = new ShoppingCart();

		catalog.addProduct(toothbrush, price);
		teller.addSpecialOffer(SpecialOfferType.FiveForAmount, toothbrush, 0.99);
		cart.addItemQuantity(toothbrush, quantity);
		cart.addItemQuantity(toothbrush, 1);

		Receipt receipt = teller.checksOutArticlesFrom(cart);
		double current = receipt.getTotalPrice();

		assertThat(current).as("Receipt total price").isEqualTo(expected, within(0.001));
	}

	@Test
	void tenPercentDiscount(){
		final double 	price = 5;
		final double 	expected = price * 0.8;
		final Product 	toothbrush = new Product("toothbrush", ProductUnit.Each);

		SupermarketCatalog catalog = new FakeCatalog();
		Teller teller = new Teller(catalog);
		ShoppingCart cart = new ShoppingCart();

		catalog.addProduct(toothbrush, price);
		teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, toothbrush, 20);
		cart.addItem(toothbrush);

		Receipt receipt = teller.checksOutArticlesFrom(cart);
		double current = receipt.getTotalPrice();

		assertThat(current).as("Receipt total price").isEqualTo(expected, within(0.001));
	}

	@Test
	void receiptGetItems(){
		final double 	toothbrush_price = 0.99;
		final int 		toothbrush_quantity = 4;
		final double 	apples_price = 2.99;
		final double 	apples_quantity = 2.5;

		final double 	expected = toothbrush_price + apples_price * apples_quantity;

		final Product 	toothbrush = new Product("toothbrush", ProductUnit.Each);
		final Product 	apples = new Product("apples", ProductUnit.Kilo);

		SupermarketCatalog catalog = new FakeCatalog();
		Teller teller = new Teller(catalog);
		ShoppingCart cart = new ShoppingCart();

		catalog.addProduct(toothbrush, toothbrush_price);
		catalog.addProduct(apples, apples_price);
		teller.addSpecialOffer(SpecialOfferType.FiveForAmount, toothbrush, 0.99);
		cart.addItemQuantity(toothbrush, toothbrush_quantity);
		cart.addItemQuantity(toothbrush, 1);

		cart.addItemQuantity(apples, apples_quantity);

		Receipt receipt = teller.checksOutArticlesFrom(cart);
		double current = receipt.getTotalPrice();

		assertThat(current).as("Receipt total price").isEqualTo(expected, within(0.001));
	}


	/*
	** Buy 3 toothbrushes for the price of two
	 * And 1Kilo of apples
	 */
	@Test
	void receiptPrinter() {
		final double 	price = 0.99;
		final int 		quantity = 3;
		final double 	expected = 2 * price + 2.99;
		final Product 	toothbrush = new Product("toothbrush", ProductUnit.Each);
		final Product 	apples = new Product("apples", ProductUnit.Kilo);

		SupermarketCatalog catalog = new FakeCatalog();
		Teller teller = new Teller(catalog);
		ShoppingCart cart = new ShoppingCart();

		catalog.addProduct(toothbrush, price);
		catalog.addProduct(apples, 2.99);
		teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, 0.99);
		cart.addItemQuantity(toothbrush, quantity);
		cart.addItemQuantity(apples, 1);

		Receipt receipt = teller.checksOutArticlesFrom(cart);
		double current = receipt.getTotalPrice();
		ReceiptPrinter receiptPrinter = new ReceiptPrinter();
		receiptPrinter.printReceipt(receipt);
		assertThat(current).isEqualTo(expected, within(0.001));

	}

	@Test
	void AllSpecialOfferTypeAvailable_receiptDiscount_should_be_null(){
		final double 	toothbrush_price = 0.99; // offer two for amount
		final int 		toothbrush_quantity = 1;

		final double 	spoon_price = 1.99; // offer three for two
		final int 		spoon_quantity = 2;

		final double 	avocado_price = 1.2; //Five for amount
		final int 		avocado_quantity = 4;

		final double 	expected = toothbrush_price * toothbrush_quantity + spoon_price * spoon_quantity + avocado_price * avocado_quantity;

		final Product 	toothbrush = new Product("toothbrush", ProductUnit.Each);
		final Product 	spoon = new Product("spoon", ProductUnit.Each);
		final Product 	avocado = new Product("avocado", ProductUnit.Each);

		SupermarketCatalog catalog = new FakeCatalog();
		Teller teller = new Teller(catalog);
		ShoppingCart cart = new ShoppingCart();

		catalog.addProduct(toothbrush, toothbrush_price);
		catalog.addProduct(spoon, spoon_price);
		catalog.addProduct(avocado, avocado_price);

		teller.addSpecialOffer(SpecialOfferType.TwoForAmount, toothbrush, 1.5);
		teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, spoon,spoon_price);
		teller.addSpecialOffer(SpecialOfferType.FiveForAmount, avocado, 5.5);

		cart.addItemQuantity(toothbrush, toothbrush_quantity);
		cart.addItemQuantity(spoon, spoon_quantity);
		cart.addItemQuantity(avocado, avocado_quantity);

		Receipt receipt = teller.checksOutArticlesFrom(cart);
		double current = receipt.getTotalPrice();

		assertThat(current).as("Receipt total price").isEqualTo(expected, within(0.001));
	}
}
