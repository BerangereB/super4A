package fr.esiea.model.Offers;

import fr.esiea.model.Discount;
import fr.esiea.model.Product;
import fr.esiea.model.ProductUnit;
import fr.esiea.model.SupermarketCatalog;

import java.util.Map;
import java.util.stream.Collectors;

public class AmountBundleOffer implements Offer {

	public final Map<Product,Integer> products;
	public final double argument;
	private Discount discount = null;


	public AmountBundleOffer(Map<Product,Integer> products, double argument) {
		this.argument = argument;
		this.products = products;
	}

	@Override
	public Product[] getProducts() {
		return products.keySet().toArray(new Product[products.size()]);
	}

	@Override
	public Discount getDiscount() {
		return discount;
	}

	@Override
	public Map<Product, Double> calculateDiscount(Map<Product, Double> items, SupermarketCatalog catalog) {

		boolean checkedQuantities = true;
		for(Map.Entry<Product,Integer> product : products.entrySet()){
			if(product.getValue() > items.get(product.getKey())){
				checkedQuantities = false;
				break;
			}
		}


		if(checkedQuantities){
			double discountTotal = 0;
			for(Map.Entry<Product,Integer> productEntry : products.entrySet()){
				Product product = productEntry.getKey();
				int quantity_offer = productEntry.getValue();

				double quantity = items.get(product);
				double unitPrice = catalog.getUnitPrice(product);

				discountTotal += quantity_offer*unitPrice ;

				items.put(product,quantity - quantity_offer);
			}

			discountTotal -= argument;

			String name = products.keySet().stream().map(Product::getName).collect(Collectors.joining( " & " ));
			Product p = new Product(name, ProductUnit.Each);
			discount = new Discount(p,  "BundleOffer for " + argument, discountTotal);
		}

		return items;
	}
}
