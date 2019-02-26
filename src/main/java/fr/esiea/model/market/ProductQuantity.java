package fr.esiea.model.market;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductQuantity {
	@JsonProperty("Product")
	private final String product;
	@JsonProperty("Quantity")
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
