package fr.esiea.model.marketReceipt;

import fr.esiea.model.Offers.Offer;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.ProductQuantity;
import fr.esiea.model.market.SupermarketCatalog;

import java.util.*;

public class ShoppingCart {

	private final ArrayList<ProductQuantity> items = new ArrayList<ProductQuantity>();
	private Map<Product, Double> productQuantities = new HashMap<Product,Double>();


	public List<ProductQuantity> getItems() {
		return new ArrayList<ProductQuantity>(items);
	}

	public void addItem(Product product) {
		this.addItemQuantity(product, 1.0);
	}

	public Map<Product, Double> productQuantities() {
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
		Map<Product, Double> products = productQuantities();

		//pour chaque offre
		loop:
		for (Map.Entry<Product[], Offer> offer : offers.entrySet()) {
			//Vérification de la présence des produits de l'offre dans la copie du caddie
			for (Product p : offer.getKey()) {
				if (!products.containsKey(p)) {
					continue loop;
				}
			}

			// Si les produits sont bien présents on applique l'offre et on supprime
			// de la copie du caddie le nombre d'éléments nécessaires
			products = offer.getValue().calculateDiscount(products, catalog);

			// On ajoute le Discount au Receipt si les conditions de l'offre sur les
			// quantités ont été vérifiées
			Discount discount = offer.getValue().getDiscount();
			if (discount != null) {
				receipt.addDiscount(discount);
			}


		}
	}
}
