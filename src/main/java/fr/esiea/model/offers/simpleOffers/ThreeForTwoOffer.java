package fr.esiea.model.Offers.simpleOffers;


import fr.esiea.model.Offers.Offer;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.SupermarketCatalog;

import java.util.Map;

/**
 * Cette offre s'applique sur un produit : 3 pour le prix de 2
 */
public class ThreeForTwoOffer implements Offer {

	public final Product product;
	private Discount discount = null;


	public ThreeForTwoOffer(Product product) {
		this.product = product;
	}

	@Override
	public Map<Product, Double> calculateDiscount(Map<Product, Double> productQuantities, SupermarketCatalog catalog) {
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

	@Override
	public Product[] getProducts() {
		return new Product[]{product};
	}

	@Override
	public Discount getDiscount() {
		return discount;
	}
}
