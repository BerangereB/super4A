package fr.esiea.model.offers.simpleOffers;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.ProductQuantity;
import fr.esiea.model.offers.Offer;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.SupermarketCatalog;
import fr.esiea.model.offers.OfferType;

import java.util.*;

/**
 * Cette offre s'applique sur un produit : 3 pour le prix de 2
 */
public class ThreeForTwoOffer implements Offer {

	@JsonProperty("Type")
	private final OfferType type = OfferType.ThreeForTwo;
	@JsonProperty("Product")
	public final String product;
	private Discount discount = null;


	public ThreeForTwoOffer(String product) {
		this.product = product;
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

}
