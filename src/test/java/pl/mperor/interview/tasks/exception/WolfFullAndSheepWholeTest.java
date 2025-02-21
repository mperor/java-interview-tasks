package pl.mperor.interview.tasks.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WolfFullAndSheepWholeTest {

    @Test
    public void shouldFeedWolfAndKeepSheepAlive() {
        Wolf wolf = new Wolf();
        Sheep sheep = new Sheep();
        feedTheWolf(wolf, sheep, () -> {
            // Todo: Try to save sheep!
            throw new SomethingExceptionallyGood("candies 🍬");
        });
        Assertions.assertTrue(!wolf.isHungry() && sheep.isAlive(), "The wolf should not be hungry and the sheep alive!");
    }

    /**
     * The wolf should eat the sheep. However, if we offer something exceptionally good,
     * the wolf may choose to go for it instead.
     * <p>
     * Handle this exceptional situation appropriately.
     * Do not modify the existing code.
     */
    private static void feedTheWolf(Wolf wolf, Sheep sheep, Runnable doSomethingToSaveSheep) {
        try {
            boolean willEatSheep = sheep.doesWantToBeEaten();
            if (willEatSheep || !willEatSheep) { // actually, the sheep has no say
                doSomethingToSaveSheep.run();
                wolf.eat(sheep);
            }
        } catch (SomethingExceptionallyGood something) {
            wolf.eat(something);
        } finally {
            assert !wolf.isHungry();
        }
    }

}
