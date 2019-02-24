package fr.esiea;

import fr.esiea.model.market.Product;
import fr.esiea.model.market.ProductUnit;
import fr.esiea.model.market.SimpleSupermarketCatalog;
import fr.esiea.model.market.SupermarketCatalog;
import fr.esiea.model.marketReceipt.Teller;
import fr.esiea.model.offers.BundleOfferFactory;
import fr.esiea.model.offers.OfferType;
import fr.esiea.model.offers.SimpleOfferFactory;

import java.util.*;

public class SupermarketService {
	private static SupermarketCatalog catalog;
	private static Teller teller;

	static{
		catalog = new SimpleSupermarketCatalog();
		teller = new Teller(catalog);

		Product toothbrush = catalog.getProducts().get("toothbrush");
		Product toothpaste = catalog.getProducts().get("toothpaste");

		teller.addSpecialOffer(SimpleOfferFactory.getOffer(OfferType.ThreeForTwo, toothbrush, 0.0));
		teller.addSpecialOffer(SimpleOfferFactory.getOffer(OfferType.FiveForAmount, toothpaste, 2.5));

		Map<Product,Integer> productsBundle = new HashMap<Product,Integer>();
		productsBundle.put(toothbrush,3);
		productsBundle.put(toothpaste,2);
		teller.addSpecialOffer(BundleOfferFactory.getOffer(OfferType.PercentBundle,
			productsBundle,
			20));
	}

	public static SupermarketCatalog getCatalog(){
		return catalog;
	}

	public static Teller getTeller(){
		return teller;
	}


	public static Product getProduct(String name) {
		return catalog.getProducts().get(name);
	}

	public static List<Product> getProducts() {
		return new ArrayList<Product>(catalog.getProducts().values());
	}

	public static Product updateProductPrice(String name, Double price) {
		Product p = catalog.getProducts().get(name);
		if(p!=null) { // replace value if product name exists
			p.setPrice(price);
			catalog.addProduct(p);
			return p;
		}
		return null;
	}

	public static void addProduct(String name, String unit, Double price) {
		unit = unit.substring(0, 1).toUpperCase() + unit.substring(1);
		Product p = new Product(name, ProductUnit.valueOf(unit),price);
		catalog.addProduct(p);
	}

	public static void removeProduct(String name) {
		catalog.removeProduct(name);
		// removeOffersWithThisProduct(String name)
	}
}
