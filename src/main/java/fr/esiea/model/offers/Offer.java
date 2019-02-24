package fr.esiea.model.offers;

import fr.esiea.model.market.Discount;
import fr.esiea.model.market.SupermarketCatalog;

import java.util.Map;

public interface Offer {

	Map<String,Integer> getProducts();

	Discount getDiscount();

	Map<String, Double> calculateDiscount(Map<String, Double> items, SupermarketCatalog catalog);
}
