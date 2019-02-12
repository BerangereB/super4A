package fr.esiea.model;

import java.util.HashMap;
import java.util.Map;

public class SimpleSupermarketCatalog implements SupermarketCatalog{

		private Map<String, Product> products = new HashMap<String, Product>();
		private Map<String, Double> prices = new HashMap<String, Double>();

		public SimpleSupermarketCatalog(){
			Product toothbrush = new Product("Toothbrush", ProductUnit.Each);
			addProduct(toothbrush, 0.99);
			Product toothpaste = new Product("Toothpaste", ProductUnit.Each);
			addProduct(toothpaste, 0.89);
			Product apples = new Product("Apples", ProductUnit.Kilo);
			addProduct(apples, 1.99);
			Product bananas = new Product("Bananas", ProductUnit.Kilo);
			addProduct(bananas, 2.99);

		}
		public void addProduct(Product product, double price) {
			this.products.put(product.getName(), product);
			this.prices.put(product.getName(), price);
		}

		public double getUnitPrice(Product p) {
			return this.prices.get(p.getName());
		}

	@Override
	public Map<String,Product> getProducts() {
		return products;
	}

	@Override
	public Map<String, Double> getPrices() {
		return prices;
	}

}

