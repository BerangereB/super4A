package fr.esiea.model.Offers;

import fr.esiea.model.market.Discount;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.SupermarketCatalog;

import java.util.Map;

public interface Offer {

	Product[] getProducts();

	Discount getDiscount();

	Map<Product, Double> calculateDiscount(Map<Product, Double> items, SupermarketCatalog catalog);
}
