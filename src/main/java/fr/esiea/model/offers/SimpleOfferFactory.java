package fr.esiea.model.offers;

import fr.esiea.model.offers.simpleOffers.FiveForAmountOffer;
import fr.esiea.model.offers.simpleOffers.PercentOffer;
import fr.esiea.model.offers.simpleOffers.ThreeForTwoOffer;
import fr.esiea.model.offers.simpleOffers.TwoForAmountOffer;
import fr.esiea.model.market.Product;
public class SimpleOfferFactory {
	public static Offer getOffer(OfferType type, Product p, double argument){
		switch (type){
			case Percent:
				return new PercentOffer(p,argument);
			case ThreeForTwo:
				return new ThreeForTwoOffer(p);
			case TwoForAmount:
				return new TwoForAmountOffer(p,argument);
			case FiveForAmount:
				return new FiveForAmountOffer(p,argument);
			default:
				return null;
		}
	}

}
