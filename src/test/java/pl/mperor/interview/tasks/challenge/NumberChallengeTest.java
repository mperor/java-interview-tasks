package pl.mperor.interview.tasks.challenge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Number Challenges 🔢
 */
public class NumberChallengeTest {

    /**
     * <p><b>Number Challenge 1</b>
     *
     * <p>This method calculates recursively an element of a sequence where every single element is a sum of 3 previous elements.
     * The first three elements of this sequence are 0, 1, 2. Therefore, it is a sequence {0,1,2,3,6,11,20,37,68,..}.
     *
     * @param index The index of the element in the sequence to calculate.
     * @return The element at the given index in the sequence.
     */
    private int calculateRecursively(int index) {
        if (index < 0)
            throw new IllegalArgumentException("Index must be non-negative.");
        if (index == 0)
            return 0;
        if (index == 1)
            return 1;
        if (index == 2)
            return 2;

        return calculateRecursively(index - 3) + calculateRecursively(index - 2) + calculateRecursively(index - 1);
    }

    /**
     * <p><b>Number Challenge 1</b>
     *
     * <p>This method calculates iteratively an element of a sequence where every single element is a sum of 3 previous elements.
     * The first three elements of this sequence are 0, 1, 2. Therefore, it is a sequence {0,1,2,3,6,11,20,37,68,..}.
     *
     * @param index The index of the element in the sequence to calculate.
     * @return The element at the given index in the sequence.
     */
    private int calculateIteratively(int index) {
        int[] lastThreeElements = {0, 1, 2};
        if (index < 0)
            throw new IllegalArgumentException("Index must be non-negative.");
        if (index >= 0 && index <= 2)
            return lastThreeElements[index];

        for (int i = 3; i <= index; i++) {
            int sum = lastThreeElements[0] + lastThreeElements[1] + lastThreeElements[2];
            lastThreeElements[0] = lastThreeElements[1];
            lastThreeElements[1] = lastThreeElements[2];
            lastThreeElements[2] = sum;
        }

        return lastThreeElements[2];
    }

    @Test
    public void testCalculateRecursively() {
        Assertions.assertEquals(0, calculateRecursively(0));
        Assertions.assertEquals(1, calculateRecursively(1));
        Assertions.assertEquals(2, calculateRecursively(2));
        Assertions.assertEquals(3, calculateRecursively(3));
        Assertions.assertEquals(6, calculateRecursively(4));
        Assertions.assertEquals(11, calculateRecursively(5));
        Assertions.assertEquals(20, calculateRecursively(6));
        Assertions.assertEquals(37, calculateRecursively(7));
        Assertions.assertEquals(68, calculateRecursively(8));
    }

    @Test
    public void testCalculateIteratively() {
        Assertions.assertEquals(0, calculateIteratively(0));
        Assertions.assertEquals(1, calculateIteratively(1));
        Assertions.assertEquals(2, calculateIteratively(2));
        Assertions.assertEquals(3, calculateIteratively(3));
        Assertions.assertEquals(6, calculateIteratively(4));
        Assertions.assertEquals(11, calculateIteratively(5));
        Assertions.assertEquals(20, calculateIteratively(6));
        Assertions.assertEquals(37, calculateIteratively(7));
        Assertions.assertEquals(68, calculateIteratively(8));
    }

    /**
     * <p><b>Number Challenge 2</b>
     *
     * <p>Write a program to find the factorial of a given number in Java.
     */
    @Test
    public void testFindFactorial() {
        UnaryOperator<Long> factorial = n -> LongStream.range(1, n + 1)
                .reduce(1L, (left, right) -> left * right);

        assertEquals(1L, factorial.apply(0L));
        assertEquals(1L, factorial.apply(1L));
        assertEquals(2L, factorial.apply(2L));
        assertEquals(6L, factorial.apply(3L));
        assertEquals(24L, factorial.apply(4L));
        assertEquals(120L, factorial.apply(5L));
    }

    /**
     * <p><b>Number Challenge 3</b>
     *
     * <p>Write a program to find the largest number in an array in Java.
     */
    @Test
    public void testFindLargestNumberInArray() {
        Function<int[], Integer> largestNumberFinder = array -> Arrays.stream(array).max().orElse(-1);

        assertEquals(-1, largestNumberFinder.apply(new int[0]));
        assertEquals(5, largestNumberFinder.apply(new int[]{3, 2, 1, 5}));
    }

