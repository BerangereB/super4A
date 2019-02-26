package fr.esiea.model.market;

/**
 * @author Quentin on 24/02/2019
 * @project super4A - fr.esiea.model.market
 */

import fr.esiea.model.marketReceipt.ShoppingCart;

import java.util.LinkedHashMap;
import java.util.Map;

public enum Customers {
	INSTANCE;

	private Map<Integer, ShoppingCart> customers;
	private static int numberOfAccounts = 0;

	Customers() {
		customers = new LinkedHashMap<>();
	}

	public int createCustomer(final ShoppingCart cart) {
		int newId = getNewCustomerId();
		this.customers.put(newId, cart);
		return newId;
	}

	public Map<Integer, ShoppingCart> getCustomers() {
		return this.customers;
	}

	public boolean addProductToShoppingCart(final int customerID, final ProductQuantity productQuantity) {
		ShoppingCart cart = this.customers.get(customerID);
		if(cart != null){
			cart.addItemQuantity(productQuantity.getProduct(),productQuantity.getQuantity());
			//customers.put(customerID,cart);
			return true;
		}
		return false;
	}

	public boolean removeProductFromShoppingCart(final int customerID, final ProductQuantity productQuantity) {
		ShoppingCart cart = this.customers.get(customerID);
		if(cart != null){
			cart.removeItemQuantity(productQuantity.getProduct(),productQuantity.getQuantity());
			//customers.put(customerID,cart);
			return true;
		}
		return false;
	}

	public ShoppingCart getShoppingCartById(final int customerID) {
		return customers.get(customerID);
	}

	private static int getNewCustomerId() {
		return ++numberOfAccounts;
	}
}
