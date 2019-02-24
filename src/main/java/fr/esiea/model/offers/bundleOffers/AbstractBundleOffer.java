package fr.esiea.model.offers.bundleOffers;


import fr.esiea.model.market.SupermarketCatalog;
import java.util.Map;
import java.util.function.BiFunction;

class AbstractBundleOffer {


	int getNumberOfPacks(Map<String, Integer> products, Map<String, Double> items){

		int numberOfXs = 100; // max packs

		for(Map.Entry<String,Integer> productEntry : products.entrySet()) {
			int x = productEntry.getValue();
			double quantity = items.get(productEntry.getKey());
			int quantityAsInt = (int)quantity;

			numberOfXs = Math.min(numberOfXs,quantityAsInt/x);
		}

		return numberOfXs;
	}


	double getTotalDiscount(Map<String, Integer> products, Map<String, Double> items, SupermarketCatalog catalog, int numberOfXs, BiFunction<Integer, Double, Double> offer_function){
		double discountTotal = 0;
		for(Map.Entry<String,Integer> productEntry : products.entrySet()){
			String product = productEntry.getKey();
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
