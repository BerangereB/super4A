package fr.esiea.model.market;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class SimpleSupermarketCatalog implements SupermarketCatalog {

	private Map<String, Product> products = new HashMap<String, Product>();

	public SimpleSupermarketCatalog() {
		Product toothbrush = new Product("Toothbrush", ProductUnit.Each,0.99);
		addProduct(toothbrush);
		Product toothpaste = new Product("Toothpaste", ProductUnit.Each,0.89);
		addProduct(toothpaste);
		Product apples = new Product("Apples", ProductUnit.Kilo,1.99);
		addProduct(apples);
		Product bananas = new Product("Bananas", ProductUnit.Kilo,2.99);
		addProduct(bananas);

	}

	public void addProduct(Product product) {
		this.products.put(product.getName(), product);
	}

	@Override
	public void removeProduct(String name) {
		products.remove(name);
	}

	public double getUnitPrice(Product p) {
		return this.products.get(p.getName()).getPrice();
	}

	@Override
	public Map<String, Product> getProducts() {
		return products;
	}


}

