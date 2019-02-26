package fr.esiea.model.offers.bundleOffers;


import com.fasterxml.jackson.annotation.JsonProperty;
import fr.esiea.model.market.ProductQuantity;
import fr.esiea.model.market.SupermarketCatalog;
import fr.esiea.model.offers.Offer;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public abstract class AbstractBundleOffer implements Offer {
	@JsonProperty("Products")
	protected final List<ProductQuantity> products;
	@JsonProperty("Argument")
	protected final double argument;

	public AbstractBundleOffer(List<ProductQuantity> products, double argument) {
		this.products = products;
		this.argument = argument;
	}

	int getNumberOfPacks(List<ProductQuantity> products, Map<String, Double> items){

		int numberOfXs = 100; // max packs

		for(ProductQuantity product : products) {
			int x = (int) product.getQuantity();
			double quantity = items.get(product.getProduct());
			int quantityAsInt = (int)quantity;

			numberOfXs = Math.min(numberOfXs,quantityAsInt/x);
		}

		return numberOfXs;
	}


	double getTotalDiscount(List<ProductQuantity> products, Map<String, Double> items, SupermarketCatalog catalog, int numberOfXs, BiFunction<Double, Double, Double> offer_function){
		double discountTotal = 0;
		for(ProductQuantity productQ : products){
			String product = productQ.getProduct();
			double quantity_offer = productQ.getQuantity();

			double quantity = items.get(product);
			double unitPrice = catalog.getUnitPrice(product);

			// calcul de la réduction totale
			discountTotal += offer_function.apply(quantity_offer,unitPrice);

			// on
			items.put(product,quantity - quantity_offer*numberOfXs);
		}

		return discountTotal;
	}
}
