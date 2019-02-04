package fr.esiea.model.Offers;

import fr.esiea.model.Discount;
import fr.esiea.model.Product;
import fr.esiea.model.SupermarketCatalog;

import java.util.Map;

public interface Offer {

	Product[] getProducts();

	Discount getDiscount();

	Map<Product, Double> calculateDiscount(Map<Product, Double> items, SupermarketCatalog catalog);
}
