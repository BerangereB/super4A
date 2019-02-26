package fr.esiea.model.offers.simpleOffers;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.esiea.model.offers.Offer;

/**
 * @author Quentin on 26/02/2019
 * @project super4A - fr.esiea.model.offers.simpleOffers
 */
public abstract class SimpleOffers implements Offer {
	@JsonProperty("Product")
	protected final String product;
	@JsonProperty("Argument")
	protected final double argument;

	public SimpleOffers(String product, double argument) {
		this.product = product;
		this.argument = argument;
	}
	public SimpleOffers(String product) {
		this(product, 0.0);
	}
}
