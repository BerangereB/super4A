package fr.esiea.model.Offers.simpleOffers;


import fr.esiea.model.Offers.Offer;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.SupermarketCatalog;

import java.util.Map;

/**
 * Offre qui applique une réduction de argument%
 */
public class PercentOffer implements Offer {

	public final Product product;
	public final double argument;
	private Discount discount = null;


	public PercentOffer(Product product, double argument) {
		this.argument = argument;
		this.product = product;
	}

	@Override
	public Product[] getProducts() {
		return new Product[]{product};
	}

	@Override
	public Discount getDiscount() {
		return discount;
	}

	@Override
	public Map<Product, Double> calculateDiscount(Map<Product, Double> productQuantities, SupermarketCatalog catalog) {
		double quantity = productQuantities.get(product);
		double unitPrice = catalog.getUnitPrice(product);
		discount = new Discount(product, argument + "% off", quantity * unitPrice * argument / 100.0);

		// On applique l'offre sur l'ensemble des produits concernés donc on remove le produit du caddie
		productQuantities.remove(product);
		return productQuantities;
	}

}
