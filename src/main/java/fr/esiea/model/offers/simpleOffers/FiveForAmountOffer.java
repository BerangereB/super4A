package fr.esiea.model.offers.simpleOffers;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.esiea.model.offers.Offer;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.SupermarketCatalog;
import fr.esiea.model.offers.OfferType;

import java.util.*;

/**
 * Cette offre s'applique sur un produit
 * 5 unités de ce produit au prix de 'argument'€
 */
public class FiveForAmountOffer implements Offer {
	@JsonProperty("Product")
	public final Product product;
	@JsonProperty("Argument")
	public final double argument;
	private Discount discount = null;
	@JsonProperty("Type")
	private final OfferType type = OfferType.FiveForAmount;


	public FiveForAmountOffer(Product product, double argument) {
		this.argument = argument;
		this.product = product;
	}


	@Override
	public Map<Product, Double> calculateDiscount(Map<Product, Double> productQuantities, SupermarketCatalog catalog) {
		double quantity = productQuantities.get(product);
		int quantityAsInt = (int) quantity;

		int numberOfXs = quantityAsInt / 5;

		// Si il y a au moins 5 unités du produit on calcul la réduction
		if (quantityAsInt >= 5) {
			double unitPrice = catalog.getUnitPrice(product);

			double discountTotal = unitPrice * quantity - (argument * numberOfXs + quantityAsInt % 5 * unitPrice);
			discount = new Discount(product.getName(), 5 + " for " + argument, discountTotal);
		}
		// On modifie la quantité du produit dans le caddie
		productQuantities.put(product,(double)quantityAsInt % 5);

		return productQuantities;
	}

	@Override
	public Map<Product,Integer> getProducts()
	{
		Map<Product,Integer> product = new HashMap<Product,Integer>();
		product.put(this.product,1);
		return product;
	}

	@Override
	public Double getArgument() {
		return argument;
	}

	@Override
	public Discount getDiscount() {
		return discount;
	}

	@Override
	public OfferType getType() {
		return type;
	}
}
