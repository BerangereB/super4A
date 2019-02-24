package fr.esiea.model.offers.simpleOffers;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.esiea.model.offers.Offer;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.SupermarketCatalog;
import fr.esiea.model.offers.OfferType;

import java.util.*;

public class TwoForAmountOffer implements Offer {

	@JsonProperty("Type")
	private final OfferType type = OfferType.TwoForAmount;
	@JsonProperty("Product")
	public final String product;
	@JsonProperty("Argument")
	private final double argument;
	private Discount discount;


	public TwoForAmountOffer(String product, double argument) {
		this.argument = argument;
		this.product = product;
	}

	@Override
	public Map<String, Double> calculateDiscount(Map<String, Double> productQuantities, SupermarketCatalog catalog) {
		double quantity = productQuantities.get(product);
		double unitPrice = catalog.getUnitPrice(product);
		int quantityAsInt = (int) quantity;

		int numberOfXs = quantityAsInt / 2;

		if (quantityAsInt >= 2) {
			double total = argument * numberOfXs + quantityAsInt % 2 * unitPrice;
			double discountN = unitPrice * quantity - total;
			discount = new Discount(product, "2 for " + argument, discountN);
		}

		productQuantities.put(product,(double)quantityAsInt % 2);

		return productQuantities;

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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TwoForAmountOffer offer = (TwoForAmountOffer) o;
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
