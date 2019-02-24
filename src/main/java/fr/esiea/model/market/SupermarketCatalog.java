package fr.esiea.model.market;

import java.util.Map;

public interface SupermarketCatalog {
	void addProduct(Product product);

	double getUnitPrice(String product);

	Map<String, Product> getProducts();

}
