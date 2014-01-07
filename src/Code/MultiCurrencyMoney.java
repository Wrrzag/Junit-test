/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

/**
 *
 * @author Marc
 */
interface MultiCurrencyMoney {

    /**
     * Adds money to the total.
     *
     * @param money the money to add.
     */
    public void addMoney(Money money);

    /**
     * Returns the accumulated total in the MultiCurrencyMoney object.
     *
     * @param exchanger the CurrencyExchanger to be used to convert the
     * currencies.
     * @param currency the Currency for the money to be returned as.
     * @return the accumulated money in the object converted to the given
     * Currency.
     * @throws BadCurrencyException if the given Currency is not valid.
     */
    public Money getTotal(CurrencyExchanger exchanger, Currency currency) throws BadCurrencyException;

    /**
     * Empties the MultiCurrencyMoney object.
     */
    public void empty();
}
