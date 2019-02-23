package fr.esiea.web;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.ProductUnit;
import fr.esiea.model.market.SupermarketCatalog;

import java.util.Map;

public class JsonNodeFactoryUtils {

	private static JsonNodeFactory jnf = JsonNodeFactory.instance;


	public static ObjectNode getProduct(String name, ProductUnit unit, double price) {
		ObjectNode productAndPrice = jnf.objectNode();
		productAndPrice.put("name",name);
		productAndPrice.put("unit",unit.toString());
		productAndPrice.put("price",price);

		return productAndPrice;
	}

	public static ObjectNode getCatalog(SupermarketCatalog catalog) {
		Map<String, Product> products = catalog.getProducts();
		Map<String, Double> prices = catalog.getPrices();

		ObjectNode catalog_ = jnf.objectNode();
		catalog_.put("Size",prices.size());

		ArrayNode productsArrayNode = catalog_.putArray("Products");

		for(String name : products.keySet()){
			ObjectNode productObjectNode = getProduct(name,products.get(name).getUnit(),prices.get(name));
			productsArrayNode.add(productObjectNode);
		}

		return catalog_;
	}
}
