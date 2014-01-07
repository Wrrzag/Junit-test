package Code;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * HashMap based implementation of the MultiCurrencyMoney interface.
 *
 * @see MultiCurrencyMoney
 * @author Marc
 */
public class MultiCurrencyMoneyImpl implements MultiCurrencyMoney {

    private Map<Currency, BigDecimal> storedMoney = new HashMap();


    /**
     * {@Inheritdoc}
     */
    @Override
    public void addMoney(Money money) {
        if (storedMoney.containsKey(money.getCurrency())) {
            storedMoney.put(money.getCurrency(), storedMoney.get(money.getCurrency()).add(money.getPrice()));
        } else {
            storedMoney.put(money.getCurrency(), money.getPrice());
        }
    }


    /**
     * {@Inheritdoc}
     */
    @Override
    public Money getTotal(CurrencyExchanger exchanger, Currency currency) throws BadCurrencyException {
        BigDecimal total = new BigDecimal("0");

        for (Currency m : storedMoney.keySet()) {
            total = total.add(exchanger.convert(new Money(storedMoney.get(m), m), currency).getPrice());
        }

        return new Money(total, currency);
    }


    /**
     * {@Inheritdoc}
     */
    @Override
    public void empty() {
        storedMoney.clear();
    }
}
