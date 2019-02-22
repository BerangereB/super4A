package fr.esiea.model.marketReceipt;


import fr.esiea.model.Offers.Offer;
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
		List<ProductQuantity> productQuantities = theCart.getItems();
		for (ProductQuantity pq: productQuantities) {
			Product p = pq.getProduct();
			double quantity = pq.getQuantity();
			double unitPrice = this.catalog.getUnitPrice(p);
			double price = quantity * unitPrice;
			receipt.addProduct(p, quantity, unitPrice, price);
		}
		theCart.handleOffers(receipt, this.offers, this.catalog);

		return receipt;
	}

}