    /**
     * <p><b>Number Challenge 4</b>
     *
     * <p>Write a program to check if a given number is prime in Java.
     */
    @Test
    public void testIsPrimeNumber() {
        Predicate<Integer> primeNumberChecker = number -> {
            for (int i = 2; i * i <= number; i++) {
                if (number % i == 0)
                    return false;
            }

            return true;
        };

        assertTrue(primeNumberChecker.test(2));
        assertTrue(primeNumberChecker.test(3));
        assertTrue(primeNumberChecker.test(5));
        assertTrue(primeNumberChecker.test(7));
        assertTrue(primeNumberChecker.test(19));
        assertTrue(primeNumberChecker.test(23));

        assertFalse(primeNumberChecker.test(4));
        assertFalse(primeNumberChecker.test(18));
        assertFalse(primeNumberChecker.test(135));
    }

    /**
     * <p><b>Number Challenge 5</b>
     *
     * <p>Write a program to remove duplicate elements from an array in Java.
     */
    @Test
    public void testRemoveDuplicatesFromArray() {
        UnaryOperator<int[]> distinctStreamRemover = numbers -> Arrays.stream(numbers).distinct().toArray();
        UnaryOperator<int[]> setRemover = numbers -> new LinkedHashSet<>(Arrays.stream(numbers).boxed().collect(Collectors.toList()))
                .stream().mapToInt(Integer::intValue).toArray();

        assertArrayEquals(new int[]{1}, distinctStreamRemover.apply(new int[]{1, 1, 1, 1}));
        assertArrayEquals(new int[]{1, 2, 3}, distinctStreamRemover.apply(new int[]{1, 2, 3, 3, 2, 1}));

        assertArrayEquals(new int[]{1}, setRemover.apply(new int[]{1, 1, 1, 1}));
        assertArrayEquals(new int[]{1, 2, 3}, setRemover.apply(new int[]{1, 2, 3, 3, 2, 1}));
    }

    /**
     * <p><b>Number Challenge 6</b>
     *
     * <p>Write a program to find the second largest number in an array in Java.
     */
    @Test
    public void testFindSecondLargestNumberInArray() {
        Function<int[], Integer> secondLargestNumberFinder = numbers -> Arrays.stream(numbers)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .collect(Collectors.toCollection(LinkedList::new))
                .getLast();

        Function<int[], Integer> iterativeSecondLargestNumberFinder = numbers -> {
            int max = Integer.MIN_VALUE;
            int secondMax = Integer.MIN_VALUE;
            for (int number : numbers) {
                if (number > max) {
                    secondMax = max;
                    max = number;
                } else if (number > secondMax) {
                    secondMax = number;
                }
            }
            return secondMax;
        };

        assertEquals(10, secondLargestNumberFinder.apply(new int[]{1, 5, 10, 20}));
        assertEquals(-10, secondLargestNumberFinder.apply(new int[]{-1, -10}));

        assertEquals(10, iterativeSecondLargestNumberFinder.apply(new int[]{1, 5, 10, 20}));
        assertEquals(-10, iterativeSecondLargestNumberFinder.apply(new int[]{-1, -10}));
    }

    /**
     * <p><b>Number Challenge 7</b>
     *
     * <p>Write a program to find the nth Fibonacci number in Java.
     */
    @Test
    public void testFindFibonacci() {
        UnaryOperator<Integer> fibonacci = n -> Stream.iterate(new int[]{1, 1}, fib -> new int[]{fib[1], fib[0] + fib[1]})
                .limit(n)
                .reduce((first, second) -> second)
                .orElse(new int[]{0})[0];

        assertEquals(0, fibonacci.apply(0));
        assertEquals(1, fibonacci.apply(1));
        assertEquals(1, fibonacci.apply(2));
        assertEquals(2, fibonacci.apply(3));
        assertEquals(3, fibonacci.apply(4));
        assertEquals(5, fibonacci.apply(5));
        assertEquals(8, fibonacci.apply(6));
        assertEquals(13, fibonacci.apply(7));
        assertEquals(21, fibonacci.apply(8));
        assertEquals(34, fibonacci.apply(9));
        assertEquals(55, fibonacci.apply(10));
    }

}
