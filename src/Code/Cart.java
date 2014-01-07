package Code;

import java.math.BigDecimal;

/**
 * This class represents the shopping cart. It can store different products and
 * different quantities of them, and can return the total price in a specified
 * currency. It can also change this currency after the Cart has been created
 * and it can empty itself.
 *
 * @author Marc
 */
public class Cart {

    private Currency currency;
    private MultiCurrencyMoney cart;
    private ProductDB db;
    private CurrencyExchanger exchanger;

    /**
     * Constructs a new Cart that returns with the specified currency. 
     * It is meant to be used with a factory. If not, setMultiCurrencyMoney must be used to set the internal MultiCurrencyMoney.
     *
     * @param currency the currency to return the total price as.
     * @param exchanger the exchanger containing the money exchange rates.
     * @param db the productDB containig the product UPCs.
     */
    public Cart(Currency currency, CurrencyExchanger exchanger, ProductDB db) {
        this.currency = currency;
        this.exchanger = exchanger;
        this.db = db;
    }

    /**
     * Empties the Cart.
     */
    public void init() {
        cart.empty();
    }

    /**
     * Changes the current currency used by the Cart.
     *
     * @param currency the new currency to use.
     */
    public void selectCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * Returns the total price of the Cart.
     *
     * @return the total price of the Cart.
     * @throws BadCurrencyException if it can't convert the price to the cart's
     * currency.
     */
    public Money getTotal() throws BadCurrencyException {
        return cart.getTotal(exchanger, currency);
    }

    /**
     * Adds the specified quantity of the product with the specified code to the
     * cart.
     *
     * @param upc the code of the product to add.
     * @param quantity the quantity to add.
     * @throws BadUPCException if the code doesn't belong to any product.
     * @throws BadQuantityException if the quantity is less than 0.
     */
    public void addItem(UPC upc, int quantity) throws BadUPCException, BadQuantityException {
        if (quantity < 0) {
            throw new BadQuantityException();
        }

        if (quantity > 0) {
            cart.addMoney(new Money(db.getPrice(upc).getPrice().multiply(new BigDecimal(quantity)), db.getPrice(upc).getCurrency()));
        }
    }

    /**
     * Sets the MultiCurrencyMoney instance to be used to perform the money conversions.
     * @param mCMoney the MultiCurrencyMoney object used to perform the money conversions.
     */
    public void setMultiCurrencyMoney(MultiCurrencyMoney mCMoney) {
        this.cart = mCMoney;
    }
}
