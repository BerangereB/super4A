package fr.esiea.model.offers.bundleOffers;


import fr.esiea.model.market.Product;
import fr.esiea.model.market.SupermarketCatalog;

import java.util.Map;
import java.util.function.BiFunction;

public class AbstractBundleOffer {


	protected int getNumberOfPacks(Map<Product,Integer> products, Map<Product, Double> items){

		int numberOfXs = 100; // max packs

		for(Map.Entry<Product,Integer> productEntry : products.entrySet()) {
			int x = productEntry.getValue();
			double quantity = items.get(productEntry.getKey());
			int quantityAsInt = (int)quantity;

			numberOfXs = Math.min(numberOfXs,quantityAsInt/x);
		}

		return numberOfXs;
	}


	protected double getTotalDiscount(Map<Product,Integer> products, Map<Product, Double> items, SupermarketCatalog catalog, int numberOfXs, BiFunction<Integer, Double, Double> offer_function){
		double discountTotal = 0;
		for(Map.Entry<Product,Integer> productEntry : products.entrySet()){
			Product product = productEntry.getKey();
			int quantity_offer = productEntry.getValue();

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
