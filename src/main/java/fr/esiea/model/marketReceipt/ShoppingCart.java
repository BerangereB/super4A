package fr.esiea.model.marketReceipt;

import fr.esiea.model.offers.Offer;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.ProductQuantity;
import fr.esiea.model.market.SupermarketCatalog;

import java.util.*;

public class ShoppingCart {

	//TODO : to optimise/readapt -> duplicates
	private final ArrayList<ProductQuantity> items = new ArrayList<ProductQuantity>();
	private Map<String, Double> productQuantities = new HashMap<String,Double>();


	public List<ProductQuantity> getItems() {
		return new ArrayList<ProductQuantity>(items);
	}

	public void addItem(String product) {
		this.addItemQuantity(product, 1.0);
	}

	public void removeItem(String product) {
		this.removeItemQuantity(product, 1.0);
	}

	public Map<String, Double> productQuantities() {
		return productQuantities;
	}


	public void addItemQuantity(String product, double quantity) {
		items.add(new ProductQuantity(product, quantity));
		if (productQuantities.containsKey(product)) {
			productQuantities.put(product, productQuantities.get(product) + quantity);
		} else {
			productQuantities.put(product, quantity);
		}
	}

	//can be private
	public void removeItemQuantity(String product, double quantity) {
		items.remove(new ProductQuantity(product, quantity));
		productQuantities.remove(product);
	}
	//	TODO: unacceptable
	void handleOffers(Receipt receipt, List<Offer> offers, SupermarketCatalog catalog) {
		Map<String, Double> products = productQuantities();

		//pour chaque offre
		loop:
		for (Offer offer : offers) {
			//Vérification de la présence des produits de l'offre dans la copie du caddie
			for (ProductQuantity p : offer.getProducts()) {
				if (!products.containsKey(p.getProduct())) {
					continue loop;
				}
			}

			// Si les produits sont bien présents on applique l'offre et on supprime
			// de la copie du caddie le nombre d'éléments nécessaires
			products = offer.calculateDiscount(products, catalog);

			// On ajoute le Discount au Receipt si les conditions de l'offre sur les
			// quantités ont été vérifiées
			Discount discount = offer.getDiscount();
			if (discount != null) {
				receipt.addDiscount(discount);
			}


		}
	}
}
