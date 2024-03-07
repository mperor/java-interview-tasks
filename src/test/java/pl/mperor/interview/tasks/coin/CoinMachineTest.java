package pl.mperor.interview.tasks.coin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoinMachineTest {

    @Test
    public void testWithdrawCoins_insufficientTypeOfCoins_throwsIllegalStateException() {
        CoinMachine coinMachine = new CoinMachine(Map.of(
                PolishCoin.ZLOTY_5, 1,
                PolishCoin.GROSZ_5, 10,
                PolishCoin.GROSZ_2, 40
        ));
        Assertions.assertThrows(IllegalStateException.class, () -> coinMachine.withdraw(1, 31));
    }

    @Test
    public void testWithdrawCoins_insufficientNumberOfCoins_throwsIllegalStateException() {
        CoinMachine coinMachine = new CoinMachine(Map.of(
                PolishCoin.ZLOTY_5, 5,
                PolishCoin.ZLOTY_2, 10,
                PolishCoin.ZLOTY_1, 50,
                PolishCoin.GROSZ_50, 10,
                PolishCoin.GROSZ_20, 20,
                PolishCoin.GROSZ_10, 50,
                PolishCoin.GROSZ_5, 100,
                PolishCoin.GROSZ_2, 100,
                PolishCoin.GROSZ_1, 1000
        ));
        Assertions.assertThrows(IllegalStateException.class, () -> coinMachine.withdraw(250, 0));
    }

    @Test
    public void testWithdrawCoins_severalCasesInRow_returnsExpectedResult() {
        CoinMachine coinMachine = new CoinMachine(Map.of(
                PolishCoin.ZLOTY_5, 1,
                PolishCoin.ZLOTY_2, 3,
                PolishCoin.ZLOTY_1, 5,
                PolishCoin.GROSZ_50, 10,
                PolishCoin.GROSZ_20, 20,
                PolishCoin.GROSZ_10, 200,
                PolishCoin.GROSZ_5, 100,
                PolishCoin.GROSZ_2, 100,
                PolishCoin.GROSZ_1, 1000
        ));

        Map<PolishCoin, Integer> result = coinMachine.withdraw(1, 30);
        assertEquals(1, result.get(PolishCoin.ZLOTY_1));
        assertEquals(1, result.get(PolishCoin.GROSZ_20));
        assertEquals(1, result.get(PolishCoin.GROSZ_10));

        result = coinMachine.withdraw(11, 70);
        assertEquals(1, result.get(PolishCoin.ZLOTY_5));
        assertEquals(3, result.get(PolishCoin.ZLOTY_2));
        assertEquals(1, result.get(PolishCoin.GROSZ_50));
        assertEquals(1, result.get(PolishCoin.GROSZ_20));

        result = coinMachine.withdraw(6, 70);
        assertEquals(4, result.get(PolishCoin.ZLOTY_1));
        assertEquals(5, result.get(PolishCoin.GROSZ_50));
        assertEquals(1, result.get(PolishCoin.GROSZ_20));

        result = coinMachine.withdraw(4, 31);
        assertEquals(4, result.get(PolishCoin.GROSZ_50));
        assertEquals(11, result.get(PolishCoin.GROSZ_20));
        assertEquals(1, result.get(PolishCoin.GROSZ_10));
        assertEquals(1, result.get(PolishCoin.GROSZ_1));
    }

}
