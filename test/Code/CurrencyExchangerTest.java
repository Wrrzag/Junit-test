/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Marc
 */
public class CurrencyExchangerTest {

    private static final BigDecimal DOLLAR_QUANTITY = new BigDecimal("10");
    private static final BigDecimal EURO_QUANTITY = new BigDecimal("20");
    private static final Double RATE = 2.0;
    private CurrencyExchanger exchanger;
    private Currency dollarsCurr;
    private Currency eurosCurr;
    private Money dollars;

    @Before
    public void initCurrencyExchanger() throws Exception {
        exchanger = new CurrencyExchangerImpl();
        dollarsCurr = new Currency("USD");
        eurosCurr = new Currency("EUR");
        dollars = new Money(DOLLAR_QUANTITY, dollarsCurr);
    }

    @Test(expected = BadCurrencyException.class)
    public void testConvertBadCurrencyExceptionWhenCurrenciesNotInExchanger() throws Exception {
        exchanger.convert(dollars, eurosCurr);
    }

    @Test(expected = BadCurrencyException.class)
    public void testGetRateBadCurrencyExceptionWhenCurrenciesNotInExchanger() throws Exception {
        exchanger.getRate(dollarsCurr, eurosCurr);
    }

    @Test
    public void testSetRateWhenCurrenciesNotInExchanger() throws Exception {
        exchanger.setRate(dollarsCurr, eurosCurr, RATE);

        double result = exchanger.getRate(dollarsCurr, eurosCurr);
        
        assertEquals((double) RATE, (double) result, RATE / 1000000);
    }

    @Test
    public void testBackwardsSetRateWhenCurrenciesNotInExchanger() throws Exception {
        exchanger.setRate(dollarsCurr, eurosCurr, RATE);

        double result = exchanger.getRate(eurosCurr, dollarsCurr);

        assertEquals((double) 1 / RATE, (double) result, RATE / 1000000);
    }

    @Test
    public void testSetRateWhenCurrenciesInExchanger() throws Exception {
        exchanger.setRate(dollarsCurr, eurosCurr, RATE);
        exchanger.setRate(eurosCurr, dollarsCurr, 1 / RATE);

        double result = exchanger.getRate(dollarsCurr, eurosCurr);

        assertEquals((double) RATE, (double) result, RATE / 1000000);
    }

    @Test
    public void testBackwardsSetRateWhenCurrenciesInExchanger() throws Exception {
        exchanger.setRate(dollarsCurr, eurosCurr, RATE);
        exchanger.setRate(eurosCurr, dollarsCurr, 1 / RATE);

        double result = exchanger.getRate(eurosCurr, dollarsCurr);

        assertEquals((double) 1 / RATE, (double) result, RATE / 1000000);
    }

    @Test
    public void testSetRateToSelf() throws Exception {
        exchanger.setRate(dollarsCurr, dollarsCurr, 1);

        double result = exchanger.getRate(dollarsCurr, dollarsCurr);
        
        assertEquals((double) 1, (double) result, 1 / 1000000);
    }

    @Test
    public void testConvert() throws Exception {
        exchanger.setRate(dollarsCurr, eurosCurr, RATE);

        Money result = exchanger.convert(dollars, eurosCurr);
        Money expected = new Money(dollars.getPrice().multiply(new BigDecimal(RATE)), eurosCurr);
        
        assertEquals(expected, result);
    }

    @Test
    public void testGetRate() throws Exception {
        exchanger.setRate(dollarsCurr, eurosCurr, RATE);
        
        double result = exchanger.getRate(dollarsCurr, eurosCurr);
        
        assertEquals((double) RATE, (double) result, RATE / 1000000);
    }
}
