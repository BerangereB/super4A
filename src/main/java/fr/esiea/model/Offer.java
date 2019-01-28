package fr.esiea.model;

public interface Offer {

	Discount getDiscount(Product p, double quantity, double unitPrice);
}
