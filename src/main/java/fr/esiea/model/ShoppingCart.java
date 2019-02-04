package fr.esiea.model;


import java.util.*;

public class ShoppingCart {

	private final List<ProductQuantity> items = new ArrayList<>();
	Map<Product, Double> productQuantities = new HashMap<>();


	List<ProductQuantity> getItems() {
		return new ArrayList<>(items);
	}

	void addItem(Product product) {
		this.addItemQuantity(product, 1.0);
	}

	Map<Product, Double> productQuantities() {
		return productQuantities;
	}


	public void addItemQuantity(Product product, double quantity) {
		items.add(new ProductQuantity(product, quantity));
		if (productQuantities.containsKey(product)) {
			productQuantities.put(product, productQuantities.get(product) + quantity);
		} else {
			productQuantities.put(product, quantity);
		}
	}

	void handleOffers(Receipt receipt, Map<Product[], Offer> offers, SupermarketCatalog catalog) {
		Map<Product, Double> items = productQuantities();

		//pour chaque offre
		loop:
		for (Map.Entry<Product[], Offer> offer : offers.entrySet()) {
			//si l'offre est applicable à items i.e. les Produits sont présents
			for (Product p : offer.getKey()) {
				if (!items.containsKey(p)) {
					continue loop;
				}
			}
			// alors on l'applique et on supprime le nombre d'éléments nécessaires
			items = offer.getValue().calculateDiscount(items, catalog);

			Discount discount = offer.getValue().getDiscount();
			if (discount != null) {
				receipt.addDiscount(discount);
			}


		}
	}
}
