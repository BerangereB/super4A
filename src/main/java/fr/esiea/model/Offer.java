package fr.esiea.model;

import java.util.Map;

public interface Offer {

	Product[] getProducts();

	Discount getDiscount();

	Map<Product, Double> calculateDiscount(Map<Product, Double> items, SupermarketCatalog catalog);
}
