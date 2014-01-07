/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

/**
 * This is a factory used to create Cart objects. It has an overridable method
 * used to choose whatever MultiCurrencyMoney implementation is wanted for the
 * Cart to use.
 *
 * @author Marc
 */
public class CartFactory {

    /**
     * Creates a Cart object and returns it.
     *
     * @param currency the currency to return the total price as.
     * @param exchanger the exchanger containing the money exchange rates.
     * @param db the productDB containig the product UPCs.
     * @return the Cart object.
     */
    public Cart createCart(Currency currency, CurrencyExchanger exchanger, ProductDB db) {
        Cart toReturn = new Cart(currency, exchanger, db);
        toReturn.setMultiCurrencyMoney(createMCM());

        return toReturn;
    }

    /**
     * Creates a MultiCurrencyMoney to be used by the cart. It can be redefined
     * to use whatever MultiCurrencyMoney implementation is wanted.
     *
     * @return a new MultiCurrencyMoney object.
     */
    protected MultiCurrencyMoney createMCM() {
        return new MultiCurrencyMoneyImpl();
    }
}
