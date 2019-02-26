package fr.esiea.model.offers;

import fr.esiea.model.offers.simpleOffers.FiveForAmountOffer;
import fr.esiea.model.offers.simpleOffers.PercentOffer;
import fr.esiea.model.offers.simpleOffers.ThreeForTwoOffer;
import fr.esiea.model.offers.simpleOffers.TwoForAmountOffer;

public class SimpleOfferFactory {
	public Offer getOffer(OfferType type, String p, double argument){
		switch (type){
			case Percent:
				return new PercentOffer(p,argument);
			case ThreeForTwo:
				return new ThreeForTwoOffer(p);
			case TwoForAmount:
				return new TwoForAmountOffer(p,argument);
			default: //FiveForAmount:
				return new FiveForAmountOffer(p,argument);
		}
	}

}
