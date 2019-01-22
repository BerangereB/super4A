package fr.esiea.model;

public class Offer {
	public final SpecialOfferType offerType;
	public final Product product;
	public final double argument;

	public Offer(SpecialOfferType offerType, Product product, double argument) {
		this.offerType = offerType;
		this.argument = argument;
		this.product = product;
	}
}
