package fr.esiea.model.market;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


public class Product {

	@JsonProperty("Name")
	private String name;
	@JsonProperty("Unit")
	private ProductUnit unit;
	@JsonProperty("Price")
	private double price;

	public Product() {}

	public Product(String name, ProductUnit unit,double price) {
		this.name = name.toLowerCase();
		this.unit = unit;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public ProductUnit getUnit() {
		return unit;
	}

	public double getPrice() {
		return price;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return Objects.equals(name, product.name) &&
			unit == product.unit &&
			price == product.price;
	}

	@Override
	public int hashCode() {

		return Objects.hash(name, unit,price);
	}

}
