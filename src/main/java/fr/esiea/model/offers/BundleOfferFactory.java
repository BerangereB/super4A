package fr.esiea.model.offers;

import fr.esiea.model.offers.bundleOffers.AmountBundleOffer;
import fr.esiea.model.offers.bundleOffers.PercentBundleOffer;
import fr.esiea.model.market.Product;

import java.util.Map;

public class BundleOfferFactory {

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
}

