package fr.esiea.web;

import fr.esiea.model.market.*;
import fr.esiea.model.marketReceipt.ShoppingCart;
import fr.esiea.model.marketReceipt.Teller;
import fr.esiea.model.offers.BundleOfferFactory;
import fr.esiea.model.offers.Offer;
import fr.esiea.model.offers.OfferType;
import fr.esiea.model.offers.SimpleOfferFactory;

import java.util.*;
import java.util.stream.Collectors;

public class SupermarketService {
	private static SupermarketCatalog catalog;
	private static Teller teller;
	private static List<Offer> inactiveOffers;
	private static Customers clients;
	static{
		reset();
	}

	public static SupermarketCatalog getCatalog(){
		return catalog;
	}

	public static Product getProduct(String name) {
		return catalog.getProducts().get(name);
	}

	public static List<Product> getProducts() {
		return new ArrayList<Product>(catalog.getProducts().values());
	}

	public static List<Offer> getActiveOffers(String type ) {
		List<Offer> res = teller.getOffers();

		switch (type){
			case "simple":
				res = res
					.stream()
					.filter(x->x.getProducts().size() == 1)
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

	public static List<Offer> getInactiveOffers() {
		return inactiveOffers;
	}

	public static Offer deactivateOffer(int index){

		if(index <0 || index >=teller.getOffers().size()) {
			return null;
		}
		Offer o = teller.getOffers().get(index);
		teller.removeOffer(o);
		inactiveOffers.add(o);

		return o;
	}

	public static Offer activateOffer(int index){
		if(index <0 || index >=inactiveOffers.size()) {
			return null;
		}
		Offer o = inactiveOffers.get(index);
		inactiveOffers.remove(index);
		teller.addSpecialOffer(o);

		return o;
	}

	public static void reset() {
		inactiveOffers = new ArrayList<Offer>();

		// init catalog
		catalog = new SimpleSupermarketCatalog();

		Product toothbrush = new Product("Toothbrush", ProductUnit.Each,0.99);
		catalog.addProduct(toothbrush);
		Product toothpaste = new Product("Toothpaste", ProductUnit.Each,0.89);
		catalog.addProduct(toothpaste);
		Product apples = new Product("Apples", ProductUnit.Kilo,1.99);
		catalog.addProduct(apples);
		Product bananas = new Product("Bananas", ProductUnit.Kilo,2.99);
		catalog.addProduct(bananas);

		// init Teller

		teller = new Teller(catalog);

		teller.addSpecialOffer(SimpleOfferFactory.getOffer(OfferType.ThreeForTwo, toothbrush.getName(), 0.0));
		teller.addSpecialOffer(SimpleOfferFactory.getOffer(OfferType.FiveForAmount, toothpaste.getName(), 2.5));

		List<ProductQuantity> productsBundle = new ArrayList<ProductQuantity>();
		productsBundle.add(new ProductQuantity(toothbrush.getName(),2));
		productsBundle.add(new ProductQuantity(toothpaste.getName(),2));
		teller.addSpecialOffer(BundleOfferFactory.getOffer(OfferType.PercentBundle,
			productsBundle,
			20));

		//init Customer
		clients = Customers.INSTANCE;

		//init customers shopping cart
		ShoppingCart cart1 = new ShoppingCart();
		cart1.addItemQuantity(apples.getName(), 2.5);

		ShoppingCart cart2 = new ShoppingCart();
		cart2.addItemQuantity(toothbrush.getName(), 1);
		cart2.addItemQuantity(toothpaste.getName(), 1);

		clients.createCustomer(cart1);
		clients.createCustomer(cart2);


	}

	public static Product removeProduct(String name) {
		Product p = SupermarketService.getProduct(name);
		if(p!=null){
			catalog.deleteProduct(name);
		}
		return p;
	}

	public static Product addProduct(Product p) {
		boolean exists = SupermarketService.getProduct(p.getName()) != null;
		if(!exists){
			catalog.addProduct(p);
			return p;
		}else{
			return null;
		}

	}

	public static Map<Integer, ShoppingCart> getCustomers(){
		return clients.getCustomers();
	}

	public static ShoppingCart getCustomerById(final int id){
		return clients.getShoppingCartById(id);
	}

	//does not handle quantity ^^' TODO: handle quantity
	public static boolean addProductToCart(final int id, final String p){
		try {
			clients.addProductToShoppingCart(id, p);
			return true;
		} catch (Exception e){
			return false;
		}
	}
}
