package fr.esiea.model.market;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.esiea.web.CustomCatalogSerializer;

import java.util.Map;

@JsonSerialize(using = CustomCatalogSerializer.class)
public interface SupermarketCatalog {
	void addProduct(Product product, double price);

	double getUnitPrice(Product product);

	Map<String, Product> getProducts();

	Map<String, Double> getPrices();

}
