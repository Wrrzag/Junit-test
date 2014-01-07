package Code;

import java.util.Arrays;
import java.util.List;

/**
 * This class represents a currency. It uses 3-worded strings in all caps
 * referring to the abbreviation of the currency. It has methods used to
 * retrieve this abbreviation.
 *
 * @author Marc
 */
public class Currency {

    private static final int INITIAL_NUM = 3;
    private static List<String> currencies = Arrays.asList("GBP", "EUR", "USD");
    private String currency;

    /**
     * Constructs a new Currency with the specified abbreviation.
     *
     * @param initials the name of the currency.
     * @throws BadCurrencyException if the currency's name is invalid.
     */
    public Currency(String initials) throws BadCurrencyException {
        if (!acceptableInitials(initials)) {
            throw new BadCurrencyException();
        }

        currency = initials;
    }

    /**
     * Returns the currency's abbreviation.
     *
     * @return the currency's abbreviation.
     */
    public String getAbbreviation() {
        return currency;
    }

    /**
     * {@Inheritdoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Currency && obj != null) {
            Currency cmpObj = (Currency) obj;
            return currency.equals(cmpObj.getAbbreviation());
        }

        return false;
    }

    /**
     * {@Inheritdoc}
     */
    @Override
    public int hashCode() {
        return currency.hashCode();
    }

    /**
     * Checks if a currency is acceptable.
     *
     * @param initials the abbreviation of the currency.
     * @return whether the currency is acceptable or not.
     */
    private boolean acceptableInitials(String initials) {
        if (initials.length() != INITIAL_NUM || !initials.toUpperCase().equals(initials)) {
            return false;
        }

        for (String init : currencies) {
            if (init.equals(initials)) {
                return true;
            }
        }

        return false;
    }
}
