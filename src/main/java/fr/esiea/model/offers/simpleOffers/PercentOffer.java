package fr.esiea.model.offers.simpleOffers;


import com.fasterxml.jackson.annotation.JsonProperty;
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
 * Offre qui applique une réduction de argument%
 */
public class PercentOffer implements Offer {

	@JsonProperty("Product")
	public final Product product;
	@JsonProperty("Argument")
	public final double argument;
	private Discount discount = null;
	@JsonProperty("Type")
	private final OfferType type = OfferType.Percent;


	public PercentOffer(Product product, double argument) {
		this.argument = argument;
		this.product = product;
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
	public Map<Product, Double> calculateDiscount(Map<Product, Double> productQuantities, SupermarketCatalog catalog) {
		double quantity = productQuantities.get(product);
		double unitPrice = catalog.getUnitPrice(product);
		discount = new Discount(product.getName(), argument + "% off", quantity * unitPrice * argument / 100.0);

		// On applique l'offre sur l'ensemble des produits concernés donc on remove le produit du caddie
		productQuantities.remove(product);
		return productQuantities;
	}


	@Override
	public OfferType getType() {
		return type;
	}

}
