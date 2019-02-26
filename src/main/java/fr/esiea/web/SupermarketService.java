package fr.esiea.web;

import fr.esiea.model.market.*;
import fr.esiea.model.marketReceipt.ShoppingCart;
import fr.esiea.model.marketReceipt.Teller;
import fr.esiea.model.offers.BundleOfferFactory;
import fr.esiea.model.offers.Offer;
import fr.esiea.model.offers.OfferType;
import fr.esiea.model.offers.SimpleOfferFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SupermarketService {

	private SupermarketCatalog catalog;
	private Teller teller;
	private List<Offer> inactiveOffers;

	public SupermarketService(){
		reset();
	}

	public SupermarketCatalog getCatalog(){
		return catalog;
	}


	public Product getProduct(String name) {
		return catalog.getProducts().get(name);
	}

	public List<Product> getProducts() {
		return new ArrayList<Product>(catalog.getProducts().values());
	}




	public List<Offer> getActiveOffers(String type ) {
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

	public Product removeProduct(String name) {
		Product p = catalog.getProducts().get(name);
		if(p!=null){
			catalog.deleteProduct(name);
		}
		return p;
	}

	public Product addProduct(Product p) {
		boolean exists = catalog.getProducts().get(p.getName()) != null;
		if(!exists){
			catalog.addProduct(p);
			return p;
		}else{
			return null;
		}

	}


	public void reset(){
		BundleOfferFactory bundleOfferFactory = new BundleOfferFactory();
		SimpleOfferFactory simpleOfferFactory = new SimpleOfferFactory();

		inactiveOffers = new ArrayList<Offer>();
		catalog = new SimpleSupermarketCatalog();
		teller = new Teller(catalog);

		List<ProductQuantity> productsBundle = new ArrayList<ProductQuantity>();
		productsBundle.add(new ProductQuantity("toothbrush",2));
		productsBundle.add(new ProductQuantity("toothpaste",2));

		teller.addSpecialOffer(simpleOfferFactory.getOffer(OfferType.ThreeForTwo, "toothbrush", 0.0));
		teller.addSpecialOffer(simpleOfferFactory.getOffer(OfferType.FiveForAmount, "toothpaste", 2.5));
		teller.addSpecialOffer(bundleOfferFactory.getOffer(OfferType.PercentBundle, productsBundle, 20));
	}

	public Map<Integer, ShoppingCart> getCustomers() {

		return null;
	}

	public ShoppingCart getCustomerById(int id) {
		return null;
	}

	public boolean addProductToCart(int id, String product) {
		return false;
	}

}
