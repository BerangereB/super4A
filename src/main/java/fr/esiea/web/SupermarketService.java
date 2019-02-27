package fr.esiea.web;

import fr.esiea.model.market.*;
import fr.esiea.model.marketReceipt.Receipt;
import fr.esiea.model.marketReceipt.ReceiptPrinter;
import fr.esiea.model.marketReceipt.ShoppingCart;
import fr.esiea.model.marketReceipt.Teller;
import fr.esiea.model.offers.BundleOfferFactory;
import fr.esiea.model.offers.Offer;
import fr.esiea.model.offers.OfferType;
import fr.esiea.model.offers.SimpleOfferFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum SupermarketService {
	INSTANCE;

	private SupermarketCatalog catalog;
	private Teller teller;
	private List<Offer> inactiveOffers;
	private Customers customers;

	SupermarketService() {
		reset();
	}

	public void reset() {
		// catalog
		catalog = new SimpleSupermarketCatalog();

		// Teller -- offers
		teller = new Teller(catalog);

		inactiveOffers = new ArrayList<Offer>();

		List<ProductQuantity> productsBundle = new ArrayList<ProductQuantity>();
		productsBundle.add(new ProductQuantity("toothbrush", 2));
		productsBundle.add(new ProductQuantity("toothpaste", 2));

		BundleOfferFactory bundleOfferFactory = new BundleOfferFactory();
		SimpleOfferFactory simpleOfferFactory = new SimpleOfferFactory();
		teller.addSpecialOffer(simpleOfferFactory.getOffer(OfferType.ThreeForTwo, "toothbrush", 0.0));
		teller.addSpecialOffer(simpleOfferFactory.getOffer(OfferType.FiveForAmount, "toothpaste", 2.5));
		teller.addSpecialOffer(bundleOfferFactory.getOffer(OfferType.PercentBundle, productsBundle, 20));


		// Shopping CART
		customers = new Customers();
		ShoppingCart cart = new ShoppingCart();
		cart.addItemQuantity("toothbrush", 2);
		cart.addItemQuantity("toothpaste", 1);

		ShoppingCart cart2 = new ShoppingCart();
		cart2.addItemQuantity("apples", 2.15);
		cart2.addItemQuantity("bananas", 2.5);

		customers.createCustomer(cart);
		customers.createCustomer(cart2);
	}


	/**** PRODUCTS *****/
	public Product getProduct(String name) {
		return catalog.getProducts().get(name);
	}
	public List<Product> getProducts() {
		return new ArrayList<Product>(catalog.getProducts().values());
	}

	public Product removeProduct(String name) {
		Product p = catalog.getProducts().get(name);
		if(p!=null){
			catalog.deleteProduct(name);
		}
		return p;
	}

	public void addProduct(Product p) {
		boolean exists = catalog.getProducts().get(p.getName()) != null;
		catalog.addProduct(p);
	}


	/**** OFFERS *****/
	public List<Offer> getActiveOffers(String type ) {
		List<Offer> res = teller.getOffers();

		switch (type){
			case "simple":
				res = res
					.stream()
					.filter(x -> x.getProducts().size() == 1)
					.collect(Collectors.toList());
				break;
			case "bundle":
				res = res
					.stream()
					.filter(x->x.getProducts().size() > 1)
					.collect(Collectors.toList());
				break;
		}
		return res;
	}

	public List<Offer> getInactiveOffers() {
		return inactiveOffers;
	}

	public Offer deactivateOffer(int index){

		if(index <0 || index >=teller.getOffers().size()) {
			return null;
		}
		Offer o = teller.getOffers().get(index);
		teller.removeOffer(o);
		inactiveOffers.add(o);

		return o;
	}

	public Offer activateOffer(int index){
		if(index <0 || index >=inactiveOffers.size()) {
			return null;
		}
		Offer o = inactiveOffers.get(index);
		inactiveOffers.remove(index);
		teller.addSpecialOffer(o);

		return o;
	}



	/**** CUSTOMERS *****/

	public Map<Integer, ShoppingCart> getCustomers() {
		return customers.getCustomers();
	}

	public ShoppingCart getCustomerById(int id) {
		return customers.getShoppingCartById(id);
	}

	public boolean addProductToCart(int id, ProductQuantity productQuantity) {
		return customers.addProductToShoppingCart(id,productQuantity);
	}

	public boolean removeProductToCart(int id, ProductQuantity pq) {
		return customers.removeProductFromShoppingCart(id,pq);
	}

	public Receipt getCustomerReceipt(int id) {
		ShoppingCart cart = customers.getShoppingCartById(id);
		if(cart != null){
			return teller.checksOutArticlesFrom(cart);
		}
		return null;
	}

	public String getCustomerPrintedReceipt(int id) {
		ShoppingCart cart = customers.getShoppingCartById(id);
		if(cart != null){
			ReceiptPrinter receiptPrinter = new ReceiptPrinter();
			return receiptPrinter.printReceipt(teller.checksOutArticlesFrom(cart));
		}
		return null;
	}
}
