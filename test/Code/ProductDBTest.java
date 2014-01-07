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
public class ProductDBTest {

    private ProductDB db;
    private UPC upc;
    private Money price;

    @Before
    public void initProductDB() throws Exception {
        db = new ProductDB();
        upc = new UPC("TestUPC");
        price = new Money(new BigDecimal("5"), new Currency("USD"));
    }

    @Test(expected = BadUPCException.class)
    public void testBadUPCException() throws Exception {
        UPC notInTheDB = new UPC("I'm not there");
        db.getPrice(notInTheDB);
    }

    @Test
    public void testOneInsertion() throws Exception {
        db.addProduct(upc, price);
        Money result = db.getPrice(upc);
        
        assertEquals(price, result);
    }

    @Test
    public void testManyInsertions() throws Exception {
        UPC secondUpc = new UPC("TestMultipleUPCs");
        Money secondPrice = new Money(new BigDecimal("3454.45"), new Currency("EUR"));
        db.addProduct(upc, price);
        db.addProduct(secondUpc, secondPrice);
        
        Money result = db.getPrice(upc);
        Money secondRes = db.getPrice(secondUpc);
        
        assertEquals(price, result);
        assertEquals(secondPrice, secondRes);
    }

    @Test
    public void testGetPrice() throws Exception {
        db.addProduct(upc, price);

        Money result = db.getPrice(upc);
        assertEquals(price, result);
    }
}
