package Code;

/**
 * This class represents a product code. It stores a product code that can be
 * accessed with one of its methods.
 *
 * @author Marc
 */
public class UPC {

    private String upc;

    /**
     * Constructs a new UPC with the specified code.
     *
     * @param upc the code of the product.
     */
    public UPC(String upc) {
        this.upc = upc;
    }

    /**
     * Returns the UPC.
     *
     * @return the product's code.
     */
    public String getUPC() {
        return upc;
    }


    /**
     * {@Inheritdoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UPC) {
            UPC cmpObj = (UPC) obj;
            return upc.equals(cmpObj.getUPC());
        }

        return false;
    }


    /**
     * {@Inheritdoc}
     */
    @Override
    public int hashCode() {
        return upc.hashCode();
    }
}
