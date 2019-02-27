package fr.esiea.model.marketReceipt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.ProductQuantity;
import fr.esiea.model.market.SupermarketCatalog;
import fr.esiea.model.offers.Offer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

	@JsonProperty("Products")
	private Map<String, Double> productQuantities = new LinkedHashMap<String,Double>();

	public void addItem(String product) {
		this.addItemQuantity(product, 1.0);
	}
	public Map<String, Double> productQuantities() {
		return productQuantities;
	}


	public void addItemQuantity(String product, double quantity) {
		if (productQuantities.containsKey(product)) {
			productQuantities.put(product, productQuantities.get(product) + quantity);
		} else {
			productQuantities.put(product, quantity);
		}
	}


	public void removeItemQuantity(String product, double quantity) {
		productQuantities.remove(product);
	}

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
