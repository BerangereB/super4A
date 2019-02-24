package fr.esiea.model.offers.bundleOffers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.esiea.model.offers.Offer;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.ProductUnit;
import fr.esiea.model.market.SupermarketCatalog;
import fr.esiea.model.offers.OfferType;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;


/**
 * Cette offre s'applique sur un ensemble de produits
 * Ce lot est au prix de 'argument'€
 */
public class AmountBundleOffer extends AbstractBundleOffer implements Offer {

	@JsonProperty("Products")
	public final Map<Product,Integer> products;
	@JsonProperty("Argument")
	public final double argument;
	private Discount discount = null;
	@JsonProperty("Type")
	public final OfferType type = OfferType.AmountBundle;


	public AmountBundleOffer(Map<Product,Integer> products, double argument) {
		this.argument = argument;
		this.products = products;
	}

	@Override
	public OfferType getType() {
		return type;
	}

	@Override
	public Double getArgument() {
		return argument;
	}

	@Override
	public Map<Product,Integer> getProducts() {
		return products;
	}

	@Override
	public Discount getDiscount() {
		return discount;
	}


	@Override
	public Map<Product, Double> calculateDiscount(Map<Product, Double> items, SupermarketCatalog catalog) {

		// Vérification des quantités de chaque produit concerné par l'offre
		boolean checkedQuantities = true;
		for(Map.Entry<Product,Integer> product : products.entrySet()){
			if(product.getValue() > items.get(product.getKey())){
				checkedQuantities = false;
				break;
			}
		}



		if(checkedQuantities){

			// On compte le nombre de lots
			int numberOfXs =getNumberOfPacks(products,items);

			BiFunction<Integer,Double, Double> offer_function = (X, Y) -> X * Y * numberOfXs;

			double discountTotal = getTotalDiscount(products,items,catalog,numberOfXs,offer_function);


			// On retranche le prix du lot pour avoir la réduction totale sur le prix total du caddie
			discountTotal -= argument*numberOfXs;

			// Ecriture du Discount
			String name = products.keySet().stream().map(Product::getName).collect(Collectors.joining( " & " ));
			discount = new Discount(name,  "BundleOffer for " + argument, discountTotal);
		}

		return items;
	}

}
