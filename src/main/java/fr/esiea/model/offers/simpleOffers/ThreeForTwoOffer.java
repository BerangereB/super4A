package fr.esiea.model.offers.simpleOffers;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.esiea.model.market.ProductQuantity;
import fr.esiea.model.offers.Offer;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.SupermarketCatalog;
import fr.esiea.model.offers.OfferType;

import java.util.*;

/**
 * Cette offre s'applique sur un produit : 3 pour le prix de 2
 */
public class ThreeForTwoOffer extends SimpleOffers {

	@JsonProperty("Type")
	private final OfferType type = OfferType.ThreeForTwo;
	private Discount discount = null;


	public ThreeForTwoOffer(String product) {
		super(product);
	}

	@Override
	public Map<String, Double> calculateDiscount(Map<String, Double> productQuantities, SupermarketCatalog catalog) {
		double quantity = productQuantities.get(product);
		double unitPrice = catalog.getUnitPrice(product);
		int quantityAsInt = (int) quantity;

		int numberOfXs = quantityAsInt / 3;

		if (quantityAsInt > 2) {
			double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
			discount = new Discount(product, "3 for 2", discountAmount);
		}

		productQuantities.put(product,(double)quantityAsInt%3);

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


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ThreeForTwoOffer offer = (ThreeForTwoOffer) o;
		return Objects.equals(type, offer.type) &&
			discount == offer.discount &&
			Objects.equals(product,offer.product);
	}

	@Override
	public int hashCode() {

		return Objects.hash(type,discount,product);
	}

}
