package fr.esiea.model.market;

import java.util.Map;

public interface SupermarketCatalog {
	void addProduct(Product product);

	void removeProduct(String name);

	double getUnitPrice(Product product);

	Map<String, Product> getProducts();

}
