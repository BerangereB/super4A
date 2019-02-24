package fr.esiea.model.marketReceipt;

import fr.esiea.model.market.ProductUnit;

public class ReceiptItem {
	private final String product;
	private final ProductUnit unit;
	private final double price;
	private double totalPrice;
	private final double quantity;

	public ReceiptItem(String p, ProductUnit unit, double quantity, double price, double totalPrice) {
		this.product = p;
		this.unit = unit;
		this.quantity = quantity;
		this.price = price;
		this.totalPrice = totalPrice;
	}

	public double getPrice() {
		return this.price;
	}

	public String getProduct() {
		return product;
	}

	public ProductUnit getUnit() {
		return unit;
	}

	public double getQuantity() {
		return quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}
}
