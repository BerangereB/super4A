package fr.esiea.model;

import java.util.Dictionary;
import java.util.Map;

public class FiveForAmountOffer implements Offer {
	public final Product product;
	public final double argument;
	private Discount discount = null;


	public FiveForAmountOffer(Product product, double argument) {
		this.argument = argument;
		this.product = product;
	}

	private double quantity;

	@Override
	public Map<Product, Double> calculateDiscount(Map<Product, Double> productQuantities, SupermarketCatalog catalog) {
		double quantity = productQuantities.get(product);
		double unitPrice = catalog.getUnitPrice(product);
		int quantityAsInt = (int) quantity;

		int numberOfXs = quantityAsInt / 5;

		if (quantityAsInt >= 5) {
			double discountTotal = unitPrice * quantity - (argument * numberOfXs + quantityAsInt % 5 * unitPrice);
			discount = new Discount(product, 5 + " for " + argument, discountTotal);
		}

		productQuantities.put(product,(double)quantityAsInt % 5);

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
