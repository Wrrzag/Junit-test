/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Marc
 */
public class UPCTest {

    private static final String CODE = "Test UPC";
    private static final String DIFFERENT_CODE = "This is not the same as before";
    private UPC upc;

    @Before
    public void initUPC() {
        upc = new UPC(CODE);
    }

    @Test
    public void testGetUPC() {
        String result = upc.getUPC();

        assertEquals(CODE, result);
    }

    @Test
    public void testEqualsWhenEquals() {
        UPC secondUPC = new UPC(CODE);

        assertTrue(upc.equals(secondUPC));
    }

    @Test
    public void testEqualsWhenNotEquals() {
        UPC secondUPC = new UPC(DIFFERENT_CODE);

        assertFalse(upc.equals(secondUPC));
    }

    @Test
    public void testHashCode() {
        UPC secondUPC = new UPC(CODE);

        int firstHash = upc.hashCode();
        int secondHash = secondUPC.hashCode();

        assertEquals(firstHash, secondHash);

    }
}
