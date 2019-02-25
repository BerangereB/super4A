package fr.esiea.model.offers;

import fr.esiea.model.market.Discount;
import fr.esiea.model.market.ProductQuantity;
import fr.esiea.model.market.SupermarketCatalog;

import java.util.List;
import java.util.Map;

public interface Offer {

	List<ProductQuantity> getProducts();

	Discount getDiscount();

	Map<String, Double> calculateDiscount(Map<String, Double> items, SupermarketCatalog catalog);
}
