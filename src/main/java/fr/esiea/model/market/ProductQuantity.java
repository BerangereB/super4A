package fr.esiea.model.market;


import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductQuantity {
	@JsonProperty("Product")
	private String product;
	@JsonProperty("Quantity")
	private double quantity;

	public ProductQuantity(){}

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
