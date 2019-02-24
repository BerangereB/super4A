package fr.esiea.model.market;

public class ProductQuantity {
	private final String product;
	private final double quantity;

	public ProductQuantity(String product, double weight) {
		this.product = product;
		this.quantity = weight;
	}

	public String getProduct() {
		return product;
	}

	public double getQuantity() {
		return quantity;
	}
}
