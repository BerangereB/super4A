package fr.esiea.model.offers;

import fr.esiea.model.market.Discount;
import fr.esiea.model.market.SupermarketCatalog;
import fr.esiea.model.offers.simpleOffers.FiveForAmountOffer;
import fr.esiea.model.offers.simpleOffers.PercentOffer;
import fr.esiea.model.offers.simpleOffers.ThreeForTwoOffer;
import fr.esiea.model.offers.simpleOffers.TwoForAmountOffer;
import fr.esiea.model.market.Product;

import java.util.Map;
import java.util.Set;

public class SimpleOfferFactory implements Offer {
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
