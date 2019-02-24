package fr.esiea.model.market;

/**
 * @author Quentin on 24/02/2019
 * @project super4A - fr.esiea.model.market
 */

import fr.esiea.model.marketReceipt.ShoppingCart;
import java.util.HashMap;
import java.util.Map;

public enum Customers {
	INSTANCE;

	private Map<Integer, ShoppingCart> customers;
	private static int numberOfAccounts = 0;

	Customers() {
		customers = new HashMap<>();
	}

	public int createCustomer(final ShoppingCart cart) {
		int newId = getNewCustomerId();
		this.customers.put(newId, cart);
		return newId;
	}

	public Map<Integer, ShoppingCart> getCustomers() {
		return this.customers;
	}

	public void addProductToShoppingCart(final int customerID, final String product) {
		this.customers.get(customerID).addItem(product);
	}

	public void removeProductFromShoppingCart(final int customerID, final String product) {
		this.customers.get(customerID).removeItem(product);
	}

	public ShoppingCart getShoppingCartById(final int customerID) {
		return customers.get(customerID);
	}

	private static int getNewCustomerId() {
		return ++numberOfAccounts;
	}
}
