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

/**
 * Cette offre s'applique sur un produit : 3 pour le prix de 2
 */
public class ThreeForTwoOffer implements Offer {

	public final Product product;
	private Discount discount = null;
	private final OfferType type = OfferType.ThreeForTwo;


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
	public Map<Product,Integer> getProducts()
	{
		Map<Product,Integer> product = new HashMap<Product,Integer>();
		product.put(this.product,1);
		return product;
	}

	@Override
	public Double getArgument() {
		return 0.0d;
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
