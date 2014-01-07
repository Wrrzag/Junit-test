/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Marc
 */
public class MultiCurrencyMoneyTest {

    private static final BigDecimal MONEY_QUANTITY = new BigDecimal("5");
    private MultiCurrencyMoney mCMoney;
    private CurrencyExchanger exchanger;
    private Currency dollarsCurr;
    private Currency eurosCurr;

    private static class ExchangerStub implements CurrencyExchanger {
        // This class will simulate a 1:1 conversion from all to all currencies

        @Override
        public void setRate(Currency from, Currency to, double rate) {
            throw new UnsupportedOperationException("Not needed.");
        }

        @Override
        public Money convert(Money from, Currency to) throws BadCurrencyException {
            return new Money(from.getPrice(), to);
        }

        @Override
        public double getRate(Currency from, Currency to) throws BadCurrencyException {
            throw new UnsupportedOperationException("Not needed.");
        }
    }

    @Before
    public void initMultiCurrencyMoney() throws Exception {
        mCMoney = new MultiCurrencyMoneyImpl();
        exchanger = new ExchangerStub();
        dollarsCurr = new Currency("USD");
        eurosCurr = new Currency("EUR");
    }

    @Test(expected = BadCurrencyException.class)
    public void testGetTotalWithBadCurrencyException() throws Exception {
        mCMoney.getTotal(exchanger, new Currency("This is not acceptable"));
    }

    @Test
    public void testAddMoney() throws Exception {
        Money expected = new Money(MONEY_QUANTITY, dollarsCurr);
        mCMoney.addMoney(expected);

        Money result = mCMoney.getTotal(exchanger, expected.getCurrency());

        assertEquals(expected, result);
    }

    @Test
    public void testAddMultipleCurrencyMoney() throws Exception {
        Money dollars = new Money(MONEY_QUANTITY, dollarsCurr);
        Money euros = new Money(MONEY_QUANTITY, eurosCurr);

        mCMoney.addMoney(dollars);
        mCMoney.addMoney(euros);

        Money expected = new Money(MONEY_QUANTITY.add(MONEY_QUANTITY), euros.getCurrency());
        Money result = mCMoney.getTotal(exchanger, euros.getCurrency());

        assertEquals(expected, result);
    }

    @Test
    public void testGetTotalWhenEmpty() throws Exception {
        Money result = mCMoney.getTotal(exchanger, dollarsCurr);
        Money expected = new Money(BigDecimal.ZERO, dollarsCurr);

        assertEquals(expected, result);
    }

    @Test
    public void testGetTotalWithOneMoney() throws Exception {
        Money expected = new Money(MONEY_QUANTITY, dollarsCurr);

        mCMoney.addMoney(expected);

        Money result = mCMoney.getTotal(exchanger, expected.getCurrency());

        assertEquals(expected, result);
    }

    @Test
    public void testGetTotalWithMoreThanOneInsertion() throws Exception {
        Money firstMoney = new Money(MONEY_QUANTITY, dollarsCurr);
        Money secondMoney = new Money(MONEY_QUANTITY, dollarsCurr);

        mCMoney.addMoney(firstMoney);
        mCMoney.addMoney(secondMoney);

        Money expected = new Money(MONEY_QUANTITY.add(MONEY_QUANTITY), dollarsCurr);
        Money result = mCMoney.getTotal(exchanger, firstMoney.getCurrency());

        assertEquals(expected, result);
    }

    @Test
    public void testGetTotalWhenMultipleCurrenciesInserted() throws Exception {
        Money dollars = new Money(MONEY_QUANTITY, dollarsCurr);
        Money euros = new Money(MONEY_QUANTITY, eurosCurr);

        mCMoney.addMoney(dollars);
        mCMoney.addMoney(euros);

        Money expected = new Money(MONEY_QUANTITY.add(MONEY_QUANTITY), dollarsCurr);
        Money result = mCMoney.getTotal(exchanger, dollars.getCurrency());

        assertEquals(expected, result);
    }

    @Test
    public void testBackwardsGetTotalWhenMultipleCurrenciesInserted() throws Exception {
        Money dollars = new Money(MONEY_QUANTITY, dollarsCurr);
        Money euros = new Money(MONEY_QUANTITY, eurosCurr);

        mCMoney.addMoney(dollars);
        mCMoney.addMoney(euros);

        Money expected = new Money(MONEY_QUANTITY.add(MONEY_QUANTITY), eurosCurr);
        Money result = mCMoney.getTotal(exchanger, euros.getCurrency());

        assertEquals(expected, result);
    }

    @Test
    public void testEmpty() throws Exception {
        Money money = new Money(MONEY_QUANTITY, eurosCurr);
        mCMoney.addMoney(money);

        mCMoney.empty();

        Money result = mCMoney.getTotal(exchanger, eurosCurr);
        Money expected = new Money(BigDecimal.ZERO, eurosCurr);

        assertEquals(expected, result);
    }
}
