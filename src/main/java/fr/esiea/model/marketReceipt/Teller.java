package fr.esiea.model.marketReceipt;


import fr.esiea.model.offers.Offer;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.ProductQuantity;
import fr.esiea.model.market.SupermarketCatalog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teller {

	private final SupermarketCatalog catalog;
	private List<Offer> offers = new ArrayList<Offer>();

	public Teller(SupermarketCatalog catalog) {
		this.catalog = catalog;
	}

	public void addSpecialOffer(Offer offer) {
		this.offers.add(offer);
	}

	public Receipt checksOutArticlesFrom(ShoppingCart theCart) {
		Receipt receipt = new Receipt();
		Map<String,Double> productQuantities = theCart.productQuantities();
		for (Map.Entry<String,Double> pq: productQuantities.entrySet()) {
			String p = pq.getKey();
			double quantity = pq.getValue();
			double unitPrice = this.catalog.getUnitPrice(p);
			double price = quantity * unitPrice;
			receipt.addProduct(p,catalog.getProducts().get(p).getUnit(), quantity, unitPrice, price);
		}
		theCart.handleOffers(receipt, this.offers, this.catalog);

		return receipt;
	}

	public List<Offer> getOffers() {
		return offers;
	}

	public void removeOffer(Offer o) {
		offers.remove(o);
	}
}
