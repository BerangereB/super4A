package fr.esiea.model.marketReceipt;


import fr.esiea.model.market.Discount;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.ProductUnit;

import java.util.ArrayList;
import java.util.List;

public class Receipt {
	private List<ReceiptItem> items = new ArrayList<ReceiptItem>();
	private List<Discount> discounts = new ArrayList<Discount>();

	public Double getTotalPrice() {
		double total = 0.0;
		// calcul du prix du caddie entier sans les offres
		for (ReceiptItem item : this.items) {
			total += item.getTotalPrice();
		}
		// prix du caddie avec les offres
		for (Discount discount : this.discounts) {
			total -= discount.getDiscountAmount();
		}
		return total;
	}

	public void addProduct(String p, ProductUnit unit, double quantity, double price, double totalPrice) {
		this.items.add(new ReceiptItem(p,unit, quantity, price, totalPrice));
	}

	public List<ReceiptItem> getItems() {
		return new ArrayList<ReceiptItem>(this.items);
	}

	public void addDiscount(Discount discount) {
		this.discounts.add(discount);
	}

	public List<Discount> getDiscounts() {
		return discounts;
	}
}
