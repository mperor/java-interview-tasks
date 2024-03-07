package pl.mperor.interview.tasks.coin;

import java.util.*;

public class CoinMachine {

    private final Map<PolishCoin, Integer> store;

    public CoinMachine(Map<PolishCoin, Integer> store) {
        this.store = new HashMap<>(store);
    }

    public Map<PolishCoin, Integer> withdraw(int major, int minor) {
        return calculateCoins(major * 100 + minor);
    }

    private Map<PolishCoin, Integer> calculateCoins(int valueInPennies) {
        Map<PolishCoin, Integer> result = new EnumMap<>(PolishCoin.class);

        Iterator<PolishCoin> polishCoinIterator = Arrays.stream(PolishCoin.values()).iterator();
        while (valueInPennies > 0 && polishCoinIterator.hasNext()) {
            PolishCoin coin = polishCoinIterator.next();
            while (store.getOrDefault(coin, 0) > 0 && valueInPennies - coin.getValueInPennies() >= 0) {
                valueInPennies -= coin.getValueInPennies();
                store.merge(coin, -1, Integer::sum);
                result.merge(coin, 1, Integer::sum);
            }
        }

        if (valueInPennies > 0) {
            throw new IllegalStateException("No coins available to withdraw (%s.%s) PLN!");
        }
        return result;
    }

}
