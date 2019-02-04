package fr.esiea.model.Offers;

import fr.esiea.model.Discount;
import fr.esiea.model.Offers.Offer;
import fr.esiea.model.Product;
import fr.esiea.model.SupermarketCatalog;

import java.util.Map;

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

		productQuantities.remove(product);
		return productQuantities;
	}

}
