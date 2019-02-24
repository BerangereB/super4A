package fr.esiea.model.offers.simpleOffers;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.esiea.model.offers.Offer;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.SupermarketCatalog;
import fr.esiea.model.offers.OfferType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Offre qui applique une réduction de argument%
 */
public class PercentOffer implements Offer {

	@JsonProperty("Type")
	private final OfferType type = OfferType.Percent;
	@JsonProperty("Product")
	public final String product;
	@JsonProperty("Argument")
	private final double argument;
	private Discount discount = null;



	public PercentOffer(String product, double argument) {
		this.argument = argument;
		this.product = product;
	}

	@JsonIgnore
	@Override
	public Map<String,Integer> getProducts()
	{
		Map<String,Integer> product = new HashMap<String,Integer>();
		product.put(this.product,1);
		return product;
	}

	@JsonIgnore
	@Override
	public Discount getDiscount() {
		return discount;
	}

	@Override
	public Map<String, Double> calculateDiscount(Map<String, Double> productQuantities, SupermarketCatalog catalog) {
		double quantity = productQuantities.get(product);
		double unitPrice = catalog.getUnitPrice(product);
		discount = new Discount(product, argument + "% off", quantity * unitPrice * argument / 100.0);

		// On applique l'offre sur l'ensemble des produits concernés donc on remove le produit du caddie
		productQuantities.remove(product);
		return productQuantities;
	}



	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PercentOffer offer = (PercentOffer) o;
		return Objects.equals(type, offer.type) &&
			argument == offer.argument &&
			discount == offer.discount &&
			Objects.equals(product,offer.product);
	}

	@Override
	public int hashCode() {

		return Objects.hash(type,argument,discount,product);
	}

}
