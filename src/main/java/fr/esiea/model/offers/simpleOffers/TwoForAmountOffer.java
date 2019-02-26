package fr.esiea.model.offers.simpleOffers;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.esiea.model.market.ProductQuantity;
import fr.esiea.model.offers.Offer;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.SupermarketCatalog;
import fr.esiea.model.offers.OfferType;

import java.util.*;

public class TwoForAmountOffer extends SimpleOffers {

	@JsonProperty("Type")
	private final OfferType type = OfferType.TwoForAmount;
	private Discount discount;


	public TwoForAmountOffer(String product, double argument) {
		super(product, argument);
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
	public List<ProductQuantity> getProducts() {
		List<ProductQuantity> product = new ArrayList<ProductQuantity>();
		product.add(new ProductQuantity(this.product, 1.0));
		return product;
	}

	@JsonIgnore
	@Override
	public Discount getDiscount() {
		return discount;
	}

}
