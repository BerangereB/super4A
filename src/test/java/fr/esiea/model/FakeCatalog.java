package fr.esiea.model;


import fr.esiea.model.market.Product;
import fr.esiea.model.market.SupermarketCatalog;

import java.util.HashMap;
import java.util.Map;

public class FakeCatalog implements SupermarketCatalog {
	private Map<String, Product> products = new HashMap<String, Product>();

	public void addProduct(Product product) {
		this.products.put(product.getName(), product);
	}

	@Override
	public void removeProduct(String name) {
		products.remove(name);
	}

	//TODO: add try/catch when prices is null > Double to double conversion
	public double getUnitPrice(Product p) {
		return this.products.get(p.getName()).getPrice();
	}

	@Override
	public Map<String, Product> getProducts() {
		return products;
	}
}
