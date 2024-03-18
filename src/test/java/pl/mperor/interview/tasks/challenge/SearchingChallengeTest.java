package pl.mperor.interview.tasks.challenge;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Searching Challenge 🧾
 *
 * <p>Have the function <code>SearchingChallenge(String str)</code> take the <code>str</code>
 * parameter being passed and return the first word with the greatest number of repeated letters.
 * For example: "Today, is the greatest day ever!" should return greatest because it has 2 e's (and 2 t's)
 * and it comes before ever which also has 2 e's. If there are no words with repeating letters return -1.
 * Words will be separated by spaces.
 *
 * <p><b>Note:</b> Once your function is working, take the final output string and intersperse it character-by-character
 * with your ChallengeToken.</p>
 *
 * <p><b>Your ChallengeToken:</b> nw59voedu37</p>
 *
 * <p><b>Example:</b>
 *
 * <ul>
 *  <li><b>Input:</b> "Hello apple pie"<br/>
 *      <b>Output:</b> Hello<br/>
 *      <b>Final Output:</b>Hnewl5l9o voedu37</li>
 *
 *  <li><b>Input:</b>"No words"<br/>
 *      <b>Ouput:</b>-1<br/>
 *
 * Final Output: -nwl5o9vwoeddu37</li>
 * </ul>
 */
public class SearchingChallengeTest {

    private static final String INTERSPERSE_CHALLENGE_TOKEN = "nw59voedu37";
    private static final String CHARS_TO_REMOVE_CHALLENGE_TOKEN = "69svmglwc7";

    private Function<String, String> removeCharsExistingInToken = word -> word.chars()
            .mapToObj(i -> String.valueOf((char) i))
            .filter(s -> !CHARS_TO_REMOVE_CHALLENGE_TOKEN.contains(s))
            .collect(Collectors.joining());

    private Function<String, String> intersperseWordWithToken = word -> {
        StringBuilder sb = new StringBuilder();
        var wordChars = word.toCharArray();
        var tokenChars = INTERSPERSE_CHALLENGE_TOKEN.toCharArray();
        int commonSize = Math.min(word.length(), INTERSPERSE_CHALLENGE_TOKEN.length());
        for (int i = 0; i < commonSize; i++) {
            sb.append(wordChars[i]).append(tokenChars[i]);
        }
        sb.append(word.substring(commonSize));
        sb.append(INTERSPERSE_CHALLENGE_TOKEN.substring(commonSize));
        return sb.toString();
    };

    private Function<String, String> searchingChallenge = text -> Arrays.stream(text.split(" "))
            .map(s -> new Pair<>(s, s.length() - s.chars().distinct().count()))
            .filter(pair -> pair.right > 0)
            .max(Comparator.comparing(pair -> pair.right))
            .map(Pair::left)
            .orElse("-1");

    @Test
    public void testWithExamples() {
        assertEquals("greatest", searchingChallenge.apply("Today, is the greatest day ever!"));
        assertEquals("Hello", searchingChallenge.apply("Hello apple pie"));
        assertEquals("-1", searchingChallenge.apply("No words"));
    }

    @Test
    public void testIntersperseWithChallengeToken() {
        assertEquals("Hnewl5l9ovoedu37", intersperseWordWithToken.apply("Hello"));
        assertEquals("-n1w59voedu37", intersperseWordWithToken.apply("-1"));
    }

    @Test
    public void testRemoveCharsWithChallengeToken() {
        assertEquals("Heo", removeCharsExistingInToken.apply("Hello"));
        assertEquals("-1", removeCharsExistingInToken.apply("-1"));
    }

    public record Pair<L, R>(L left, R right) {
    }

}
