package fr.esiea.model.offers;

import fr.esiea.model.market.ProductQuantity;
import fr.esiea.model.offers.bundleOffers.AmountBundleOffer;
import fr.esiea.model.offers.bundleOffers.PercentBundleOffer;

import java.util.List;

public class BundleOfferFactory {

	public static Offer getOffer(OfferType type, List<ProductQuantity> products, double argument){
		switch (type){
			case PercentBundle:
				return new PercentBundleOffer(products,argument);
			default: //AmountBundle:
				return new AmountBundleOffer(products,argument);
		}
	}
}

