package fr.esiea.model.market;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


public class Product {

	@JsonProperty("Name")
	public String name;
	@JsonProperty("Unit")
	public ProductUnit unit;
	@JsonProperty("Price")
	public double price;

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

	public void setPrice(double newPrice) {
		this.price = newPrice;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return Objects.equals(name, product.name) &&
			unit == product.unit;
	}

	@Override
	public int hashCode() {

		return Objects.hash(name, unit);
	}

}
