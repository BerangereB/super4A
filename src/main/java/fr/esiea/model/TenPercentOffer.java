package fr.esiea.model;

public class TenPercentOffer implements Offer{

	public final Product product;
	public final double argument;


	public TenPercentOffer(Product product, double argument) {
		this.argument = argument;
		this.product = product;
	}

	@Override
	public Discount getDiscount(Product p, double quantity, double unitPrice) {
		return new Discount(p, argument + "% off", quantity * unitPrice * argument / 100.0);
	}
}
