package fr.esiea.model;

public class TwoForAmountOffer implements Offer {
	public final Product product;
	public final double argument;


	public TwoForAmountOffer(Product product, double argument) {
		this.argument = argument;
		this.product = product;
	}

	@Override
	public Discount getDiscount(Product p, double quantity, double unitPrice) {
		int quantityAsInt = (int) quantity;
		Discount discount = null;

		if (quantityAsInt >= 2) {
			double total = argument * quantityAsInt / 2 + quantityAsInt % 2 * unitPrice;
			double discountN = unitPrice * quantity - total;
			discount = new Discount(p, "2 for " + argument, discountN);
		}


		return discount;
	}
}
