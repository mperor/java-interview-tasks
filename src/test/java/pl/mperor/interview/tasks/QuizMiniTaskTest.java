package pl.mperor.interview.tasks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

        String actual = Pattern.compile("(?<=(.))(?!\\1)")
                .splitAsStream("wwwegwwh")
                .map(s -> s.length() + s.substring(0, 1))
                .collect(Collectors.joining());
        assertEquals("3w1e1g2w1h", actual);
    }

}
