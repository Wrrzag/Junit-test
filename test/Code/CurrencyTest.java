/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Marc
 */
public class CurrencyTest {

    private static final String MONEY_CODE = "USD";
    private static final String DIFFERENT_MONEY_CODE = "EUR";
    private Currency currency;

    @Before
    public void initCurrency() throws Exception {
        currency = new Currency(MONEY_CODE);
    }

    @Test(expected = BadCurrencyException.class)
    public void testBadCurrencyExceptionWhenNameIsTooLong() throws Exception {
        Currency notAcceptable = new Currency("STRING TOO LONG");
    }

    @Test(expected = BadCurrencyException.class)
    public void testBadCurrencyExceptionWhenNameIsLowercase() throws Exception {
        Currency notAcceptable = new Currency("string lowercase");
    }

    @Test(expected = BadCurrencyException.class)
    public void testBadCurrencyExceptionWhenNameIsMixedcase() throws Exception {
        Currency notAcceptable = new Currency("String with BOTH letters upPerCAse and LOWERCASE");
    }

    @Test(expected = BadCurrencyException.class)
    public void testBadCurrencyExceptionWhenNameIsNotACurrency() throws Exception {
        Currency notAcceptable = new Currency("FOO");
    }

    @Test
    public void testGetAbbreviation() {
        String result = currency.getAbbreviation();

        assertEquals(MONEY_CODE, result);
    }

    @Test
    public void testEqualsWhenEquals() throws Exception {
        Currency secondCurrency = new Currency(MONEY_CODE);

        assertTrue(currency.equals(secondCurrency));
    }

    @Test
    public void testEqualsWhenNotEquals() throws Exception {
        Currency secondCurrency = new Currency(DIFFERENT_MONEY_CODE);

        assertFalse(currency.equals(secondCurrency));
    }

    @Test
    public void testHashCode() throws Exception {
        Currency secondCurrency = new Currency(MONEY_CODE);

        int firstHash = currency.hashCode();
        int secondHash = secondCurrency.hashCode();

        assertEquals(firstHash, secondHash);
    }
}
