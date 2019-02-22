package fr.esiea.model.Offers.bundleOffers;


import fr.esiea.model.Offers.Offer;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.ProductUnit;
import fr.esiea.model.market.SupermarketCatalog;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Cette offre s'applique sur un ensemble de produits
 * Chaque lot subit une réduction de argument%
 */
public class PercentBundleOffer extends AbstractBundleOffer implements Offer {

	public final Map<Product,Integer> products;
	public final double argument;
	private Discount discount = null;


	public PercentBundleOffer(Map<Product,Integer> products, double argument) {
		this.argument = argument;
		this.products = products;
	}

	@Override
	public Set<Product> getProducts() {
		return products.keySet();
	}
	@Override
	public Discount getDiscount() {
		return discount;
	}

	@Override
	public Map<Product, Double> calculateDiscount(Map<Product, Double> items, SupermarketCatalog catalog) {

		// Vérification des quantités
		boolean checkedQuantities = true;
		for(Map.Entry<Product,Integer> product : products.entrySet()){
			if(product.getValue() > items.get(product.getKey())){
				checkedQuantities = false;
				break;
			}
		}

		if(checkedQuantities){

			// On compte le nombre de lots
			int numberOfXs =getNumberOfPacks(products,items);

			BiFunction<Integer,Double, Double> offer_function = (X, Y) -> X * Y * numberOfXs * (100 - argument)/100;

			double discountTotal = getTotalDiscount(products,items,catalog,numberOfXs,offer_function);

			String name = products.keySet().stream().map(Product::getName).collect(Collectors.joining( " & " ));
			Product p = new Product(name, ProductUnit.Each);
			discount = new Discount(p,  "BundleOffer for " + argument, discountTotal);
		}

		return items;
	}
}

