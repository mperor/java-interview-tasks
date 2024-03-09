package pl.mperor.interview.tasks.challenge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class ArrayChallengeTest {

    @Test
    public void testFindMaximumProfitNaiveWay() {
        Function<int[], Integer> naiveProfitCalculator = prices -> {
            int maxProfit = -1;
            for (int i = 0; i < prices.length - 1; i++) {
                int currentProfit = 0;
                for (int j = i; j < prices.length - 1; j++) {
                    currentProfit += prices[j + 1] - prices[j];
                    if (currentProfit > maxProfit) {
                        maxProfit = currentProfit;
                    }
                }
            }
            return maxProfit;
        };

        Assertions.assertEquals(-1, naiveProfitCalculator.apply(new int[]{10, 9, 8, 2}));
        Assertions.assertEquals(5, naiveProfitCalculator.apply(new int[]{10, 12, 4, 5, 9}));
        Assertions.assertEquals(8, naiveProfitCalculator.apply(new int[]{14, 20, 4, 12, 5, 11}));
        Assertions.assertEquals(16, naiveProfitCalculator.apply(new int[]{44, 30, 24, 32, 35, 30, 40, 38, 15}));
    }

    @Test
    public void testFindMaximumProfitValleyPeakApproach() {
        Function<int[], Integer> valleyPeakApproachProfitCalculator = prices -> {
            int maxProfit = -1;
            int maxCost = 0;

            for (int i = prices.length - 1; i >= 0; i--) {
                maxCost = Math.max(maxCost, prices[i]);
                if (maxCost != prices[i])
                    maxProfit = Math.max(maxProfit, maxCost - prices[i]);
            }

            return maxProfit;
        };

        Assertions.assertEquals(-1, valleyPeakApproachProfitCalculator.apply(new int[]{10, 9, 8, 2}));
        Assertions.assertEquals(5, valleyPeakApproachProfitCalculator.apply(new int[]{10, 12, 4, 5, 9}));
        Assertions.assertEquals(8, valleyPeakApproachProfitCalculator.apply(new int[]{14, 20, 4, 12, 5, 11}));
        Assertions.assertEquals(16, valleyPeakApproachProfitCalculator.apply(new int[]{44, 30, 24, 32, 35, 30, 40, 38, 15}));
        Assertions.assertEquals(327, valleyPeakApproachProfitCalculator.apply(new int[]{50, 90, 130, 155, 20, 267, 347}));
    }

}
