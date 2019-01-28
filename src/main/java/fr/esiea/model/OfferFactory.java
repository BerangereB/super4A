package fr.esiea.model;

public class OfferFactory {


	public static Offer getOffer(SpecialOfferType offerType, Product product, double argument) {

		if(offerType == SpecialOfferType.FiveForAmount){
			return new FiveForAmountOffer(product,argument);
		}
		else if(offerType == SpecialOfferType.ThreeForTwo){
			return new ThreeForTwoOffer(product,argument);
		}
		else if(offerType == SpecialOfferType.TwoForAmount){
			return new TwoForAmountOffer(product,argument);
		}
		else if(offerType == SpecialOfferType.TenPercentDiscount){
			return new TenPercentOffer(product,argument);
		}

		return null;
	}
}
