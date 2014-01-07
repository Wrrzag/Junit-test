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
public class CartTest {

    private static final UPC DOLLARS_UPC = new UPC("This item will be priced in dollars.");
    private static final UPC EUROS_UPC = new UPC("This item will be priced in euros.");
    private static final BigDecimal DOLLARS_PRICE = new BigDecimal("10");
    private static final BigDecimal EUROS_PRICE = new BigDecimal("5");
    private Cart cart;
    private ProductDB db;
    private Currency dollars;
    private Currency euros;

    private static class TestingCartFactory extends CartFactory {

        @Override
        protected MultiCurrencyMoney createMCM() {
            return new MultiCurrencyMoneyStub();
        }
    }

    private static class MultiCurrencyMoneyStub implements MultiCurrencyMoney {
        // This class will add all money given to it, converting each currency by a 1:1 ratio.

        private BigDecimal total;

        public MultiCurrencyMoneyStub() {
            total = new BigDecimal("0");
        }

        @Override
        public void addMoney(Money money) {
            total = total.add(money.getPrice());
        }

        @Override
        public Money getTotal(CurrencyExchanger exchanger, Currency currency) throws BadCurrencyException {
            return new Money(total, currency);
        }

        @Override
        public void empty() {
            total = new BigDecimal("0");
        }
    }

    @Before
    public void initCart() throws Exception {
        db = new ProductDB();
        dollars = new Currency("USD");
        euros = new Currency("EUR");
        cart = new TestingCartFactory().createCart(dollars, null, db);

        db.addProduct(DOLLARS_UPC, new Money(DOLLARS_PRICE, dollars));
        db.addProduct(EUROS_UPC, new Money(EUROS_PRICE, euros));
    }

    @Test(expected = BadQuantityException.class)
    public void testAddItemBadQuantityException() throws Exception {
        cart.addItem(DOLLARS_UPC, -1);
    }

    @Test
    public void testSelectCurrency() throws Exception {
        cart.selectCurrency(euros);

        assertEquals(euros, cart.getTotal().getCurrency());
    }

    @Test
    public void testInit() throws Exception {
        cart.addItem(DOLLARS_UPC, 15);

        cart.init();
        Money expected = new Money(BigDecimal.ZERO, dollars);
        Money result = cart.getTotal();

        assertEquals(expected, result);
    }

    @Test
    public void testGetTotalWithOneElementInCart() throws Exception {
        cart.addItem(DOLLARS_UPC, 1);

        Money expected = new Money(DOLLARS_PRICE, dollars);
        Money returned = cart.getTotal();

        assertEquals(returned, expected);
    }

    @Test
    public void testGetTotalWithMultipleElementsOfTheSameCurrencyInCart() throws Exception {
        cart.addItem(DOLLARS_UPC, 2);

        Money expected = new Money(DOLLARS_PRICE.add(DOLLARS_PRICE), dollars);
        Money returned = cart.getTotal();

        assertEquals(returned, expected);
    }

    @Test
    public void testGetTotalWithElementsOfDifferentCurrencyInCart() throws Exception {
        cart.addItem(DOLLARS_UPC, 1);
        cart.addItem(EUROS_UPC, 1);

        Money expected = new Money(DOLLARS_PRICE.add(EUROS_PRICE), dollars);
        Money returned = cart.getTotal();

        assertEquals(returned, expected);
    }

    @Test
    public void testAddItemWhenCartIsEmpty() throws Exception {
        cart.addItem(DOLLARS_UPC, 1);

        Money expected = new Money(DOLLARS_PRICE, dollars);
        Money returned = cart.getTotal();

        assertEquals(returned, expected);
    }

    @Test
    public void testAddItemWhenThereAreElementsOfTheSameUPCInTheCart() throws Exception {
        cart.addItem(DOLLARS_UPC, 1);
        cart.addItem(DOLLARS_UPC, 1);

        Money expected = new Money(DOLLARS_PRICE.add(DOLLARS_PRICE), dollars);
        Money returned = cart.getTotal();

        assertEquals(returned, expected);
    }

    @Test
    public void testAddItemWhenThereAreElementsOfTheSameCurrencyInTheCart() throws Exception {
        UPC secondUPC = new UPC("This is another unique UPC to be used with a price.");
        Money secondUPCPrice = new Money(new BigDecimal("50"), dollars);

        db.addProduct(secondUPC, secondUPCPrice);
        cart.addItem(DOLLARS_UPC, 1);
        cart.addItem(secondUPC, 1);

        Money expected = new Money(DOLLARS_PRICE.add(secondUPCPrice.getPrice()), dollars);
        Money returned = cart.getTotal();

        assertEquals(returned, expected);
    }

    @Test
    public void testAddItemWhenThereAreElementsOfDifferentCurrencyInTheCart() throws Exception {
        UPC secondUPC = new UPC("This is the UPC of a product in a different currency.");
        Money secondUPCPrice = new Money(new BigDecimal("50"), euros);

        db.addProduct(secondUPC, secondUPCPrice);
        cart.addItem(DOLLARS_UPC, 1);
        cart.addItem(secondUPC, 1);

        Money expected = new Money(DOLLARS_PRICE.add(secondUPCPrice.getPrice()), dollars);
        Money returned = cart.getTotal();

        assertEquals(returned, expected);
    }
}
