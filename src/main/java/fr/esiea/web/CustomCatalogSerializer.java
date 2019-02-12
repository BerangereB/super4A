package fr.esiea.web;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import fr.esiea.model.market.Product;
import fr.esiea.model.market.SupermarketCatalog;

import java.io.IOException;
import java.util.Map;

public class CustomCatalogSerializer extends StdSerializer<SupermarketCatalog> {

	public CustomCatalogSerializer() {
		this(null);
	}
	public CustomCatalogSerializer(Class<SupermarketCatalog> t) {
		super(t);
	}

	@Override
	public void serialize(
		SupermarketCatalog catalog, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
		Map<String, Product> products = catalog.getProducts();
		Map<String, Double> prices = catalog.getPrices();

		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("Total",Integer.toString(products.size()));
		jsonGenerator.writeObjectFieldStart("Products");


		for (String productName : products.keySet()) {

			jsonGenerator.writeObjectFieldStart(productName);
			jsonGenerator.writeStringField("Unit", products.get(productName).getUnit().toString());
			jsonGenerator.writeStringField("Price", prices.get(productName).toString() + "€");
			jsonGenerator.writeEndObject();
		}
		jsonGenerator.writeEndObject();
		jsonGenerator.writeEndObject();
	}
}
