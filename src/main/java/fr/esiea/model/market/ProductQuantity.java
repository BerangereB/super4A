package fr.esiea.model.market;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProductQuantity pq = (ProductQuantity) o;
		return Objects.equals(product, pq.product) &&
			quantity == pq.quantity;

	}

	@Override
	public int hashCode() {

		return Objects.hash(product,quantity);
	}
}
