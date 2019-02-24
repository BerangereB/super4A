package fr.esiea.model.market;

import java.util.HashMap;
import java.util.Map;

public class SimpleSupermarketCatalog implements SupermarketCatalog {

	private Map<String, Product> products = new HashMap<String, Product>();

	@Override
	public void addProduct(Product product) {
		this.products.put(product.getName(), product);
	}

	@Override
	public void deleteProduct(String productName){
		this.products.remove(productName);
	}

	@Override
	public double getUnitPrice(String p) {
		return this.products.get(p).getPrice();
	}

	@Override
	public Map<String, Product> getProducts() {
		return products;
	}



}

