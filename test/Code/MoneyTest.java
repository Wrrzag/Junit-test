/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.math.BigDecimal;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Marc
 */
public class MoneyTest {

    private static final BigDecimal PRICE = new BigDecimal("47");
    private static final BigDecimal DIFFERENT_PRICE = new BigDecimal("1337");
    private Currency currency;
    private Money money;

    @Before
    public void initMoney() throws Exception {
        currency = new Currency("USD");
        money = new Money(PRICE, currency);
    }

    @Test
    public void testGetPrice() {
        BigDecimal result = money.getPrice();

        assertEquals(PRICE, result);
    }

    @Test
    public void testGetCurrency() {
        Currency result = money.getCurrency();

        assertEquals(currency, result);
    }

    @Test
    public void testEqualsWhenEquals() {
        Money secondMoney = new Money(PRICE, currency);

        assertTrue(money.equals(secondMoney));
    }

    @Test
    public void testEqualsWhenNotEquals() {
        Money secondMoney = new Money(DIFFERENT_PRICE, currency);

        assertFalse(money.equals(secondMoney));
    }

    @Test
    public void testHashCode() {
        Money secondMoney = new Money(PRICE, currency);

        int firstHash = money.hashCode();
        int secondHash = secondMoney.hashCode();

        assertEquals(firstHash, secondHash);
    }
}
