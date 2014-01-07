package Code;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * HashMap based implementation of the CurrencyExchanger interface.
 *
 * @see CurrencyExchanger
 * @author Marc
 */
public class CurrencyExchangerImpl implements CurrencyExchanger {

    private Map<Currency, Map<Currency, Double>> rates = new HashMap<>();


    /**
     * {@Inheritdoc}
     */
    @Override
    public void setRate(Currency from, Currency to, double rate) {
        if (!rates.containsKey(from)) {
            rates.put(from, new HashMap<Currency, Double>());
            rates.get(from).put(from, 1.0);
        }
        if (!rates.containsKey(to)) {
            rates.put(to, new HashMap<Currency, Double>());
            rates.get(to).put(to, 1.0);
        }

        rates.get(from).put(to, rate);
        rates.get(to).put(from, 1 / rate);
    }


    /**
     * {@Inheritdoc}
     */
    @Override
    public Money convert(Money from, Currency to) throws BadCurrencyException {
        if (!rates.containsKey(from.getCurrency()) || !rates.containsKey(to)) {
            throw new BadCurrencyException();
        }

        BigDecimal rate = new BigDecimal(rates.get(from.getCurrency()).get(to).toString());
        return new Money(from.getPrice().multiply(rate), to);
    }


    /**
     * {@Inheritdoc}
     */
    @Override
    public double getRate(Currency from, Currency to) throws BadCurrencyException {
        if (!rates.containsKey(from) || !rates.containsKey(to)) {
            throw new BadCurrencyException();
        }

        return rates.get(from).get(to);
    }
}
