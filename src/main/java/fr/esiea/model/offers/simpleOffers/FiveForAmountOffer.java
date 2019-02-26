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
 * Cette offre s'applique sur un produit
 * 5 unités de ce produit au prix de 'argument'€
 */
public class FiveForAmountOffer implements Offer {
	@JsonProperty("Type")
	private final OfferType type = OfferType.FiveForAmount;

	@JsonProperty("Product")
	public final String product;

	@JsonProperty("Argument")
	private final double argument;
	private Discount discount = null;



	public FiveForAmountOffer(String product, double argument) {
		this.argument = argument;
		this.product = product;
	}


	@Override
	public Map<String, Double> calculateDiscount(Map<String, Double> productQuantities, SupermarketCatalog catalog) {
		double quantity = productQuantities.get(product);
		int quantityAsInt = (int) quantity;

		int numberOfXs = quantityAsInt / 5;

		// Si il y a au moins 5 unités du produit on calcul la réduction
		if (quantityAsInt >= 5) {
			double unitPrice = catalog.getUnitPrice(product);

			double discountTotal = unitPrice * quantity - (argument * numberOfXs + quantityAsInt % 5 * unitPrice);
			discount = new Discount(product, 5 + " for " + argument, discountTotal);
		}
		// On modifie la quantité du produit dans le caddie
		productQuantities.put(product, (double) quantityAsInt % 5);

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

