package fr.esiea.model.offers;

import fr.esiea.model.market.Discount;
import fr.esiea.model.market.SupermarketCatalog;
import fr.esiea.model.offers.bundleOffers.AmountBundleOffer;
import fr.esiea.model.offers.bundleOffers.PercentBundleOffer;
import fr.esiea.model.market.Product;

import java.util.Map;
import java.util.Set;

public class BundleOfferFactory implements Offer {

	public static Offer getOffer(OfferType type, Map<Product,Integer> products, double argument){
		switch (type){
			case PercentBundle:
				return new PercentBundleOffer(products,argument);
			case AmountBundle:
				return new AmountBundleOffer(products,argument);
			default:
				return null;
		}
	}

	@Override
	public Set<Product> getProducts() {
		return null;
	}

	@Override
	public Discount getDiscount() {
		return null;
	}

	@Override
	public Map<Product, Double> calculateDiscount(Map<Product, Double> items, SupermarketCatalog catalog) {
		return null;
	}
}

