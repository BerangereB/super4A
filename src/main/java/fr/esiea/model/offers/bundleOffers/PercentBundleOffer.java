package fr.esiea.model.offers.bundleOffers;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.esiea.model.offers.Offer;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.SupermarketCatalog;
import fr.esiea.model.offers.OfferType;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Cette offre s'applique sur un ensemble de produits
 * Chaque lot subit une réduction de argument%
 */
public class PercentBundleOffer extends AbstractBundleOffer implements Offer {

	@JsonProperty("Type")
	private final OfferType type = OfferType.PercentBundle;
	@JsonProperty("Products")
	private final Map<String,Integer> products;
	@JsonProperty("Argument")
	private final double argument;
	private Discount discount = null;


	// TODO: refactor
	public PercentBundleOffer(Map<String,Integer> products, double argument) {
		this.argument = argument;
		this.products = products;
	}

	@Override
	public Map<String,Integer> getProducts() {
		return products;
	}


	@JsonIgnore
	@Override
	public Discount getDiscount() {
		return discount;
	}

	@Override
	public Map<String, Double> calculateDiscount(Map<String, Double> items, SupermarketCatalog catalog) {

		// Vérification des quantités
		boolean checkedQuantities = true;
		for(Map.Entry<String,Integer> product : products.entrySet()){
			if(product.getValue() > items.get(product.getKey())){
				checkedQuantities = false;
				break;
			}
		}

		if(checkedQuantities){

			// On compte le nombre de lots
			int numberOfXs =getNumberOfPacks(products,items);

			BiFunction<Integer,Double, Double> offer_function = (X, Y) -> X * Y * numberOfXs * (100 - argument)/100;

			double discountTotal = getTotalDiscount(products,items,catalog,numberOfXs,offer_function);

			String name = String.join(" & ", products.keySet());
			discount = new Discount(name,  "BundleOffer for " + argument, discountTotal);
		}

		return items;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PercentBundleOffer offer = (PercentBundleOffer) o;
		return Objects.equals(type, offer.type) &&
			argument == offer.argument &&
			discount == offer.discount &&
			Objects.equals(products,offer.products);
	}

	@Override
	public int hashCode() {

		return Objects.hash(type,argument,discount,products);
	}

}

