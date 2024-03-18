package pl.mperor.interview.tasks.coin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Change Coin Machine 🏦
 *
 * <p>Create a console program that accepts an indefinite number of remaining amounts at the output.
 * The cash register for small change has the following state:</p>
 * <ul>
 *     <li>5 x 1 zł</li>
 *     <li>2 x 2 zł</li>
 *     <li>1 x 5 zł</li>
 *     <li>50 x 20 gr</li>
 *     <li>200 x 10 gr</li>
 *     <li>100 x 5 gr</li>
 *     <li>100 x 2 gr</li>
 *     <li>10,000 x 1 gr</li>
 * </ul>
 *
 * <p>The program should print which coins are to be used for giving individual change, using as few coins as possible
 * (remembering that previous coins are removed from the state and cannot be used when giving subsequent change).</p>
 *
 * <p><strong>Example:</strong></p>
 * <ul>
 *     <li>For a change of 1.30 zł:
 *         <ul>
 *             <li>Give 1 coin of 1zł</li>
 *             <li>Give 1 coin of 20gr</li>
 *             <li>Give 1 coin of 10gr</li>
 *         </ul>
 *     </li>
 *
 *     <li>For a change of 11.70 zł:
 *         <ul>
 *             <li>Give 1 coin of 5zł</li>
 *             <li>Give 3 coins of 2zł</li>
 *             <li>Give 1 coin of 50gr</li>
 *             <li>Give 2 coins of 20gr</li>
 *         </ul>
 *     </l>
 * </ul>
 */
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
