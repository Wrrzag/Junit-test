/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

/**
 * 
 * @author Marc
 */
interface CurrencyExchanger {

    /**
     * Sets the rate of exchange between the two currencies.
     *
     * @param from the currency from which the conversion rate is defined.
     * @param to the currency to which the conversion rate is defined.
     * @param rate the rate of the conversion from the "from" parameter to the
     * "to".
     */
    public void setRate(Currency from, Currency to, double rate);

    /**
     * Converts the given Money to the given Currency.
     *
     * @param from the Money to convert.
     * @param to the Currency wanted.
     * @return a Money object of the specified Currency.
     * @throws BadCurrencyException if the given Currency is not valid.
     */
    public Money convert(Money from, Currency to) throws BadCurrencyException;

    /**
     * Returns the conversion rate between two currencies.
     *
     * @param from the currency from which the conversion rate is defined.
     * @param to the currency to which the conversion rate is defined.
     * @return the conversion rate between the two currencies.
     * @throws BadCurrencyException if the given Currency is not valid.
     */
    public double getRate(Currency from, Currency to) throws BadCurrencyException;
}
