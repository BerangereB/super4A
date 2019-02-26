package fr.esiea.model.offers.bundleOffers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.esiea.model.market.ProductQuantity;
import fr.esiea.model.offers.Offer;
import fr.esiea.model.market.Discount;
import fr.esiea.model.market.SupermarketCatalog;
import fr.esiea.model.offers.OfferType;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;


/**
 * Cette offre s'applique sur un ensemble de produits
 * Ce lot est au prix de 'argument'€
 */
public class AmountBundleOffer extends AbstractBundleOffer {

	@JsonProperty("Type")
	private final OfferType type = OfferType.AmountBundle;
	private Discount discount = null;

	// TODO: refactor Map<String,Integer> products -> Product/ProductQuantity
	public AmountBundleOffer(List<ProductQuantity> products, double argument) {
		super(products, argument);
	}
	
	@Override
	public List<ProductQuantity> getProducts() {
		return products;
	}

	@JsonIgnore
	@Override
	public Discount getDiscount() {
		return discount;
	}


	@Override
	public Map<String,Double> calculateDiscount(Map<String,Double> items, SupermarketCatalog catalog) {

		// Vérification des quantités de chaque produit concerné par l'offre
		boolean checkedQuantities = true;
		for(ProductQuantity product : products){
			if(product.getQuantity() > items.get(product.getProduct())){
				checkedQuantities = false;
				break;
			}
		}



		if(checkedQuantities){

			// On compte le nombre de lots
			int numberOfXs =getNumberOfPacks(products,items);

			BiFunction<Double,Double, Double> offer_function = (X, Y) -> X * Y * numberOfXs;

			double discountTotal = getTotalDiscount(products,items,catalog,numberOfXs,offer_function);


			// On retranche le prix du lot pour avoir la réduction totale sur le prix total du caddie
			discountTotal -= argument*numberOfXs;

			// Ecriture du Discount
			String name = products.stream().map(ProductQuantity::getProduct).collect(Collectors.joining(" & "));
			discount = new Discount(name,  "BundleOffer for " + argument, discountTotal);
		}

		return items;
	}



	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AmountBundleOffer offer = (AmountBundleOffer) o;
		return Objects.equals(type, offer.type) &&
			argument == offer.argument &&
			discount == offer.discount &&
			Objects.equals(products,offer.products);
	}

	@Override
	public int hashCode() {

		return Objects.hash(type,argument,discount,products);
	}

}
