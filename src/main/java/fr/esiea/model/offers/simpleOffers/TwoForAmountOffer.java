package fr.esiea.model.offers.simpleOffers;


import fr.esiea.model.offers.Offer;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.SupermarketCatalog;
import fr.esiea.model.offers.OfferType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TwoForAmountOffer implements Offer {
	public final Product product;
	public final double argument;
	private Discount discount;
	private final OfferType type = OfferType.TwoForAmount;


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
