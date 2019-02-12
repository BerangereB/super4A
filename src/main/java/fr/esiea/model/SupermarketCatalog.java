package fr.esiea.model;


import java.util.Map;

public interface SupermarketCatalog {
	void addProduct(Product product, double price);

	double getUnitPrice(Product product);

	Map<String, Product> getProducts();

	Map<String, Double> getPrices();

}
