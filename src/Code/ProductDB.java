package Code;


import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a database where there are stored products by their UPC, each one linked with its price. It can return the price of a specified UPC.
 * @author Marc
 */
public class ProductDB {
    private Map<UPC, Money> upcs = new HashMap();
    
    /**
     * Adds a product with the specified UPC and the specified price.
     * @param upc the upc of the product.
     * @param price the price of the product.
     */
    public void addProduct(UPC upc, Money price){
        upcs.put(upc, price);
    }
   
    /**
     * Returns the price of the product with the specified UPC.
     * @param upc the UPC of the product.
     * @return the price of the product.
     * @throws BadUPCException if the UPC doesn't belong to any product.
     */
    public Money getPrice(UPC upc) throws BadUPCException{
        Money price = upcs.get(upc);
        
        if(price == null) {
            throw new BadUPCException();
        }
        
        return price;
        
    }
}
