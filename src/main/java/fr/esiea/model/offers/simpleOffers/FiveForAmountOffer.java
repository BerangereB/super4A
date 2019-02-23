package fr.esiea.model.offers.simpleOffers;

import fr.esiea.model.offers.Offer;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.SupermarketCatalog;

import java.util.*;

/**
 * Cette offre s'applique sur un produit
 * 5 unités de ce produit au prix de 'argument'€
 */
public class FiveForAmountOffer implements Offer {
	public final Product product;
	public final double argument;
	private Discount discount = null;


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
			discount = new Discount(product, 5 + " for " + argument, discountTotal);
		}
		// On modifie la quantité du produit dans le caddie
		productQuantities.put(product,(double)quantityAsInt % 5);

		return productQuantities;
	}

	@Override
	public Set<Product> getProducts() {
		Set<Product> set = new HashSet<Product>();
		set.add(product);
		return set;
	}
	@Override
	public Discount getDiscount() {
		return discount;
	}
}
