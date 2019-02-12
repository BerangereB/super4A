package fr.esiea.model.Offers.simpleOffers;


import fr.esiea.model.Offers.Offer;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.SupermarketCatalog;

import java.util.Map;

public class TwoForAmountOffer implements Offer {
	public final Product product;
	public final double argument;
	private Discount discount;


	public TwoForAmountOffer(Product product, double argument) {
		this.argument = argument;
		this.product = product;
	}

	@Override
	public Map<Product, Double> calculateDiscount(Map<Product, Double> productQuantities, SupermarketCatalog catalog) {
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

	@Override
	public Product[] getProducts() {
		return new Product[]{product};
	}

	@Override
	public Discount getDiscount() {
		return discount;
	}
}
