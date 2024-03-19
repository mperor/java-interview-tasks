package pl.mperor.interview.tasks.challenge;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * String Challenges 🔡
 */
public class StringChallengeTest {

    /**
     * <b>String Challenge 1</b>
     *
     * <p>This function, <code>StringChallenge(str)</code>, takes the <code>str</code> parameter being passed and returns a compressed version of the string using the Run-length encoding algorithm.
     *
     * <p>This algorithm works by taking the occurrence of each repeating character and outputting that number along with a single character of the repeating sequence. For example: "wwwggopp" would return 3w2g1o2p. The string will not contain any numbers, punctuation, or symbols.</p>
     *
     * <p><b>Examples</b>
     * <ul>
     *   <li><b>Input:</b> "aabbcde"<br/>
     *       <b>Output:</b> 2a2b1c1d1e</li>
     *   <li><b>Input:</b> "wwwbbbw"<br/>
     *       <b>Output:</b> 3w3b1w</li>
     *   <li><b>Input:</b> "wwwggopp"<br/>
     *       <b>Output:</b> 3w2g1o2p</li>
     * </ul>
     */
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
        assertEquals("2a2b1c1d1e", textProcessor.apply("aabbcde"));
        assertEquals("3w3b1w", textProcessor.apply("wwwbbbw"));
        assertEquals("3w2g1o2p", textProcessor.apply("wwwggopp"));

        String actual = Pattern.compile("(?<=(.))(?!\\1)")
                .splitAsStream("wwwegwwh")
                .map(s -> s.length() + s.substring(0, 1))
                .collect(Collectors.joining());
        assertEquals("3w1e1g2w1h", actual);
    }


    /**
     * <b>String Challenge 2</b>
     *
     * <p>This function, <code>StringChallenge(str)</code>, takes the <code>str</code> parameter being passed which will be two times (each properly formatted with a colon and am or pm) separated by a hyphen and return the total number of minutes between the two times. The time will be in a 12 hour clock format.
     *
     * <p>For example: if str is 9:00am-10:00am then the output should be 60. If str is 1:00pm - 11:00am the output should be 1320.</p>
     *
     * <p><b>Examples</b>
     * <ul>
     *   <li><b>Input:</b> "12:30pm-12:00am"<br/>
     *       <b>Output:</b> 690</li>
     *   <li><b>Input:</b> "1:23am-1:08am"<br/>
     *       <b>Output:</b> 1425</li>
     *   <li><b>Input:</b> "9:00am-10:00am"<br/>
     *       <b>Output:</b> 60</li>
     *   <li><b>Input:</b> "1:00pm-11:00am"<br/>
     *       <b>Output:</b> 1320</li>
     * </ul>
     */
    @Test
    public void calculateMinutesBetweenTimeRange() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mma");

        Function<String, Duration> timeRangeCalculator = input -> {
            LinkedList<LocalTime> dates = Arrays.stream(input.split("-"))
                    .map(String::toUpperCase)
                    .map(text -> LocalTime.parse(text, timeFormatter))
                    .collect(Collectors.toCollection(LinkedList::new));

            Duration between = Duration.between(dates.getFirst(), dates.getLast());
            return between.isNegative() ? Duration.between(LocalTime.MIDNIGHT, LocalTime.MIDNIGHT.plusSeconds(between.toSeconds())) : between;
        };

        assertEquals(Duration.ofMinutes(540), timeRangeCalculator.apply("11:00am-8:00pm"));
        assertEquals(Duration.ofMinutes(60), timeRangeCalculator.apply("9:00am-10:00am"));
        assertEquals(Duration.ofMinutes(1425), timeRangeCalculator.apply("1:23am-1:08am"));
        assertEquals(Duration.ofMinutes(1320), timeRangeCalculator.apply("1:00pm-11:00am"));
    }

    /**
     * <b>String Challenge 3</b>
     *
     * <p>Write a program to reverse a string in Java.
     */
    @Test
    public void testReverseString() {
        UnaryOperator<String> builderReverser = string -> new StringBuilder(string).reverse().toString();
        UnaryOperator<String> charsReverser = string -> {
            char[] chars = string.toCharArray();
            int left = 0;
            int right = chars.length - 1;
            while (left < right) {
                char tmp = chars[left];
                chars[left] = chars[right];
                chars[right] = tmp;
                right--;
                left++;
            }
            return String.valueOf(chars);
        };
        UnaryOperator<String> unicodeSafeReverser = string -> {
            List<String> chars = string.codePoints()
                    .mapToObj(codePoint -> String.valueOf(Character.toChars(codePoint)))
                    .collect(Collectors.toList());

            Collections.reverse(chars);
            return chars.stream().collect(Collectors.joining());
        };

        assertEquals("cba", builderReverser.apply("abc"));
        assertEquals("cba", charsReverser.apply("abc"));
        assertEquals("sdrawkcab ekil skool txet siht woh rednow I", builderReverser.apply("I wonder how this text looks like backwards"));
        assertEquals("sdrawkcab ekil skool txet siht woh rednow I", charsReverser.apply("I wonder how this text looks like backwards"));
        assertEquals("👶👧👩👵💀🤖", unicodeSafeReverser.apply("🤖💀👵👩👧👶"));
    }

    /**
     * <b>String Challenge 4</b>
     *
     * <p>Write a program to check if two strings are anagrams in Java.
     */
    @Test
    public void testAreAnagrams() {
        BiPredicate<String, String> anagramChecker = (first, second) -> sortByLetters(first).equals(sortByLetters(second));

        assertTrue(anagramChecker.test("lore", "role"));
        assertTrue(anagramChecker.test("listen", "silent"));
        assertFalse(anagramChecker.test("good", "doom"));
        assertFalse(anagramChecker.test("hello", "world"));
    }

    private static String sortByLetters(String string) {
        return string.codePoints()
                .sorted()
                .mapToObj(codePoint -> String.valueOf(Character.toChars(codePoint)))
                .collect(Collectors.joining());
    }

}
