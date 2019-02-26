package fr.esiea.model.market;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleSupermarketCatalog implements SupermarketCatalog {

	private Map<String, Product> products = new LinkedHashMap<String, Product>();

	public SimpleSupermarketCatalog(){
		Product toothbrush = new Product("toothbrush", ProductUnit.Each,0.99);
		Product toothpaste = new Product("toothpaste", ProductUnit.Each,0.89);
		Product apples = new Product("apples", ProductUnit.Kilo,1.99);
		Product bananas = new Product("bananas", ProductUnit.Kilo,2.99);
		addProduct(toothbrush);
		addProduct(toothpaste);
		addProduct(apples);
	    addProduct(bananas);
	}

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

