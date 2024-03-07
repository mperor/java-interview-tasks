package pl.mperor.interview.tasks.coin;

import static pl.mperor.interview.tasks.coin.PolishCoinLabel.GROSZ;
import static pl.mperor.interview.tasks.coin.PolishCoinLabel.ZLOTY;

public enum PolishCoin {
    ZLOTY_5(500, ZLOTY),
    ZLOTY_2(200, ZLOTY),
    ZLOTY_1(100, ZLOTY),
    GROSZ_50(50, GROSZ),
    GROSZ_20(20, GROSZ),
    GROSZ_10(10, GROSZ),
    GROSZ_5(5, GROSZ),
    GROSZ_2(2, GROSZ),
    GROSZ_1(1, GROSZ);

    private final int valueInPennies;
    private final PolishCoinLabel label;

    PolishCoin(int valueInPennies, PolishCoinLabel label) {
        this.valueInPennies = valueInPennies;
        this.label = label;
    }

    public int getValueInPennies() {
        return valueInPennies;
    }
}

