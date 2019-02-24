package fr.esiea.model.market;



public class Discount {
	private final String description;
	private final double discountAmount;
	private final String productName;

	public Discount(String productName, String description, double discountAmount) {
		this.productName = productName;
		this.description = description;
		this.discountAmount = discountAmount;
	}


	public String getDescription() {
		return description;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public String getProductName() {
		return productName;
	}

}
