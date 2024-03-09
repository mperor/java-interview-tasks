package pl.mperor.interview.tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuizMiniTaskTest {

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
    public void testProcessTextByCountingDuplicates() {
        Function<String, String> textProcessor = text -> {
            StringBuilder sb = new StringBuilder();
            char[] chars = text.toCharArray();
            char previous = chars[0];
            int counter = 0;
            for (char c : chars) {
                if (c == previous) {
                    counter++;
                } else {
                    sb.append(counter).append(previous);
                    counter = 1;
                    previous = c;
                }
            }
            sb.append(counter).append(previous);
            return sb.toString();
        };
        assertEquals("3w1e1g2w1h", textProcessor.apply("wwwegwwh"));
        assertEquals("3w2g1o2p", textProcessor.apply("wwwggopp"));

        String actual = Pattern.compile("(?<=(.))(?!\\1)")
                .splitAsStream("wwwegwwh")
                .map(s -> s.length() + s.substring(0, 1))
                .collect(Collectors.joining());
        assertEquals("3w1e1g2w1h", actual);
    }

    @Test
    public void calculateMinutesBetweenTimeRange() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mma");

        Function<String, Duration> timeRangeCalculator = input -> {
            Iterator<LocalTime> it = Arrays.stream(input.split("-"))
                    .map(String::toUpperCase)
                    .map(text -> LocalTime.parse(text, timeFormatter))
                    .iterator();

            var first = it.hasNext() ? it.next() : LocalTime.MIN;
            var second = it.hasNext() ? it.next() : LocalTime.MIN;

            Duration between = Duration.between(first, second);
            return between.isNegative() ? Duration.between(LocalTime.MIDNIGHT, LocalTime.MIDNIGHT.plusSeconds(between.toSeconds())) : between;
        };

        assertEquals(Duration.ofMinutes(540), timeRangeCalculator.apply("11:00am-8:00pm"));
        assertEquals(Duration.ofMinutes(60), timeRangeCalculator.apply("9:00am-10:00am"));
        assertEquals(Duration.ofMinutes(1425), timeRangeCalculator.apply("1:23am-1:08am"));
        assertEquals(Duration.ofMinutes(1320), timeRangeCalculator.apply("1:00pm-11:00am"));
    }

    @Test
    public void testFactorial() {
        UnaryOperator<Long> factorial = n -> LongStream.range(1, n + 1)
                .reduce(1L, (left, right) -> left * right);

        assertEquals(1L, factorial.apply(0L));
        assertEquals(1L, factorial.apply(1L));
        assertEquals(2L, factorial.apply(2L));
        assertEquals(6L, factorial.apply(3L));
        assertEquals(24L, factorial.apply(4L));
        assertEquals(120L, factorial.apply(5L));
    }

}
